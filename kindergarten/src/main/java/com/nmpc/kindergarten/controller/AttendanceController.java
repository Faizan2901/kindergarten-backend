package com.nmpc.kindergarten.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nmpc.kindergarten.dto.AttendanceDTO;
import com.nmpc.kindergarten.model.Attendance;
import com.nmpc.kindergarten.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:4200")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@PostMapping("/submit")
	public ResponseEntity<Map<String, List<AttendanceDTO>>> saveAttendance(
			@RequestBody List<Attendance> attendanceData) {

		Map<String, List<AttendanceDTO>> response = new HashMap<>();

//		List<AttendanceDTO> isPresentDateAttendance = attendanceService
//				.getAttendanceByDate(attendanceData.get(0).getDate());
//		System.out.println(isPresentDateAttendance.get(0));
//		if (!isPresentDateAttendance.isEmpty()) {
//			response.put("alreadyPresentAttendance", isPresentDateAttendance);
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		}

		List<AttendanceDTO> savedAttendanceList = attendanceService.saveAttendance(attendanceData);

		response.put("savedAttendance", savedAttendanceList);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	@GetMapping("/by-date")
	public ResponseEntity<Map<String, List<AttendanceDTO>>> getAttendanceByDate(@RequestParam("date") String date) {

		Map<String, List<AttendanceDTO>> response = new HashMap<>();

		List<AttendanceDTO> isPresentDateAttendance = attendanceService.getAttendanceByDate(LocalDate.parse(date));

		if (isPresentDateAttendance.isEmpty()) {
			response.put("attendanceNotFound", isPresentDateAttendance);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}

		response.put("alreadyPresentAttendance", isPresentDateAttendance);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@PutMapping("/update")
	public ResponseEntity<Map<String, List<AttendanceDTO>>> updateAttendance(
			@RequestBody List<AttendanceDTO> updationAttendance, @RequestParam("date") String date) {
		LocalDate updationDate=LocalDate.parse(date);
		Map<String, List<AttendanceDTO>> response = new HashMap<>();
		boolean isOnePresent=updationAttendance.stream().anyMatch(attendance->attendance.isPresent());
		if(!isOnePresent) {
			attendanceService.deleteAllAttendance(updationAttendance, updationDate);
			response.put("atLeastOnePresent", updationAttendance);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		List<AttendanceDTO> updatedAttendanceList = attendanceService.updateAttendance(updationAttendance,
				updationDate);
		response.put("attendanceUpdated", updatedAttendanceList);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

}
