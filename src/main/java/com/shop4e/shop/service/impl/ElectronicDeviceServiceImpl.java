package com.shop4e.shop.service.impl;

import static com.shop4e.shop.util.service.ProductUtil.TOTAL_IMAGES;

import com.shop4e.shop.domain.Category;
import com.shop4e.shop.domain.ElectronicDevice;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.domain.enumeration.DeviceType;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.ElectronicDeviceRepository;
import com.shop4e.shop.service.CategoryService;
import com.shop4e.shop.service.ElectronicDeviceService;
import com.shop4e.shop.util.UserUtil;
import com.shop4e.shop.util.mapper.ProductMapper;
import com.shop4e.shop.util.service.ProductUtil;
import com.shop4e.shop.web.request.DeviceCreationRequest;
import com.shop4e.shop.web.request.DeviceUpdateRequest;
import com.shop4e.shop.web.response.DeviceResponse;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ElectronicDeviceServiceImpl implements ElectronicDeviceService {

  private final CategoryService categoryService;
  private final ElectronicDeviceRepository electronicDeviceRepository;
  private final UserUtil userUtil;
  private final ProductUtil productUtil;
  private final ProductMapper productMapper;

  public ElectronicDeviceServiceImpl(
      CategoryService categoryService,
      ElectronicDeviceRepository electronicDeviceRepository,
      UserUtil userUtil,
      ProductUtil productUtil,
      ProductMapper productMapper) {
    this.categoryService = categoryService;
    this.electronicDeviceRepository = electronicDeviceRepository;
    this.userUtil = userUtil;
    this.productUtil = productUtil;
    this.productMapper = productMapper;
  }

  @Override
  public DeviceResponse createElectronicDevice(
      DeviceCreationRequest request,
      Optional<MultipartFile[]> images,
      Authentication principal) {
    User seller = getUserFromPrincipal(principal);

    images.ifPresent(productUtil::checkImages);
    productUtil.checkCurrency(request);
    checkDeviceType(request);

    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
    Category category = categoryService.getCategoryOrFail(request);
    DeviceType deviceType = DeviceType.getDeviceType(request.getType());

    ElectronicDevice product = new ElectronicDevice();
    product.setSeller(seller);
    product.setTitle(request.getTitle());
    product.setDescription(request.getDescription());
    product.setCurrency(currency);
    product.setPrice(request.getPrice());
    product.setCategory(category);
    //Device specific
    product.setType(deviceType);
    product.setBrand(request.getBrand());
    product.setMemory(request.getMemory());
    product.setProcessor(request.getProcessor());
    product.setHardDrive(request.getHardDrive());
    product.setVideoCard(request.getVideoCard());

    productUtil.attachImages(product, images);

    electronicDeviceRepository.saveAndFlush(product);

    DeviceResponse response = productMapper.mapDeviceToDeviceResponse(product);

    return response;
  }

  @Override
  public DeviceResponse getDevice(String id) {
    ElectronicDevice device = electronicDeviceRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new CustomException("This device does not exist!"));

    return productMapper.mapDeviceToDeviceResponse(device);
  }

  @Override
  public DeviceResponse updateDevice(
      String deviceId,
      DeviceUpdateRequest request,
      Optional<MultipartFile[]> images,
      Authentication principal) {
    User seller = getUserFromPrincipal(principal);

    images.ifPresent(productUtil::checkImages);
    productUtil.checkCurrency(request);
    checkDeviceType(request);

    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
    DeviceType deviceType = DeviceType.getDeviceType(request.getType());

    ElectronicDevice product = electronicDeviceRepository.findElectronicDeviceByIdAndSeller(
            UUID.fromString(deviceId), seller)
        .orElseThrow(() -> new CustomException("Device not found."));
    images.ifPresent(i -> {
      if (i.length + product.getPhotos().size() > TOTAL_IMAGES) {
        throw new CustomException("Total count of images exceeded.");
      }

      productUtil.attachImages(product, images);
    });
    product.setTitle(request.getTitle());
    product.setDescription(request.getDescription());
    product.setCurrency(currency);
    product.setPrice(request.getPrice());
    //Device specific
    product.setType(deviceType);
    product.setBrand(request.getBrand());
    product.setMemory(request.getMemory());
    product.setProcessor(request.getProcessor());
    product.setHardDrive(request.getHardDrive());
    product.setVideoCard(request.getVideoCard());

    electronicDeviceRepository.saveAndFlush(product);

    DeviceResponse response = productMapper.mapDeviceToDeviceResponse(product);

    return response;
  }

  private <T extends com.shop4e.shop.web.request.DeviceType> void checkDeviceType(T request) {
    if (!DeviceType.supportDevice(request.getType())) {
      throw new CustomException("Device type not supported!");
    }
  }

  private User getUserFromPrincipal(Authentication principal) {
    return userUtil.getUserFromPrincipal(principal);
  }
}
