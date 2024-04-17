package com.shop4e.shop.configuration;

import com.shop4e.shop.filter.ProductRequestFilter;
import org.apache.tika.Tika;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

  private final UserDetailsService userDetailsService;
  private final ProductRequestFilter productRequestFilter;

  public ApplicationConfiguration(
      UserDetailsService userDetailsService,
      ProductRequestFilter productRequestFilter) {
    this.userDetailsService = userDetailsService;
    this.productRequestFilter = productRequestFilter;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(userDetailsService);
    return authenticationProvider;
  }

  @Bean
  public FilterRegistrationBean<ProductRequestFilter> productFilter() {
    FilterRegistrationBean<ProductRequestFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(productRequestFilter);
    registrationBean.addUrlPatterns("/api/v1/product/*");

    return registrationBean;
  }

  @Bean
  public Tika tika() {
    return new Tika();
  }
}
