package com.island.visitorcenter.reservations.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.island.visitorcenter.api.model.Inventory;
import com.island.visitorcenter.reservations.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller to manage reservation of campsite
 * 
 * @author aadhav
 */
@RestController
@RequestMapping(value = "/api/v1/")
public class InventoryController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    public Inventory saveInventory(@RequestBody @Valid Inventory inventory)
                throws JsonProcessingException {
        logger.info("Received Inventory save request for date={}, campSite={}",
                inventory.getDate(),
                inventory.getCampsiteId());

        return inventoryService.saveInventory(inventory);
    }


    @RequestMapping(value = "/campsite/{campsiteId}/inventory/{fromDate}/{toDate}",  method = RequestMethod.GET)
    public List<Inventory> findByDateBetween(@PathVariable("campsiteId") long campsiteId,
                                             @PathVariable("fromDate") String fromDate,
                                             @PathVariable("toDate") String toDate)
            throws JsonProcessingException {
        logger.info("Received Inventory request for fromDate={}, toDate={}",
                fromDate,
                toDate);

        return inventoryService.findAvailableInvenotryByDateBetweenAndCampsiteId(fromDate, toDate, campsiteId);
    }

    @RequestMapping(value = "/campsite/{campsiteId}/inventory",  method = RequestMethod.GET)
    public List<Inventory> findByCampsiteId(@PathVariable("campsiteId") long campsiteId)
            throws JsonProcessingException {
        logger.info("Received Inventory request for campsiteId={} for one month", campsiteId);

        return inventoryService.
                findAvailableInvenotryByDateBetweenAndCampsiteId(null, null, campsiteId);
    }

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public List<Inventory> findByDateBetween()
            throws JsonProcessingException {

        logger.info("Received Inventory request for one month");

        return inventoryService.findByDateBetween(null, null);
    }


    @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.GET)
    public Inventory findById(@PathVariable("inventoryId") long inventoryId)
            throws JsonProcessingException {

        logger.info("Received Inventory request for Id={}", inventoryId);

        return inventoryService.findById(inventoryId);
    }

}
