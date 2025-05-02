package com.nmpc.kindergarten.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nmpc.kindergarten.exception.InvalidCredentialsException;
import com.nmpc.kindergarten.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, String>> registerUser(@RequestParam("photo") MultipartFile photo,
			@RequestParam("firstName") String firstName, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("gender") String gender,
			@RequestParam("dob") String dob, @RequestParam("address") String address,
			@RequestParam("parentContact") String contact, @RequestParam("parentFatherName") String fatherName,
			@RequestParam("parentMotherName") String motherName) {

		Map<String, String> response = new HashMap<>();
		try {
			String result = authService.registerUser(firstName, fatherName, motherName, email, password, dob, contact,
					gender, address, photo);
			response.put("message", result);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (Exception e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {

		Map<String, String> response = new HashMap<>();

		try {
			String token = authService.loginUser(request.get("playCenterId"), request.get("password"));
			response.put("token", token);
			response.put("message", "User logged in successfully");
		} catch (UsernameNotFoundException | InvalidCredentialsException e) {
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}

		return ResponseEntity.ok(response);
	}

}
