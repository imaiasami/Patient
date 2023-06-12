package com.example.manager;

import java.util.List;

import com.example.dao.PatientDao;
import com.example.vo.Doctor;
import com.example.vo.Patient;
import com.example.vo.Reservation;
import com.example.vo.Schedule;

public class PatientManager {
	
	PatientDao dao = new PatientDao();
	
	public Doctor searchDoctorBySeq(int seq_doc) {
		return dao.searchDoctorBySeq(seq_doc);
	}
	
	public List<Doctor> searchDoctorByName(String name) {
		return dao.searchDoctorByName(name);
	}
	
	public List<Doctor> searchDoctorByDept (String dept) {
		return dao.searchDoctorByDept(dept);
	}
	
	public boolean addPatient(Patient patient) {
		return dao.addPatient(patient);
	}
	
	public Patient searchPatientBySeq(int seq_pat) {
		return dao.searchPatientBySeq(seq_pat);
	}
	
	public Patient searchPatientBySsn(String ssn) {
		return dao.searchPatientBySsn(ssn);
	}
	
	public boolean updatePatient (Patient patient) {
		return dao.updatePatient(patient);
	}
	
	public boolean addReservation (Reservation reservation) {
		return dao.addReservation(reservation);
	}
	
	public Schedule searchSchedule(int seq_doc, int year, int month) {
		return dao.searchSchedule(seq_doc, year, month);
	}
	
	public Reservation searchReservation (int seq_doc, String time) {
		return dao.searchReservation(seq_doc, time);
	}
	
	public List<Reservation> searchReservationByPat (int seq_pat) {
		return dao.searchReservationByPat(seq_pat);
	}
	
	public boolean deleteReservation (Reservation reservation) {
		return dao.deleteReservation(reservation);
	}

}