package com.adammahmood.creditcardservice.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "CardNetworks")
@Table(name="CardNetworks")
public class CardNetworkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

//    @OneToOne(mappedBy = "cardNetwork")
//    private CreditCardEntity creditCard;
}
