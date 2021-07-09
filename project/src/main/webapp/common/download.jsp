<%@page import="ch.qos.logback.core.recovery.ResilientSyslogOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>
<%
// 3개의 파라미터를 받음
// 경로, 원본파일명, 실제파일명
String path = request.getParameter("path");
String org = URLEncoder.encode(request.getParameter("org"), "UTF-8");
String real = request.getParameter("real");

// 실제경로
path = request.getRealPath(path+real);

try {
	// 파일객체 생성
	File f = new File(path); 
	// 입력객체(서버)
	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
	// 출력객체(브라우저)
	BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
	// 초기화
	response.reset();
	// 브라우저 헤더 설정
	response.setHeader("Content-Type", "application/octet-stream"); // browser에서 정의해놓은 헤더이기 때문에 무조건 써줘야함
	response.setHeader("Content-Disposition", "attachment;filename="+org+";"); // browser을 속이는것. 이 파일 이름은 ""이야!. 다운은 real, 저장은 org 이름으로
	response.setHeader("Content-Transfer-Encoding", "binary");
	response.setHeader("Content-Length", f.length()+""); // 용량
	// 스트림 출력
	byte[] b = new byte[1024];
	for (int i = 0; (i=bis.read(b)) != -1;) {
		bos.write(b, 0, i);
		bos.flush();
	}
	bis.close();
	bos.close();
} catch (Exception e) {
	System.out.println(e.getMessage());
}
%>