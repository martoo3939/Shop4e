package com.shop4e.shop.service;

import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  Map<String, String> upload(MultipartFile file);

  void delete(String identifier);
}
