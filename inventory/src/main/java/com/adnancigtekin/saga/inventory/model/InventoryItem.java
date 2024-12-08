package com.adnancigtekin.saga.inventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("inventory")
@Getter
@Setter
public class InventoryItem {
    @Id
    @JsonProperty
    private String id;
    @JsonProperty("item_name")
    private String itemName;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("amount")
    private Integer amount;
}
