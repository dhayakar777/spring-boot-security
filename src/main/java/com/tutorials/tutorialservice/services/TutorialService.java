package com.tutorials.tutorialservice.services;

import com.tutorials.tutorialservice.models.Tutorial;
import com.tutorials.tutorialservice.repository.TutorialRepository;
import lombok.extern.slf4j.Slf4j;
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

    public Tutorial getTutorial(Long id) {
        log.info("fetching the tutorial with id {}", id);
        return tutorialRepository.findById(id).get();
    }

    public Tutorial updateTutorial(Long id, Tutorial tutorial) {
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

    public void deleteTutorial(Long id) {
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
