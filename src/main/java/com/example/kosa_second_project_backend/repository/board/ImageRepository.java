package com.example.kosa_second_project_backend.repository.board;

import com.example.kosa_second_project_backend.entity.board.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByBoard_BoardId(Long boardId);
}