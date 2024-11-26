package com.ssafy.checklist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.checklist.domain.Checklist;
import com.ssafy.checklist.domain.ChecklistApt;
import com.ssafy.checklist.domain.ChecklistAptItem;
import com.ssafy.checklist.domain.ChecklistItem;
import com.ssafy.checklist.service.ChecklistService;
import com.ssafy.checklist.service.dto.ChecklistDto;
import com.ssafy.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/checklist")
public class ChecklistController {

	private final ChecklistService checklistService;
	private final JWTUtil jwtUtil;
	
	@GetMapping()
	public ResponseEntity<?> checklistView(HttpServletRequest request) throws Exception{
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		
		if(checklistService.findChecklist(userId) == null) {
			checklistService.registChecklist(userId);
		}

		Checklist checklist =  checklistService.findChecklist(userId);
		List<ChecklistItem> items = checklistService.findAllChecklistItem(checklist.getChecklistId());
		ChecklistDto.ChecklistResponse checklistResponse = new ChecklistDto.ChecklistResponse(checklist.getChecklistId(), items);
		
		return ResponseEntity.ok(checklistResponse);
	}
	
	@PostMapping("/item")
	public ResponseEntity<?> registChecklistItem(@RequestBody ChecklistDto.ChecklistItemRegist checklistItemRegist) throws Exception{
		checklistService.registChecklistItem(checklistItemRegist.getChecklistId(), checklistItemRegist.getItem());		
		return ResponseEntity.status(HttpStatus.CREATED)
				.build();
	}
	
	@DeleteMapping("/item/{itemId}")
	public ResponseEntity<?> deleteChecklistItem(@PathVariable int itemId) throws Exception{
		checklistService.deleteChecklistItem(itemId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.build();
	}
	
	// 나의 체크리스트 목록 조회
	@GetMapping("/mypage")
	public ResponseEntity<?> findMyChecklist(HttpServletRequest request) throws Exception{
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		
		List<AptDto> checklists = checklistService.findMyChecklist(userId);
		return ResponseEntity.ok()
				.body(checklists);
	}

	// 아파트별 체크리스트 및 체크리스트 아이템 생성
	@PostMapping("/apt/{aptId}")
	public ResponseEntity<?> registChecklistItem(@PathVariable String aptId,
												HttpServletRequest request) throws Exception{
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		
		int checklistAptId = checklistService.registChecklistApt(aptId, userId);
		
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(checklistAptId);
	}

	// 아파트 체크리스트 번호 조회
	@GetMapping("/apt/{aptId}")
	public ResponseEntity<?> checklistApt(@PathVariable String aptId, HttpServletRequest request) throws Exception{
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		
		ChecklistApt checklistApt = checklistService.findChecklistApt(aptId, userId);
		log.info(checklistApt.toString());
		
		return ResponseEntity.ok()
				.body(checklistApt);
	}
	
	// 아파트별 체크리스트 아이템 조회
	@GetMapping("/apt/item/{checklistAptId}")
	public ResponseEntity<?> checklistAptItem(@PathVariable int checklistAptId) throws Exception{
		List<ChecklistAptItem> items = checklistService.findAllChecklistAptItem(checklistAptId);
		
		return ResponseEntity.ok()
				.body(items);
	}
	
	// 아파트별 체크리스트 아이템 상태 변환
	@PatchMapping("/apt/item/checked/{itemId}")
	public ResponseEntity<?> changeChecked(@PathVariable int itemId) throws Exception{
		checklistService.changeChecked(itemId);
		return ResponseEntity.ok()
				.build();
	}
	
	@DeleteMapping("/apt/{checklistAptId}")
	public ResponseEntity<?> deleteChecklistApt(@PathVariable int checklistAptId) throws Exception{
		checklistService.deleteChecklistApt(checklistAptId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.build();
	}
	
}
