package com.example.board.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import com.example.board.model.tour_spot.Tour_spotResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class T_ApiService {
	 
	public Tour_spotResponse xmlToJavaObject() {

    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/AttractionService/getAttractionKr"); /*URL*/
    try {
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("sw83zO/bpzYjWuo1H87gY9UM0BIjoxFClZDk/6NAQsKHnA0QZtLcxPD/eATrTuc7CEm8Dw59RaFsyrt+xkfkDw==", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*공공데이터포털에서 받은 인증키*/
    try {
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("140", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*월 단위 모집공고일 (검색시작월)*/
    
    URL url = null;
		try {
			url = new URL(urlBuilder.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    conn.setRequestProperty("Content-type", "application/json");

    BufferedReader rd = null;
    try {
    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
        
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    StringBuilder sb = new StringBuilder();
    String line;
    try {
			while ((line = rd.readLine()) != null) {
			    sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    try {
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    conn.disconnect();
    
    // String 형식의 xml
    String xml = sb.toString();

    // String 형식의 xml을 Java Object인 Response로 변환
    Tour_spotResponse apiResponse = new Tour_spotResponse();
    try {
        JAXBContext jaxbContext = JAXBContext.newInstance(Tour_spotResponse.class); // JAXB Context 생성
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
        apiResponse = (Tour_spotResponse) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출
        
    } catch (JAXBException e) {
        e.printStackTrace();
    }
    return apiResponse;
  }
   
   
}
