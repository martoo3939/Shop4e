package com.shop4e.shop.service.impl;

import com.shop4e.shop.repository.PhotoRepository;
import com.shop4e.shop.service.FileService;
import com.shop4e.shop.service.PhotoService;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

  private final PhotoRepository photoRepository;
  private final FileService fileService;

  public PhotoServiceImpl(PhotoRepository photoRepository, FileService fileService) {
    this.photoRepository = photoRepository;
    this.fileService = fileService;
  }

  @Override
  public void deletePhoto(String identifier) {
    fileService.delete(identifier);
    photoRepository.deletePhotoByIdentifier(identifier);
  }
}
