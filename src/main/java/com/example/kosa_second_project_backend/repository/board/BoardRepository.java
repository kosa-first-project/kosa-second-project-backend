package com.example.kosa_second_project_backend.repository.board;

import com.example.kosa_second_project_backend.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Repository Bean으로 인식한다.
public interface BoardRepository extends JpaRepository<Board, Long> { // <클래스 명, 기본 키>

    // 게시글 제목에 특정 키워드가 포함된 게시글들을 페이징 처리하여 조회하는 메서드
    // Page는 현재 페이지, 전체 페이지 수, 전체 요소 수 등을 포함한다.
    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
}
