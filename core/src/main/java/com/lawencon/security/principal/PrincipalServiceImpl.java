package com.lawencon.security.principal;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author lawencon05
 */

@Service
@Profile("default")
public class PrincipalServiceImpl implements PrincipalService {

	@Override
	public String getAuthPrincipal() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null || auth.getPrincipal() == null)
			throw new Exception("Invalid Login");

		return auth.getPrincipal().toString();
	}
}
