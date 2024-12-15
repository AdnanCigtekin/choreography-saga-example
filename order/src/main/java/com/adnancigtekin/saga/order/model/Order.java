package com.adnancigtekin.saga.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("order")
@Getter
@Setter
public class Order {
    @Id
    @JsonProperty
    private String id;
    @Field("items")
    private List<Item> items;
    @Field("address")
    private Address address;
    @Field("payment_method")
    private PaymentMethod paymentMethod;
    @Field("cargo_firm_id")
    private String cargoFirmId;
    @Field("status")
    private String status;
}
