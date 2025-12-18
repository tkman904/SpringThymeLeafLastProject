package com.sist.web.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.vo.*;

import lombok.RequiredArgsConstructor;

import com.sist.web.service.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seoul/") // 공통 URI주소 설정
public class SeoulController {
	// 필요한 객체를 스프링으로 값을 얻어 온다 => 의존성 주입 (DI)
	private final SeoulService sService;
	private String[] tables = {"", "seoul_location", "seoul_nature", "seoul_shop"};
	
	public void seoul_common(String page, Model model, String tbl) {
		if(page == null) {
			page = "1";
		}
		int curpage = Integer.parseInt(page); // 현재 페이지
		int start = (curpage*12)-12;
		
		Map map = new HashMap();
		map.put("start", start);
		map.put("table_name", tbl);
		// 현재 페이지의 데이터 읽기
		List<SeoulVO> list = sService.seoulListData(map);
		// 0, 12, 24, ...
		
		// 총페이지 읽기
		int totalpage = sService.seoulTotalPage(map);
		
		// 블록별 처리
		final int BLOCK = 10;
		int startPage = ((curpage-1)/BLOCK*BLOCK)+1;
		int endPage = ((curpage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage) {
			endPage = totalpage;
		}
		
		model.addAttribute("list", list);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		model.addAttribute("main_html", tbl.replace("_", "/"));
	}
	
	@GetMapping("location")
	public String seoul_location(@RequestParam(name = "page", required = false) String page, Model model) {
		seoul_common(page, model, "seoul_location");
		
		return "main/main";
	}
	
	@GetMapping("nature")
	public String seoul_nature(@RequestParam(name = "page", required = false) String page, Model model) {
		seoul_common(page, model, "seoul_nature");
		
		return "main/main";
	}
	
	@GetMapping("shop")
	public String seoul_shop(@RequestParam(name = "page", required = false) String page, Model model) {
		seoul_common(page, model, "seoul_shop");
		
		return "main/main";
	}
	
	@GetMapping("detail")
	public String seoul_detail(@RequestParam("no") int no, @RequestParam("type") int type, Model model) {
		// DB 연동
		Map map = new HashMap();
		map.put("no", no);
		map.put("table_name", tables[type]);
		SeoulVO vo = sService.seoulDetailData(map);
		String address = vo.getAddress();
		address = address.substring(address.indexOf(" ")+1);
		vo.setAddress(address.trim());
		
		model.addAttribute("vo", vo);
		
		String[] addr = vo.getAddress().split(" ");
		String addr1 = addr[1].trim();
		System.out.println(addr1);
		List<FoodVO> list = sService.seoulNearFoodHouse(addr1);
		
		model.addAttribute("list", list);
				
		model.addAttribute("main_html", "seoul/detail");
		
		return "main/main";
	}
}
