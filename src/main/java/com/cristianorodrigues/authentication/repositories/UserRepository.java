package com.cristianorodrigues.authentication.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cristianorodrigues.authentication.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);
    
}
