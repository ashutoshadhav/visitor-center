package com.island.visitorcenter.persistance.repository;

import com.island.visitorcenter.persistance.model.Inventory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * InventoryRepository for CRUD operations and for which implementation is not needed
 * 
 * @author aadhav
 *
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryRepositoryCustom {
    List<Inventory> findByDateBetween(Date arrivalDate, Date departureDate);

    Optional<Inventory> findByDateAndCampsiteId(Date date, Long campsiteId);

    List<Inventory> findByDateBetweenAndCampsiteIdAndStatus(Date reservationRequestStartDate,
                                                            Date reservationRequestEndDate,
                                                            long campsiteId, String status);
}
