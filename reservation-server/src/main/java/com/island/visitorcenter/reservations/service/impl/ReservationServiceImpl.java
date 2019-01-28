package com.island.visitorcenter.reservations.service.impl;

import com.island.visitorcenter.api.model.ReservationRequest;
import com.island.visitorcenter.api.model.ReservationResponse;
import com.island.visitorcenter.api.model.common.EntityConstants;
import com.island.visitorcenter.async.service.EmailAsyncService;
import com.island.visitorcenter.persistance.model.Campsite;
import com.island.visitorcenter.persistance.model.Inventory;
import com.island.visitorcenter.persistance.model.Reservation;
import com.island.visitorcenter.persistance.model.User;
import com.island.visitorcenter.persistance.repository.CampsiteRepository;
import com.island.visitorcenter.persistance.repository.InventoryRepository;
import com.island.visitorcenter.persistance.repository.ReservationRepository;
import com.island.visitorcenter.persistance.repository.UserRepository;
import com.island.visitorcenter.reservations.exception.ReservationValidationException;
import com.island.visitorcenter.reservations.service.ReservationService;
import com.island.visitorcenter.reservations.service.ServiceValidations;
import com.island.visitorcenter.reservations.service.helper.ServiceHelper;
import com.island.visitorcenter.reservations.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Impl for Reservation
 *
 * @author aadhav
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EmailAsyncService emailAsyncService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampsiteRepository campsiteRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public ReservationResponse reserve(ReservationRequest reservationRequest) {
        try {
           String validationMessage = ServiceValidations.isValidRequest(reservationRequest);
            if (!StringUtils.isEmpty(validationMessage)) {
                throw new ReservationValidationException(validationMessage);
            }

            Optional<Campsite> campsiteOptional = campsiteRepository.findById(reservationRequest.getCampsiteId());
            Campsite campsite = null;
            if (campsiteOptional.isPresent()) {
                campsite = campsiteOptional.get();
            } else {
                throw new ReservationValidationException("Campsite with id="+
                        reservationRequest.getCampsiteId()+" not found.");
            }

            Reservation reservationPersistanceModel = ServiceHelper.getReservationPersistanceModel(reservationRequest);
            User user = userRepository.findByEmail(reservationRequest.getEmail());
            if (user == null) {
                user = ServiceHelper.getUserFromReservationRequest(reservationRequest);
                user = userRepository.save(user);
            }
            reservationPersistanceModel.setUser(user);
            // subtract one day from the reservation request as the deeparture date should not be reserved
            Date departureDate = reservationRequest.getReservationDepartureDate();
            Date endDateForReservation = Utils.subtractOneDayFromDate(departureDate);
            List<Inventory> inventoryList = inventoryRepository.
                    findByDateBetweenAndCampsiteIdAndStatus(reservationRequest.getReservationArrivalDate(),
                            endDateForReservation, reservationRequest.getCampsiteId(),
                            EntityConstants.InventoryStatus.AVAILABLE.name());

            //The inventory is not available and booked by someone else.
            if (inventoryList.size() < Utils.numberOfDaysBetweenDates(
                    reservationRequest.getReservationArrivalDate(),
                    endDateForReservation) + 1) {
                throw new ReservationValidationException("Campsite with id="+
                        reservationRequest.getCampsiteId()+" is already reserved by other user for one or more days " +
                        "you wish to reserve it for.");
            }

            reservationPersistanceModel.setInventoryList(inventoryList);

            Reservation reservation = reservationRepository.save(reservationPersistanceModel);

            //set reservation object in the inventoryList
            inventoryList.forEach(inventory -> {inventory.setReservation(reservation);
            inventory.setStatus(EntityConstants.InventoryStatus.RESERVED.name());});

            inventoryRepository.saveAll(reservationPersistanceModel.getInventoryList());

            ReservationResponse reservationResponse = ServiceHelper.getReservationAPIResponse(reservation, campsite);
            emailAsyncService.sendAsyncEmail("Your reservation for the dates requested is confirmed");
            return reservationResponse;
        } catch (Exception re) {
            logger.error("Error occurred while processing reservation request. ", re);
            throw re;
        }
    }

    @Override
    public void cancel(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (!reservationOptional.isPresent()) {
            throw new ReservationValidationException("No reservation found with reservationId = "+reservationId);
        }
        Reservation reservation = reservationOptional.get();
        List<Inventory> inventoryList = (List<Inventory>) reservation.getInventoryList();

        inventoryList.forEach(inventory -> {inventory.setReservation(null);
            inventory.setStatus(EntityConstants.InventoryStatus.AVAILABLE.name());});

        reservation.setStatus(EntityConstants.ReservationStatus.CANCELLED.name());

        reservationRepository.save(reservation);
        inventoryRepository.saveAll(inventoryList);
        emailAsyncService.sendAsyncEmail("Your reservation has been cancelled as requested.");

    }
}
