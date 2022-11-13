package com.sh.member.view;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.sh.member.controller.MemberController;
import com.sh.member.model.dto.Member;

public class MemberMenu {
	
	private Scanner sc = new Scanner(System.in);
	private MemberController controller = new MemberController();

	public void mainMenu() {
		
		String menu = "===== 회원관리프로그램 =====\n"
				+ "1. 회원 전체조회\n"
				+ "2. 아이디 조회\n"
				+ "3. 이름 검색\n"
				+ "4. 회원가입\n"
				+ "5. 회원정보 수정\n"
				+ "6. 회원 탈퇴\n"
				+ "0. 프로그램 종료\n"
				+ "=========================\n"
				+ "선택 : ";
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			
			Member member = new Member();
			int result = 0;
			List<Member> members = null;
			String id = null;
			String name = null;
			
			switch(choice) {
			case "1" :
				members = controller.findAll();
				displayMembers(members);
				break;
			case "2" :
				id = inputId("조회");
				member = controller.findById(id);
				displayMember(member);
				break;
			case "3" : 
				name = inputName("조회");
				member = controller.findByName(name);
				displayMember(member);
				break;
			case "4" :
				member = inputMember();
				System.out.println("> 입력 회원정보 : " + member);
				result = controller.insertMember(member);
				displayResult("회원가입",result); //결과출력
				break;
			case "5" :
				id = inputId("조회");
				member = controller.findById(id);
				if (member == null) {
					System.out.println("조회하신 회원은 없습니다.");
					break;
				}
				else {
					displayMember(member);
					updateMenu(id);
					break;					
				}
			case "6" : 
				id = inputId("조회");
				result = controller.deleteMember(id);
				displayResult("회원탈퇴",result);
				break;
			case "0" : return; //종료
			default :
				System.out.println("> 잘못 입력하셨습니다.");
			}
		}
	}

	/**
	 * 사용자로 부터 조회할 이름 조회
	 * @param mode
	 * @return
	 */
	private String inputName(String mode) {
		System.out.print("> " + mode + "할 이름 입력 : ");
		return sc.next();
	}

	/**
	 *
	 */
	private void updateMenu(String id) {
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
				result = controller.updateName(id,name);
				displayResult("이름수정",result);
				break;
			case "2" : 
				birthday = inputBirthday("수정");
				result = controller.updateBirthday(id,birthday);
				displayResult("생일수정",result);
				break;
			case "3" : 
				email = inputEmail("수정");
				result = controller.updateEmail(id,email);
				displayResult("이메일수정",result);
				break;
			case "0" : return;
			default :
				System.out.println("> 잘못 입력하셨습니다.");
			}
			
		}
	}

	/**
	 * 사용자로부터 수정할 이메일 입력
	 */
	private String inputEmail(String mode) {
		System.out.print("> " + mode + "할 이메일 입력 : ");
		return sc.next();
	}

	/**
	 * 사용자로부터 수정할 생일 입력
	 */
	private Date inputBirthday(String mode) {
		System.out.print("> " + mode + "할 생일 입력 : ");
		return Date.valueOf(sc.next());
	}

	/**
	 * 한명의 member를 출력하는 메소드
	 */
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

	/**
	 * 사용자로부터 조회/삭제할 아이디 조회
	 * @param string
	 * @return
	 */
	private String inputId(String mode) {
		System.out.print("> " + mode + "할 아이디 입력 : ");
		return sc.next();
	}

	/**
	 * 여러명의 회원을 출력하는 메소드
	 * @param members
	 */
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

	private void displayResult(String type,int result) {
		System.out.println(type + " " + (result >0 ? "성공!" : "실패!"));
	}

	/**
	 * 사용자로부터 회원정보를 입력받는 메소드
	 */
	private Member inputMember() {
		System.out.println("> 새 회원정보를 입력하세요.");
		System.out.print("> ID : ");
		String id = sc.next();
		System.out.print("> 이름 : ");
		String name = sc.next();
		System.out.print("> 성별(M/F) : ");
		String gender = sc.next();
		System.out.println("> 생일(1999-09-09) : ");
		Date birthday = Date.valueOf(sc.next());
		System.out.print("> 이메일 : ");
		String email = sc.next();
		
		return new Member(id, name, gender, birthday, email, 0, null);
	}

}
