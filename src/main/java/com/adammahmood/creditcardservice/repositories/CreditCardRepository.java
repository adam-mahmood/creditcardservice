package com.adammahmood.creditcardservice.repositories;

import com.adammahmood.creditcardservice.entities.CreditCardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Long> {
    @Query(value = "SELECT u FROM CreditCards u WHERE u.creditCardNumber = ?1 ORDER BY id")
    CreditCardEntity findByCreditCardNumber(String creditCardNumber);

}
