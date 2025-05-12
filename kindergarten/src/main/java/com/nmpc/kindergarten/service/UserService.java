package com.nmpc.kindergarten.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nmpc.kindergarten.dto.StudentDTO;
import com.nmpc.kindergarten.exception.UserNotFoundException;
import com.nmpc.kindergarten.model.User;
import com.nmpc.kindergarten.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserInfo() {
		String playCenterId = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByPlayCenterId(playCenterId).get();
		
		if (user == null) {
			throw new UserNotFoundException("User with " + playCenterId + " is not found");
		}
		
		user.setPhotoUrl("http://localhost:8080/" + user.getPhotoUrl());
		
		return user;
	}

	public List<StudentDTO> getAllStudents() {
		List<User> studentsList = userRepository.findAllStudents();
		studentsList.addAll(userRepository.findAllTeachers());
		List<StudentDTO> studentsDtoList = new ArrayList<>();

		for (User user : studentsList) {
			StudentDTO studentDTO = new StudentDTO.Builder().playCenterId(user.getPlayCenterId())
					.firstName(user.getFirstName()).email(user.getEmail()).password(user.getPassword())
					.gender(user.getGender()).dateOfBirth(user.getDateOfBirth()).address(user.getAddress())
					.contactNumber(user.getContactNumber()).fatherName(user.getFatherName())
					.motherName(user.getMotherName()).photoUrl("http://localhost:8080/" + user.getPhotoUrl()).build();
			studentsDtoList.add(studentDTO);
		}

		return studentsDtoList;
	}

	public void updateStudentInfo(String firstName, String email, String gender, String dateOfBirth, String address,
			String contactNumber, String fatherName, String motherName, String playCeneterId) {

		User dbUser = userRepository.findByPlayCenterId(playCeneterId).get();

		dbUser.setFirstName(firstName);
		dbUser.setFatherName(fatherName);
		dbUser.setMotherName(motherName);
		dbUser.setAddress(address);
		dbUser.setContactNumber(contactNumber);
		dbUser.setEmail(email);
		dbUser.setDateOfBirth(LocalDate.parse(dateOfBirth));
		dbUser.setGender(gender);

		userRepository.save(dbUser);

	}

}
