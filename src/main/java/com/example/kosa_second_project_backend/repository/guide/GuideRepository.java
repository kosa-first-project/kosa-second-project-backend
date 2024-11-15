package com.example.kosa_second_project_backend.repository.guide;

import com.example.kosa_second_project_backend.model.entity.guide.GuideInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<GuideInfo, Integer> {

    public List<GuideInfo> findByTextContains(String keyword);
    public List<GuideInfo> findByUserIdContains(String keyword);


    // 제목과 내용에서 모두 검색
    @Query("SELECT g FROM GuideInfo g WHERE g.title LIKE %?1% OR g.text LIKE %?1%")
    List<GuideInfo> findByTitleOrTextContains(String keyword);


    // ID로 삭제하는 메소드
    void deleteById(int guideInfoId);

    // userId로 삭제하는 메소드
    void deleteByUserId(String userId);
}
