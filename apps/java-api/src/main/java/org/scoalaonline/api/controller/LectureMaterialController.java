package org.scoalaonline.api.controller;

import org.scoalaonline.api.exception.lectureMaterial.LectureMaterialInvalidDocumentException;
import org.scoalaonline.api.exception.lectureMaterial.LectureMaterialNotFoundException;
import org.scoalaonline.api.model.LectureMaterial;
import org.scoalaonline.api.service.LectureMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
  GET:	"/lecture-materials"	retrieves all the entries
  GET:	"/lecture-materials/{id}"	retrieves the entry with the provided id
  POST: "/lecture-materials"	creates a new entry
  PATCH:	"/lecture-materials/{id}	edits the entry with the provided id
  DELETE:	"/lecture-materials/{id}	deletes the entry with the provided id
 */
@CrossOrigin
@RestController
@RequestMapping("/lecture-materials")
public class LectureMaterialController {
  @Autowired
  LectureMaterialService lectureMaterialService;

  /**
   * Sends an HTTP Response Entity with all the lecture material entries
   * @return a Response Entity
   *         with HTTP Status OK and a list of the lecture material entries
   */
  @GetMapping(value = {"", "/"})
  public ResponseEntity<List<LectureMaterial>> getAllLectureMaterials () {
    List<LectureMaterial> lectureMaterials = lectureMaterialService.getAll();
    return new ResponseEntity<>(lectureMaterials, HttpStatus.OK);
  }

  /**
   * Sends an HTTP Response Entity with a specific lecture material entry and
   * Status OK or HTTP Status Not Found if there is no entry with the provided id
   * @param id
   * @return the Response Entity with a Status Code and a Body
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<LectureMaterial> getLectureMaterialById(@PathVariable("id") String id) {
    LectureMaterial lectureMaterial;
    try
    {
      lectureMaterial = lectureMaterialService.getOneById(id);
    } catch (LectureMaterialNotFoundException e)
    {
      throw new ResponseStatusException( HttpStatus.NOT_FOUND, "GET: Lecture Material Not Found", e );
    }
    return new ResponseEntity<>(lectureMaterial, HttpStatus.OK);
  }

  /**
   * Sends HTTP status Response Entity with the LectureMaterial entry that has been created.
   * Sends HTTP status Invalid Value if the LectureMaterial to be posted is invalid.
   * @param lectureMaterial the LectureMaterial to be added in the db.
   * @return the Response Entity with a Status Code and a body.
   */
  @PostMapping(value = {"", "/"})
  public ResponseEntity<LectureMaterial> addLectureMaterial (@RequestBody LectureMaterial lectureMaterial) {
    LectureMaterial savedLectureMaterial;
    try {
      savedLectureMaterial = lectureMaterialService.add(lectureMaterial);
    } catch (LectureMaterialInvalidDocumentException e) {
      throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "POST: Lecture Material Invalid Document", e );
    }
    return new ResponseEntity<>(savedLectureMaterial, HttpStatus.CREATED);
  }

  /**
   * Sends HTTP status Response Entity with the LectureMaterial entry that has been updated.
   * Sends HTTP status Not Found if the LectureMaterial cannot be found.
   * Sends HTTP status Invalid Value if the LectureMaterial to be posted is invalid.
   * @param id the id of the LectureMaterial to be updated.
   * @param lectureMaterial the LectureMaterial to be updated.
   * @return the Response Entity with a Status Code with a body.
   */
  @PatchMapping( value = "/{id}" )
  public ResponseEntity<LectureMaterial> updateLectureMaterial( @PathVariable( "id" ) String id, @RequestBody LectureMaterial lectureMaterial ) {
    LectureMaterial updatedLectureMaterial;
    try
    {
      updatedLectureMaterial = lectureMaterialService.update( id, lectureMaterial );
    } catch ( LectureMaterialNotFoundException e )
    {
      throw new ResponseStatusException( HttpStatus.NOT_FOUND, "PATCH: Lecture Material Not Found", e );
    } catch ( LectureMaterialInvalidDocumentException e){
      throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "PATCH: Lecture Material Invalid Document", e );
    }

    return new ResponseEntity<>( updatedLectureMaterial, HttpStatus.OK );
  }

  /**
   * Deletes a lecture material entry and sends an HTTP Response Entity
   * with the Status OK or Not Found if there is no entry with the provided id
   * @param id
   * @return a Response Entity with Status OK
   */
  @DeleteMapping( value = "/{id}" )
  public ResponseEntity<HttpStatus> deleteLectureMaterial( @PathVariable( "id" ) String id ) {
    try
    {
      lectureMaterialService.delete( id );
    } catch ( LectureMaterialNotFoundException e )
    {
      throw new ResponseStatusException( HttpStatus.NOT_FOUND, "DELETE: Lecture Material Not Found", e );
    }
    return new ResponseEntity<>( HttpStatus.OK );
  }

}
