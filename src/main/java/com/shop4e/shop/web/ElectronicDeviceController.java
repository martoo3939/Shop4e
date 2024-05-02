package com.shop4e.shop.web;

import com.shop4e.shop.service.ElectronicDeviceService;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.util.validator.ValidatorUtil;
import com.shop4e.shop.web.request.DeviceCreationRequest;
import com.shop4e.shop.web.request.DeviceUpdateRequest;
import com.shop4e.shop.web.response.DeviceResponse;
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
@RequestMapping("/api/v1/electronics")
@Validated
public class ElectronicDeviceController {

  private final ElectronicDeviceService electronicDeviceService;
  private final ValidatorUtil validator;

  public ElectronicDeviceController(ElectronicDeviceService electronicDeviceService,
      ValidatorUtil validator) {
    this.electronicDeviceService = electronicDeviceService;
    this.validator = validator;
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> viewDevice(@PathVariable String id) {
    DeviceResponse response = electronicDeviceService.getDevice(id);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }

  @PreAuthorize("@userUtil.isUserVerified(authentication)")
  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createDevice(
      @RequestPart(value = "data") @Valid DeviceCreationRequest request,
      @RequestPart(value = "images", required = false) Optional<List<MultipartFile>> images,
      Authentication principal,
      Errors errors
  ) {
    validator.checkForErrors(errors);

    DeviceResponse response = electronicDeviceService.createElectronicDevice(request, images, principal);

    return ResponseBuilder.buildResponse(HttpStatus.CREATED, response);
  }

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updateDevice(
      @PathVariable String id,
      @RequestPart(value = "data") @Valid DeviceUpdateRequest request,
      @RequestPart(value = "images", required = false) Optional<List<MultipartFile>> images,
      Authentication principal,
      Errors errors
  ) {
    validator.checkForErrors(errors);

    DeviceResponse response = electronicDeviceService.updateDevice(id, request, images, principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }
}
