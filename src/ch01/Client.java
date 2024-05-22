package ch01;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args) {
		// 클라이언트 측 -- 소켓통신을 위해서 준비물
		// 1. 서버측 컴퓨터에 주소:포트번호  가 필요하다.
		// 2. 서버측과 연결 될 기본 소켓이 필요하다.
		
		// try - catch - resource
		// 생성자 매개변수에 서버측 (IP주소, 포트번호)
		try( // loopBack -> 자기 자신 IP주소
				Socket socket = new Socket("localhost", 5000)
				) {
			// new Socket("localhost", 5000) -> 객체 생성 시 서버측과 연결 되어서   // "192.168.0.133"
			// 스트림을 활용할 수 있다.
			
			// 대상은 소켓이다.
			OutputStream output = socket.getOutputStream(); // 소켓에서 기반 스트림 꺼냄
			PrintWriter writer = new PrintWriter(output, true); // 기능 확장 - 보조 스트림
			writer.println("Hello, Sever!"); // 데이터를 줄 단위로 보낸다.
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	} // end of main

} // end of class
