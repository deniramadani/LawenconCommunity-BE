package com.lawencon.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.security.principal.PrincipalService;

/**
 * @author lawencon05
 */

@Service
public class RefreshTokenService {

	@Autowired
	private PrincipalService principalService;

	public RefreshTokenEntity saveToken(RefreshTokenEntity data) throws Exception {
		try {
			if (data.getId() != null) {
				data.setUpdatedBy(principalService.getAuthPrincipal());
				data = ConnHandler.getManager().merge(data);
				ConnHandler.getManager().flush();
			} else {
				data.setCreatedBy(principalService.getAuthPrincipal());
				ConnHandler.getManager().persist(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

		return data;
	}

	public RefreshTokenEntity getByRefreshTokenId(String id) throws Exception {
		return ConnHandler.getManager().find(RefreshTokenEntity.class, id);
	}
	
	public boolean deleteByRefreshTokenId(String id) throws Exception {
		try {
			RefreshTokenEntity data = ConnHandler.getManager().find(RefreshTokenEntity.class, id);
			if (data != null) {
				ConnHandler.getManager().remove(data);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void validateRefreshToken(String refreshToken) {
		String sql = "SELECT r FROM RefreshTokenEntity r "
				+ "	WHERE token = :refreshToken AND expiredDate > current_timestamp() ";

		RefreshTokenEntity result = null;
		try {
			result = ConnHandler.getManager().createQuery(sql, RefreshTokenEntity.class)
					.setParameter("refreshToken", refreshToken)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result == null) {
				throw new InvalidTokenException("Invalid Refresh Token");
			}
		}
	}
	
	public class InvalidTokenException extends RuntimeException {

		private static final long serialVersionUID = 3475438369643938945L;

		public InvalidTokenException(String message) {
			super(message);
		}

	}
}
