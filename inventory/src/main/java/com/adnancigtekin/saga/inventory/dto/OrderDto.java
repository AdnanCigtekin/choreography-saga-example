package com.adnancigtekin.saga.inventory.dto;

import com.adnancigtekin.saga.inventory.model.Address;
import com.adnancigtekin.saga.inventory.model.Item;
import com.adnancigtekin.saga.inventory.model.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
public class OrderDto {
    @JsonProperty("items")
    private List<Item> items;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;
    @JsonProperty("cargo_firm_id")
    private Integer cargoFirmId;
}
