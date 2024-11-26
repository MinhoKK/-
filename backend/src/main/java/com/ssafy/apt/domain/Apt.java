package com.ssafy.apt.domain;

import lombok.Data;

public class Apt {
    private String aptId;  // VARCHAR(15)
    private String aptName;  // VARCHAR(30)
    private String building;  // VARCHAR(30)
    private int constructionYear;  // INT
    private String roadAddress;  // VARCHAR(50)
    private String legalAddress;  // VARCHAR(50)
    private double lat;  // DOUBLE
    private double lon;  // DOUBLE
    private String regionCode;  // VARCHAR(10)
    private String roadName;  // VARCHAR(20)
    private String roadBuildingMainCode;  // VARCHAR(4)
    private String roadBuildingSubCode;  // VARCHAR(4)
    private String roadId;  // VARCHAR(4)
    private String roadGroundCode;  // VARCHAR(4)
    private String roadCode;  // VARCHAR(8)
    private String legalDong;  // VARCHAR(10)
    private String legalDongMainCode;  // VARCHAR(10)
    private String legalDongSubCode;  // VARCHAR(10)
    private String legalDongSigunguCode;  // VARCHAR(10)
    private String legalDongEupmyeondongCode;  // VARCHAR(10)
    private String legalDongLotCode;  // VARCHAR(4)
    private String legalDongFullCode; // VARCHAR(10)
    

}
