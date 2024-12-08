package com.adnancigtekin.saga.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentMethod {
    @JsonProperty("card_no")
    private String cardNo;
    @JsonProperty("exp_month")
    private Integer expirationMonth;
    @JsonProperty("exp_year")
    private Integer expirationYear;
    @JsonProperty("owner_name")
    private String ownerName;
    @JsonProperty("owner_surname")
    private String ownerSurname;
    @JsonProperty("cvv")
    private String cvv;
}
