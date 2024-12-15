package com.adnancigtekin.saga.shipping.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("cargo_firm")
@Getter
@Setter
@NoArgsConstructor
public class CargoFirm {

    @Id
    @JsonProperty
    private String id;
    @Field("firm_name")
    private String firmName;
}
