package com.ssafy.apt.domain;

import java.sql.Timestamp;

public class AptDeal {
	
	private int dealId; // INT
	private String aptID;
	private long dealAmount;
	private String dealType;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private double area;
	private int floor;
	private String seller;
	private String buyer;
	private Timestamp registrationDate;
	private String brokerLocation;
	private Timestamp cancelDate;
	private String isCancelled;
	
}
