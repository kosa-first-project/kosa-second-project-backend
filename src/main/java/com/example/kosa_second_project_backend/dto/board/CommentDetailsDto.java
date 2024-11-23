package com.example.kosa_second_project_backend.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class CommentDetailsDto {
    private Long commentId; // commentId 필드 추가
    private String nickname;
    private String password;
    private String content;
    private Integer hearts;

    @JsonFormat(pattern = "yyyy.MM.dd. HH:mm")
    private LocalDateTime createDate;

    @Builder
    public CommentDetailsDto(Long commentId, String password, String nickname, String content, Integer hearts, LocalDateTime createDate) {
        this.commentId = commentId;
        this.password = password;
        this.nickname = nickname;
        this.content = content;
        this.hearts = hearts;
        this.createDate = createDate;
    }
}
