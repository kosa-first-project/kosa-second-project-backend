package com.example.kosa_second_project_backend.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class CommentEditDto {
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 3, max = 100, message = "내용은 3자 이상 100자 이하만 가능합니다.")
    private String content;

    @Builder
    public CommentEditDto(String password, String content) {
        this.password = password;
        this.content = content;
    }
}
