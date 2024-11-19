package com.example.kosa_second_project_backend.repository.schedule;

import com.example.kosa_second_project_backend.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {
    List<ScheduleEntity> findAll(); // List all schedules
    List<ScheduleEntity> findByTitle(String title);
//    List<ScheduleEntity> searchAllBy(String keyword);
}
