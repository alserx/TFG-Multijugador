package connection;

public interface MessageReceivedObserver {

	/**
	 * This method is called when the client receives a message from the server
	 * 
	 * @param message The message received from the server
	 */
	void onMessageReceived(String message);
}
