package member.view;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

/** 
 * view단 클래스
 * - 메뉴노출, 사용자입력값처리, 결과값출력
 * 
 */
public class MemberMenu {
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController(); 
	
	public void mainMenu() {
		String menu = "===== 회원관리 =====\n"
					+ "1. 회원 전체 조회\n"
					+ "2. 회원 아이디 조회\n"
					+ "3. 회원 이름 검색\n"
					+ "4. 회원 가입\n"
					+ "5. 회원 정보 변경\n"
					+ "6. 회원 탈퇴\n"
					+ "7. 탈퇴 회원 조회\n"
					+ "0. 프로그램 종료\n"
					+ "------------------\n"
					+ "선택 : ";
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			
			Member member = null;
			int result = 0;
			String name = null;
			String id = null;
			List<Member> list = null;
			
			switch (choice) {
			case "1":
				list = memberController.selectAll();
				printMemberList(list);
				break;
			case "2":
				id = inputMemberId();
				member = memberController.selectOne(id);
				printMember(member);
				break;
			case "3": 
				name = inputMemberName();
				list = memberController.findMemberByName(name);
				printMemberList(list);
				break;
			case "4": 
				member = inputMember();
				result = memberController.insertMember(member);
				printResultMsg(result, "회원 가입 성공!", "회원 가입 실패!");
				break;			
			case "5":
				updateMenu(); 
				break;
			case "6":
				id = inputMemberId();
				result = memberController.deleteMember(id);
				printResultMsg(result, "회원 탈퇴 성공!", "회원 탈퇴 실패!");
				break;
			case "7" :
				list = memberController.secessionAll();
				printDelMemberList(list);
				break;
			case "0": return;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}

	private String inputMemberId() {
		System.out.println("> 회원 아이디를 입력하세요.");
		System.out.print("아이디 : ");
		return sc.next();
	}

	/**
	 * 회원정보 수정 메뉴 
	 * 1. 수정메뉴 보이기전에 입력한 사용자 id에 해당하는 정보를 조회/출력
	 * 		- 조회된 회원이 없다면 메인메뉴로 돌아갈것
	 * 2. 선택한 메뉴에 해당하는 컬럼을 수정처리
	 * 3. 다시 수정메뉴가 보이기전에 해당 회원 정보 출력
	 */
	private void updateMenu() {
		String menu = "----------- 회원 정보 수정 -----------\n"
					+ "1. 이름 변경\n"
					+ "2. 생일 변경\n"
					+ "3. 이메일 변경\n"
					+ "4. 주소 변경\n"
					+ "0. 메인메뉴로 돌아가기\n"
					+ "-----------------------------------\n"
					+ "선택 : ";
		
		String id = inputMemberId();
		
		while(true) {
			Member member = memberController.selectOne(id);
			if(member == null) {
				System.out.println("> 조회된 회원이 없습니다.");
				return;
			}
			else {
				printMember(member);
			}
			
			System.out.print(menu);
			String choice = sc.next();
			String colName = null;
			Object newValue = null; // String, Date를 모두 처리
			
			switch(choice) {
			case "1" : 
				System.out.print("변경할 이름 : ");
				colName = "name";
				newValue = sc.next();
				break;
			case "2" : 
				System.out.print("변경할 생일(예:19990909) : ");
				colName = "birthday";
				String _birthday = sc.next();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				try {
					long millis = sdf.parse(_birthday).getTime();
					newValue = new Date(millis);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				 
				break;
			case "3" : 
				System.out.print("변경할 이메일 : ");
				colName = "email";
				newValue = sc.next();
				break;
			case "4" : 
				System.out.print("변경할 주소 : ");
				colName = "address";
				sc.nextLine(); // 개행날리기용
				newValue = sc.nextLine();
				break;
			case "0" : return;
			default : 
				System.out.println("잘못 입력하셨습니다.");
				continue;
			}
			
			int result = memberController.updateMember(id, colName, newValue);
			printResultMsg(result, "회원정보 수정 성공!", "회원정보 수정 실패!");
			
		}
		
	}

	/**
	 * 회원 1명 출력 메소드
	 * - null
	 * - member 1개
	 * @param member
	 */
	private void printMember(Member member) {
		if(member == null) {
			System.out.println("> 조회된 회원이 없습니다.");
		}
		else {
			System.out.println("==========================");
			System.out.printf("id : %s%n", member.getId());
			System.out.printf("name : %s%n", member.getName());
			System.out.printf("gender : %s%n", member.getGender());
			System.out.printf("birthday : %s%n", member.getBirthday());
			System.out.printf("email : %s%n", member.getEmail());
			System.out.printf("address : %s%n", member.getAddress());
			System.out.printf("regDate : %s%n", member.getRegDate());
			System.out.println("---------------------------");
			System.out.println();
		}
	}
	
	/**
	 * 여러행 조회결과 출력 메소드
	 * (list가 null인 경우 없음)
	 * - 0행
	 * - n행
	 * @param list
	 */
	public void printMemberList(List<Member> list) {
		if(list == null || list.isEmpty()) {
			System.out.println("> 조회된 행이 없습니다.");
		}
		else {
			System.out.println("> 조회결과");
			System.out.print("=====================================================");
			System.out.println("=====================================================");
			System.out.printf("%10s%10s%15s%10s%15s%20s%20s%n", 
								"id", "name", "gender", "birthday", "email", "address", "regDate");
			System.out.print("-----------------------------------------------------");
			System.out.println("-----------------------------------------------------");
			for(Member member : list) {
				System.out.printf("%10s%10s%10s%15s%20s%20s%30s%n", 
						member.getId(), 
						member.getName(),
						member.getGender(),
						member.getBirthday(),
						member.getEmail(),
						member.getAddress(),
						member.getRegDate());
			}
			System.out.print("-----------------------------------------------------");
			System.out.println("-----------------------------------------------------");
		}
		System.out.println();
	}
	
	/**
	 * 여러행 조회결과 출력 메소드
	 * (list가 null인 경우 없음)
	 * - 0행
	 * - n행
	 * @param list
	 */
	public void printDelMemberList(List<Member> list) {
		if(list == null || list.isEmpty()) {
			System.out.println("> 조회된 행이 없습니다.");
		}
		else {
			System.out.println("> 조회결과");
			System.out.print("=====================================================");
			System.out.println("=====================================================");
			System.out.printf("%10s%10s%15s%10s%15s%20s%20s%25s%n", 
								"id", "name", "gender", "birthday", "email", "address", "regDate", "delDate");
			System.out.print("-----------------------------------------------------");
			System.out.println("-----------------------------------------------------");
			for(Member member : list) {
				System.out.printf("%10s%10s%10s%15s%20s%15s%30s|%10s%n", 
						member.getId(), 
						member.getName(),
						member.getGender(),
						member.getBirthday(),
						member.getEmail(),
						member.getAddress(),
						member.getRegDate(),
						member.getDelDate());
			}
			System.out.print("-----------------------------------------------------");
			System.out.println("-----------------------------------------------------");
		}
		System.out.println();
	}
	
	/**
	 * 조회할 회원명 입력 메소드
	 * @return
	 */
	private String inputMemberName() {
		System.out.println("> 조회할 이름을 입력하세요.");
		System.out.print("이름 : ");
		return sc.next();
	}

	/**
	 * 신규회원정보를 입력받는 메소드
	 * @return
	 */
	private Member inputMember() {
		Member member = new Member();
		System.out.println("> 신규회원정보를 입력하세요.");
		while(true) {
			System.out.print("아이디 : ");
			String id = sc.next();
			Member checkId = memberController.selectOne(id);
			if(checkId == null) {
				System.out.println("사용 가능한 아이디 입니다 :)");
				member.setId(id);
				break;
			} else {
				System.out.println("사용 불가능한 아이디 입니다. 다시 입력하세요.");
				continue;
			}
		}
		System.out.print("이름 : ");
		member.setName(sc.next());
		System.out.print("성별(M/F) : ");
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));
		System.out.print("생일(예:19990314) : ");
		String _birthday = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			long millis = sdf.parse(_birthday).getTime();
			Date birthday = new Date(millis);
			member.setBirthday(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.print("이메일 : ");
		member.setEmail(sc.next());
		
		System.out.print("주소 : ");
		sc.nextLine(); // 개행 날리기용
		member.setAddress(sc.nextLine());
		
		return member;
	}

	/**
	 * DML 처리 결과를 출력하는 메소드
	 * @param result
	 * @param successMsg
	 * @param failureMsg
	 */
	private void printResultMsg(int result, String successMsg, String failureMsg) {
		if(result > 0)
			System.out.println("> " + successMsg);
		else
			System.out.println("> " + failureMsg);
		System.out.println();
	}	
	
	

}
