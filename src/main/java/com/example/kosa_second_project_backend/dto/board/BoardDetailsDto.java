package com.example.kosa_second_project_backend.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardDetailsDto {
    private String nickname;
    private Integer views;
    @JsonFormat(pattern = "yyyy.MM.dd. HH:mm")
    private LocalDateTime createDate;
    private String title;
    private String content;
    private List<ImageDetailsDto> images;
    private List<CommentDetailsDto> comments;

    @Builder
    public BoardDetailsDto(String nickname, Integer views, LocalDateTime createDate, String title, String content, List<ImageDetailsDto> images, List<CommentDetailsDto> comments) {
        this.nickname = nickname;
        this.views = views;
        this.createDate = createDate;
        this.title = title;
        this.content = content;
        this.images = images;
        this.comments = comments;
    }
}