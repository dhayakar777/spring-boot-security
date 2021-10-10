package com.tutorials.tutorialservice.controllers;

import com.tutorials.tutorialservice.models.Tutorial;
import com.tutorials.tutorialservice.services.TutorialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutorials")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "Tutorials Controller")
public class TutorialController {

     private final TutorialService tutorialService;

     public TutorialController(TutorialService tutorialService) {
         this.tutorialService = tutorialService;
     }

     @PostMapping(consumes = "application/json", produces = "application/json")
     @ApiOperation(value = "Create a new tutorial")
     @PreAuthorize("hasRole('ROLE_ADMIN')")
     public ResponseEntity<Tutorial> create(@ApiParam(name = "tutorial", value = "Create a new tutorial") @RequestBody Tutorial tutorial) {
         return new ResponseEntity<>(tutorialService.addTutorial(tutorial), HttpStatus.CREATED);
     }

     @GetMapping(produces = "application/json")
     @ApiOperation(value = "Retrieve all the tutorials")
     public ResponseEntity<List<Tutorial>> getAll(@ApiParam(name = "title", value = "Tutorial title") @RequestParam(required = false) String title) {
         HttpHeaders responseHeaders = new HttpHeaders();
         responseHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
      return new ResponseEntity<>(tutorialService.getAllTutorials(title), responseHeaders, HttpStatus.OK);
     }

     @GetMapping(value = "/{tutorialId}", produces = "application/json")
     @ApiOperation(value = "Get tutorial with tutorial Id")
     public ResponseEntity<Tutorial> get(@ApiParam(name = "tutorialId", value = "Tutorial id to retrieve", required = true) @PathVariable("tutorialId") String tutorialId) {
         return new ResponseEntity<>(tutorialService.getTutorial(tutorialId), HttpStatus.OK);
     }

     @PutMapping(value = "/{tutorialId}", produces = "application/json")
     @ApiOperation(value = "Update tutorial with tutorial Id")
     public ResponseEntity<Tutorial> update(@ApiParam(name = "tutorialId", value = "Tutorial id to update", required = true) @RequestBody Tutorial tutorial, @PathVariable("tutorialId") String tutorialId) {
         return new ResponseEntity<>(tutorialService.updateTutorial(tutorialId, tutorial), HttpStatus.OK);
     }

     @DeleteMapping(value = "/{tutorialId}")
     @ApiOperation(value = "Delete a tutorial with tutorial Id")
     public ResponseEntity<?> delete(@ApiParam(name = "tutorialId", value = "Tutorial id to delete", required = true) @PathVariable("tutorialId") String tutorialId) {
          tutorialService.deleteTutorial(tutorialId);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }
     @DeleteMapping
     @ApiOperation(value = "Delete all the tutorials as admin")
     public ResponseEntity<?> deleteAll() {
         tutorialService.deleteAllTutorials();
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

}
