package com.example.quarkus.kafka.handler;

import org.slf4j.LoggerFactory;

public class MyHandler implements Handler{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyHandler.class);

    @Override
    public String handle(String message) {
        // adding a bit of a lag
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (message != null && !message.isEmpty()) {
            logger.info("Received: {}", message);
            return message.toUpperCase();
        } else {
            logger.info("Received empty message");
            return null;
        }
    }
}
