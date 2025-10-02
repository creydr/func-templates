package com.example.quarkus.kafka;

import com.example.quarkus.kafka.handler.Handler;
import com.example.quarkus.kafka.handler.MyHandler;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class App {
    private final Handler messageHandler;

    public App() {
        messageHandler = new MyHandler();
    }

    @Incoming("my-channel")
    @Retry(delay = 10, maxRetries = 5)
    @Outgoing("my-reply-channel")
    public Message<String> consume(ConsumerRecord<String, String> record) {
        String message = record.value();

        var resp = messageHandler.handle(message);
        return Message.of(resp);
    }
}
