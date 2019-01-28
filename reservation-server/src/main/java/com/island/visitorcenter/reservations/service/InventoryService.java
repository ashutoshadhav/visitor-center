package com.island.visitorcenter.reservations.service;

import com.island.visitorcenter.api.model.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing Inventory
 *  
 * @author aadhav
 *
 */
@Service
public interface InventoryService {
    List<Inventory> findByDateBetween(String startDate, String endDate);
    Inventory saveInventory(Inventory inventory);

    List<Inventory> findAvailableInvenotryByDateBetweenAndCampsiteId(String fromDate, String toDate, long campsiteId);

    Inventory findById(long inventoryId);
}
