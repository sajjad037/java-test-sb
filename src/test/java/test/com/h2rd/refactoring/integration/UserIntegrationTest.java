package test.com.h2rd.refactoring.integration;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.h2rd.refactoring.controller.UserController;
import com.h2rd.refactoring.model.User;

import org.junit.Test;


import junit.framework.Assert;

/**
 * Integration test case (end to end test case) to test UserResource
 * functionality.
 * 
 * @author aldocuevas
 *
 */
@SpringBootTest
public class UserIntegrationTest {

	@Test
	/**
	 * Test Case: Create User
	 */
	public void createUserTest() {
		UserController userResource = new UserController();

		User integration = new User();
		integration.setName("integration");
		integration.setEmail("initial@integration.com");
		integration.setRoles(Arrays.asList("admin"));

		ResponseEntity<?> response =  userResource.addUser(integration);
		Assert.assertEquals(201, response.getStatusCodeValue());
		
	}
	
	@Test
	/**
	 * Test Case: Create User Fail
	 */
	public void createUserFailTest() {
		UserController userResource = new UserController();

		User integration = new User();
		integration.setName("initial");
		integration.setEmail("integration@integration.com");
		integration.setRoles(new ArrayList<String>());

		ResponseEntity<?> response = userResource.addUser(integration);
		Assert.assertEquals(400, response.getStatusCodeValue());
	}

	@Test
	/**
	 * Test Case: Update user.
	 */
	public void updateUserTest() {
		UserController userResource = new UserController();

		User integration = new User();
		integration.setName("integration1");
		integration.setEmail("initial1@integration.com");
		integration.setRoles(Arrays.asList("admin"));

		ResponseEntity<?> response = userResource.addUser(integration);
		
		User updated = new User();
		updated.setName("updated");
		updated.setEmail("initial1@integration.com");
		updated.setRoles(Arrays.asList("admin", "master"));

		response = userResource.updateUser(updated);
		Assert.assertEquals(200, response.getStatusCodeValue());
		
	}
}
