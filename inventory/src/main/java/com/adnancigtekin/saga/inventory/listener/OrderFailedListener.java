package com.adnancigtekin.saga.inventory.listener;

import com.adnancigtekin.saga.event.inventory.ItemAllocationFailedEvent;
import com.adnancigtekin.saga.event.inventory.ItemAllocationSuccessEvent;
import com.adnancigtekin.saga.event.order.OrderCreatedEvent;
import com.adnancigtekin.saga.inventory.model.InventoryItem;
import com.adnancigtekin.saga.inventory.model.Item;
import com.adnancigtekin.saga.inventory.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderFailedListener {

    private InventoryRepository inventoryRepository;


    private final KafkaTemplate<String, ItemAllocationSuccessEvent> kafkaItemAllocationSuccessTemplate;
    private final KafkaTemplate<String, ItemAllocationFailedEvent> kafkaItemAllocationFailedTemplate;


    @KafkaListener(
            topics = "orderTopic",
            groupId = "my-group",
            containerFactory = "orderFailedListenerContainerFactory"
    )
    public void listen(OrderCreatedEvent event){
        if (Boolean.TRUE.equals(event.getDetails().getItems().get(0).getPassed())){
            for (Item item : event.getDetails().getItems()){
                // Assuming database is always stable
                InventoryItem databaseItem = inventoryRepository.findById(item.getItemId()).get();

                databaseItem.setAmount(databaseItem.getAmount() + item.getPurchasedAmount());
                inventoryRepository.save(databaseItem);
            }
            log.info("All of the items readded for : {}",event.getOrderId());
        }
        else{
            log.info("No item is processed for : {}",event.getOrderId());
        }
    }
}
