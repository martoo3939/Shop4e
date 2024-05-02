package com.shop4e.shop.filter;

import com.shop4e.shop.domain.ProductViewHistory;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.service.ViewHistoryService;
import com.shop4e.shop.service.impl.UserService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class ProductRequestFilter implements Filter {

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  private final UserService userService;
  private final ViewHistoryService viewHistoryService;

  public ProductRequestFilter(UserService userService, ViewHistoryService viewHistoryService) {
    this.userService = userService;
    this.viewHistoryService = viewHistoryService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse res = (HttpServletResponse) response;

    final String requestMethod = req.getMethod();
    final String requestURI = req.getRequestURI();
    final String pattern = "/api/v1/product/{id}";
    final String uuidPattern = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

    final String userEmail = getUserEmail();
    final User user = userService.getUserByEmail(userEmail);

    if (userEmail == null || user == null) {
      filterChain.doFilter(request, response);
      return;
    }

    if (pathMatcher.match(pattern, requestURI) && requestMethod.equals("GET")) {
      String productId = pathMatcher.extractUriTemplateVariables(pattern, requestURI).get("id");

      if (!productId.matches(uuidPattern)) {
        filterChain.doFilter(request, response);
        return;
      }

      viewHistoryService.logProductView(productId, user);
    }

    filterChain.doFilter(request, response);
  }

  private String getUserEmail() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = null;
    if (authentication != null && authentication.isAuthenticated()) {
      username = authentication.getName();
    }

    return username;
  }
}
