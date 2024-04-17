package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.UserVerification;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.UserRepository;
import com.shop4e.shop.repository.UserVerificationRepository;
import com.shop4e.shop.service.UserVerificationService;
import com.shop4e.shop.util.UserUtil;
import java.util.Objects;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class UserVerificationServiceImpl implements UserVerificationService {

  private static final Logger logger = LoggerFactory.getLogger(UserVerificationServiceImpl.class);
  private final UserVerificationRepository userVerificationRepository;
  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;
  private final UserUtil userUtil;
  private final UserRepository userRepository;

  @Value("${application.hostname}")
  private String applicationHostname;

  public UserVerificationServiceImpl(
      UserVerificationRepository userVerificationRepository,
      JavaMailSender javaMailSender,
      TemplateEngine templateEngine,
      UserUtil userUtil,
      UserRepository userRepository) {
    this.userVerificationRepository = userVerificationRepository;
    this.javaMailSender = javaMailSender;
    this.templateEngine = templateEngine;
    this.userUtil = userUtil;
    this.userRepository = userRepository;
  }

  @Override
  public void sendVerification(MimeMessage email) {
    try {
      javaMailSender.send(email);
      logger.info("Message has been sent");
    } catch (Exception ex) {
      logger.error("Error during email: {}", ex.getMessage());
    }
  }

  @Override
  @Async
  public void doVerification(final User user) {
    checkUserAlreadyVerified(user);
    try {
      UserVerification verification = createVerification(user);
      MimeMessage email = createVerificationEmail(user, verification);

      sendVerification(email);
    } catch (Exception ex) {
      logger.error("Error occurred during verification:", ex);
    }
  }

  private void checkUserAlreadyVerified(User user) {
    if(user.isVerified())
      throw new CustomException("User already verified.");
  }

  @Override
  public void resendVerification(Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    doVerification(user);
  }

  @Override
  public boolean verifyUser(String verificationToken) {
    try {
      UserVerification userVerification = userVerificationRepository.findByToken(verificationToken)
          .orElseThrow(() -> new CustomException("Invalid verification token supplied."));
      User verifiedUser = userVerification.getUser().setVerified(true);
      userRepository.saveAndFlush(verifiedUser);

      return true;
    } catch (Exception ex) {
      logger.error("Error during verification: ", ex);

      return false;
    }
  }

  @Override
  public void sendVerification(Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    doVerification(user);
  }

  @Override
  public UserVerification createVerification(final User user) {
    UserVerification userVerification = userVerificationRepository.findByUser(user)
        .orElse(new UserVerification());

    if (Objects.isNull(userVerification.getToken()) && Objects.isNull(userVerification.getUser())) {
      userVerification.setUser(user);
      userVerification.setToken(UUID.randomUUID().toString());
    } else {
      userVerification.setToken(UUID.randomUUID().toString());
    }

    return userVerificationRepository.saveAndFlush(userVerification);
  }

  @Override
  public MimeMessage createVerificationEmail(User user, UserVerification verification)
      throws MessagingException {
    final String userFullname = user.getFirstName() + " " + user.getLastName();
    final String verificationLink =
        applicationHostname + "/api/v1/verify/user/" + verification.getToken();

    Context context = new Context();
    context.setVariable("userFullname", userFullname);
    context.setVariable("verificationLink", verificationLink);
    String process = templateEngine.process("emails/email-verification", context);

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
    email.setTo(user.getEmail());
    email.setSubject("Welcome to Shop4e!");
    email.setText(process, true);

    return mimeMessage;
  }
}
