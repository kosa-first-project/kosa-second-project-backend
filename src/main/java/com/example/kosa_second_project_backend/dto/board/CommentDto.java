package com.example.kosa_second_project_backend.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

// 클라이언트로부터 댓글을 생성하거나 수정할 때 전달되는 데이터를 캡슐화한다.
// 주로 댓글 작성 및 수정 요청을 처리할 때 사용되며 입력 데이터의 유효성을 검증하여 서버 측에서 데이터의 무결성을 유지
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class CommentDto {
    private Long commentId; // commentId 필드 추가
    @NotBlank(message = "닉네임을 입력하세요.") // 빈 문자열이나 null을 허용하지 않는다.
    @Size(min = 1, max = 20, message = "닉네임은 1자 이상, 20자 이하만 가능합니다.") // 길이 제한
//    @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임은 영문, 숫자, 한글만 가능합니다.") // 유효성 검사 시 사용
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 1, max = 100, message = "내용은 1자 이상 100자 이하만 가능합니다.")
    private String content;

    @Builder
    public CommentDto(Long commentId, String nickname, String password, String content) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.password = password;
        this.content = content;
    }
}