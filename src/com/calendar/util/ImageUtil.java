package com.calendar.util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/* 해당 URL에서 이미지를 불러오기 위한 클래스 */
public class ImageUtil {
	public static Image getImage(String url) {
		Image image = null;
		try {
			URL u = new URL(url); // 해당 서버의 url에 대한 객체 생성
	        image = ImageIO.read(u); // 서버로 부터 이미지 가져옴
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		return image;
	}
}


