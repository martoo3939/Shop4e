package com.shop4e.shop.web;

import com.shop4e.shop.service.ProfileService;
import com.shop4e.shop.util.GenericResponse;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.web.request.ProfileRequest;
import com.shop4e.shop.web.response.ProfilePictureResponse;
import com.shop4e.shop.web.response.ProfileResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

  private final ProfileService profileService;

  public ProfileController(ProfileService profileService) {
    this.profileService = profileService;
  }

  @GetMapping
  public GenericResponse<ProfileResponse> getProfile(Authentication principal) {
    ProfileResponse profileResponse = profileService.getProfile(principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, profileResponse);
  }

  @PostMapping
  public GenericResponse<ProfileResponse> updateProfile(@RequestBody @Valid ProfileRequest profileRequest,
      Authentication principal) {
    ProfileResponse profileResponse = profileService.updateProfile(profileRequest, principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, profileResponse);
  }

  @PostMapping(value = "/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public GenericResponse<ProfilePictureResponse> updateProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture,
      Authentication principal) {
    ProfilePictureResponse profilePictureResponse = profileService.updateProfilePicture(
        profilePicture, principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, profilePictureResponse);
  }
}
