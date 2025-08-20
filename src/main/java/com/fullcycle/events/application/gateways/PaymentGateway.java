package com.fullcycle.events.application.gateways;

public class PaymentGateway {

    // Construtor vazio
    public PaymentGateway() {
        // aqui você poderia injetar adaptadores de pagamento, ex: PayPal, Stripe
    }

    /**
     * Realiza um pagamento com o token do cartão e o valor informado
     *
     * @param token token do cartão
     * @param amount valor a ser cobrado
     * @throws Exception caso o pagamento falhe
     */
    public void payment(String token, double amount) throws Exception {
        // Implementação futura para gateway de pagamento
        // Por enquanto não faz nada
    }
}