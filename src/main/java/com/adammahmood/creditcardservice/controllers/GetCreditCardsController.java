package com.adammahmood.creditcardservice.controllers;

import com.adammahmood.creditcardservice.api.CreditcardsApi;
import com.adammahmood.creditcardservice.entities.CreditCardEntity;
import com.adammahmood.creditcardservice.model.CardNetwork;
import com.adammahmood.creditcardservice.model.CreditCard;
import com.adammahmood.creditcardservice.services.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class GetCreditCardsController implements CreditcardsApi {


    private final CreditCardService creditCardService;

    @Autowired
    public GetCreditCardsController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }


    @Override
    public ResponseEntity<List<CreditCard>> getCreditCards(Integer skip, Integer limit) {
        List<CreditCardEntity> creditCards = creditCardService.getAllCreditCards();

        return ResponseEntity.ok(toCreditCard(creditCards));
    }

    private List<CreditCard> toCreditCard(List<CreditCardEntity> creditCards) {

       return creditCards.stream().map(this::toCreditCard).collect(Collectors.toList());
    }

    private CreditCard toCreditCard(CreditCardEntity creditCardEntity) {
        return new CreditCard()
                .creditCardNumber(creditCardEntity.getCreditCardNumber())
                .cardNetwork(new CardNetwork().name(creditCardEntity.getCardNetwork().getName()))
                .creditLimit(creditCardEntity.getCreditLimit())
                .cvd(creditCardEntity.getCvd())
                .expiryYear(creditCardEntity.getExpiryYear())
                .expiryMonth(creditCardEntity.getExpiryMonth())
                .name(creditCardEntity.getName());
    }
}
