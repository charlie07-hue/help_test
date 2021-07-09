<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=yes">
    <meta name="format-detection" content="telephone=no, address=no, email=no">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>게시판 상세</title>
    <%@ include file ="/WEB-INF/view/include/head.jsp" %>
</head>
<body>
    <div class="wrap">
        <%@ include file ="/WEB-INF/view/include/top.jsp" %>
        <div class="sub">
            <div class="size">
                <h3 class="sub_title">게시판</h3>
                <div class="bbs">
                    <div class="view">
                        <div class="title">
                            <dl>
                                <dt>${vo.title} </dt>
                                <dd class="date">작성일 : ${vo.regdate} </dd>
                            </dl>
                        </div>
                        <div class="cont"><p>${vo.content}</p> </div>
                        <dl class="file">
                            <dt>첨부파일 </dt>
                            <dd>
                            <!-- img 파일은 다운이 안되고 브라우저에서 열린다. 그 외의 파일 (pdf, excel 등등은 바로 다운로드됨) -->
                            <a href="/project/common/download.jsp?path=/upload/&org=${vo.filename_org }&real=${vo.filename_real}"  
                            target="_blank">${vo.filename_org } </a></dd> 
                        </dl>
                                    
                        <div class="btnSet clear">
                            <div class="fl_l"><a href="index.do?&reqPage=${newsVo.reqPage }&stype=${param.stype}&sval=${param.sval}&orderby=${param.orderby}&direct=${param.direct}" class="btn">목록으로</a></div> 
                            <div class="fl_l"><a href="edit.do?no=${vo.no }" class="btn">수정</a></div>
                            <div class="fl_l"><a href="javascript:isDel();" class="btn">삭제</a></div>
                            <!-- index.do?&stype=${param.stype}&sval=${param.sval}&orderby=${param.orderby}&direct=${param.direct} -->
                        </div>                
                    </div>
                </div>
            </div>
        </div>
        <%@ include file ="/WEB-INF/view/include/bottom.jsp" %>
    </div>
    <script>
    	function isDel() {
    		if (confirm('삭제하시겠습니까?')) {
    			// 삭제처리
    			$.ajax({
    				url:'delete.do',
    				data: { // agax에서 데이터를 전송하는 속성
    					'no':${vo.no} // 'reqPage' : ${vo.reqPage} -> comma로 계속 추가 가능    					
    				},
    				method:'post',
    				success: function(res) { // ajax 라는 메소드 안에서 function이라는 함수를 호출을 하는데, 응답받은 결과를 res에 받아서 
    					if (res.trim() == 'true') {
    						alert('정상적으로 삭제되었습니다.');
    						location.href='index.do';
    					} else {
    						alert('삭제 실패');
    					}
    				},
    				error : function(res) {
    					console.log(res);
    				}
    			});
    		}
    	}
    </script>
</body>
</html>