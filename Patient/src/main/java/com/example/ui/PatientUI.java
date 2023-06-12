package com.example.ui;

import java.util.*;
import com.example.manager.PatientManager;
import com.example.vo.*;

public class PatientUI {
	
	private Scanner scanner = new Scanner(System.in);
	private PatientManager manager = new PatientManager();
	
	public PatientUI() {
		while(true) {
			showMain();
			String input = scanner.next();
			switch (input) {
			case "1": addPatient(); break;
			case "2": searchPatientBySsn(); break;
			case "3": showAllDoctors(); break;
			case "4": System.exit(0);
			default: System.out.println("다시 입력해 주세요.");		
			}
		}
	}
	
	public void showMain() {
		System.out.println("===========================");
		System.out.println("진료 예약 관리 시스템");
		System.out.println("===========================");
		System.out.println("1. 처음 오시는 분 등록");
		System.out.println("2. 본인 인증");
		System.out.println("3. 의료진 검색");
		System.out.println("4. 프로그램 종료");
		System.out.print("선택> ");
	}
	
	public void displayCalender(Schedule sc) {
		Calendar c = Calendar.getInstance();
		c.set(sc.getYear(), sc.getMonth()-1, 1);
		int dow = c.get(Calendar.DAY_OF_WEEK); // day of week
		int eod = c.getActualMaximum(Calendar.DATE); // end of date of month
		List<Integer> days = sc.getDays();
		
		System.out.println("               " + sc.getYear() + "년 " + sc.getMonth() + "월");
		System.out.println(" 일    월    화    수    목    금    토");
		for (int i = 1; i < dow; i++) {
			System.out.print("      ");
		}
		for (int i = 0; i < eod; i++) {
			if (days.get(i) == 1) System.out.print("[");
			else System.out.print(" ");
			if (i < 9) System.out.print(" ");
			System.out.print(i + 1);
			if (days.get(i) == 1) System.out.print("]");
			else System.out.print(" ");
			if ((dow + i) % 7 == 0 || i == eod - 1) System.out.println();
			else System.out.print("  ");
		}
	}
	
	public void addPatient() {
		System.out.println("---------------------------");
		System.out.println("처음 오시는 분 등록");
		System.out.println("---------------------------");
		String name = inputText("이름> ");
		String phone = inputText("전화번호> ", "^\\d{2,3}-\\d{3,4}-\\d{3,4}$", "올바른 전화번호가 아닙니다.");
		String ssn = inputText("주민번호> ", "^\\d{6}-\\d{7}$", "올바론 주민번호가 아닙니다.");
		
		Patient patient = new Patient (name, phone, ssn);
		boolean p = manager.addPatient(patient);
		if (p)	 {
			System.out.println("등록되었습니다.");
		} else {
			System.out.println("등록에 실패했습니다.");
		}
	}
	
	public void searchPatientBySsn() {
		System.out.println("---------------------------");
		System.out.println("본인 인증");
		System.out.println("---------------------------");
		String ssn = inputText("주민번호> ", "^\\d{6}-\\d{7}$", "올바론 주민번호가 아닙니다.");
		Patient p = manager.searchPatientBySsn(ssn);
		if (p == null) {
			System.out.println("검색 결과가 없습니다.");
			return;
		}
		while (true) {
			System.out.println("---------------------------");
			System.out.println(p.getName() + " 님, 환영합니다.");
			System.out.println("---------------------------");
			System.out.println("1. 예약 시간 조회");
			System.out.println("2. 정보 수정");
			System.out.println("3. 로그아웃");
			String input = inputText("선택> ");
			switch(input) {
			case "1": printReservation(p); break;
			case "2": updatePatient(p); break;
			case "3": return;
			default: System.out.println("다시 입력해 주세요.");
			}
		}
	}
	
	public void printReservation(Patient p) {
		while (true) {
			System.out.println("---------------------------");
			System.out.println(p.getName() + " 님의 진료 예약 현황");
			System.out.println("---------------------------");
			List<Reservation> res = showReservationPat(p, false);
			System.out.println("1. 예약하기");
			System.out.println("2. 예약 삭제");
			System.out.println("3. 돌아가기");
			String input = inputText("선택> ");
			if (input.matches("1")) {
				Doctor d = searchDoctor(p);
				if (d != null) searchCalenderPat(d.getSeq_doc(), p.getSeq_pat());
			} else if (input.matches("2")) {
				if (res == null || res.isEmpty()) System.out.println("삭제할 예약 내역이 없습니다.");
				else deleteReservationPat(p);
			} else if (input.matches("3")) {
				return;
			} else {
				System.out.println("다시 입력해 주세요.");
			}
		}
	}
	
