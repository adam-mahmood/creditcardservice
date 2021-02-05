package com.adammahmood.creditcardservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity(name = "CreditCards")
@Table(name="CreditCards")
public class CreditCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


    private String creditCardNumber;


    private String creditLimit ;


    private String cvd;


    private String expiryMonth;


    private String expiryYear;

    private Double balance;

    @Column(name = "creationDate", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "card__network_id", referencedColumnName = "id")
    private CardNetworkEntity cardNetwork;

}
