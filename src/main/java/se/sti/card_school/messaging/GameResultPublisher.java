package se.sti.card_school.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Publisher that sends messages about game results through RabbitMQ
@Component
public class GameResultPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public GameResultPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Sends player score to security microservice
    public void sendScore(PlayerScoreDTO score) {
        rabbitTemplate.convertAndSend("player-scores", score);
    }
}
