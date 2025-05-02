package com.nmpc.kindergarten.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nmpc.kindergarten.dto.StudentDTO;
import com.nmpc.kindergarten.model.User;
import com.nmpc.kindergarten.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<Map<String, User>> getUserInfo() {

		Map<String, User> response = new HashMap<>();

		User user = userService.getUserInfo();

		response.put("user", user);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@GetMapping("/students")
	public ResponseEntity<Map<String, List<StudentDTO>>> getAllStudents() {

		Map<String, List<StudentDTO>> response = new HashMap<>();

		List<StudentDTO> studentDTOs = userService.getAllStudents();

		response.put("students", studentDTOs);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@PutMapping(value = "/students/{playCenterId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String updateStudentInfo(@RequestParam("firstName") String firstName, @RequestParam("email") String email,
			@RequestParam("gender") String gender, @RequestParam("dateOfBirth") String dob,
			@RequestParam("address") String address, @RequestParam("contactNumber") String contact,
			@RequestParam("fatherName") String fatherName, @RequestParam("motherName") String motherName,
			@PathVariable("playCenterId") String playCenterId) {

		userService.updateStudentInfo(firstName, email, gender, dob, address, contact, fatherName, motherName,
				playCenterId);

		return "User info updated successfully";
	}

}
