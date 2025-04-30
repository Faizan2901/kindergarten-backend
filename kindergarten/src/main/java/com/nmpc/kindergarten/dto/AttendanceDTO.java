package com.nmpc.kindergarten.dto;

import java.time.LocalDate;

public class AttendanceDTO {

    private String playCenterId;
    private LocalDate date;
    private boolean present;
    private String firstName;

    
    public AttendanceDTO() {}

   
    public String getPlayCenterId() {
        return playCenterId;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isPresent() {
        return present;
    }

    public String getFirstName() {
        return firstName;
    }

    // ✅ Private constructor for builder
    private AttendanceDTO(Builder builder) {
        this.playCenterId = builder.playCenterId;
        this.date = builder.date;
        this.present = builder.present;
        this.firstName = builder.firstName;
    }

    // ✅ Builder class
    public static class Builder {
        private String playCenterId;
        private LocalDate date;
        private boolean present;
        private String firstName;

        public Builder playCenterId(String playCenterId) {
            this.playCenterId = playCenterId;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder present(boolean present) {
            this.present = present;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AttendanceDTO build() {
            return new AttendanceDTO(this);
        }
    }

	@Override
	public String toString() {
		return "AttendanceDTO [playCenterId=" + playCenterId + ", date=" + date + ", present=" + present
				+ ", firstName=" + firstName + "]";
	}
    
    
}
