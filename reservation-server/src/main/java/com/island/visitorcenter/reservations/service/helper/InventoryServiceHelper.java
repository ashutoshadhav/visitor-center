package com.island.visitorcenter.reservations.service.helper;

import com.island.visitorcenter.api.model.Inventory;
import com.island.visitorcenter.api.model.ReservationRequest;
import com.island.visitorcenter.api.model.common.EntityConstants;
import com.island.visitorcenter.persistance.model.Campsite;
import com.island.visitorcenter.persistance.model.Reservation;
import com.island.visitorcenter.reservations.util.Utils;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryServiceHelper {


    public static List<Inventory> convertToInventoryApiModel(List<com.island.visitorcenter.persistance.model.Inventory>
                                                                     inventoryList) {
        List<Inventory> apiInventoryList = new ArrayList<>();
        for (com.island.visitorcenter.persistance.model.Inventory inventoryDB: inventoryList) {
            Inventory inventory = new Inventory();
            inventory.setCampsiteId(inventoryDB.getCampsite().getId());
            inventory.setDate(inventoryDB.getDate());
            inventory.setStatus(inventoryDB.getStatus());
            inventory.setId(inventoryDB.getId());
            apiInventoryList.add(inventory);
        }
        return apiInventoryList;
    }

    public static com.island.visitorcenter.persistance.model.Inventory convertToDBModel(Inventory inventory) {
        Campsite campsite = new Campsite();
        campsite.setId(inventory.getCampsiteId());
        com.island.visitorcenter.persistance.model.Inventory inventoryDB =
                new com.island.visitorcenter.persistance.model.Inventory();

        inventoryDB.setCampsite(campsite);
        inventoryDB.setDate(inventory.getDate());
        EntityConstants.InventoryStatus inventoryStatus = EntityConstants.InventoryStatus.NEW;
        //Make Inventory Available for booking if the inventory date is within one month
        if (inventory.getStatus() == null) {
            if (inventory.getDate().compareTo(new Date()) < Utils.maxDaysInCurrentMonth(new Date())) {
                inventoryStatus = EntityConstants.InventoryStatus.AVAILABLE;
            }
        } else {
            inventoryStatus = EntityConstants.InventoryStatus.valueOf(inventory.getStatus());
        }
        inventoryDB.setStatus(inventoryStatus.name());
        inventoryDB.setId(inventory.getId());
        return inventoryDB;
    }
}
