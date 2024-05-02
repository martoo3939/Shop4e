package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Address;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.AddressRepository;
import com.shop4e.shop.service.AddressService;
import com.shop4e.shop.util.UserUtil;
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

  public AddressServiceImpl(AddressRepository addressRepository, UserUtil userUtil) {
    this.addressRepository = addressRepository;
    this.userUtil = userUtil;
  }

  @Override
  public AddressResponse createAddress(AddressRequest addressRequest, Authentication principal) {
    Address address = new Address();
    address.setCountry(addressRequest.getCountry());
    address.setCity(addressRequest.getCity());
    address.setStreet(addressRequest.getStreet());
    address.setPostalCode(address.getPostalCode());
    address.setDescription(address.getDescription());

    return mapAddressToAddressResponse(addressRepository.save(address));
  }

  @Override
  public AddressResponse updateAddress(AddressRequest addressRequest, String addressId, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    Address address = addressRepository.findByIdAndUser(UUID.fromString(addressId), user)
        .orElseThrow(() -> new CustomException("Address not found."));
    address.setCountry(addressRequest.getCountry());
    address.setCity(addressRequest.getCity());
    address.setStreet(addressRequest.getStreet());
    address.setPostalCode(address.getPostalCode());
    address.setDescription(address.getDescription());

    return mapAddressToAddressResponse(addressRepository.save(address));
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
    List<AddressResponse> addresses = addressRepository.findAddressesByUser(user).stream()
        .map(this::mapAddressToAddressResponse)
        .collect(Collectors.toList());

    return addresses;
  }

  @Override
  public AddressResponse getAddress(String addressId, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    Address address = addressRepository.findByIdAndUser(UUID.fromString(addressId), user)
        .orElseThrow(() -> new CustomException("Address not found."));

    return mapAddressToAddressResponse(address);
  }

  private AddressResponse mapAddressToAddressResponse(Address address) {
    AddressResponse addressResponse = new AddressResponse();
    addressResponse.setId(address.getId().toString());
    addressResponse.setCountry(address.getCountry());
    addressResponse.setPostalCode(address.getPostalCode());
    addressResponse.setCity(address.getCity());
    addressResponse.setDescription(address.getDescription());

    return addressResponse;
  }
}
