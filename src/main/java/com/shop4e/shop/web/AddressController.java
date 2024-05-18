package com.shop4e.shop.web;

import com.shop4e.shop.service.AddressService;
import com.shop4e.shop.util.GenericResponse;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.web.request.AddressRequest;
import com.shop4e.shop.web.response.AddressResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
  private final AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @GetMapping("/{id}")
  public GenericResponse<AddressResponse> getAddress(@PathVariable("id") String addressId, Authentication principal) {
    AddressResponse address = addressService.getAddress(addressId, principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, address);
  }

  @GetMapping("/delivery/{id}")
  public GenericResponse<AddressResponse> getAddress(@PathVariable("id") String addressId) {
    AddressResponse address = addressService.getAddressForDelivery(addressId);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, address);
  }

  @GetMapping("/all")
  public GenericResponse<List<AddressResponse>> getAddresses(Authentication principal) {
    List<AddressResponse> addresses = addressService.getAddresses(principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, addresses);
  }

  @PostMapping("/create")
  public GenericResponse<AddressResponse> createAddress(@RequestBody AddressRequest addressRequest, Authentication principal) {
    AddressResponse address = addressService.createAddress(addressRequest, principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, address);
  }

  @PutMapping("/{id}")
  public GenericResponse<AddressResponse> updateAddress(@PathVariable("id") String addressId, @RequestBody AddressRequest addressRequest, Authentication principal) {
    AddressResponse address = addressService.updateAddress(addressRequest, addressId,
        principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, address);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAddress(@PathVariable("id") String addressId, Authentication principal) {
    addressService.deleteAddress(addressId, principal);

    return ResponseBuilder.buildResponse(HttpStatus.NO_CONTENT);
  }
}
