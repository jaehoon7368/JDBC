package com.sh.member.view;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.sh.member.controller.MemberController;
import com.sh.member.model.dto.Member;

public class MemberMenu {

	private MemberController memberController = new MemberController();
	private Scanner sc = new Scanner(System.in);
	
	public void mainMenu() {
		String menu = "===== 회원관리프로그램 =====\n"
                + "1. 회원 전체조회\n"
                + "2. 아이디 조회\n"
                + "3. 이름 검색\n" 
                + "4. 회원 가입\n"
                + "5. 회원 정보 수정\n"
                + "6. 회원 탈퇴\n"
                + "7. 탈퇴 회원 조회\n"
                + "0. 프로그램 종료\n"
                + "=======================\n"
                + "선택 : ";
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			Member member = null;
			int result = 0;
			String name = null;
			String id = null;
			List<Member> members = null;
			
			switch(choice) {
			case "1" : 
				members = memberController.findAll();
				displayMembers(members);
				break;
			case "2" : 
				id = inputId("조회");
				member = memberController.findById(id);
				displayMember(member);
				break;
			case "3" : 
				name = inputName("조회");
				members = memberController.findByName(name);
				displayMembers(members);
				break;
			case "4" : 
				member = inputMember();
				System.out.println("> 입력정보 확인 : " + member);
				result = memberController.insertMember(member);
				displayResult("회원가입",result);
				break;
			case "5" : 
				id = inputId("수정");
				member = memberController.findById(id);
				if (member == null) {
					System.out.println("조회하신 회원이 없습니다.");
					break;
				}else {
					displayMember(member);
					updateMember(id);
					break;
				}
			case "6" : 
				id = inputId("탈퇴");
				result = memberController.deleteMember(id);
				displayResult("회원탈퇴",result);
				break;
			case "7" : 
				members = memberController.findDelAll();
				displayDelMembers(members);
				break;
			case "0" : return;
			default : System.out.println("> 잘못입력하셨습니다.");
			}
		}
	}

	private void updateMember(String id) {
		String menu = "===== 회원관리프로그램 =====\n"
				+ "1. 이름변경\n"
				+ "2. 생일변경\n"
				+ "3. 이메일변경\n"
				+ "0. 메인메뉴 돌아가기\n"
				+ "=========================\n"
				+ "선택 : ";
		
		String name = null;
		Date birthday = null;
		String email = null;
		int result = 0;
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			
			switch(choice) {
			case "1" : 
				name = inputName("수정");
				result = memberController.updateName(id,name);
				displayResult("수정",result);
				break;
			case "2" : 
				birthday = inputBirthday("수정");
				result = memberController.updateBirthday(id,birthday);
				displayResult("수정",result);
				break;
			case "3" : 
				email = inputEmail("수정");
				result = memberController.updateEmail(id,email);
				displayResult("수정",result);
				break;
			case "0" : return;
			default :
				System.out.println("잘못입력하셨습니다.");
			}
		}
		
	}

	private String inputEmail(String mode) {
		System.out.print("> " + mode + "할 이메일 입력 : ");
		return sc.next();
	}

	private String inputId(String mode) {
		System.out.print("> " + mode + "할 아이디 입력 : ");
		return sc.next();
	}
	
	private Date inputBirthday(String mode) {
		System.out.print("> " + mode + "할 생일 입력 : ");
		return Date.valueOf(sc.next());
	}

	private void displayMembers(List<Member> members) {
		if(members.isEmpty()) {
			//조회결과가 없는경우
			System.out.println(">조회된 결과가 없습니다.");
		}
		else {
			//조회결과가 있는 경우
			System.out.println("------------------------------------------------------------");
			System.out.printf("%-10s%-10s%-10s%-20s%-20s%-10s%-10s\n",
					"id","name","gender","birthday","email","point","regDate");
			System.out.println("-----------------------------------------------------------");
			for(Member member: members) {
				System.out.printf("%-10s%-10s%-10s%-20s%-20s%-10s%-10s\n",
						member.getId(),
						member.getName(),
						member.getGender(),
						member.getBrithday(),
						member.getEmail(),
						member.getPoint(),
						member.getRegDate());
			}
			System.out.println("-----------------------------------------------------------");
		}
		
	}
	
	private void displayDelMembers(List<Member> members) {
		if(members.isEmpty()) {
			//조회결과가 없는경우
			System.out.println(">조회된 결과가 없습니다.");
		}
		else {
			//조회결과가 있는 경우
			System.out.println("------------------------------------------------------------");
			System.out.printf("%-10s%-10s%-10s%-20s%-20s%-10s%-10s%-10s\n",
					"id","name","gender","birthday","email","point","regDate","delDate");
			System.out.println("-----------------------------------------------------------");
			for(Member member: members) {
				System.out.printf("%-10s%-10s%-10s%-20s%-20s%-10s%-10s%-10s\n",
						member.getId(),
						member.getName(),
						member.getGender(),
						member.getBrithday(),
						member.getEmail(),
						member.getPoint(),
						member.getRegDate(),
						member.getDelDate());
			}
			System.out.println("-----------------------------------------------------------");
		}
		
	}

	private String inputName(String mode) {
		System.out.print("> " + mode + "할 이름 입력 : ");
		return sc.next();
	}

	private void displayResult(String mode, int result) {
		System.out.printf("%s %s\n",mode, result > 0 ? "성공!" : "실패!");
	}
	
	
	private void displayMember(Member member) {
		if(member == null) {
		System.out.println("> 조회된 결과가 없습니다.");
		}
		else {
			System.out.println("----------------------------------");
			System.out.println("ID : " + member.getId());
			System.out.println("NAME : " + member.getName());
			System.out.println("GENDER : " + member.getGender());
			System.out.println("BIRTHDAY : " + member.getBrithday());
			System.out.println("EMAIL : " + member.getEmail());
			System.out.println("POINT : " + member.getPoint());
			System.out.println("REGDATE : " + member.getRegDate());
			
			System.out.println("----------------------------------");
		}
		
	}

	private Member inputMember() {
		List<Member> members = memberController.findAll();
		
		System.out.println("> 새 회원정보를 입력하세요.");
		r_id:
		while(true) {
		System.out.print("> 아이디 : ");
		String id = sc.next();
		for(Member member : members) {
			if(member.getId().equals(id)) {
				System.out.println("이미 존재하는 아이디입니다.다시입력해주세요");
				continue r_id;
			}
		}
		
		System.out.print("> 이름 : ");
		String name = sc.next();
		System.out.print("> 성별(M/F) : ");
		String gender = sc.next();
		System.out.print("> 생일(19990909) : ");
		LocalDate _birthday = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("yyyyMMdd"));
		Date birthday = Date.valueOf(_birthday);
		
		r_email:
		while(true) {
			System.out.print("> 이메일 : ");
			String email = sc.next();
			for(Member member : members) {
				if(member.getEmail().equals(email)) {
					System.out.println("이미 존재하는 이메일입니다.다시입력해주세요.");
					continue r_email;
				}
			}
			return new Member(id, name, gender, birthday, email, 0, null);
		}
		}
	}

}
