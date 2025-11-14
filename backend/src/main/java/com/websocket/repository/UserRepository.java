package com.websocket.repository;

import java.util.List;
import java.util.Optional;

import com.websocket.model.User;
import com.websocket.model.Status;
import com.websocket.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long>{
    List<UserData> findAllByStatus(Status status);
    Optional<UserData> findByEmail(String email);
    Optional<UserData> findByUsername(String username);
    Optional<UserData> findByVerificationCode(String verificationCode);
}
