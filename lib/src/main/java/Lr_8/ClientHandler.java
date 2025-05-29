package Lr_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket socket;
    private ChatServer server;
    private BufferedReader in;
    private String nickname;

    PrintWriter out;

    public ClientHandler(Socket clientSocket, ChatServer server) {
	this.socket = clientSocket;
	this.server = server;
    }

    @Override
    public void run() {
	try {
	    initConnection();

	    String message;
	    while ((message = in.readLine()) != null) {
		server.broadcast(this, message);
	    }
	} catch (IOException e) {
	    System.out.println("Client " + getInfo() + " disconnected unexpectedly: " + e.getMessage());
	} finally {
	    disconnect();
	}
    }

    /**
     * 
     */
    private void initConnection() {
	try {
	    out = new PrintWriter(socket.getOutputStream(), true);
	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    nickname = in.readLine();
	    if (nickname == null) {
		throw new IOException("Client disconnected before sending nickname.");
	    }
	    server.confirmConnectClient(getInfo());

	    // Send chat history to the new client
	    for (var message : server.getChatHistory()) {
		out.println(message);
	    }
	} catch (IOException e) {
	    System.out.println("Error during connection initialization: " + e.getMessage());
	    disconnect();
	}
    }

    /**
     * 
     * @return
     */
    public String getInfo() {
	return socket.getInetAddress().getHostAddress() + " - " + nickname;
    }

    /**
     * 
     * @return
     */
    public String getName() {
	return nickname;
    }

    /**
     * 
     */
    public void disconnect() {
	try {
	    var localInfo = getInfo();

	    if (socket != null) {
		socket.shutdownOutput();
		socket.shutdownInput();

		if (in != null) {
		    in.close();
		}
		if (out != null) {
		    out.close();
		}
		socket.close();
	    }
	    server.disconnectClientHandler(localInfo);
	} catch (IOException e) {
	    System.out.println("Ошибка при закрытии соединения: " + e.getMessage());
	}
    }
}