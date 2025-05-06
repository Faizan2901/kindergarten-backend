package com.nmpc.kindergarten.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmpc.kindergarten.dto.AttendanceDTO;
import com.nmpc.kindergarten.dto.MonthlyAttendanceStatDTO;
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

			User user = userRepository.findByPlayCenterId(attendance.getPlayCenterId())
					.orElseThrow(() -> new RuntimeException("User not found"));

			attendance.setUser(user);

			Attendance dbSavedAttendance = attendanceRepository.save(attendance);

			AttendanceDTO builderAttendance = new AttendanceDTO.Builder()
					.playCenterId(dbSavedAttendance.getPlayCenterId()).date(dbSavedAttendance.getDate())
					.present(dbSavedAttendance.isPresent()).firstName(user.getFirstName()).build();

			savedAttendanceList.add(builderAttendance);
		}

		return savedAttendanceList;
	}

	public List<AttendanceDTO> getAttendanceByDate(LocalDate date) {

		List<Attendance> attendanceList = attendanceRepository.findByDate(date);
		List<AttendanceDTO> dtoList = new ArrayList<>();

		for (Attendance attendance : attendanceList) {
			User user = attendance.getUser();
			AttendanceDTO dto = new AttendanceDTO.Builder().playCenterId(attendance.getPlayCenterId())
					.date(attendance.getDate()).present(attendance.isPresent()).firstName(user.getFirstName()).build();

			dtoList.add(dto);
		}

		return dtoList;

	}

	public List<AttendanceDTO> updateAttendance(List<AttendanceDTO> updationAttendanceList, LocalDate date) {

		List<AttendanceDTO> updatedAttendanceList = new ArrayList<>();

		for (AttendanceDTO attendance : updationAttendanceList) {
			Attendance dbSavedAttendance = attendanceRepository.findByPlayCenterIdAndDate(attendance.getPlayCenterId(),
					date);
			dbSavedAttendance.setPresent(attendance.isPresent());

			Attendance updatedAttendance = attendanceRepository.save(dbSavedAttendance);

			User user = updatedAttendance.getUser();

			AttendanceDTO attendanceDTO = new AttendanceDTO.Builder().date(updatedAttendance.getDate())
					.playCenterId(updatedAttendance.getPlayCenterId()).present(updatedAttendance.isPresent())
					.firstName(user.getFirstName()).build();
			updatedAttendanceList.add(attendanceDTO);
		}

		return updatedAttendanceList;

	}

	@Transactional
	public void deleteAllAttendance(List<AttendanceDTO> attendanceList, LocalDate date) {
		for (AttendanceDTO attendance : attendanceList) {
			attendanceRepository.deleteByPlayCenterIdAndDate(attendance.getPlayCenterId(), date);
		}
	}

	public List<LocalDate> getAllAttendanceDates() {
		return attendanceRepository.findAllMarkedDates();
	}

	public boolean isAnyAttendanceChange(List<AttendanceDTO> updationAttendanceList, LocalDate date) {

		for (AttendanceDTO attendance : updationAttendanceList) {
			Attendance dbSavedAttendance = attendanceRepository.findByPlayCenterIdAndDate(attendance.getPlayCenterId(),
					date);
			if (dbSavedAttendance.isPresent() != attendance.isPresent()) {
				return true;
			}
		}

		return false;
	}

	public List<MonthlyAttendanceStatDTO> getMonthlyStatsForStudent(String playCenterId) {

		Map<String, Map<String, Integer>> attendanceData = new HashMap<>();

		List<MonthlyAttendanceStatDTO> monthWiseStatList=new ArrayList<>();

		List<Attendance> allMonthAttendanceList = attendanceRepository.findByPlayCenterId(playCenterId);

		for (Attendance attendance : allMonthAttendanceList) {
			String month = attendance.getDate().getMonth().name();
			String year=String.valueOf(attendance.getDate().getYear());
			String finalMonth=month+"-"+year;

			boolean isPresent = attendance.isPresent();
			Map<String, Integer> presentAbsentList = attendanceData.getOrDefault(finalMonth, new HashMap<>());

			if (isPresent) {
				presentAbsentList.put("present", presentAbsentList.getOrDefault("present", 0) + 1);
			} else {
				presentAbsentList.put("absent", presentAbsentList.getOrDefault("absent", 0) + 1);
			}

			attendanceData.put(finalMonth, presentAbsentList);

		}

		for(Map.Entry<String, Map<String, Integer>> map:attendanceData.entrySet()){
			String monthName=map.getKey();
			Map<String,Integer> daysMap=map.getValue();
			Integer absentDays=daysMap.get("absent")!=null?daysMap.get("absent"):0;
			Integer presentDays=daysMap.get("present")!=null?daysMap.get("present"):0;
			Integer totalDays=absentDays+presentDays;
			MonthlyAttendanceStatDTO statDTO=new MonthlyAttendanceStatDTO.Builder()
					.month(monthName)
					.absentDays(absentDays)
					.presentDays(presentDays)
					.totalDays(totalDays)
					.build();

			monthWiseStatList.add(statDTO);

		}

		System.out.println(monthWiseStatList);

		return monthWiseStatList;
	}
}
