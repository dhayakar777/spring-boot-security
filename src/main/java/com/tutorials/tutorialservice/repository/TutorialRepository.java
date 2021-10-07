package com.tutorials.tutorialservice.repository;

import com.tutorials.tutorialservice.models.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByTitleStartingWith(String title);
}
