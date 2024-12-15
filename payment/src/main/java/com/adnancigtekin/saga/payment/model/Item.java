package com.adnancigtekin.saga.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Item {
    @JsonProperty("item_id")
    private String itemId;
    @JsonProperty("purchased_amount")
    private Integer purchasedAmount;
    private Boolean passed;
}
