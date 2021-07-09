package news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	NewsDao dao;

	@Override
	public List<NewsVo> selectAll(NewsVo vo) {
		int totCount = dao.count(vo); // 총갯수
		// 총페이지수
		int totPage = totCount / vo.getPageRow();
		if (totCount % vo.getPageRow() > 0) totPage++;
		// 시작페이지
		int startPage = (vo.getReqPage()-1)/vo.getPageRange()*vo.getPageRange()+1;
		int endPage = startPage + (vo.getPageRange()-1);
		
		if(endPage > totPage) endPage = totPage;
		
		vo.setStartPage(startPage);
		vo.setEndPage(endPage);
		vo.setTotCount(totCount);
		vo.setTotPage(totPage); 
		return dao.selectAll(vo);
	}
	
	@Override
	public NewsVo detail(NewsVo vo) {
		dao.updateReadcount(vo);
		return dao.detail(vo);	
	}
	
	@Override
	public int insert(NewsVo vo) {
		return dao.insert(vo); // 이제 dao 만들러감
	}

	@Override
	public NewsVo edit(NewsVo vo) {
		return dao.detail(vo);	
	}

	@Override
	public int update(NewsVo vo) {
		if ("1".equals(vo.getIsDel())) {
			dao.delFilename(vo);
		}
		return dao.update(vo); 
	}

	@Override
	public int delete(NewsVo vo) {
		return dao.delete(vo);
	}
}
