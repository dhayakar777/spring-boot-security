package com.tutorials.tutorialservice.controllers;

import com.tutorials.tutorialservice.models.Tutorial;
import com.tutorials.tutorialservice.services.TutorialService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutorials")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TutorialController {

     private final TutorialService tutorialService;

     public TutorialController(TutorialService tutorialService) {
         this.tutorialService = tutorialService;
     }

     @PostMapping(consumes = "application/json")
     public ResponseEntity<Tutorial> create(@RequestBody Tutorial tutorial) {
         return new ResponseEntity<>(tutorialService.addTutorial(tutorial), HttpStatus.CREATED);
     }

     @GetMapping(produces = "application/json")
     public ResponseEntity<List<Tutorial>> getAll(@RequestParam(required = false) String title) {
         HttpHeaders responseHeaders = new HttpHeaders();
         responseHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
      return new ResponseEntity<>(tutorialService.getAllTutorials(title), responseHeaders, HttpStatus.OK);
     }

     @GetMapping(value = "/{tutorialId}", produces = "application/json")
     public ResponseEntity<Tutorial> get(@PathVariable("tutorialId") Long tutorialId) {
         return new ResponseEntity<>(tutorialService.getTutorial(tutorialId), HttpStatus.OK);
     }

     @PutMapping(value = "/{tutorialId}", produces = "application/json")
     public ResponseEntity<Tutorial> update(@RequestBody Tutorial tutorial, @PathVariable("tutorialId") Long tutorialId) {
         return new ResponseEntity<>(tutorialService.updateTutorial(tutorialId, tutorial), HttpStatus.OK);
     }

     @DeleteMapping(value = "/{tutorialId}")
     public ResponseEntity<?> delete(@PathVariable("tutorialId") Long tutorialId) {
          tutorialService.deleteTutorial(tutorialId);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }
     @DeleteMapping
     public ResponseEntity<?> deleteAll() {
         tutorialService.deleteAllTutorials();
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

}
