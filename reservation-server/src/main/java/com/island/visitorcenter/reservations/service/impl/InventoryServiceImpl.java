package com.island.visitorcenter.reservations.service.impl;

import com.island.visitorcenter.api.model.Inventory;
import com.island.visitorcenter.api.model.common.EntityConstants;
import com.island.visitorcenter.persistance.model.Campsite;
import com.island.visitorcenter.persistance.repository.CampsiteRepository;
import com.island.visitorcenter.persistance.repository.InventoryRepository;
import com.island.visitorcenter.reservations.exception.ReservationValidationException;
import com.island.visitorcenter.reservations.service.InventoryService;
import com.island.visitorcenter.reservations.service.ServiceValidations;
import com.island.visitorcenter.reservations.service.helper.InventoryServiceHelper;
import com.island.visitorcenter.reservations.service.helper.ServiceHelper;
import com.island.visitorcenter.reservations.util.Utils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Impl for Reservation
 *
 * @author aadhav
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CampsiteRepository campsiteRepository;

    @Override
    public List<Inventory> findByDateBetween(String startDate, String endDate) {
        Date startDateOb = null;
        Date endDateOb = null;
        try {
                //if there is no inout date in the request find thew inventory for one month
                if (startDate == null && endDate == null) {
                    startDateOb = new Date();
                    DateTime dateTime = new DateTime(startDateOb);
                    dateTime = dateTime.plusMonths(1);
                    endDateOb = dateTime.toDate();
                    df.format(startDateOb);
                    df.format(endDateOb);
                } else {
                    startDateOb = df.parse(startDate);
                    endDateOb = df.parse(endDate);
                    if (Utils.numberOfDaysBetweenDates(startDateOb, endDateOb) >
                            Utils.maxDaysInCurrentMonth(startDateOb)) {
                        throw new ReservationValidationException("Only one month of inventory can be looked up " +
                                "at any time. Please provide date range smaller than one month.");
                    }
                }
        } catch (ParseException ex) {
            logger.error("Error occurred while parsing date for inventory request. startDate={}," +
                    "endDate={}", startDate, endDate);
            throw new ReservationValidationException("Error occurred while parsing date for inventory request.");
        }

        List<com.island.visitorcenter.persistance.model.Inventory>  inventoryList =
                inventoryRepository.findByDateBetween(startDateOb, endDateOb);

        return (InventoryServiceHelper.convertToInventoryApiModel (inventoryList));

    }


    @Override
    public List<Inventory> findAvailableInvenotryByDateBetweenAndCampsiteId(String startDate,
                                                                            String endDate, long campsiteId) {
        Date startDateOb = null;
        Date endDateOb = null;
        try {
            //if there is no inout date in the request find thew inventory for one month
            if (startDate == null && endDate == null) {
                startDateOb = new Date();
                DateTime dateTime = new DateTime(startDateOb);
                dateTime = dateTime.plusMonths(1);
                endDateOb = dateTime.toDate();
                df.format(startDateOb);
                df.format(endDateOb);
            } else {
                startDateOb = df.parse(startDate);
                endDateOb = df.parse(endDate);
                if (Utils.numberOfDaysBetweenDates(startDateOb, endDateOb) >
                        Utils.maxDaysInCurrentMonth(startDateOb)) {
                    throw new ReservationValidationException("Only one month of inventory can be looked up " +
                            "at any time. Please provide date range smaller than one month.");
                }
            }
        } catch (ParseException ex) {
            logger.error("Error occurred while parsing date for inventory request. startDate={}," +
                    "endDate={}", startDate, endDate);
            throw new ReservationValidationException("Error occurred while parsing date for inventory request.");
        }

        List<com.island.visitorcenter.persistance.model.Inventory>  inventoryList =
                inventoryRepository.findByDateBetweenAndCampsiteIdAndStatus(startDateOb, endDateOb, campsiteId,
                        EntityConstants.InventoryStatus.AVAILABLE.name());

        return (InventoryServiceHelper.convertToInventoryApiModel (inventoryList));

    }

    @Override
    public Inventory findById(long inventoryId) {
        Optional<com.island.visitorcenter.persistance.model.Inventory> inventoryDB =
                inventoryRepository.findById(inventoryId);
        if(inventoryDB.isPresent()) {
            return ServiceHelper.getInventoryAPIModel(inventoryDB.get());
        }
        return null;
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        String validationMessage = ServiceValidations.isValidRequest(inventory);
        if (!StringUtils.isEmpty(validationMessage)) {
            throw new ReservationValidationException(validationMessage);
        }

        Optional<Campsite> campsiteOptional = campsiteRepository.findById(inventory.getCampsiteId());
        Campsite campsite = null;
        if (campsiteOptional.isPresent()) {
            campsite = campsiteOptional.get();
        } else {
            throw new ReservationValidationException("Campsite with id="+
                    inventory.getCampsiteId()+" not found.");
        }

        Optional<com.island.visitorcenter.persistance.model.Inventory> inventoryOptional =
                inventoryRepository.findByDateAndCampsiteId(inventory.getDate(),
                inventory.getCampsiteId());

        if (inventoryOptional.isPresent()) {
            throw new ReservationValidationException("Inventory for Date="+inventory.getDate()+" And campSiteId="+
                    inventory.getCampsiteId()+" is already added.");
        }

        com.island.visitorcenter.persistance.model.Inventory inventoryDb =
                inventoryRepository.save(InventoryServiceHelper.convertToDBModel(inventory));
        inventory.setId(inventoryDb.getId());
        return inventory;
    }
}
