package com.example.quarkus.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class App {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(App.class);

    @Incoming("my-in-channel")
    @Retry(delay = 10, maxRetries = 5)
    @Outgoing("my-reply-channel")
    public Message<String> consume(ConsumerRecord<String, String> record) {
        String message = record.value();

        logger.info("Received: {}", message);

        // handle message and create a response
        var response = message;

        return Message.of(response);
    }
}
