package com.example.kosa_second_project_backend.model.entity.guide;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
public class guideInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guideInfoId;
    private String userId;
    private String city;
    private String name;
    private String title;
    private String career;
    private int capacity;
    private String text;
    private int weekdayPrice;
    private Integer boardRating; // decimal(10,2)
    private Integer likeCount;
    private String guideInfoState; // 'activate','deactivate','delete'

//    @CreationTimestamp//데이터를 insert할 때의 시간으로 입력됨
//    @Column(name = "writedate")
//    private LocalDate writeDate;

}
