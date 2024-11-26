package com.ssafy.user_house_like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.user_house_like.mapper.UserHouseLikeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserHouseLikeServiceImpl implements UserHouseLikeService {
	
	private final UserHouseLikeMapper userHouseLikeMapper;
	

	@Override
	public void checkLike(String userId, String aptId, String like) throws Exception {
		if(like.equals("on")) {
			System.out.println("삭제");
			deleteLike(userId, aptId);
		} else if(like.equals("off")) {
			System.out.println("추가");
			addLike(userId, aptId);
		}
		return;
	}
	
	@Override
	@Transactional
	public void addLike(String userId, String aptId) throws Exception {
		userHouseLikeMapper.addLike(userId, aptId);
		return;

	}

	@Override
	@Transactional
	public void deleteLike(String userId, String aptId) throws Exception {
		userHouseLikeMapper.deleteLike(userId, aptId);
		return;
	}

	@Override
	public int isLike(String userId, String aptId) throws Exception {
		return userHouseLikeMapper.isLike(userId, aptId);
		
	}
	
	


}
