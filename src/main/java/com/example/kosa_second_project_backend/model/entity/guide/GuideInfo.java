package com.example.kosa_second_project_backend.model.entity.guide;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "guide_info")
public class GuideInfo {
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
    private Integer boardRating; // 후기평점 // decimal(10,2)
    private Integer likeCount; // 좋아요수
    private Integer hits; // 조회수
    private String guideInfoState; // 'activate','deactivate','delete'

//    @CreationTimestamp//데이터를 insert할 때의 시간으로 입력됨
//    @Column(name = "writedate")
//    private LocalDate writeDate;

}
