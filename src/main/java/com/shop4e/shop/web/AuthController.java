package com.shop4e.shop.web;

import com.shop4e.shop.service.impl.AuthenticationService;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.util.jwt.JwtUtil;
import com.shop4e.shop.util.jwt.JwtUtil.Token;
import com.shop4e.shop.util.validator.ValidatorUtil;
import com.shop4e.shop.web.request.AuthenticationRequest;
import com.shop4e.shop.web.request.RegistrationRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

  private final AuthenticationService authenticationService;
  private final JwtUtil jwtUtil;
  private final ValidatorUtil validator;

  public AuthController(
      AuthenticationService authenticationService,
      JwtUtil jwtUtil, ValidatorUtil validator) {
    this.authenticationService = authenticationService;
    this.jwtUtil = jwtUtil;
    this.validator = validator;
  }

  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest,
      Errors errors) {
    validator.checkForErrors(errors);

    UserDetails userDetails = authenticationService.register(registrationRequest);

    return ResponseBuilder.buildResponse(HttpStatus.OK, jwtUtil.generateToken(userDetails));
  }

  @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> authenticateUser(@RequestBody @Valid AuthenticationRequest request,
      Errors errors) {
    validator.checkForErrors(errors);

    UserDetails userDetails = authenticationService.login(request);

    if (userDetails != null) {
      return ResponseBuilder.buildResponse(HttpStatus.OK, jwtUtil.generateToken(userDetails));
    }

    return ResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED);
  }

  @GetMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> refreshToken(@RequestParam(name = "token") String refreshToken) {
    Token token = authenticationService.refreshToken(refreshToken);

    return ResponseBuilder.buildResponse(HttpStatus.OK, token);
  }

  @GetMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> validateToken(@RequestParam(name = "token") String token) {
    ResponseEntity<?> response = null;
    try {
      Boolean isValid = authenticationService.validateToken(token);

      if(isValid) response = ResponseBuilder.buildResponse(HttpStatus.OK);
    } catch (Exception ex) {
      response = ResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    return response;
  }
}
