package com.shop4e.shop.web;

import com.shop4e.shop.service.PhotoService;
import com.shop4e.shop.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/photo")
public class PhotoController {

  private final PhotoService photoService;

  public PhotoController(PhotoService photoService) {
    this.photoService = photoService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePhoto(@PathVariable String id) {
    photoService.deletePhoto(id);

    return ResponseBuilder.buildResponse(HttpStatus.NO_CONTENT);
  }
}
