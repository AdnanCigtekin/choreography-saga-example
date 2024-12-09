package com.adnancigtekin.saga.inventory.listener;


import com.adnancigtekin.saga.event.OrderEvent;
import com.adnancigtekin.saga.inventory.model.InventoryItem;
import com.adnancigtekin.saga.inventory.model.Item;
import com.adnancigtekin.saga.inventory.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;


    @KafkaListener(
            topics = "orderTopic",
            groupId = "order-created",
            containerFactory = "orderCreatedListenerContainerFactory"
    )
    public void listen(OrderEvent event){
        if(!event.getType().equals("order-created")){
            return;
        }
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
                    OrderEvent itemAllocationFailedEvent = new OrderEvent(event.getOrderId(), "FAILED",event.getDetails(),"item-allocation-failed");
                    kafkaTemplate.send("inventoryTopic",itemAllocationFailedEvent);
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
                OrderEvent itemAllocationFailedEvent = new OrderEvent(event.getOrderId(), "FAILED",event.getDetails(),"item-allocation-failed");
                kafkaTemplate.send("inventoryTopic",itemAllocationFailedEvent);
                break;
            }
        }

        if (transactionSuccessful){
            log.info("Transaction successful for : {}",event.getOrderId());
            inventoryRepository.saveAll(inventoryItems);
            OrderEvent itemAllocationSuccessEvent = new OrderEvent(event.getOrderId(), "PENDING",event.getDetails(),"items-allocated");
            itemAllocationSuccessEvent.getDetails().setItems(passedEventItems);
            kafkaTemplate.send("inventoryTopic",itemAllocationSuccessEvent);
        }else{
            log.error("Transaction failed for : {}",event.getOrderId());
        }
    }
}
