package com.nmpc.kindergarten.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmpc.kindergarten.dto.AttendanceDTO;
import com.nmpc.kindergarten.model.Attendance;
import com.nmpc.kindergarten.model.User;
import com.nmpc.kindergarten.repository.AttendanceRepository;
import com.nmpc.kindergarten.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private UserRepository userRepository;

	public List<AttendanceDTO> saveAttendance(List<Attendance> attendanceList) {
	    List<AttendanceDTO> savedAttendanceList = new ArrayList<>();

	    for (Attendance attendance : attendanceList) {
	       
	        User user = userRepository.findByPlayCenterId(attendance.getPlayCenterId()).orElseThrow(() -> new RuntimeException("User not found"));

	        attendance.setUser(user); 

	       
	        Attendance dbSavedAttendance = attendanceRepository.save(attendance);

	        
	        AttendanceDTO builderAttendance = new AttendanceDTO.Builder()
	                .playCenterId(dbSavedAttendance.getPlayCenterId())
	                .date(dbSavedAttendance.getDate())
	                .present(dbSavedAttendance.isPresent())
	                .firstName(user.getFirstName())
	                .build();

			savedAttendanceList.add(builderAttendance);
		}

		return savedAttendanceList;
	}

	public List<AttendanceDTO> getAttendanceByDate(LocalDate date) {

		List<Attendance> attendanceList = attendanceRepository.findByDate(date);
		List<AttendanceDTO> dtoList = new ArrayList<>();

		for (Attendance attendance : attendanceList) {
			User user=attendance.getUser();
		    AttendanceDTO dto = new AttendanceDTO.Builder()
		            .playCenterId(attendance.getPlayCenterId())
		            .date(attendance.getDate())
		            .present(attendance.isPresent())
		            .firstName(user.getFirstName())
		            .build();

		    dtoList.add(dto);
		}

		return dtoList;

	}
	
	public List<AttendanceDTO> updateAttendance(List<AttendanceDTO> updationAttendanceList,LocalDate date){
		
		List<AttendanceDTO> updatedAttendanceList=new ArrayList<>();
		
		for(AttendanceDTO attendance:updationAttendanceList) {
			Attendance dbSavedAttendance=attendanceRepository.findByPlayCenterIdAndDate(attendance.getPlayCenterId(), date);
			dbSavedAttendance.setPresent(attendance.isPresent());
			
			Attendance updatedAttendance=attendanceRepository.save(dbSavedAttendance);
			
			User user=updatedAttendance.getUser();
			
			AttendanceDTO attendanceDTO=new AttendanceDTO.Builder()
														.date(updatedAttendance.getDate())
														.playCenterId(updatedAttendance.getPlayCenterId())
														.present(updatedAttendance.isPresent())
														.firstName(user.getFirstName())
														.build();
			updatedAttendanceList.add(attendanceDTO);
		}
		
		return updatedAttendanceList;
		
	}
	
	@Transactional
	public void deleteAllAttendance(List<AttendanceDTO> attendanceList,LocalDate date) {
		for(AttendanceDTO attendance:attendanceList) {
			attendanceRepository.deleteByPlayCenterIdAndDate(attendance.getPlayCenterId(), date);
		}
	}

}