	public List<Reservation> showReservationPat(Patient p, boolean flag) {
		List<Reservation> res = manager.searchReservationByPat(p.getSeq_pat());
		if (res == null || res.isEmpty()) System.out.println("-");
		for (int i = 0; i < res.size(); i++) {
			if (flag) System.out.print((i+1) + ". ");
			Doctor d = manager.searchDoctorBySeq(res.get(i).getSeq_doc());
			System.out.println(res.get(i).getTime() + " " + d.getName() + "(" + d.getDept() + ")");
		}
		return res;
	}
	
	public Doctor searchDoctor(Patient p) {
		List<Doctor> ds = new ArrayList<>();
		Doctor d = null;
		
		while (true) {
			System.out.println("---------------------------");
			System.out.println("진료 예약할 의사 검색");
			System.out.println("---------------------------");
			System.out.println("1. 이름으로 검색");
			System.out.println("2. 진료과목으로 검색");
			System.out.println("3. 돌아가기");
			String input = inputText("선택> ");
			if (input.matches("1")) {
				input = inputText("검색할 이름> ");
				ds = manager.searchDoctorByName(input);
				if (ds == null || ds.isEmpty()) System.out.println("검색 결과가 없습니다.");
				else break;
			} else if (input.matches("2")) {
				input = inputText("검색할 진료과목> ");
				ds = manager.searchDoctorByDept(input);
				if (ds == null || ds.isEmpty()) System.out.println("검색 결과가 없습니다.");
				else break;
			} else if (input.matches("3")) {
				return null;
			} else {
				System.out.println("다시 입력해 주세요.");
			}
		}
		
		if (ds.size() == 1) d = ds.get(0);
		else {
			while (true) {
				for (int i = 0; i < ds.size(); i++) {
					System.out.println((i+1) + ". " + ds.get(i));
				}
				String input = inputText("선택> ");
				if (input.matches("\\d+")) {
					int num = Integer.parseInt(input);
					if (num < 1 || num > ds.size()) {
						System.out.println("다시 입력해 주세요.");
					} else {
						d = ds.get(num - 1);
						break;
					}
				} else {
					System.out.println("다시 입력해 주세요.");
				}
			}
		}
		
		System.out.println(d);
		return d;
	}
	
