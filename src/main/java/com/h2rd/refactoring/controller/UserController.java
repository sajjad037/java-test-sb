package com.h2rd.refactoring.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.h2rd.refactoring.model.User;
import com.h2rd.refactoring.service.UserService;
import com.h2rd.refactoring.utilities.Validation;

@RestController
@RequestMapping("api/users")

/**
 * Web service exposed API.
 * 
 * @author SajjadAshrafCan
 *
 */
public class UserController {

	@Autowired
	private UserService userService = new UserService();
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/add/", method = RequestMethod.POST)
	/**
	 * Add User
	 * 
	 * @param user
	 * @return
	 */
	public ResponseEntity<?> addUser(@RequestBody User user) {

		ArrayList<String> errorrs = new Validation().validateUser(user);
		if (errorrs.size() > 0) {
			logger.error("Inputs are not correct, Reason {}", String.join(",", errorrs));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.join(",", errorrs));
		}

		if (userService.isUserExist(user)) {
			logger.error("User '" + user.getEmail() + "' already exist.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User '" + user.getEmail() + "' already exist.");
		}

		userService.saveUser(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/update/", method = RequestMethod.PUT)
	/**
	 * update User
	 * 
	 * @param user User
	 * @return
	 */
	public ResponseEntity<?> updateUser(@RequestBody User user) {

		ArrayList<String> errorrs = new Validation().validateUser(user);
		if (errorrs.size() > 0) {
			logger.error("Inputs are not correct, Reason {}", String.join(",", errorrs));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.join(",", errorrs));
		}

		if (!userService.isUserExist(user)) {
			logger.error("User '" + user.getEmail() + "' does not exist.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User '" + user.getEmail() + "' does not exist.");
		}

		userService.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/", method = RequestMethod.DELETE)
	/**
	 * delete User
	 * 
	 * @param user User
	 * @return
	 */
	public ResponseEntity<?> deleteUser(@RequestBody User user) {
		ArrayList<String> errorrs = new Validation().validateUser(user);
		if (errorrs.size() > 0) {
			logger.error("Inputs are not correct, Reason {}", String.join(",", errorrs));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.join(",", errorrs));
		}

		if (!userService.isUserExist(user)) {
			logger.error("User '" + user.getEmail() + "' does not exist.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User '" + user.getEmail() + "' does not exist.");
		}

		userService.deleteUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/find/", method = RequestMethod.GET)
	/**
	 * Get Users
	 * 
	 * @return Response
	 */
	public ResponseEntity<?> getUsers() {
		List<User> users = userService.getUsers();
		if (users == null) {
			logger.error("User not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/search/{email}", method = RequestMethod.GET)
	/**
	 * find User
	 * 
	 * @param email String
	 * @return Response
	 */
	public ResponseEntity<?> findUser(@PathVariable("email") String email) {
		User user = userService.findUserByEmail(email);
		if (user == null) {
			logger.error("User not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
