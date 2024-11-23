package com.example.kosa_second_project_backend.entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 생성 및 접근 제한자를 protected로 설정
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Board extends Time { // Board는 Time을 상속받기 때문에 자동으로 createDate 와 modifiedDate 필드를 가진다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스가 기본 키 값을 자동으로 생성
    private Long boardId; // 게시판 id
    private String nickname; // 닉네임
    private String password; // 게시글 수정, 삭제 시 필요한 비밀번호

    @Setter
    private String title; // 게시글 제목

    @Setter
    private String content; // 게시글 내용
    private int views; // 조회 수

    // 조회수 증가 메서드
    public void incrementViews() {
        this.views++;
    }
}
