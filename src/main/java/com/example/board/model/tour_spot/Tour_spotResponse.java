package com.example.board.model.tour_spot;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.StreamingHttpOutputMessage.Body;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="response")
public class Tour_spotResponse {
	
	@XmlElement(name="header")
	private Header header;
	@XmlElement(name="body")
	private Body body;
	
	@XmlRootElement(name="header")
	private static class Header {
		private String resultCode; // 결과코드

		private String resultMsg; // 결과메시지
	}
	
	@Data
	@XmlRootElement(name="body")
	public
	static class Body {
		
		private Items items;
		
		private Long numOfRows; // 한 페이지 결과 수

		private Long pageNo; // 페이지 번호

		private Long totalCount; // 전체 결과 수
		
		@Data
		@XmlRootElement(name="items")
		public
		static class Items {
			
			private List<Item> item;
			
			@Data
			@XmlRootElement(name="item")
			public static class Item {
				private Long UC_SEQ; // 콘텐츠ID

				private String MAIN_TITLE; // 콘텐츠명

				private String GUGUN_NM; // 구군

				private double LAT; // 위도

				private double LNG; // 경도
				
				private String PLACE; // 여행지
				
				private String TITLE; // 제목

				private String SUBTITLE; // 부제목

				private String ADDR1; // 주소

				private String CNTCT_TEL; // 연락처

				private String HOMEPAGE_URL; // 홈페이지

				private String TRFC_INFO; // 교통정보

				private String USAGE_DAY; // 운영일

				private String HLDY_INFO; // 휴무일

				private String USAGE_DAY_WEEK_AND_TIME; // 운영 및 시간

				private String USAGE_AMOUNT; // 이용요금

				private String MIDDLE_SIZE_RM1; // 편의시설

				private String MAIN_IMG_NORMAL; // 이미지URL

				private String MAIN_IMG_THUMB; // 썸네일이미지URL

				private String ITEMCNTNTS; // 상세내용
			}
			
		}
		
	}
	
}


