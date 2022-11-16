package com.lawencon.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.util.JwtUtil;
import com.lawencon.util.JwtUtil.ClaimKey;

import io.jsonwebtoken.Claims;

/**
 * @author lawencon05
 */

@Component
@Profile("default")
public class AuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	@Qualifier("webIgnoring")
	private List<RequestMatcher> antMatchers;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		long count = antMatchers.stream().filter(matcher -> matcher.matches(request)).collect(Collectors.counting());
		if (count == 0 && !request.getRequestURI().equals("/login")) {
			String authorization = request.getHeader("Authorization");

			if (authorization == null || authorization.length() < 8 || !authorization.contains("Bearer")) {
				resError(response);
				return;
			}

			String token = authorization.replaceFirst("Bearer ", "");

			try {
				// getting claims data
				Claims claims = jwtUtil.getClaims(token);

				Object idObj = claims.get(ClaimKey.ID.name());
				Object roleObj = claims.get(ClaimKey.ROLE.name());

				if (idObj == null) {
					throw new RuntimeException("Claim Key with Name ID not set, use Enum ClaimKey in JwtUtil");
				}
				if (roleObj == null) {
					throw new RuntimeException("Claim Key with Name ROLE not set, use Enum ClaimKey in JwtUtil");
				}

				GrantedAuthority authority = new SimpleGrantedAuthority(roleObj.toString());
				List<GrantedAuthority> authorities = Arrays.asList(authority);

				// register id and authority
				Authentication auth = new UsernamePasswordAuthenticationToken(idObj.toString(), null, authorities);
				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception e) {
				e.printStackTrace();
				resError(response);
				return;
			}

		}

		filterChain.doFilter(request, response);
	}

	private void resError(HttpServletResponse response) {
		try {
			response.setStatus(401);
			Map<String, Object> map = new HashMap<>();
			map.put("message", "Invalid token");
			response.getWriter().append(objectMapper.writeValueAsString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
