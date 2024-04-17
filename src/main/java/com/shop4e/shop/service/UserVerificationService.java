package com.shop4e.shop.service;

import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.UserVerification;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.security.core.Authentication;

public interface UserVerificationService {

  void sendVerification(MimeMessage email);

  void doVerification(User user);

  void resendVerification(Authentication principal);

  boolean verifyUser(String verificationToken);

  void sendVerification(Authentication principal);

  UserVerification createVerification(User user);

  MimeMessage createVerificationEmail(User user, UserVerification verification)
      throws MessagingException;
}
