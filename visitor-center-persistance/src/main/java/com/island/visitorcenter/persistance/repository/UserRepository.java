package com.island.visitorcenter.persistance.repository;

import com.island.visitorcenter.persistance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * UserRepository for CRUD operations and for which implementation is not needed
 * 
 * @author aadhav
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByEmail(String email);
}
