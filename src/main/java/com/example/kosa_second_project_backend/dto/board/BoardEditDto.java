package com.example.kosa_second_project_backend.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardEditDto {
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 2, max = 30, message = "제목은 2자 이상, 30자 이하만 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 10, message = "내용은 10자 이상 입력해야 합니다.")
    private String content;

    @Builder
    public BoardEditDto(String password, String title, String content) {
        this.password = password;
        this.title = title;
        this.content = content;
    }
}
