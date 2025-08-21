package com.fullcycle.email;

import com.fullcycle.email.controller.EmailsController;
import com.fullcycle.email.service.EmailsService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailsControllerTest {

    @Test
    void shouldReturnHelloWorld() {
        EmailsService emailsService = new EmailsService();
        EmailsController controller = new EmailsController(emailsService);

        String response = controller.getHello();

        assertThat(response).isEqualTo("Hello World!");
    }
}