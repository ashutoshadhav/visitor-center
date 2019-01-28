package com.island.visitorcenter.reservation.service;

import com.island.visitorcenter.api.config.VisitorCenterAPISpringConfig;
import com.island.visitorcenter.api.model.ReservationRequest;
import com.island.visitorcenter.api.model.ReservationResponse;
import com.island.visitorcenter.async.config.VisitorCenterAsyncConfig;
import com.island.visitorcenter.persistance.config.VisitorCenterPersistanceSpringConfig;
import com.island.visitorcenter.persistance.model.Campsite;
import com.island.visitorcenter.persistance.model.Inventory;
import com.island.visitorcenter.persistance.model.Reservation;
import com.island.visitorcenter.persistance.model.User;
import com.island.visitorcenter.persistance.repository.CampsiteRepository;
import com.island.visitorcenter.persistance.repository.InventoryRepository;
import com.island.visitorcenter.persistance.repository.ReservationRepository;
import com.island.visitorcenter.persistance.repository.UserRepository;
import com.island.visitorcenter.reservation.config.ReservationServerTestConfig;
import com.island.visitorcenter.reservations.exception.ReservationValidationException;
import com.island.visitorcenter.reservations.service.InventoryService;
import com.island.visitorcenter.reservations.service.ReservationService;
import com.island.visitorcenter.reservations.util.Utils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
        ReservationServerTestConfig.class,
        VisitorCenterAPISpringConfig.class,
        VisitorCenterPersistanceSpringConfig.class,
        VisitorCenterAsyncConfig.class})

public class ReservationServiceIntegrationTest {
    Logger logger = LoggerFactory.getLogger(ReservationServiceIntegrationTest.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    CampsiteRepository campsiteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationService reservationService;
    @Autowired
    InventoryService inventoryService;


    @Autowired
    private ExecutorService executorService;

    @Bean
    public ExecutorService executorService(@Value("50") Integer threadPoolSize) {
        return Executors.newFixedThreadPool(threadPoolSize);
    }


    @Test
    public void testConcurrency() {
        Campsite campsite = campsiteRepository.save(getNewCampSite(1L));
        Inventory inventory = inventoryRepository.save(getNewInventory(1L, campsite));
        //ReservationRequest reservationRequest = getNewReservationRequest(inventory);
        List<Callable<Object>> requests = new ArrayList<>(5);

        for (int i = 0; i < 50; i++) {
            requests.add(new ReservationTest(getNewReservationRequest(inventory), reservationService));
        }
        try {
            List<Future<Object>> listFutures =  executorService.invokeAll(requests);
            for (Future f: listFutures ){
                while (!f.isDone()) {
                    // Do nothing
                }
            }
        } catch (Exception e) {
            logger.info("Exception occurred while reserving");
        }
        //Assert only one reservation is made in DB
        List<Reservation> reservationListInDB = reservationRepository.findAll();
        Assert.assertEquals(1, reservationListInDB.size());
        //Assert Inventory is updated with  correct reservationId in DB
        Optional<Inventory> inventoryFromDbOpt = inventoryRepository.findById(inventory.getId());
        Inventory inventoryFromDb = inventoryFromDbOpt.get();

        Assert.assertEquals(reservationListInDB.get(0).getId(),
                inventoryFromDb.getReservation().getId());

        //Assert only one user is saved in DB. Other user save transactions are rolled back.

        List<User> userListInDB = userRepository.findAll();
        Assert.assertEquals(1, userListInDB.size());

        //Assert Reservation is updated with  correct userId in DB
        Assert.assertEquals(reservationListInDB.get(0).getUser().getId(),
                userListInDB.get(0).getId());
    }

    @Test(expected = ReservationValidationException.class)
    public void testSaveIfInventoryHasSameDateAndCampaignId () {
        Campsite campsite = campsiteRepository.save(getNewCampSite(1L));

        inventoryService.saveInventory(getNewAPIInventory());
        inventoryService.saveInventory(getNewAPIInventory());
    }

    private com.island.visitorcenter.api.model.Inventory getNewAPIInventory() {
        com.island.visitorcenter.api.model.Inventory inventory = new com.island.visitorcenter.api.model.Inventory();
        try {
            inventory.setStatus("AVAILABLE");
            Date date = new Date();
            date = Utils.addDaysToDate(date, 5);
            String strDate = sdf.format(date);
            inventory.setDate(sdf.parse(strDate));
            inventory.setCampsiteId(1L);
        } catch (Exception e) {
            logger.error("Error occurred while parsing date");
        }
        return inventory;
    }

    private ReservationRequest getNewReservationRequest(Inventory inventory) {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setCampsiteId(inventory.getCampsite().getId());
        reservationRequest.setFirstName("FTest1");
        reservationRequest.setFirstName("LTest1");
        reservationRequest.setEmail("ABC"+Math.random()+"@abcd.com");
        reservationRequest.setReservationArrivalDate(inventory.getDate());
        reservationRequest.setReservationDepartureDate(Utils.addDaysToDate(inventory.getDate(), 1));
        return reservationRequest;
    }

    private Inventory getNewInventory (Long id, Campsite campsite) {
        Inventory inventory = new Inventory();
        try {

            inventory.setStatus("AVAILABLE");
            inventory.setCampsite(campsite);
            Date date = new Date();
            date = Utils.addDaysToDate(date, 5);
            String strDate = sdf.format(date);
            inventory.setDate(sdf.parse(strDate));
            inventory.setId(id);
        } catch (Exception e) {
            logger.error("Error occurred while parsing date");
        }
        return inventory;
    }

    private Campsite getNewCampSite(long id) {
        Campsite campsite = new Campsite();
        campsite.setId(id);
        campsite.setName("ABC");
        campsite.setSpecialInstructions("Special");
        campsite.setStatus("ACTIVE");
        campsite.setType("GENERAL");
        return campsite;
    }

    class ReservationTest implements Callable<Object> {
        public ReservationTest (ReservationRequest reservationRequest,
                                ReservationService reservationService) {
            this.reservationRequest =  reservationRequest;
            this.reservationService =  reservationService;


        }
        ReservationService reservationService;
        ReservationRequest reservationRequest;

        @Override
        public ReservationResponse call() throws Exception {
            return reservationService.reserve(reservationRequest);
        }
    }
}
