package ru.lid.progertrainer.service.listeners;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.lid.progertrainer.dto.Topic1Dto;
import ru.lid.progertrainer.dto.Topic2Dto;
import ru.lid.progertrainer.dto.Topic3Dto;

@Component
@ConditionalOnProperty(value = "kafka.proger-trainer.enabled", havingValue = "true")
public class GlobalListener {

    @KafkaListener(topics = "topic1", groupId = "group_id", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void topic1Listener(@Payload Topic1Dto topic1Dto) {

    }


    @KafkaListener(topics = "topic2", groupId = "group_id", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void topic2Listener(Topic2Dto topic2Dto) {

    }

    @KafkaListener(topics = {"topic3"}, groupId = "group_id", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void topic3Listener(Topic3Dto topic3Dto) {

    }
}

