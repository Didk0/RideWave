package com.ride.wave.gateway;

import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

// @Component
public class JwtAuthFilter implements WebFilter {

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {

    final String authHeader =
        exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    // TODO: Validate JWT Token

    return chain.filter(exchange);
  }
}
