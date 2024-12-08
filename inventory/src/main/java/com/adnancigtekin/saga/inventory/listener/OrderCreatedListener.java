package com.adnancigtekin.saga.inventory.listener;


import com.adnancigtekin.saga.event.inventory.ItemAllocationFailedEvent;
import com.adnancigtekin.saga.event.inventory.ItemAllocationSuccessEvent;
import com.adnancigtekin.saga.event.order.OrderCreatedEvent;
import com.adnancigtekin.saga.inventory.model.InventoryItem;
import com.adnancigtekin.saga.inventory.model.Item;
import com.adnancigtekin.saga.inventory.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class OrderCreatedListener {

    private InventoryRepository inventoryRepository;


    private final KafkaTemplate<String, ItemAllocationSuccessEvent> kafkaItemAllocationSuccessTemplate;
    private final KafkaTemplate<String, ItemAllocationFailedEvent> kafkaItemAllocationFailedTemplate;


    @KafkaListener(
            topics = "orderTopic",
            groupId = "my-group",
            containerFactory = "orderCreatedListenerContainerFactory"
    )
    public void listen(OrderCreatedEvent event){
        log.info("Received Following Order Created Event: {}",event);
        List<Item> orderedItems = event.getDetails().getItems();



        Boolean transactionSuccessful = true;
        List<InventoryItem> inventoryItems = new ArrayList<>();
        List<Item> passedEventItems = new ArrayList<>();
        for (Item item : orderedItems){
            log.info("Checking for following item : {}",item);
            InventoryItem databaseItem = inventoryRepository.findById(item.getItemId()).orElseGet(() -> null);

            if(databaseItem != null){
                Integer itemAmount = databaseItem.getAmount();
                itemAmount -= item.getPurchasedAmount();
                if(itemAmount < 0){
                    transactionSuccessful = false;
                    ItemAllocationFailedEvent itemAllocationFailedEvent = new ItemAllocationFailedEvent(event.getOrderId(), "FAILED",event.getDetails());
                    kafkaItemAllocationFailedTemplate.send("inventoryTopic",itemAllocationFailedEvent);
                    break;
                }
                else{
                    databaseItem.setAmount(itemAmount);
                    inventoryItems.add(databaseItem);
                    item.setPassed(true);
                    passedEventItems.add(item);
                }
            }
            else{
                transactionSuccessful = false;
                ItemAllocationFailedEvent itemAllocationFailedEvent = new ItemAllocationFailedEvent(event.getOrderId(), "FAILED",event.getDetails());
                kafkaItemAllocationFailedTemplate.send("inventoryTopic",itemAllocationFailedEvent);
                break;
            }
        }

        if (transactionSuccessful){
            log.info("Transaction successful for : {}",event.getOrderId());
            inventoryRepository.saveAll(inventoryItems);
            ItemAllocationSuccessEvent itemAllocationSuccessEvent = new ItemAllocationSuccessEvent(event.getOrderId(), "PENDING",event.getDetails());
            itemAllocationSuccessEvent.getDetails().setItems(passedEventItems);
            kafkaItemAllocationSuccessTemplate.send("inventoryTopic",itemAllocationSuccessEvent);
        }else{
            log.error("Transaction failed for : {}",event.getOrderId());
        }
    }
}
