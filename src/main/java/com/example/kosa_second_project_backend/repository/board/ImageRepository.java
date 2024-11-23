package com.example.kosa_second_project_backend.repository.board;

import com.example.kosa_second_project_backend.entity.board.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    // 특정 게시글에 첨부된 모든 이미지를 조회한다.
    List<Image> findAllByBoard_BoardId(Long boardId);
}