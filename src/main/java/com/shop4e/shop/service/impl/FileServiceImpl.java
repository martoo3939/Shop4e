package com.shop4e.shop.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shop4e.shop.service.FileService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

  private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

  private final Cloudinary cloudinaryConfig;
  private final Tika tika;

  public FileServiceImpl(Cloudinary cloudinaryConfig, Tika tika) {
    this.cloudinaryConfig = cloudinaryConfig;
    this.tika = tika;
  }

  @Override
  public Map<String, String> upload(MultipartFile file) {
    try {
      String fileId = UUID.randomUUID().toString();
      File uploadedFile = convertMultipartFileToFile(file, fileId);
      Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
      boolean isDeleted = uploadedFile.delete();

      logger.info("Upload result: {}, Is deleted: {}", uploadResult, isDeleted);

      Map<String, String> fileProperties = new HashMap<>();
      fileProperties.put("location", uploadResult.get("url").toString());
      fileProperties.put("identifier", uploadResult.get("public_id").toString());

      return fileProperties;
    } catch (Exception e) {
      logger.error("File upload service threw an exception: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(String identifier) {
    try {
      cloudinaryConfig.uploader().destroy(identifier, ObjectUtils.emptyMap());
    } catch (IOException e) {
      logger.error("File upload service threw an exception: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private File convertMultipartFileToFile(MultipartFile file, String fileName)
      throws IOException, MimeTypeException {
    String fileExtension = getFileExtension(file);
    File convFile = new File(fileName + fileExtension);
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();

    return convFile;
  }

  private String getFileExtension(MultipartFile file) throws IOException, MimeTypeException {
    MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
    String detect = tika.detect(file.getBytes());
    MimeType type = allTypes.forName(detect);
    String extension = type.getExtension();

    return extension;
  }
}
