package com.herocheer.instructor.config;

import com.herocheer.instructor.enums.BorrowStatusEnums;
import com.herocheer.instructor.enums.CacheKeyConst;
import com.herocheer.instructor.service.EquipmentBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    /**
     * 创建线程池 调整队列数 拒绝服务
     */
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(500));

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
                            equipmentBorrowService.modifyBorrowInfoByInfo(id, BorrowStatusEnums.overdue.getStatus());
                            log.info("当前时间：{}，未在规定时间内借出ID：{}，自动取消预约", LocalDateTime.now(),id);
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
