package com.shop4e.shop.service;

import com.shop4e.shop.web.request.AddressRequest;
import com.shop4e.shop.web.response.AddressResponse;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
  AddressResponse createAddress(AddressRequest addressRequest, Authentication principal);

  AddressResponse updateAddress(AddressRequest addressRequest, String addressId, Authentication principal);

  void deleteAddress(String addressId, Authentication principal);

  List<AddressResponse> getAddresses(Authentication principal);

  AddressResponse getAddress(String addressId, Authentication principal);

  AddressResponse getAddressForDelivery(String addressId);
}
