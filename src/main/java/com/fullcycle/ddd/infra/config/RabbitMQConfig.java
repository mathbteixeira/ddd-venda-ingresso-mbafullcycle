package com.fullcycle.ddd.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String EXCHANGE_NAME = "domain-events";
    private static final String INGRESSO_VENDIDO_QUEUE = "ingresso.vendido.queue";
    private static final String INGRESSO_CANCELADO_QUEUE = "ingresso.cancelado.queue";

    @Bean
    public TopicExchange domainEventsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue ingressoVendidoQueue() {
        return new Queue(INGRESSO_VENDIDO_QUEUE, true);
    }

    @Bean
    public Queue ingressoCanceladoQueue() {
        return new Queue(INGRESSO_CANCELADO_QUEUE, true);
    }

    @Bean
    public Binding ingressoVendidoBinding(Queue ingressoVendidoQueue, TopicExchange domainEventsExchange) {
        return BindingBuilder.bind(ingressoVendidoQueue)
                .to(domainEventsExchange)
                .with("IngressoVendidoEvent");
    }

    @Bean
    public Binding ingressoCanceladoBinding(Queue ingressoCanceladoQueue, TopicExchange domainEventsExchange) {
        return BindingBuilder.bind(ingressoCanceladoQueue)
                .to(domainEventsExchange)
                .with("IngressoVendaCanceladaEvent");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}