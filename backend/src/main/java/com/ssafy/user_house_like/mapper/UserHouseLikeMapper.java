package com.ssafy.user_house_like.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.user_house_like.service.dto.UserHouseLikeDto;

@Mapper
public interface UserHouseLikeMapper {
	
	// 유저가 해당 아파트에 대해 찜하는 기능
	public void addLike(String userId, String aptId) throws SQLException;
	
	// 유저가 해당 아파트 찜 취소하는 기능
	public void deleteLike(String userId, String aptId) throws SQLException;
	
	// 해당 아파트가 찜 되어있나 점검하는 코드
	public int isLike(String userId, String aptId) throws SQLException;
	
}
