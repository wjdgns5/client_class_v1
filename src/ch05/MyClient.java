package ch05;

import java.io.IOException;
import java.net.Socket;

// 2 - 1 상속을 활용한 구현 클래스 설계 하기
public class MyClient extends AbstractClient {

	@Override
	protected void connectToServer() throws IOException {
		setSocket(new Socket("localhost", 5000));
		System.out.println("*** Connected to the server ***");
	}

	// 메인 함수
	public static void main(String[] args) {
		System.out.println("#### 클라이언트 실행 ####");
		MyClient client = new MyClient();
		client.run();
	}
}