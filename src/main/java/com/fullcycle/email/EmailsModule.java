package com.fullcycle.email;

import com.fullcycle.email.controller.EmailsController;
import com.fullcycle.email.service.ConsumerService;
import com.fullcycle.email.service.EmailsService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class EmailsModule {

    @Bean
    public ConnectionFactory connectionFactory() {
        // Configuração do RabbitMQ (equivalente ao forRoot do NestJS)
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public EmailsService emailsService() {
        return new EmailsService();
    }

    @Bean
    public ConsumerService consumerService() {
        return new ConsumerService();
    }

    @Bean
    public EmailsController emailsController(EmailsService emailsService) {
        return new EmailsController(emailsService);
    }
}