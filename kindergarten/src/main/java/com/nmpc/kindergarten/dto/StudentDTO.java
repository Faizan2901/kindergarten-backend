package com.nmpc.kindergarten.dto;

import java.time.LocalDate;

public class StudentDTO {

	private String playCenterId;
	private String firstName;
	private String email;
	private String password;
	private String gender;
	private LocalDate dateOfBirth;
	private String address;
	private String contactNumber;
	private String fatherName;
	private String motherName;
	private String imageUrl;

	private StudentDTO(Builder builder) {
		this.playCenterId = builder.playCenterId;
		this.firstName = builder.firstName;
		this.email = builder.email;
		this.password = builder.password;
		this.gender = builder.gender;
		this.dateOfBirth = builder.dateOfBirth;
		this.address = builder.address;
		this.contactNumber = builder.contactNumber;
		this.fatherName = builder.fatherName;
		this.motherName = builder.motherName;
		this.imageUrl = builder.imageUrl;
	}

	public static class Builder {
		private String playCenterId;
		private String firstName;
		private String email;
		private String password;
		private String gender;
		private LocalDate dateOfBirth;
		private String address;
		private String contactNumber;
		private String fatherName;
		private String motherName;
		private String imageUrl;

		public Builder playCenterId(String playCenterId) {
			this.playCenterId = playCenterId;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder gender(String gender) {
			this.gender = gender;
			return this;
		}

		public Builder dateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public Builder address(String address) {
			this.address = address;
			return this;
		}

		public Builder contactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
			return this;
		}

		public Builder fatherName(String fatherName) {
			this.fatherName = fatherName;
			return this;
		}

		public Builder motherName(String motherName) {
			this.motherName = motherName;
			return this;
		}

		public Builder imageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}

		public StudentDTO build() {
			return new StudentDTO(this);
		}
	}

	public String getPlayCenterId() {
		return playCenterId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getGender() {
		return gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getFatherName() {
		return fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public String toString() {
		return "StudentDTO [playCenterId=" + playCenterId + ", firstName=" + firstName + ", email=" + email
				+ ", password=" + password + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", address="
				+ address + ", contactNumber=" + contactNumber + ", fatherName=" + fatherName + ", motherName="
				+ motherName + ", imageUrl=" + imageUrl + "]";
	}
}
