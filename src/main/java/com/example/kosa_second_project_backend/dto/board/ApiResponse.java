package com.example.kosa_second_project_backend.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success; // 요청 성공 여부
    private String message; // 응답 메시지
    private T data; // 응답 데이터(제네릭 타입)
}
