package com.example.kosa_second_project_backend.repository.guide;

import com.example.kosa_second_project_backend.entity.guide.GuideInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GuideRepository extends JpaRepository<GuideInfo, Integer> {

    @Query("SELECT g FROM GuideInfo g WHERE g.guideInfoState = ?1")
    List<GuideInfo> findByState(String state);

    public List<GuideInfo> findByTextContains(String keyword);
    public List<GuideInfo> findByUserIdContains(String keyword);

    @Query("SELECT g FROM GuideInfo g WHERE g.title LIKE %?1% OR g.text LIKE %?1%")
    List<GuideInfo> findByTitleOrTextContains(String keyword);

    void deleteById(int guideInfoId);
    void deleteByUserId(String userId);
}
