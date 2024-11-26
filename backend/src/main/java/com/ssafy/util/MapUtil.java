package com.ssafy.util;

import java.util.List;
import java.util.Map;

public class MapUtil {
	
    public static String convertToPolygonString(List<Map<String, Double>> coordinates) {
        StringBuilder polygonString = new StringBuilder("ST_GeomFromText('POLYGON((");

        for (Map<String, Double> coordinate : coordinates) {
            double lat = coordinate.get("lat");
            double lng = coordinate.get("lng");
            polygonString.append(lat).append(" ").append(lng).append(", ");
        }

        // 마지막 좌표와 첫 번째 좌표를 연결하여 다각형을 완성
        Map<String, Double> firstCoordinate = coordinates.get(0);
        polygonString.append(firstCoordinate.get("lat")).append(" ")
                      .append(firstCoordinate.get("lng")).append("))");

        // 좌표 체계를 추가
        polygonString.append("', 4326)");

        return polygonString.toString();
    }
    
    // 위경도 데이터를 String으로 변환
    public static String convertToPointString(double lat, double lon) {
        int srid = 4326; // SRID 지정 (4326은 일반적으로 위도와 경도를 나타냄)
        StringBuilder sb = new StringBuilder();
        sb.append("ST_GeomFromText('POINT(")
          .append(lat)
          .append(" ")
          .append(lon)
          .append(")', ")
          .append(srid)
          .append(")");
        return sb.toString();
    }

}
