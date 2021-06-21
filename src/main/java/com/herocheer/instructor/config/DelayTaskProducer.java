package com.herocheer.instructor.config;

import com.herocheer.instructor.enums.CacheKeyConst;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 延迟任务生产者
 * 器材线上借用需在规定时间内领取，否则取消
 *
 * @author gaorh
 * @create 2021-05-25
 */
@Component
public class DelayTaskProducer {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;


    /**
     *
     * 1、借用时段外借用的，释放库存时间按照借用开始时间后半个小时进行释放。
     * 2、借用时段内借用的，释放库存时间按照当前时间+半小时后释放。
     **/
    public void produce(Long taskId, long exeTime) {
        redisTemplate.opsForZSet().add(CacheKeyConst.DELAY_AUTO_KEY,taskId,exeTime);
    }
}
