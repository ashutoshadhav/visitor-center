package com.island.visitorcenter.persistance.repository;

import com.island.visitorcenter.persistance.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * ReservationRepository for CRUD operations and for which implementation is not needed
 * 
 * @author aadhav
 *
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
}
