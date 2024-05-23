package ch05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class AbstractClient {

	private Socket socket;
	private PrintWriter writerStream;
	private BufferedReader readerStream;
	private BufferedReader keyboardReader;

	public final void run() {
		try {
			connectToServer();
			setupStreams();
			startCommunication();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			cleanup();
		}
	}

	protected abstract void connectToServer() throws IOException;

	private void setupStreams() throws IOException {
		writerStream = new PrintWriter(socket.getOutputStream(), true);
		readerStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		keyboardReader = new BufferedReader(new InputStreamReader(System.in));
	}

	private void startCommunication() throws InterruptedException {
		Thread readThread = createReadThread();
		Thread writeThread = createWriteThread();

		readThread.start();
		writeThread.start();

		readThread.join();
		writeThread.join();
	}

	private Thread createReadThread() {
		return new Thread(() -> {
			try {
				String serverMessage;
				while ((serverMessage = readerStream.readLine()) != null) {
					System.out.println("서버에서 온 msg: " + serverMessage);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private Thread createWriteThread() {
		return new Thread(() -> {
			try {
				String clientMessage;
				while ((clientMessage = keyboardReader.readLine()) != null) {
					writerStream.println(clientMessage);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	protected void setSocket(Socket socket) {
		this.socket = socket;
	}

	private void cleanup() {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}