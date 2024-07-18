package com.example.redis_demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RListMultimap;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RedisExample {

    private final RedissonClient redisson;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisExample(RedissonClient redissonClient) {
        this.redisson = redissonClient;
    }

    @PostConstruct
    public void insertData() {
        RList<String> list = redisson.getList("RList");
        list.add("Elem 1");
        list.add("Elem 2");
        list.add("Elem 3");

        log.info("RedisList Elem0={}", list.get(0));

        RMap<String, Integer> map =  redisson.getMap("RMap");
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        log.info("Map value check={}",map.containsKey("a"));

        RListMultimap<String, Object> multimap = redisson.getListMultimap("tanuj.schedule");
        multimap.put("WORK","{\"slotStartTime\":\"10:30\", \"slotEndTime\":\"12:30\"}" );
        multimap.put("WIP", "{\"slotStartTime\":\"10:30\", \"slotEndTime\":\"11:00\"}");
        multimap.put("APPT-Reserved", "{\"slotStartTime\":\"11:30\", \"slotEndTime\":\"12:30\"}");

        RListMultimap<String, Object> schedule = redisson.getListMultimap("tanuj.schedule");
        log.info("WORK schedule of tanuj={}", schedule.get("WORK"));


    }
}
