package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Photo;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.enumeration.PhotoType;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.UserRepository;
import com.shop4e.shop.service.FileService;
import com.shop4e.shop.service.ProfileService;
import com.shop4e.shop.util.UserUtil;
import com.shop4e.shop.web.request.ProfileRequest;
import com.shop4e.shop.web.response.ProfilePictureResponse;
import com.shop4e.shop.web.response.ProfileResponse;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tika.Tika;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileServiceImpl implements ProfileService {

  private final UserRepository userRepository;
  private final UserUtil userUtil;
  private final FileService fileService;
  private final Tika tika;

  public ProfileServiceImpl(UserRepository userRepository, UserUtil userUtil,
      FileService fileService, Tika tika) {
    this.userRepository = userRepository;
    this.userUtil = userUtil;
    this.fileService = fileService;
    this.tika = tika;
  }

  @Override
  public ProfileResponse updateProfile(ProfileRequest profileRequest, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    user.setUsername(profileRequest.getUsername());
    user.setFirstName(profileRequest.getFirstName());
    user.setLastName(profileRequest.getLastName());
    userRepository.saveAndFlush(user);

    return mapUserToProfileResponse(user);
  }

  @Override
  public ProfilePictureResponse
  updateProfilePicture(MultipartFile profilePicture, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    checkImage(profilePicture);

    if (user.getProfilePicture() != null) {
      fileService.delete(user.getProfilePicture().getIdentifier());
    }

    Photo photo = uploadProfilePicture(profilePicture, user);
    user.setProfilePicture(photo);
    userRepository.saveAndFlush(user);

    return new ProfilePictureResponse(user.getProfilePicture().getLocation());
  }

  @Override
  public ProfileResponse getProfile(Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    return mapUserToProfileResponse(user);
  }

  private Photo uploadProfilePicture(MultipartFile profilePicture, User user) {
    Map<String, String> image = fileService.upload(profilePicture);

    Photo photo = user.getProfilePicture() != null ? user.getProfilePicture() : new Photo();
    photo.setLocation(image.get("location"));
    photo.setIdentifier(image.get("identifier"));
    photo.setType(PhotoType.PROFILE);
    photo.setUser(user);

    return photo;
  }

  private ProfileResponse mapUserToProfileResponse(User user) {
    ProfileResponse profileResponse = new ProfileResponse();
    profileResponse.setUsername(user.getUsername());
    profileResponse.setFirstName(user.getFirstName());
    profileResponse.setLastName(user.getLastName());
    if (user.getProfilePicture() != null) {
      profileResponse.setPicture(user.getProfilePicture().getLocation());
    }
    profileResponse.setEmail(user.getEmail());
    profileResponse.setVerified(user.isVerified());

    return profileResponse;
  }

  private void checkImage(MultipartFile image) {
    final String regex = "^image/(jpeg|png|gif|bmp)$";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    String detect;
    try {
      detect = tika.detect(image.getBytes());
    } catch (IOException e) {
      throw new CustomException("IO Exception occurred.");
    }

    Matcher matcher = pattern.matcher(detect);
    if (!matcher.matches()) {
      throw new CustomException("Invalid image type file presented.");
    }
  }
}
