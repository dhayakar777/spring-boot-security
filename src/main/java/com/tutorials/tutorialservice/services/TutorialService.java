package com.tutorials.tutorialservice.services;

import com.tutorials.tutorialservice.models.Tutorial;
import com.tutorials.tutorialservice.repository.TutorialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TutorialService {

    private final TutorialRepository tutorialRepository;

    public TutorialService(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    public Tutorial addTutorial(Tutorial tutorial) {
        log.info("New tutorial created successfully");
        return tutorialRepository.save(tutorial);
    }

    public List<Tutorial> getAllTutorials(String title){
        if(title == null) {
            log.info("fetching all the tutorials");
            return tutorialRepository.findAll();
        } else {
            log.info("fetching all the tutorials with title {}", title);
            return tutorialRepository.findByTitleStartingWith(title);
        }
    }
    public Page<Tutorial> getTutorialsInPaginatedFormat(int page, int size) {
        if (page == 0 && size == 0) {
            Pageable defaultPaging = PageRequest.of(0, 50, Sort.Direction.DESC);
            return tutorialRepository.findAll(defaultPaging);
        } else {
            Pageable customPaging = PageRequest.of(page, size, Sort.Direction.ASC);
            return tutorialRepository.findAll(customPaging);
        }

    }

    public Tutorial getTutorial(String id) {
        log.info("fetching the tutorial with id {}", id);
        return tutorialRepository.findById(id).get();
    }

    public Tutorial updateTutorial(String id, Tutorial tutorial) {
        Optional<Tutorial> optionalTutorial = tutorialRepository.findById(id);
        if (optionalTutorial.isPresent()) {
            log.info("Tutorial found with id {}", id);
            optionalTutorial.get().setTitle(tutorial.getTitle());
            optionalTutorial.get().setDescription(tutorial.getDescription());
            optionalTutorial.get().setPublished(tutorial.isPublished());
            optionalTutorial.get().setPublishedYear(tutorial.getPublishedYear());
            log.info("Tutorial has been updated successfully");
            tutorialRepository.save(optionalTutorial.get());
            return optionalTutorial.get();
        } else {
            log.error("Tutorial not found with the given id {}", id);
            throw new RuntimeException("Tutorial not found with the given id");
        }
    }

    public void deleteTutorial(String id) {
        Optional<Tutorial> optionalTutorial = tutorialRepository.findById(id);
        if (optionalTutorial.isPresent()) {
            log.info("Tutorial found with id {}", id);
            tutorialRepository.deleteById(id);
            log.info("Tutorial with id {} has been deleted successfully", id);
        }  else {
            log.error("Tutorial not found with the given id {}", id);
            throw new RuntimeException("Tutorial not found with the given id");
        }
    }

    public void deleteAllTutorials() {
        log.info("Deleting all the tutorials");
        tutorialRepository.deleteAll();
        log.info("All the tutorials has been deleted successfully");
    }


}
