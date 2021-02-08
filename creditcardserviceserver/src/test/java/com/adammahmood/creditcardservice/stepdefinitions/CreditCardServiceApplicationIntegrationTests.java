package com.adammahmood.creditcardservice.stepdefinitions;

import com.adammahmood.creditcardservice.CreditCardServiceApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@CucumberContextConfiguration
@SpringBootTest(classes = CreditCardServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreditCardServiceApplicationIntegrationTests {
    public static final String HOST = "localhost";
    public static final String PORT = "8080";
    public static final String HTTP_LOCALHOST_8080 = "http://" + HOST + ":" + PORT ;

    @LocalServerPort
    protected int port;

}
