package com.adnancigtekin.saga.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("order")
@Getter
@Setter
public class Order {
    @Id
    @JsonProperty
    private String id;
    @JsonProperty("items")
    private List<Item> items;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;
    @JsonProperty("cargo_firm_id")
    private Integer cargoFirmId;
    @JsonProperty("status")
    private String status;
}
