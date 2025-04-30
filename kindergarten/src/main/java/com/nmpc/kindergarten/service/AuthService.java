package com.nmpc.kindergarten.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nmpc.kindergarten.exception.InvalidCredentialsException;
import com.nmpc.kindergarten.exception.UserAlreadyPresent;
import com.nmpc.kindergarten.model.Role;
import com.nmpc.kindergarten.model.User;
import com.nmpc.kindergarten.repository.RoleRepository;
import com.nmpc.kindergarten.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtService jwtUtility;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${upload.path}")
	private String uploadDir;

	public String registerUser(String firstName, String fatherName, String motherName, String email, String password,
			String dob, String contact, String gender, String address, MultipartFile photo) throws IOException {
		if (userRepository.findByFirstNameAndEmail(firstName, email).isPresent()) {
			throw new UserAlreadyPresent("User already exists!");
		}

		String basePath = new File("src/main/resources/").getAbsolutePath();
		String fullPath = Paths.get(basePath, uploadDir).toString();

		File directory = new File(fullPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		String newPlayCenterId = generatePlaycenterId();

		String fileName = newPlayCenterId + "_" + photo.getOriginalFilename();
		Path filePath = Paths.get(fullPath, fileName);

		Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		User newUser = new User();

		newUser.setFirstName(firstName);
		newUser.setFatherName(fatherName);
		newUser.setMotherName(motherName);
		newUser.setEmail(email);
		newUser.setGender(gender);
		newUser.setDateOfBirth(LocalDate.parse(dob));

		Role role = roleRepository.findByName("STUDENT").orElseThrow(() -> new RuntimeException("Role not found"));

		Set<Role> rolesSet = new HashSet<>();
		rolesSet.add(role);

		newUser.setRoles(rolesSet);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setAddress(address);
		newUser.setContactNumber(contact);
		newUser.setPhotoUrl("assets/photos/" + fileName);
		newUser.setPlayCenterId(newPlayCenterId);

		User saveUser = userRepository.save(newUser);

		System.out.println(saveUser);

		return "User registered successfully!";
	}

	private String generatePlaycenterId() {
		String lastGeneratedId = userRepository.findLastPlayCenterId();

		LocalDate date = LocalDate.now();

		String currentYear = String.valueOf(date.getYear());

		if (lastGeneratedId == null) {
			return "NMPCSTUDENT" + currentYear + "0001";
		}
//		if(lastGeneratedId.contains("TEACHER") || lastGeneratedId.contains("ADMIN"))

		int num = Integer.parseInt(lastGeneratedId.replace("NMPCSTUDENT" + currentYear, ""));
		num++;

		return String.format("NMPCSTUDENT" + currentYear + "%04d", num);

	}

	public String loginUser(String playCenterId, String password) {
		Optional<User> user = userRepository.findByPlayCenterId(playCenterId);

		if (!user.isPresent()) {
			throw new UsernameNotFoundException(playCenterId + " not found");
		}

		if (!passwordEncoder.matches(password, user.get().getPassword())) {
			throw new InvalidCredentialsException("Invalid credentials!");
		}

		return jwtUtility.generateToken(playCenterId);

	}

}