	public void searchCalenderPat(int seq_doc, int seq_pat) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		Schedule sc = manager.searchSchedule(seq_doc, year, month);
		if (sc == null) sc = new Schedule(seq_doc, year, month);
		showSchedulePat(sc, seq_pat);
	}
	public void searchCalenderPat(int seq_doc, int year, int month, int seq_pat) {
		Schedule sc = manager.searchSchedule(seq_doc, year, month);
		if (sc == null) sc = new Schedule(seq_doc, year, month);
		showSchedulePat(sc, seq_pat);
	}
	
	public void showSchedulePat(Schedule sc, int seq_pat) {
		Calendar c = Calendar.getInstance();
		int thisYear = c.get(Calendar.YEAR);
		int thisMonth = c.get(Calendar.MONTH) + 1;
		int thisDay = c.get(Calendar.DATE);
		c.set(sc.getYear(), sc.getMonth()-1, 1);
		int eod = c.getActualMaximum(Calendar.DATE);
		boolean flag = true;
		
		while (flag) {
			System.out.println("---------------------------");
			System.out.println("예약 가능일 보기");
			System.out.println("---------------------------");
			displayCalender(sc);
			System.out.println("년월 입력 : 해당월로 이동");
			System.out.println("날짜 입력 : 해당일의 진료 시간 보기");
			System.out.println("exit :  상위 메뉴로");
			String input = inputText("입력> ");
			if (input.matches("^\\d{6}$")) {
				int year = Integer.parseInt(input.substring(0, 4));
				int month = Integer.parseInt(input.substring(4, 6));
				searchCalenderPat(sc.getSeq_doc(), year, month, seq_pat);
				flag = false;
			} else if (input.matches("^\\d+\\D+\\d+\\D*$")) {
				String[] arr = input.split("\\D+");
				int year = Integer.parseInt(arr[0]);
				int month = Integer.parseInt(arr[1]);
				searchCalenderPat(sc.getSeq_doc(), year, month, seq_pat);
				flag = false;
			} else if (input.matches("\\d+")) {
				List<Integer> days = sc.getDays();
				int day = Integer.parseInt(input);
				if (day < 1 || day > eod) {
					System.out.println("다시 입력해 주세요.");
				} else if (days.get(day -1) == 0) {
					System.out.println("근무일이 아닙니다.");
				} else if (sc.getYear() < thisYear || (sc.getYear() == thisYear && sc.getMonth() < thisMonth) || (sc.getYear() == thisYear && sc.getMonth() == thisMonth && day <= thisDay)) {
					System.out.println("당일 또는 과거 예약은 불가합니다.");
				} else {
					int month = sc.getMonth();
					String month0 = month < 10 ? "0" + month : String.valueOf(month);
					String day0 = day < 10 ? "0" + day : String.valueOf(day);
					if (showReservationPat(seq_pat, sc.getSeq_doc(), sc.getYear() + "-" + month0 + "-" + day0)) return;
				}
			} else if (input.toLowerCase().equals("exit")) {
				return;
			} else {
				System.out.println("다시 입력해 주세요.");
			}
		}
	}
	
	public boolean showReservationPat(int seq_pat, int seq_doc, String date) {
		String[] ti = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"};
		List<String> times = new ArrayList<>(Arrays.asList(ti));
		
		while (true) {
			Map<String, Reservation> resMap = new HashMap<>();
			System.out.println("---------------------------");
			System.out.println(date + " 진료 예약 현황");
			System.out.println("---------------------------");
			for (String time : times) {
				Reservation re = manager.searchReservation(seq_doc, date + " " + time);
				resMap.put(time, re);
				System.out.println(time + " : " + (re == null ? "예약 가능" : "예약 중"));
			}
			System.out.println("시간 입력 : 해당 시간으로 예약");
			System.out.println("exit : 상위 메뉴로");
			String input = inputText("입력> ");
			if (times.contains(input)) {
				Reservation value = resMap.get(input);
				if (value == null) {
					addReservationPat(seq_pat, seq_doc, date + " " + input);
					return true;
				} else {
					System.out.println("이미 다른 예약이 존재합니다.");
				}
			} else if (input.toLowerCase().equals("exit")) {
				return false;
			} else {
				System.out.println("다시 입력해 주세요.");
			}
		}
	}
	
	public void addReservationPat(int seq_pat, int seq_doc, String time) {
		Reservation re = new Reservation(seq_pat, seq_doc, time);
		if (manager.addReservation(re)) System.out.println("예약되었습니다.");
		else System.out.println("예약에 실패했습니다.");
	}
	
	public void updatePatient(Patient p) {
		System.out.println("---------------------------");
		System.out.println("정보 수정");
		System.out.println("---------------------------");
		System.out.println(p);
		System.out.println("1. 이름 수정");
		System.out.println("2. 전화번호 수정");
		System.out.println("3. 전체 수정");
		String input = inputText("선택> ");
		boolean flag = true, once = true;
		while (flag) {
			switch (input) {
			case "3": once = false;
			case "1":
				String name = inputText("수정할 이름> ");
				p.setName(name);
				if (once) flag = false; break;
			case "2":
				String phone = inputText("수정할 전화번호> ", "^\\d{2,3}-\\d{3,4}-\\d{3,4}$", "올바른 전화번호가 아닙니다.");
				p.setPhone(phone);
				flag = false; break;
			default: System.out.println("다시 입력해 주세요.");
			}
		}
		if(manager.updatePatient(p)) {
			System.out.println("수정되었습니다.");
		} else {
			System.out.println("수정에 실패했습니다.");
		}
	}
	
	public void deleteReservationPat(Patient p) {
		while (true) {
			System.out.println("---------------------------");
			System.out.println("진료 예약 삭제");
			System.out.println("---------------------------");
			List<Reservation> res = showReservationPat(p, true);
			System.out.println("exit : 상위 메뉴로");
			String input = inputText("선택> ");
			if (input.matches("\\d+")) {
				int num = Integer.parseInt(input);
				if (num < 1 || num > res.size()) {
					System.out.println("다시 입력해 주세요.");
				} else {
					if (manager.deleteReservation(res.get(num - 1))) System.out.println("삭제되었습니다.");
					else System.out.println("삭제에 실패했습니다.");
					return;
				}
			} else if (input.toLowerCase().matches("exit")) {
				return;
			} else {
				System.out.println("다시 입력해 주세요.");
			}
		}
	}
	
	public void showAllDoctors() {
		List<Doctor> ds = manager.searchDoctorByDept("");
		for (Doctor d : ds) {
			System.out.println(d);
		}
	}
	
    public String inputText(String text) {
        String input = "";
        System.out.print(text);
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            if (!input.isEmpty()) break;
        }
        return input;
    }
    public String inputText(String text, String regex, String message) {
        String input = "";
        while (true) {
            System.out.print(text);
            while (scanner.hasNextLine()) {
                input = scanner.nextLine();
                if (!input.isEmpty()) break;
            }
            if (input.matches(regex)) break;
            else System.out.println(message);
        }
        return input;
    }
	
}