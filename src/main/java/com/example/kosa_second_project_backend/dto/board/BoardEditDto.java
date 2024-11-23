package com.example.kosa_second_project_backend.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

// 게시글 수정 요청을 처리할 때 사용되며, 사용자가 입력한 비밀번호와 새로운 제목, 내용을 검증하여
// 서버 측에서 게시글을 업데이트하는 데 도움을 준다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BoardEditDto {
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 1, max = 30, message = "제목은 1자 이상, 30자 이하만 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 1, message = "내용은 1자 이상 입력해야 합니다.")
    private String content;

    @Builder
    public BoardEditDto(String password, String title, String content) {
        this.password = password;
        this.title = title;
        this.content = content;
    }
}
