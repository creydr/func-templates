package com.example.quarkus.kafka.handler;

public interface Handler {
    String handle(String message);
}
