package com.adnancigtekin.saga.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Address {
    @JsonProperty("first_address_line")
    private String firstAddressLine;
    @JsonProperty("second_address_line")
    private String secondAddressLine;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("zip_code")
    private String zipCode;
    private Boolean passed;
}
