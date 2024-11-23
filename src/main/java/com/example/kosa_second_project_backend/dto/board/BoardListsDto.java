package com.example.kosa_second_project_backend.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

// 게시글 목록을 클라이언트에게 제공할 때 사용된다. 효율적으로 렌더링할 수 있게한다.
// 주로 게시글 목록 조회 API의 응답으로 사용되며 각 게시글의 요약 정보를 포함한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardListsDto {
    private Long boardId; // 게시글의 고유 식별자
    private String title; // 게시글의 제목
    private Long commentCount; // 해당 게시글에 작성된 댓글의 갯수
    private String nickname; // 게시글 작성자의 닉네임
    private Integer views; // 게시글의 조회 수

    @JsonFormat(pattern = "yyyy-MM-dd") // JSON 직렬화 시 날짜 형식을 클라이언트에 일관된 형식으로 전달
    private LocalDateTime createDate;

    @Builder
    public BoardListsDto(Long boardId, String title, Long commentCount, String nickname, Integer views, LocalDateTime createDate) {
        this.boardId = boardId;
        this.title = title;
        this.commentCount = commentCount;
        this.nickname = nickname;
        this.views = views;
        this.createDate = createDate;
    }
}