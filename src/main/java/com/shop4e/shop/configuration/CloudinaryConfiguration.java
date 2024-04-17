package com.shop4e.shop.configuration;

import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {

  @Value("${app.cloudinary.cloud-name}")
  private String cloudName;
  @Value("${app.cloudinary.api-key}")
  private String apiKey;
  @Value("${app.cloudinary.secret-key}")
  private String secretKey;

  @Bean
  public Cloudinary cloudinary() {
    Map config = new HashMap<>();
    config.put("cloud_name", cloudName);
    config.put("api_key", apiKey);
    config.put("api_secret", secretKey);
    Cloudinary cloudinary = new Cloudinary(config);
    return cloudinary;
  }
}
