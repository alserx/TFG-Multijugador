package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import controller.GameController;

public class GameClient {
	private String serverIp;
	private int serverPort;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Thread listenerThread;

	private GameController gameController;

	public GameClient(String serverIP, int serverPort) {
		this.serverIp = serverIP;
		this.serverPort = serverPort;
	}

	/**
	 * Starts a new connection to the server
	 * 
	 * @return True if the connection was successful, false in other case
	 */
	public boolean init(GameController gameController) {
		this.gameController = gameController;
		try {
			socket = new Socket(serverIp, serverPort);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}

		listenerThread = new Thread(new ServerListener());
		listenerThread.start();

		return true;
	}

	/**
	 * Sends a message to the server if it is not null
	 * 
	 * @param message the message to send to the server
	 */
	public void sendMessage(String message) {
		if (out != null) {
			out.println(message);
		} else {
			System.err.println("Error: Output stream is not initialized.");
		}
	}

	/**
	 * Closes the client connection and the server listener
	 */
	public void close() {
		try {
			if (listenerThread != null && listenerThread.isAlive()) {
				listenerThread.interrupt();
			}
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			System.err.println("Error closing client resources: " + e.getMessage());
		}
	}

	/**
	 * Helper class that listens to the server and parses the messages coming from
	 * the server
	 * 
	 * @author Alvaro Serna
	 *
	 */
	private class ServerListener implements Runnable {
		@Override
		public void run() {
			try {
				String response;
				while (!Thread.currentThread().isInterrupted()) {
					if ((response = in.readLine()) != null && gameController != null) {
						System.out.println("Server: " + response);
						gameController.onMessageReceived(response);
					}
				}
			} catch (IOException e) {
				System.err.println("Error reading from server: " + e.getMessage());
			}
		}
	}
}
