package com.herocheer.instructor.config;

import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.EquipmentBorrow;
import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.instructor.domain.vo.EquipmentBorrowDetailsVo;
import com.herocheer.instructor.enums.BorrowStatusEnums;
import com.herocheer.instructor.enums.CacheKeyConst;
import com.herocheer.instructor.service.EquipmentBorrowDetailsService;
import com.herocheer.instructor.service.EquipmentBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDate.now;

/**
 * 延迟任务消费者
 * 器材线上借用需在规定时间内领取，否则自动取消
 *
 * @author gaorh
 * @create 2021-05-25
 */
@Slf4j
@Component
public class DelayTaskConsumer implements ApplicationRunner {
    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @Resource
    private EquipmentBorrowService equipmentBorrowService;

    @Resource
    private EquipmentBorrowDetailsService equipmentBorrowDetailsService;


    private static final  Integer COREPOOL_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 创建线程池 调整队列数 拒绝服务
     */
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(COREPOOL_COUNT, COREPOOL_COUNT, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(500));

    /**
     * Callback used to run the bean.
     * 消费延时队列的任务
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        threadPoolExecutor.execute(() -> {
            while (true) {
                Set<Long> taskIdSet = redisTemplate.opsForZSet().rangeByScore(CacheKeyConst.DELAY_AUTO_KEY, 0, System.currentTimeMillis());
                if (!CollectionUtils.isEmpty(taskIdSet)) {
                    taskIdSet.forEach(id -> {
                        long result = redisTemplate.opsForZSet().remove(CacheKeyConst.DELAY_AUTO_KEY, id);
                        // 删除成功：1
                        if (result == 1L) {
                            // 更新状态和释放库存
                            EquipmentBorrow equipmentBorrow = equipmentBorrowService.get(id);
                            if(ObjectUtils.isEmpty(equipmentBorrow)){
                                throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据失败!");
                            }

                            // 只有器材借用预约30分钟后还是待审核状态才需更新状态为已过期
                            if(equipmentBorrow.getStatus().equals(0)){
                                log.info("当前时间：{}，未在规定时间内借出ID：{}，自动取消预约", now(),id);

                                // 更新状态为已过期
                                equipmentBorrow.setStatus(BorrowStatusEnums.overdue.getStatus());
                                equipmentBorrowService.update(equipmentBorrow);

                                // 释放库存
                                List<EquipmentBorrowDetailsVo> equipmentBorrowDetailsList = equipmentBorrowDetailsService.getDetailsByBorrowId(id);
                                for (EquipmentBorrowDetailsVo equipmentBorrowDetailsVO:equipmentBorrowDetailsList){
                                    EquipmentBorrowDetails equipmentBorrowDetails = equipmentBorrowDetailsVO.voToEntity(equipmentBorrowDetailsVO);
                                    // 取消借用时设置待归还数量为0，即释放库存
                                    equipmentBorrowDetails.setUnreturnedQuantity(0);
                                    equipmentBorrowDetailsService.update(equipmentBorrowDetails);
                                }
                            }
                        }
                    });
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
