package com.sist.web.service;

import org.springframework.stereotype.Service;

import java.util.*;

import com.sist.web.vo.*;
import com.sist.web.mapper.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/*
 *	  @Autowired
 *    public SeoulServiceImpl(SeoulMapper mapper) {
 *    	this.mapper = mapper;
 *    }
 */
public class SeoulServiceImpl implements SeoulService {
	private final SeoulMapper mapper;

	@Override
	public List<SeoulVO> seoulMainData(Map map) {
		// TODO Auto-generated method stub
		return mapper.seoulMainData(map);
	}
}
