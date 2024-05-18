package com.shop4e.shop.web;

import com.shop4e.shop.service.EntertainmentService;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.util.validator.ValidatorUtil;
import com.shop4e.shop.web.request.EntertainmentCreationRequest;
import com.shop4e.shop.web.request.EntertainmentUpdateRequest;
import com.shop4e.shop.web.response.EntertainmentResponse;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/entertainment")
@Validated
public class EntertainmentController {

  private final EntertainmentService entertainmentService;
  private final ValidatorUtil validator;

  public EntertainmentController(EntertainmentService entertainmentService,
      ValidatorUtil validator) {
    this.entertainmentService = entertainmentService;
    this.validator = validator;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> viewEntertainmentProduct(@PathVariable String id) {
    EntertainmentResponse response = entertainmentService.getEntertainment(id);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }

  @PreAuthorize("@userUtil.isUserVerified(authentication)")
  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createEntertainmentProduct(
      @RequestPart(value = "data") @Valid EntertainmentCreationRequest request,
      @RequestPart(value = "images[]", required = false) Optional<List<MultipartFile>> images,
      Authentication principal,
      Errors errors
  ) {
    validator.checkForErrors(errors);

    EntertainmentResponse response = entertainmentService.createEntertainment(request, images, principal);

    return ResponseBuilder.buildResponse(HttpStatus.CREATED, response);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updateEntertainmentProduct(
      @PathVariable String id,
      @RequestPart(value = "data") @Valid EntertainmentUpdateRequest request,
      @RequestPart(value = "images[]", required = false) Optional<List<MultipartFile>> images,
      Authentication principal,
      Errors errors
  ) {
    validator.checkForErrors(errors);

    EntertainmentResponse response = entertainmentService.updateEntertainment(id, request, images,
        principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }
}
