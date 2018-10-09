package test.com.h2rd.refactoring.unit;

import java.util.Arrays;


import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.h2rd.refactoring.controller.UserController;
import com.h2rd.refactoring.dao.UserDao;
import com.h2rd.refactoring.model.User;

import junit.framework.Assert;

/**
 * Unit test case (test a specific piece of code) to test UserResource
 * functionality. (Here the the piece of code we testing will act as end to end
 * test case)
 * 
 * @author aldocuevas
 *
 */
public class UserResourceUnitTest {

	UserController userResource;
	UserDao userDao;

	@Test
	/**
	 * Test Case: Get Users
	 */
	public void getUsersTest() {

		userResource = new UserController();
		userDao = UserDao.getUserDao();

		User user = new User();
		user.setName("fake user");
		user.setEmail("fake@user.com");
		user.setRoles(Arrays.asList("admin", "master"));
		userDao.saveUser(user);

		ResponseEntity<?> response = userResource.getUsers();
		Assert.assertEquals(200, response.getStatusCodeValue());
	}
}
