package org.scoalaonline.api.controller;

import org.scoalaonline.api.exception.LectureMaterialInvalidDataException;
import org.scoalaonline.api.exception.LectureMaterialNotFoundException;
import org.scoalaonline.api.model.LectureMaterial;
import org.scoalaonline.api.service.LectureMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/lecture-materials")
public class LectureMaterialController {

  @Autowired
  LectureMaterialService lectureMaterialService;


  /**
   * Sends an HTTP Response Entity with all the history entries
   * @return a Response Entity
   *         with HTTP Status OK and a list of the history entries
   */
  @GetMapping(value = {"", "/"})
  public ResponseEntity<List<LectureMaterial>> getAllLectureMaterials () {
    List<LectureMaterial> lectureMaterials = lectureMaterialService.getAll();
    return new ResponseEntity<>(lectureMaterials, HttpStatus.OK);
  }

  /**
   * Sends an HTTP Response Entity with a specific history entry and
   * Status OK or HTTP Status Not Found if there is no entry with the provided id
   * @param id
   * @return the Response Entity with a Status Code and a Body
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<LectureMaterial> getLectureMaterialById(@PathVariable("id") String id) {
    LectureMaterial lectureMaterial;
    try
    {
      lectureMaterial = lectureMaterialService.getOneById(id).orElseThrow(
        () -> new LectureMaterialNotFoundException("Method getOneById: Lecture Material not found")
      );
    } catch (LectureMaterialNotFoundException e)
    {
      throw new ResponseStatusException( HttpStatus.NOT_FOUND, "GET: Lecture Material Not Found", e );
    }
    return new ResponseEntity<>(lectureMaterial, HttpStatus.OK);
  }

  /**
   * Sends an HTTP Response Entity with the history entry that has been
   * created, along with Status Created
   * @param lectureMaterial
   * @return the Response Entity with a Status Code and a Body
   */
  @PostMapping(value = {"", "/"})
  public ResponseEntity<LectureMaterial> addLectureMaterial (@RequestBody LectureMaterial lectureMaterial) {
    LectureMaterial savedLectureMaterial;
    try {
      savedLectureMaterial = lectureMaterialService.add(lectureMaterial);
    } catch (LectureMaterialInvalidDataException e) {
      throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "POST: Lecture Material Invalid Data", e );
    }
    return new ResponseEntity<>(savedLectureMaterial, HttpStatus.CREATED);
  }

  /**
   * Sends an HTTP Response Entity with the history entry that has been
   * updated, along with the Status OK, or Status Not Found if there
   * is no entry with the provided id
   * @param id
   * @param lectureMaterial
   * @return the Response Entity with a Status Code and a Body
   */
  @PutMapping( value = "/{id}" )
  public ResponseEntity<LectureMaterial> updateLectureMaterial( @PathVariable( "id" ) String id, @RequestBody LectureMaterial lectureMaterial ) {
    LectureMaterial updatedLectureMaterial;
    try
    {
      updatedLectureMaterial = lectureMaterialService.update( id, lectureMaterial );
    } catch ( LectureMaterialNotFoundException | LectureMaterialInvalidDataException e )
    {
      throw new ResponseStatusException( HttpStatus.NOT_FOUND, "PUT: Lecture Material Not Found", e );
    }

    return new ResponseEntity<>( updatedLectureMaterial, HttpStatus.OK );
  }

  /**
   * Deletes a history entry and sends an HTTP Response Entity
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
