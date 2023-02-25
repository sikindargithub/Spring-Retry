/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 25-02-2023
 * @Time : 17:46
 * @Project Name : spring-retry-demo
 */
package com.spring.retry.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


// @EnableRetry --> enables retry operation for our application
// @Retryable  --> we can use at class level or method level (retry only 3 times)
// @Recover --> we can use it whenever retrying count reached threshold
@RestController
public class MessageController {

    Logger logger = LoggerFactory.getLogger(MessageController.class);

    int count = 0;
    @GetMapping("/message")
//    @Retryable (maxAttempts = 10 ,backoff = @Backoff(delay = 2000, multiplier = 2))
    @Retryable(maxAttempts = 5)
    public String getMessage(){
        logger.info(++count+"----inside getMessage end point in MessageController----");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:9090/car",Object.class);
        return "How are you John?";
    }
    @Recover
    public String recoveryLogic(){
        return "Sorry server is not responding...";
    }
}
