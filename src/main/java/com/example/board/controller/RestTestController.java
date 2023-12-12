package com.example.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@RestController
public class RestTestController {

    @GetMapping("/apitest")
    public String callApiWithXml() {
        StringBuffer result = new StringBuffer();
        try {
            String apiUrl = "http://apis.data.go.kr/6260000/AttractionService/getAttractionKr?" +
                    "serviceKey=sw83zO%2FbpzYjWuo1H87gY9UM0BIjoxFClZDk%2F6NAQsKHnA0QZtLcxPD%2FeATrTuc7CEm8Dw59RaFsyrt%2BxkfkDw%3D%3D" +
                    "&numOfRows=140";
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            
            String returnLine;
            result.append("<xmp>");
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine + "\n");
            }
            urlConnection.disconnect();
          
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result + "</xmp>";
    }
    
    
    
    
    
}