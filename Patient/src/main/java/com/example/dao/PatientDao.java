package com.example.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.example.vo.Doctor;
import com.example.vo.Patient;
import com.example.vo.Reservation;
import com.example.vo.Schedule;

public class PatientDao {
	private SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();

	
	public Doctor searchDoctorBySeq(int seq_doc){
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			return mapper.searchDoctorBySeq(seq_doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Doctor> searchDoctorByName(String name){
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			return mapper.searchDoctorByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Doctor> searchDoctorByDept(String dept){
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			return mapper.searchDoctorByDept(dept);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addPatient(Patient patient) {
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			mapper.addPatient(patient);
			session.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Patient searchPatientBySsn(String ssn) {
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			return mapper.searchPatientBySsn(ssn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Patient searchPatientBySeq(int seq_pat) {
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			return mapper.searchPatientBySeq(seq_pat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updatePatient(Patient patient) {
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			if (mapper.updatePatient(patient) > 0) {
				session.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Schedule searchSchedule(int seq_doc, int year, int month) {
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			Schedule schedule = new Schedule(seq_doc, year, month);
			return mapper.searchSchedule(schedule);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addReservation(Reservation reservation){
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			if (mapper.searchReservation(reservation) == null) {
				mapper.addReservation(reservation);
				session.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Reservation searchReservation(int seq_doc, String time) {
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			Reservation reservation = new Reservation(seq_doc, time);
			return mapper.searchReservation(reservation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Reservation> searchReservationByPat (int seq_pat) {
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			return mapper.searchReservationByPat(seq_pat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteReservation(Reservation reservation) {
		try (SqlSession session = factory.openSession()) {
			PatientMapper mapper = session.getMapper(PatientMapper.class);
			if (mapper.deleteReservation(reservation) > 0) {
				session.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}