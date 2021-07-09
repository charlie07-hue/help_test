package board;  

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {
	
	List<BoardVo> selectAll(BoardVo vo); // HttpServletRequest req 있다면 set 불요
	BoardVo detail(BoardVo vo); // BoardVo 호출하는 추상메서드 
	int insert(BoardVo vo);
	BoardVo edit(BoardVo vo);
	int update(BoardVo vo);
	int delete(BoardVo vo);
}
