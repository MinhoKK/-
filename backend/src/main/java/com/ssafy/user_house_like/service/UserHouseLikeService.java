package com.ssafy.user_house_like.service;


public interface UserHouseLikeService {
	// 해당 기능이 찜인지 취소인지 분기하는 기능
	public void checkLike(String userId, String aptId, String like) throws Exception;
	
	// 유저가 해당 아파트에 대해 찜하는 기능
	public void addLike(String userId, String aptId) throws Exception;
	
	// 유저가 해당 아파트 찜 취소하는 기능
	public void deleteLike(String userId, String aptId) throws Exception;
	
	// 해당 아파트가 찜 되어있나 점검하는 코드
	public int isLike(String userId, String aptId) throws Exception;
}
