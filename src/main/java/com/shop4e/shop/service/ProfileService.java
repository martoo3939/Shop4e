package com.shop4e.shop.service;

import com.shop4e.shop.web.request.ProfileRequest;
import com.shop4e.shop.web.response.ProfilePictureResponse;
import com.shop4e.shop.web.response.ProfileResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
  ProfileResponse updateProfile(ProfileRequest profileRequest, Authentication principal);

  ProfilePictureResponse updateProfilePicture(MultipartFile profilePicture, Authentication principal);

  ProfileResponse getProfile(Authentication principal);
}
