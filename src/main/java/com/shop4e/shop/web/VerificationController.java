package com.shop4e.shop.web;

import com.shop4e.shop.service.UserVerificationService;
import com.shop4e.shop.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/v1/verify")
public class VerificationController {

  private final UserVerificationService userVerificationService;

  public VerificationController(UserVerificationService userVerificationService) {
    this.userVerificationService = userVerificationService;
  }

  @PostMapping("/resend")
  @ResponseBody
  public ResponseEntity<?> sendVerification(Authentication authentication) {
    userVerificationService.resendVerification(authentication);

    return ResponseBuilder.buildResponse(HttpStatus.OK);
  }

  @GetMapping("/user/{token}")
  public String verifyUser(@PathVariable String token, Model model) {
    boolean isVerified = userVerificationService.verifyUser(token);
    model.addAttribute("isVerified", isVerified);

    return "users/verified";
  }
}
