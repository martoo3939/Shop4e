package com.shop4e.shop.service;

import com.shop4e.shop.web.request.DeviceCreationRequest;
import com.shop4e.shop.web.request.DeviceUpdateRequest;
import com.shop4e.shop.web.response.DeviceResponse;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface ElectronicDeviceService {

  DeviceResponse createElectronicDevice(DeviceCreationRequest request, Optional<MultipartFile[]> images, Authentication principal);

  DeviceResponse getDevice(String id);

  DeviceResponse updateDevice(String deviceId, DeviceUpdateRequest request, Optional<MultipartFile[]> images,
      Authentication principal);
}
