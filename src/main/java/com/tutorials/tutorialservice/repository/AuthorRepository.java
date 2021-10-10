package com.tutorials.tutorialservice.repository;

import com.tutorials.tutorialservice.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String>, PagingAndSortingRepository<Author, String> {
}
