package com.nmpc.kindergarten.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nmpc.kindergarten.exception.InvalidCredentialsException;
import com.nmpc.kindergarten.exception.UserAlreadyPresent;
import com.nmpc.kindergarten.model.PasswordResetToken;
import com.nmpc.kindergarten.model.Role;
import com.nmpc.kindergarten.model.User;
import com.nmpc.kindergarten.repository.PasswordResetRepository;
import com.nmpc.kindergarten.repository.RoleRepository;
import com.nmpc.kindergarten.repository.UserRepository;

import jakarta.transaction.Transactional;

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

	@Autowired
	private PasswordResetRepository passwordResetRepository;

	@Autowired
	private EmailService emailService;

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

//		System.out.println(saveUser);

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

	public ResponseEntity<Map<String, String>> processForgotPassword(String email, String playCenterId) {
		Optional<User> user = userRepository.findByEmailAndPlayCenterId(email, playCenterId);
		if (user.isEmpty()) {
			Map<String, String> response = new HashMap<>();
			response.put("message", "User not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		String token = UUID.randomUUID().toString();
		PasswordResetToken resetToken = new PasswordResetToken(token, LocalDateTime.now().plusMinutes(30), user.get());
		passwordResetRepository.save(resetToken);

		String resetLink = "http://localhost:4200/reset-password?token=" + token;

		return emailService.sendEmail(email, "Password Reset Instructions for Your NMPC Account",
				"Click the link to reset your password: " + resetLink);
	}

	@Transactional
	public ResponseEntity<Map<String, String>> resetPassword(String token, String newPassword) {
		
		Optional<PasswordResetToken> tokenObj = passwordResetRepository.findByToken(token);
		Map<String, String> response = new HashMap<>();
		
		if (tokenObj.isEmpty() || tokenObj.get().getExpiryDate().isBefore(LocalDateTime.now())) {	
			response.put("message", "Invalid or expired token");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		User user = tokenObj.get().getUser();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		passwordResetRepository.deleteByTokenAndUser(token, user);

		response.put("message", "Password reset successful, Now you can login");
		
		return ResponseEntity.ok(response);
	}

}
