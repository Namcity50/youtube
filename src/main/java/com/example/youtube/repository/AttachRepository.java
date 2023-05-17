package com.example.youtube.repository;

import com.example.youtube.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttachRepository extends JpaRepository<AttachEntity,String> {

}
