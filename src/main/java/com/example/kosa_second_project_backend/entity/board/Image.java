package com.example.kosa_second_project_backend.entity.board;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter // @Setter X
@ToString
@Builder
public class Image extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId; // 기본 키, 기본 키 값을 자동 증가
    private String originName; // 원본 이미지 파일의 이름을 저장
    private String saveName; // 서버에 저장된 이미지 파일의 이름을 저장
    private String imagePath; // 이미지 파일이 저장된 경로를 저장(Local 또는 Cloud Storage)
    private Long imageSize; // 이미지 파일의 크기를 바이트 단위로 저장

    @ManyToOne // Image 엔티티가 Board 엔티티와 다대일(N:1) 관계를 가진다.
    @JoinColumn(name = "board_id") // Image 테이블에 board_id 라는 외래 키 컬럼 생성
    private Board board; // 현재 Image가 속한 게시글을 참조한다.
}