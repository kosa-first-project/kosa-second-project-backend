package com.example.kosa_second_project_backend.entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Board extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    private String nickname;
    private String password;
    private String title;
    private String content;
    private Integer views;

    // 조회수 증가 메서드
    public void incrementViews() {
        this.views = (this.views == null) ? 1 : this.views + 1;
    }
}
