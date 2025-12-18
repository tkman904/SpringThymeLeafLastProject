package com.sist.web.service;

import java.util.*;

import com.sist.web.vo.*;

public interface SeoulService {
	public List<SeoulVO> seoulMainData(Map map);
	public List<SeoulVO> seoulListData(Map map);
	public int seoulTotalPage(Map map);
	public SeoulVO seoulDetailData(Map map);
	public List<FoodVO> seoulNearFoodHouse(String address);
}
