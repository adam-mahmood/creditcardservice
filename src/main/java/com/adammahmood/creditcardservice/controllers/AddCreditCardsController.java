package com.adammahmood.creditcardservice.controllers;

import com.adammahmood.creditcardservice.api.BranchIdApi;
import com.adammahmood.creditcardservice.entities.CreditCardEntity;
import com.adammahmood.creditcardservice.model.CreditCard;
import com.adammahmood.creditcardservice.model.CreditCardResponse;
import com.adammahmood.creditcardservice.services.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RestController
public class AddCreditCardsController implements BranchIdApi {

    private final CreditCardService creditCardService;

    @Autowired
    public AddCreditCardsController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Override
    public ResponseEntity<CreditCardResponse> addCreditCard(Long branchId, UUID customerId, CreditCard creditCard) {
        CreditCardResponse response = new CreditCardResponse();
        CreditCardEntity card = creditCardService.validateAndAddCreditCard(creditCard);
        log.info("Credit Card {} created",card);
        response.customerId(customerId.toString());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getCustomerId()).toUri();
        return ResponseEntity.created(location).build();
        //return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
