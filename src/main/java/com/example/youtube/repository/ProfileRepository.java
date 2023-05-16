package com.example.youtube.repository;

import com.example.youtube.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<ProfileEntity,Integer> {
}
