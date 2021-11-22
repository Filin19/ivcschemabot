package com.railwai.ivc.ivcschemabot.repository;

import com.railwai.ivc.ivcschemabot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
    User findByUserNumber(String number);
}
