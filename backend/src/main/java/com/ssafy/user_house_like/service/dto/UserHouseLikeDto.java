package com.ssafy.user_house_like.service.dto;

import lombok.Data;

@Data
public class UserHouseLikeDto {
	private String userId;
	private String aptId;
	private String like; // client로부터 클릭했을 때, 현재 찜 상태(on, off)
}
