package com.example.youtube.repository.report;

import com.example.youtube.entity.report.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReportRepository extends CrudRepository<ReportEntity, Integer> {
    Page<ReportEntity> findAll(Pageable pageable);



    List<ReportEntity> findByProfileId(Integer profileId);
}
