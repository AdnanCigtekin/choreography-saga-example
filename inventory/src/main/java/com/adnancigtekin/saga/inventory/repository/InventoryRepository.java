package com.adnancigtekin.saga.inventory.repository;

import com.adnancigtekin.saga.inventory.model.InventoryItem;
import com.adnancigtekin.saga.inventory.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<InventoryItem, String> {
}
