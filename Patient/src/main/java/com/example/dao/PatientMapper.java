package com.example.dao;

import java.util.List;

import com.example.vo.Doctor;
import com.example.vo.Patient;
import com.example.vo.Reservation;
import com.example.vo.Schedule;

public interface PatientMapper {
	
	Doctor searchDoctorBySeq(int seq_doc);
	List<Doctor> searchDoctorByName(String name);
	List<Doctor> searchDoctorByDept(String dept);
	
	int addPatient(Patient patient);
	Patient searchPatientBySsn(String ssn);
	Patient searchPatientBySeq(int seq_pat);
	int updatePatient(Patient patient);
	
	Schedule searchSchedule(Schedule schedule);
	
	int addReservation(Reservation reservation);
	Reservation searchReservation(Reservation reservation);
	List<Reservation> searchReservationByPat(int seq_pat);
	int deleteReservation(Reservation reservation);
	
}