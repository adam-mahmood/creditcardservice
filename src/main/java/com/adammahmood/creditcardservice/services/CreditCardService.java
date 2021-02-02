package com.adammahmood.creditcardservice.services;

import com.adammahmood.creditcardservice.entities.CardNetworkEntity;
import com.adammahmood.creditcardservice.entities.CreditCardEntity;
import com.adammahmood.creditcardservice.exceptions.RecordNotFoundException;
import com.adammahmood.creditcardservice.model.CardNetwork;
import com.adammahmood.creditcardservice.model.CreditCard;
import com.adammahmood.creditcardservice.repositories.CreditCardRepository;
import com.adammahmood.creditcardservice.validators.CreditCardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CreditCardService {

    private final List<CreditCardValidator> creditCardValidators;

    private  final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardService(List<CreditCardValidator> creditCardValidators, CreditCardRepository creditCardRepository) {
        this.creditCardValidators = creditCardValidators;
        this.creditCardRepository = creditCardRepository;
    }

    public CreditCardEntity validateAndAddCreditCard(CreditCard creditCard) {
        for (CreditCardValidator creditCardValidator : creditCardValidators) {
            creditCardValidator.validate(creditCard);
        }
        return createOrUpdateEmployee(creditCard);

    }

    public CreditCardEntity createOrUpdateEmployee(CreditCard creditCard) throws RecordNotFoundException
    {
        Optional<CreditCardEntity> cardEntity = Optional.ofNullable(creditCardRepository.findByCreditCardNumber(creditCard.getCreditCardNumber()));

        CreditCardEntity newEntity;

        if(cardEntity.isPresent())
        {
            newEntity = cardEntity.get();
            newEntity.setCreditLimit(creditCard.getCreditLimit());
            newEntity.setCvd(creditCard.getCvd());
            newEntity.setCreationDate(LocalDateTime.now());
            newEntity.setExpiryMonth(creditCard.getExpiryMonth());
            newEntity.setExpiryYear(creditCard.getExpiryYear());
            newEntity.setCardNetwork(toCardNetworkEntity(creditCard.getCardNetwork()));
            newEntity.setBalance(newEntity.getBalance());
            newEntity = creditCardRepository.save(newEntity);

            return newEntity;
        } else {
            newEntity = toCreditCardEntity(creditCard);
            newEntity = creditCardRepository.save(newEntity);
            return newEntity;
        }
    }

    private CreditCardEntity toCreditCardEntity(CreditCard creditCard) {
        CreditCardEntity newEntity = new CreditCardEntity();
        newEntity.setCreditLimit(creditCard.getCreditLimit());
        newEntity.setCvd(creditCard.getCvd());
        newEntity.setCreationDate(LocalDateTime.now());
        newEntity.setExpiryMonth(creditCard.getExpiryMonth());
        newEntity.setExpiryYear(creditCard.getExpiryYear());
        newEntity.setCardNetwork(toCardNetworkEntity(creditCard.getCardNetwork()));
        newEntity.setBalance(0.0);
        return newEntity;
    }

    private CardNetworkEntity toCardNetworkEntity(CardNetwork cardNetwork) {
        CardNetworkEntity cardNetworkEntity = new CardNetworkEntity();
        cardNetworkEntity.setName(cardNetwork.getName());
        return cardNetworkEntity;
    }

    public List<CreditCardEntity> getAllCreditCards()
    {
        List<CreditCardEntity> creditCardEntities = creditCardRepository.findAll();

        if(creditCardEntities.size() > 0) {
            return creditCardEntities;
        } else {
            return new ArrayList<CreditCardEntity>();
        }
    }
}
