package board;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {
	
	@Autowired
	BoardService service;
	
	@RequestMapping("/board/index.do")
	public String index(Model model, BoardVo vo) {
		model.addAttribute("list", service.selectAll(vo));
		return "board/index";  
	}
	
	@RequestMapping("/board/detail.do") // url 이름, 
	public String detail(Model model, BoardVo vo) { // method 이름, 
		model.addAttribute("vo", service.detail(vo));
		return "board/detail"; // jsp 이름을 맞추는 것이 좋음
	}
	
	@RequestMapping("/board/write.do")  
	public String write(Model model, BoardVo vo) { 
		return "board/write"; 
	}
	
	@RequestMapping("/board/insert.do")  
	public String insert(Model model, BoardVo vo, @RequestParam("file") MultipartFile file, HttpServletRequest req) { // 경로를 자동으로 가져오기 위해서 (getRealPath) 
//		service.insert(vo, filename, req)
		if (!file.isEmpty()) {  // 값(파일)이 있을때만 해야함. 첨부파일이 있으면, 
			try {
				String org = file.getOriginalFilename(); // 원본파일명
				String ext = ""; // 확장자
				
				ext = org.substring(org.lastIndexOf("."));
				String real = new Date().getTime()+ext; // 서버에 저장할 파일명 
	//			System.out.println("org:"+org);
	//			System.out.println("real:"+real);
				// 파일 저장
				String path = req.getRealPath("/upload/"); // 파일을 보관
	//			System.out.println(path);
				file.transferTo(new File(path+real)); // 경로+파일명
				
				// vo에 set
				vo.setFilename_org(org);
				vo.setFilename_real(real);
			} catch (Exception e) {
				
			}
		}
		int r = service.insert(vo);
		// r > 0 : 정상 → alert → 목록으로 이동
		// r == 0 : 비정상 → alert → 이전페이지로 이동(write.do)
		if (r > 0) {
			model.addAttribute("msg", "정상적으로 등록되었습니다.");
			model.addAttribute("url", "index.do");
		} else {
			model.addAttribute("msg", "등록실패");
			model.addAttribute("url", "write.do"); // alert만 따로 빼서 jsp파일을 만듦	
		}
		return "include/alert"; 
	}
	
	@RequestMapping("/board/edit.do")
	public String edit(Model model, BoardVo vo) {
		model.addAttribute("vo",service.edit(vo));
		return "board/edit";
	}
	
	@RequestMapping("/board/update.do")  
	public String update(Model model, BoardVo vo, @RequestParam("file") MultipartFile file, HttpServletRequest req) { // 경로를 자동으로 가져오기 위해서 (getRealPath) 
		if (!file.isEmpty()) {  // 값(파일)이 있을때만 해야함. 첨부파일이 있으면, 
			try {
				String org = file.getOriginalFilename(); // 원본파일명
				String ext = ""; // 확장자
				
				ext = org.substring(org.lastIndexOf("."));
				String real = new Date().getTime()+ext; // 서버에 저장할 파일명 
				// 파일 저장
				String path = req.getRealPath("/upload/"); // 파일을 보관
				file.transferTo(new File(path+real)); // 경로+파일명
				
				// vo에 set
				vo.setFilename_org(org);
				vo.setFilename_real(real);
			} catch (Exception e) {
				
			}
		}
		int r = service.update(vo);
		if (r > 0) {
			model.addAttribute("msg", "정상적으로 수정되었습니다.");
			model.addAttribute("url", "index.do");
		} else {
			model.addAttribute("msg", "수정실패");
			model.addAttribute("url", "edit.do?no="+vo.getNo()); // alert만 따로 빼서 jsp파일을 만듦	
		}
		return "include/alert"; 
	}
	
	@RequestMapping("/board/delete.do")  
	public String delete(Model model, BoardVo vo, HttpServletRequest req) { 
		int r = service.delete(vo);
		if (r > 0) {
			model.addAttribute("result", "true");
		} else {
			model.addAttribute("result", "false");
		}
		return "include/result"; 
	}
	
}
