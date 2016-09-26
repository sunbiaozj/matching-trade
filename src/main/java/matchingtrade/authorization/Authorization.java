package matchingtrade.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matchingtrade.authentication.User;
import matchingtrade.persistence.dao.UserDao;
import matchingtrade.persistence.entity.UserEntity;
import matchingtrade.persistence.entity.UserEntity.Role;

@Component
public class Authorization {

	@Autowired
	private UserDao userDao;
	
	/**
	 * Throws <pre>AuthorizationException</pre> if <pre>user</pre> or <pre>user.userId</pre> is null
	 * or if <pre>user</pre> does not have a <pre>role</pre>
	 * @param user
	 * @return UserEntity instance of <pre>user.userId</pre>
	 */
	public UserEntity doBasicAuthorization(User user) {
		if (user == null || user.getUserId() == null) {
			throw new AuthorizationException(AuthorizationException.Type.USER_NOT_REGISTERED);
		}
		UserEntity result = userDao.get(user.getUserId());
		if (result == null) {
			throw new AuthorizationException(AuthorizationException.Type.USER_NOT_REGISTERED);
		} else if (result.getRole() == null) {
			throw new AuthorizationException(AuthorizationException.Type.USER_NOT_AUTHORIZED);
		}
		return result;
	}
	
	/**
	 * Throws <pre>AuthorizationException</pre> if user is not <pre>ROLE.ADMINISTRATOR<pre> or
	 * if <pre>userEntity.userId</pre> is not equals to <pre>userId</pre>.
	 * @param userEntity
	 * @param userId
	 */
	public void validateIdentity(UserEntity userEntity, Integer userId) {
		if (userEntity.getRole() != Role.ADMINISTRATOR && !userEntity.getUserId().equals(userId)) {
			throw new AuthorizationException(AuthorizationException.Type.USER_NOT_AUTHORIZED);
		}
	}

	/**
	 * Performs <pre>doBasicAuthorization(User user)</pre> and <pre>validateIdentity(UserEntity userEntity, Integer userId)</pre>.
	 * @param user
	 * @param userId
	 * @return the result of <pre>doBasicAuthorization(User user)</pre>
	 */
	public UserEntity validateIdentityAndDoBasicAuthorization(User user, Integer userId) {
		UserEntity userEntity = doBasicAuthorization(user);
		validateIdentity(userEntity, userId);
		return userEntity;
	}
	
}
