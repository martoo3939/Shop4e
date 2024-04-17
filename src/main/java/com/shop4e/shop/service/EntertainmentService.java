package com.shop4e.shop.service;

import com.shop4e.shop.web.request.EntertainmentCreationRequest;
import com.shop4e.shop.web.request.EntertainmentUpdateRequest;
import com.shop4e.shop.web.response.EntertainmentResponse;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface EntertainmentService {

  EntertainmentResponse createEntertainment(EntertainmentCreationRequest request, Optional<MultipartFile[]> images,
      Authentication principal);

  EntertainmentResponse getEntertainment(String id);

  EntertainmentResponse updateEntertainment(String id, EntertainmentUpdateRequest request, Optional<MultipartFile[]> images,
      Authentication principal);
}
