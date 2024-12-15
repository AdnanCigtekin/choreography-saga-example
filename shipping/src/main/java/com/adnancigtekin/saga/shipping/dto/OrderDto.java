package com.adnancigtekin.saga.shipping.dto;

import com.adnancigtekin.saga.shipping.model.Address;
import com.adnancigtekin.saga.shipping.model.Item;
import com.adnancigtekin.saga.shipping.model.PaymentMethod;
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
    private String cargoFirmId;
}
