package com.adammahmood.creditcardservice.repositories;

import com.adammahmood.creditcardservice.entities.CardNetworkEntity;
import com.adammahmood.creditcardservice.entities.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CardNetworkRepository extends JpaRepository<CardNetworkEntity, Long> {
    @Query(value = "SELECT u FROM CardNetworks u WHERE u.name = ?1 ORDER BY id")
    CardNetworkEntity findByNetwork(String network);

}
