package com.example.kosa_second_project_backend.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

// 클라이언트로부터 새로운 게시글을 생성하거나 기존 게시글을 수정할 때 필요한 데이터를 전달받기 위해 사용된다.
// 주로 게시글 작성 및 수정 요청을 처리할 때 사용된다.
// 입력 데이터의 유효성을 검증하여 서버 측에서 데이터의 무결성을 유지하는 데 도움을 준다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 생성, 접근 제한자를 protected로 설정
@Getter
@Setter
@ToString
public class BoardDto {
    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(min = 1, max = 20, message = "닉네임은 1자 이상, 20자 이하만 가능합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 1, max = 30, message = "제목은 1자 이상, 30자 이하만 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 5, message = "내용은 5자 이상 입력해야 합니다.")
    private String content;

    @Builder
    public BoardDto(String nickname, String password, String title, String content) {
        this.nickname = nickname;
        this.password = password;
        this.title = title;
        this.content = content;
    }
}


