package board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDao dao;

	@Override
	public List<BoardVo> selectAll(BoardVo vo) {
		int totCount = dao.count(vo); // 총갯수
		// 총페이지수
		int totPage = totCount / vo.getPageRow();
		if (totCount % vo.getPageRow() > 0) totPage++;
		// 시작페이지
		int startPage = (vo.getReqPage()-1)/vo.getPageRange()*vo.getPageRange()+1;
		int endPage = startPage + (vo.getPageRange()-1);
//		int startIdx = (vo.getReqPage() -1) * vo.getPageRow(); // 시작인덱스
//		vo.setStartIdx(startIdx); // 구하기만 하면 안되고 set을 해야함
		
		if(endPage > totPage) endPage = totPage; // endPage가 더 크면, 맨 마지막페이지(endPage)를 총페이지로 바꿔버린다. 그러나 10페이지 넘어가면 11, 12, 13만 보이고 이전으로 돌아갈수 x
		
		vo.setStartPage(startPage);
		vo.setEndPage(endPage);
		vo.setTotCount(totCount);
		vo.setTotPage(totPage); // 새로 구한 값, 그때그때 바뀌니까 set해줘야
		return dao.selectAll(vo);
	}
	
	@Override
	public BoardVo detail(BoardVo vo) {
		dao.updateReadcount(vo);
		return dao.detail(vo);	
	}
	
	@Override
	public int insert(BoardVo vo) {
		return dao.insert(vo); // 이제 dao 만들러감
	}

	@Override
	public BoardVo edit(BoardVo vo) {
		return dao.detail(vo);	
	}

	@Override
	public int update(BoardVo vo) {
		if ("1".equals(vo.getIsDel())) {
			dao.delFilename(vo);
		}
		return dao.update(vo); 
	}

	@Override
	public int delete(BoardVo vo) {
		return dao.delete(vo);
	}
}
