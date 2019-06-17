package com.kh.testJDBC1.model.vo;

import java.sql.Date;

public class Employee {
	
	/*
	 * VO (Value Object) == DTO(Data transfer Object)
	 * == DO(Domain Object) == Entity == Record == Row
	 * --> DB 테이블의 한 행의 정보가 기록되는 저장용 객체
	 * 
	 * 1. 반드시 캡슐화 적용 : 모든필드에 private
	 * 2. 모든 필드에 대하여 get/setter 작성
	 * 3. 기본생성자, 매개변수 있는 생성자 작성
	 * 
	 * + 필요 시 직렬화(java.io.Serializable) 상속
	 *  --> DB에 객체 정보를 직접 저장하고 읽어 오고 싶은 경우
	 * 
	 * */
	
	// 실습용 Employee 테이블은
	// Oracle에서 기본 제공해주는 학습용 계정
	// scott 계정의 테이블
	private int empNo; // 사번
	private String empName; // 사원명
	private String job; // 직책
	private int mgr; // 직속 상사(manager)
	private Date hireDate; // java.sql.Date , 입사일
	private int sal; // 급여
	private int comm; // 커미션(인센티브)
	private int deptNo; // 부서 번호
	
	public Employee() {	} // 기본 생성자

	// 매개변수가 있는 생성자
	public Employee(int empNo, String empName, String job, int mgr, Date hireDate, int sal, int comm, int deptNo) {
		this(empNo, empName, job, mgr, sal, comm, deptNo);
		this.hireDate = hireDate;
		
	}
		
		public Employee(int empNo, String empName, String job, int mgr, int sal, int comm, int deptNo) {
			this(job, sal, comm);
			this.empNo = empNo;
			this.empName = empName;
			this.mgr = mgr;
			this.deptNo = deptNo;
			
	}
		
		public Employee(String job, int sal, int comm) {
			super();
			this.job = job;
			this.sal = sal;
			this.comm = comm;
		}

		public int getEmpNo() {
			return empNo;
		}

		public void setEmpNo(int empNo) {
			this.empNo = empNo;
		}

		public String getEmpName() {
			return empName;
		}

		public void setEmpName(String empName) {
			this.empName = empName;
		}

		public String getJob() {
			return job;
		}

		public void setJob(String job) {
			this.job = job;
		}

		public int getMgr() {
			return mgr;
		}

		public void setMgr(int mgr) {
			this.mgr = mgr;
		}

		public Date getHireDate() {
			return hireDate;
		}

		public void setHireDate(Date hireDate) {
			this.hireDate = hireDate;
		}

		public int getSal() {
			return sal;
		}

		public void setSal(int sal) {
			this.sal = sal;
		}

		public int getComm() {
			return comm;
		}

		public void setComm(int comm) {
			this.comm = comm;
		}

		public int getDeptNo() {
			return deptNo;
		}

		public void setDeptNo(int deptNo) {
			this.deptNo = deptNo;
		}

		@Override
		public String toString() {
			return empNo + "\t" + empName + "\t" + empName + "\t" + job + "\t" + mgr + "\t"
					+ hireDate + "\t" + sal + "\t" + comm + "\t" + deptNo + "\t";
		}
		
		
	
}
