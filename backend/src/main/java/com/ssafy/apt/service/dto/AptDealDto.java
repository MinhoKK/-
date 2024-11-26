package com.ssafy.apt.service.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class AptDealDto {
	private long dealAmount;
	private Date dealDate;
	private double area;
	private int floor;
}
