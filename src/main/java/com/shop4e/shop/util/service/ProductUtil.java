package com.shop4e.shop.util.service;

import com.shop4e.shop.domain.Photo;
import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.domain.enumeration.PhotoType;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.service.FileService;
import com.shop4e.shop.web.request.ProductCurrency;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductUtil {

  private final Tika tika;
  private final FileService fileService;

  public static final int TOTAL_IMAGES = 4;

  public ProductUtil(Tika tika, FileService fileService) {
    this.tika = tika;
    this.fileService = fileService;
  }

  public <T extends ProductCurrency> void checkCurrency(T request) {
    if (!CurrencyType.supportCurrency(request.getCurrency())) {
      throw new CustomException("Currency not supported!");
    }
  }

  public void checkImages(MultipartFile[] images) {
    if(images.length > TOTAL_IMAGES) {
      throw new CustomException("Total count of images exceeded.");
    }

    final String regex = "^image/(jpeg|png|gif|bmp)$";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    for(MultipartFile image : images) {
      try {
        String detect = tika.detect(image.getBytes());
        Matcher matcher = pattern.matcher(detect);
        if (!matcher.matches()) {
          throw new CustomException("Invalid image type file presented.");
        }
      } catch (IOException e) {
        throw new CustomException("IO Exception occurred.");
      }
    }
  }

  public List<Map<String, String>> handleImages(Optional<MultipartFile[]> images) {
    ArrayList<Map<String, String>> imageUrls = new ArrayList<>();
    images.ifPresent(result -> {
      for (MultipartFile image : result) {
        Map<String, String> imagUrl = fileService.upload(image);
        imageUrls.add(imagUrl);
      }
    });

    return imageUrls;
  }

  public <T extends Product> void attachImages(T product, Optional<MultipartFile[]> images) {
    List<Map<String, String>> imageProperties = handleImages(images);

    if (!imageProperties.isEmpty()) {
      List<Photo> productPhotos = imageProperties.stream().map(image -> {
        Photo photo = new Photo();
        photo.setLocation(image.get("location"));
        photo.setIdentifier(image.get("identifier"));
        photo.setType(PhotoType.PRODUCT);
        photo.setProduct(product);

        return photo;
      }).collect(Collectors.toList());

      product.setPhotos(productPhotos);
    }
  }
}
