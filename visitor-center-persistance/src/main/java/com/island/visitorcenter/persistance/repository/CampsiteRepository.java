package com.island.visitorcenter.persistance.repository;

import com.island.visitorcenter.persistance.model.Campsite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * CampsiteRepository for CRUD operations and for which implementation is not needed
 * 
 * @author aadhav
 *
 */
@Repository
public interface CampsiteRepository extends JpaRepository<Campsite, Long>, CampsiteRepositoryCustom {
}
