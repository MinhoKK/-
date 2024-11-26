package com.ssafy.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.token.service.RefreshTokenService;
import com.ssafy.user.domain.User;
import com.ssafy.user.mapper.UserMapper;
import com.ssafy.user.service.dto.UserDto;
import com.ssafy.user.service.dto.UserDto.UserModify;

@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final RefreshTokenService refreshTokenService;
	
	public UserServiceImpl(UserMapper userMapper, RefreshTokenService refreshTokenService) {
		super();
		this.userMapper = userMapper;
		this.refreshTokenService = refreshTokenService;
	}

	@Override
	@Transactional
	public void join(User user) throws Exception {
		if(user.getUserId().equals("")) {
			throw new IllegalArgumentException("아이디를 입력해주세요");
		} else if(user.getPassword().equals("")) {
			throw new IllegalArgumentException("비밀번호를 입력해주세요.");
		} else if(user.getName().equals("")) {
			throw new IllegalArgumentException("이름을 입력해주세요.");
		} else if(user.getAddress().equals("")) {
			throw new IllegalArgumentException("주소를 입력해주세요.");
		}
		
		userMapper.join(user);
	}
	
	@Override
	public User login(User user) throws Exception {
		User findUser = userMapper.login(user);
		
		if(user.getUserId().equals("")) {
			throw new IllegalArgumentException("아이디를 입력해주세요");
		} else if(user.getPassword().equals("")) {
			throw new IllegalArgumentException("비밀번호를 입력해주세요.");
		} else if(findUser == null) {
			throw new NullPointerException("존재하지 않는 회원이거나 비밀번호가 틀렸습니다.");
		}
		
		
		return userMapper.login(findUser);
	}

	@Override
	@Transactional
	public void modify(UserDto.UserModify userDto) throws Exception {
	    if (userDto.getPasswordCheck() == null || 
	    		userDto.getPasswordCheck().isEmpty() || 
	    		userDto.getPassword().equals(userDto.getPasswordCheck())) {
	        userMapper.modify(userDto);
	    } else {
	        throw new Exception("비밀번호가 일치하지 않습니다.");
	    }
	}

	@Override
	public User findByUserId(String userId) throws Exception {
		return userMapper.findByUserId(userId);
	}

	
	@Override
	public List<User> findAll(Map<String, String> map) throws Exception {
		return userMapper.findAll(map);
	}

	@Override
	@Transactional
	public void modifyByAdmin(User user) throws Exception {
		userMapper.modifyByAdmin(user);
	}

	@Override
	@Transactional
	public void delete(String userId) throws Exception {
		userMapper.delete(userId);
	}

	@Override
	@Transactional
	public void saveRefreshToken(String userId, String token) throws Exception{
		refreshTokenService.saveRefreshToken(userId, token);
	}

	@Override
	@Transactional
	public void deleteRefreshTokenByUserId(String userId) throws Exception {
		refreshTokenService.deleteByUserId(userId);
	}

	@Override
	public List<AptDto> findLikeAptList(String userId) throws Exception {
		return userMapper.findLikeAptList(userId);
		
	}

}
