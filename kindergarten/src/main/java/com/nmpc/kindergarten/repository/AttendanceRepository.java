package com.nmpc.kindergarten.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nmpc.kindergarten.model.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	List<Attendance> findByDate(LocalDate date);

	Attendance findByPlayCenterIdAndDate(String playCenterId, LocalDate date);

	void deleteByPlayCenterIdAndDate(String playCenterId, LocalDate date);

	@Query("SELECT DISTINCT a.date FROM Attendance a")
	List<LocalDate> findAllMarkedDates();
}
