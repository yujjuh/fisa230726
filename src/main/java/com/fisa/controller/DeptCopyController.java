package com.fisa.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisa.exception.DeptNotFoundException;
import com.fisa.model.dao.DeptCopyRepository;
import com.fisa.model.domain.entity.DeptCopy;

@RestController
public class DeptCopyController {
	
	//DAO를 멤버로 선언 및 자동 초기화
	@Autowired
	private DeptCopyRepository dao;
	
	//특정 부서 번호로 검색
	//http://localhost/guestbook/deptone?deptno=10 존재하는 부서 번호 검색시의 해결
	/* Optional API는 객체를 보유하게 되는 객체 컨테이너
	 * 데이터가 있으면 get()로 데이터 활용
	 * 없으면 get() 사용 금지, 에러 발생
	 * Optional 장점 : 간결한 코드로 예외 처리 가능
	 */
	@GetMapping("/deptone")
	public DeptCopy getDept(int deptno) throws Exception{
		
		System.out.println(deptno);
		
		/* id값으로 spring data jpa의 메소드가 검색해서 반환
		 * 
		 */
		
//		System.out.println(dao.findById(deptno).get()); //데이터가 없을 경우 실행 예외 발생
		Optional<DeptCopy> dept = dao.findById(deptno);
		System.out.println(dept);
		
		/* 부서번호 존재시 : DeptCopy(deptno=10, dname=회계, loc=NEW YORK)
		 * 부서번호 미 존재시 : 데이터 무
		 * 개발자 관점에서 확인용 코드로 간주
		 */
		dept.ifPresentOrElse(System.out::println, () -> System.out.println("데이터 무"));
		dept.ifPresentOrElse(System.out::println, Exception::new);
		
		//client에게 상태 보고 로직
		//데이터가 없을땐 error.jsp등으로 일괄 메세지 위임 즉 예외 발생을 유도
		dept.orElseThrow(Exception::new); //데이터 null인 경우 예외 생성 및 메소드 현 라인에서 실행 중지
		
		//? 검색된 데이터 반환
		return dept.get(); //예외 발생이 안된 경우에만 실행 즉 데이터가 있을 경우에만 get()
	}
	
	//모든 검색
	@GetMapping("/deptall")
	public Iterable<DeptCopy> getDeptAll(){
		System.out.println("----");
		return dao.findAll();
//		return null;
	}
	
	
	//특정 부서 번호로 삭제 ..
	@GetMapping("/deletedept")
	public String deleteDept(int deptno) throws DeptNotFoundException {
		
		dao.findById(deptno).orElseThrow(DeptNotFoundException::new);
		dao.deleteById(deptno);
		
		return "삭제 완료";
	}
	
	
	
	//DeptNotFoundException 예외 전담 처리 메소드
	@ExceptionHandler(DeptNotFoundException.class)
	public String exHandler(DeptNotFoundException e) {
		e.printStackTrace();
		
		return "해당 부서는 존재하지 않습니다.";
	}
	
	//예외 전담 처리 메소드
	@ExceptionHandler
	public String exHandler(Exception e) {
		e.printStackTrace();
		
		return "요청시 입력 데이터 재 확인 부탁합니다";
	}
	
	
}
