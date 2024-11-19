package com.example.kosa_second_project_backend.model.entity.guide;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
public class GuideUnavailableTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guideUnavailableTimeId;
    private int guideInfoId;
    private int travelerInfoId;
    private LocalDate unavailableStartDate;
    private LocalDate unavailableEndDate;

//    @CreationTimestamp//데이터를 insert할 때의 시간으로 입력됨
//    @Column(name = "writedate")
//    private LocalDate writeDate;

}
