package com.railway.ivc.ivcschemabot.repository;

import com.railway.ivc.ivcschemabot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for repository, created to work with database.
 */
public interface UsersRepository extends JpaRepository<User, Long> {

    /**
     * Method to get user from database, using user phone number.
     * <p>
     * @param number user phone number.
     * @return User object.
     */
    User findByUserNumber(String number);
}
