package com.bhushan.url_shortner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.bhushan.url_shortner.repositoires.UrlRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationWarmup implements ApplicationRunner {

    private final UrlRepository urlRepository;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) {

        long start = System.currentTimeMillis();

        urlRepository.count();
        redisTemplate.opsForValue().get("warmup");

        log.info("Warmup completed in {} ms",
                System.currentTimeMillis() - start);
    }
}