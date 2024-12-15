package com.adnancigtekin.saga.inventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("inventory")
@Data
@NoArgsConstructor
public class InventoryItem {
    @Id
    @JsonProperty
    private String id;
    @Field("item_name")
    private String itemName;
    @Field("price")
    private Integer price;
    @Field("amount")
    private Integer amount;
}
