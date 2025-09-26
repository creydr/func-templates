package com.example.quarkus.kafka;

import com.example.quarkus.kafka.handler.Handler;
import com.example.quarkus.kafka.handler.Logger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class App {
    private final Handler messageHandler;

    public App() {
        messageHandler = new Logger();
    }

    @Incoming("my-channel")
    @Retry(delay = 10, maxRetries = 5)
    public void consume(ConsumerRecord<String, String> record) {
        String message = record.value();

        if (message != null) {
            messageHandler.handle(message);
        }
    }
}
