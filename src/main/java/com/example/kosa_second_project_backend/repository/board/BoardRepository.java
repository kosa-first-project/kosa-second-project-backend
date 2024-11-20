package com.example.kosa_second_project_backend.repository.board;

import com.example.kosa_second_project_backend.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
}
