package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Address;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.AddressRepository;
import com.shop4e.shop.service.AddressService;
import com.shop4e.shop.util.UserUtil;
import com.shop4e.shop.util.mapper.ProductMapper;
import com.shop4e.shop.web.request.AddressRequest;
import com.shop4e.shop.web.response.AddressResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;
  private final UserUtil userUtil;
  private final ProductMapper productMapper;

  public AddressServiceImpl(
      AddressRepository addressRepository,
      UserUtil userUtil,
      ProductMapper productMapper) {
    this.addressRepository = addressRepository;
    this.userUtil = userUtil;
    this.productMapper = productMapper;
  }

  @Override
  public AddressResponse createAddress(AddressRequest addressRequest, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    Address address = new Address();
    address.setCountry(addressRequest.getCountry());
    address.setCity(addressRequest.getCity());
    address.setStreet(addressRequest.getStreet());
    address.setPostalCode(addressRequest.getPostalCode());
    address.setDescription(addressRequest.getDescription());
    address.setUser(user);

    return productMapper.mapAddressToAddressResponse(addressRepository.save(address));
  }

  @Override
  public AddressResponse updateAddress(AddressRequest addressRequest, String addressId, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    Address address = addressRepository.findByIdAndUser(UUID.fromString(addressId), user)
        .orElseThrow(() -> new CustomException("Address not found."));
    address.setCountry(addressRequest.getCountry());
    address.setCity(addressRequest.getCity());
    address.setStreet(addressRequest.getStreet());
    address.setPostalCode(addressRequest.getPostalCode());
    address.setDescription(addressRequest.getDescription());

    return productMapper.mapAddressToAddressResponse(addressRepository.save(address));
  }

  @Override
  public void deleteAddress(String addressId, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    Address address = addressRepository.findByIdAndUser(UUID.fromString(addressId), user)
        .orElseThrow(() -> new CustomException("Address not found."));
    address.setDeleted(true);

    addressRepository.save(address);
  }

  @Override
  public List<AddressResponse> getAddresses(Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    List<AddressResponse> addresses = addressRepository.findAddressesByUserAndDeletedIsFalse(user).stream()
        .map(productMapper::mapAddressToAddressResponse)
        .collect(Collectors.toList());

    return addresses;
  }

  @Override
  public AddressResponse getAddress(String addressId, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    Address address = addressRepository.findByIdAndUser(UUID.fromString(addressId), user)
        .orElseThrow(() -> new CustomException("Address not found."));

    return productMapper.mapAddressToAddressResponse(address);
  }

  @Override
  public AddressResponse getAddressForDelivery(String addressId) {
    Address address = addressRepository.findById(UUID.fromString(addressId))
        .orElseThrow(() -> new CustomException("Address not found."));

    return productMapper.mapAddressToAddressResponse(address);
  }
}
