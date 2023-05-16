package com.example.youtube.repository;

import com.example.youtube.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer> {
    @Query("from  ProfileEntity where email = ?1")
    Optional<ProfileEntity> findByEmail(String username);
}
