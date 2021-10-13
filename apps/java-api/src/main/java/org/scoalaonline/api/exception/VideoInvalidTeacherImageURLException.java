package org.scoalaonline.api.exception;

/**
 * Exception class for when the Video TeacherImageURL field is null
 */

public class VideoInvalidTeacherImageURLException extends Exception {
  public VideoInvalidTeacherImageURLException() {
  }

  public VideoInvalidTeacherImageURLException( String message ) { super( message ); }
}
