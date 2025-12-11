package se.sti.card_school.messaging;

// Sends player score to security microservice through RabbitMQ
public record PlayerScoreDTO(
        int score
) {}
