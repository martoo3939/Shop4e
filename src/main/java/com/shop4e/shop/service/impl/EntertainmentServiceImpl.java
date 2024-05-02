package com.shop4e.shop.service.impl;

import static com.shop4e.shop.util.service.ProductUtil.TOTAL_IMAGES;

import com.shop4e.shop.domain.Category;
import com.shop4e.shop.domain.Entertainment;
import com.shop4e.shop.domain.Genre;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.EntertainmentRepository;
import com.shop4e.shop.service.CategoryService;
import com.shop4e.shop.service.EntertainmentService;
import com.shop4e.shop.util.UserUtil;
import com.shop4e.shop.util.mapper.ProductMapper;
import com.shop4e.shop.util.service.ProductUtil;
import com.shop4e.shop.web.request.EntertainmentCreationRequest;
import com.shop4e.shop.web.request.EntertainmentType;
import com.shop4e.shop.web.request.EntertainmentUpdateRequest;
import com.shop4e.shop.web.response.EntertainmentResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EntertainmentServiceImpl implements EntertainmentService {

  private final EntertainmentRepository entertainmentRepository;
  private final CategoryService categoryService;
  private final UserUtil userUtil;
  private final ProductUtil productUtil;
  private final ProductMapper productMapper;

  public EntertainmentServiceImpl(
      EntertainmentRepository entertainmentRepository,
      CategoryService categoryService,
      UserUtil userUtil,
      ProductUtil productUtil,
      ProductMapper productMapper) {
    this.entertainmentRepository = entertainmentRepository;
    this.categoryService = categoryService;
    this.userUtil = userUtil;
    this.productUtil = productUtil;
    this.productMapper = productMapper;
  }

  @Override
  public EntertainmentResponse createEntertainment(EntertainmentCreationRequest request,
      Optional<List<MultipartFile>> images,
      Authentication principal) {
    User seller = getUserFromPrincipal(principal);

    images.ifPresent(productUtil::checkImages);
    productUtil.checkCurrency(request);
    checkEntertainmentType(request);

    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
    com.shop4e.shop.domain.enumeration.EntertainmentType entertainmentType = com.shop4e.shop.domain.enumeration.EntertainmentType.getEntertainmentType(
        request.getType());
    Category category = categoryService.getCategoryOrFail(request);
    List<Genre> genres = categoryService.getGenresOrFail(request);

    Entertainment product = new Entertainment();
    product.setSeller(seller);
    product.setTitle(request.getTitle());
    product.setDescription(request.getDescription());
    product.setCurrency(currency);
    product.setPrice(request.getPrice());
    product.setCategory(category);
    //Entertainment specific
    product.setType(entertainmentType);
    product.setGenre(genres);
    product.setDetails(request.getDetails());

    productUtil.attachImages(product, images);

    entertainmentRepository.saveAndFlush(product);

    EntertainmentResponse response = productMapper.mapEntertainmentToResponse(product);

    return response;
  }

  @Override
  public EntertainmentResponse getEntertainment(String id) {
    Entertainment product = entertainmentRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new CustomException("Product not found."));

    return productMapper.mapEntertainmentToResponse(product);
  }

  @Override
  public EntertainmentResponse updateEntertainment(String id, EntertainmentUpdateRequest request,
      Optional<List<MultipartFile>> images,
      Authentication principal) {
    User seller = getUserFromPrincipal(principal);

    productUtil.checkCurrency(request);
    checkEntertainmentType(request);

    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
    com.shop4e.shop.domain.enumeration.EntertainmentType entertainmentType = com.shop4e.shop.domain.enumeration.EntertainmentType.getEntertainmentType(
        request.getType());
    List<Genre> genres = categoryService.getGenresOrFail(request);

    Entertainment product = entertainmentRepository.findEntertainmentByIdAndSeller(
            UUID.fromString(id), seller)
        .orElseThrow(() -> new CustomException("Product not found."));
    images.ifPresent(i -> {
      if (i.size() + product.getPhotos().size() > TOTAL_IMAGES) {
        throw new CustomException("Total count of images exceeded.");
      }

      productUtil.attachImages(product, images);
    });
    product.setTitle(request.getTitle());
    product.setDescription(request.getDescription());
    product.setCurrency(currency);
    product.setPrice(request.getPrice());
    //Entertainment specific
    product.setType(entertainmentType);
    product.setGenre(genres);
    product.setDetails(request.getDetails());

    entertainmentRepository.saveAndFlush(product);

    EntertainmentResponse response = productMapper.mapEntertainmentToResponse(product);

    return response;
  }

  private User getUserFromPrincipal(Authentication principal) {
    return userUtil.getUserFromPrincipal(principal);
  }

  private <T extends EntertainmentType> void checkEntertainmentType(T request) {
    if (!com.shop4e.shop.domain.enumeration.EntertainmentType.supportEntertainment(
        request.getType())) {
      throw new CustomException("Entertainment type not supported!");
    }
  }
}
