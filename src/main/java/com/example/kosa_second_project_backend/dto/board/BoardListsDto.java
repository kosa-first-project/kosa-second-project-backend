package com.example.kosa_second_project_backend.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardListsDto {
    private Long boardId;
    private String title;
    private Long commentCount;
    private String nickname;
    private Integer views;
    @JsonFormat(pattern = "yyyy-MM-dd")
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