package Lr_8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * 
 */
public class ChatServer extends JFrame implements Runnable {
    private DefaultListModel<String> clientListModel;

    private JButton disconnectButton;
    private JList<String> clientList;
    private List<ClientHandler> clients;

    private List<String> chatHistory;

    int port;

    /**
     * 
     */
    public ChatServer(int port) {

	initUI();
	initLayout();
	initEvents();

	this.port = port;

	clients = new ArrayList<>();
	chatHistory = new ArrayList<>();
    }

    /**
     * 
     */
    private void initUI() {
	setTitle("Chat Server");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(400, 300);
	setLayout(new BorderLayout());

	this.setMinimumSize(new Dimension(400, 300));

	setLocationRelativeTo(null);
	setVisible(true);
    }

    /**
     * 
     */
    private void initLayout() {
	clientListModel = new DefaultListModel<>();
	clientList = new JList<>(clientListModel);
	disconnectButton = new JButton("Отключить");

	JPanel controlPanel = new JPanel();
	controlPanel.add(new JScrollPane(clientList));
	controlPanel.add(disconnectButton);

	add(controlPanel, BorderLayout.CENTER);
    }

    /**
     * 
     */
    private void initEvents() {
	disconnectButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int selectedIndex = clientList.getSelectedIndex();
		if (selectedIndex != -1) {
		    var client = clients.get(selectedIndex);
		    client.disconnect();
		}
	    }
	});

	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		for (var client : clients) {
		    client.disconnect();
		}
		System.exit(0);
	    }
	});
    }

    /**
     * 
     * @param port
     */
    @Override
    public void run() {
	try (ServerSocket serverSocket = new ServerSocket(port)) {

	    InetAddress serverAddress = InetAddress.getLocalHost();
	    String serverInfo = "Server Info: IP " + serverAddress.getHostAddress() + " Port " + port;

	    setTitle(getTitle() + serverInfo);

	    System.out.println("Server started");
	    System.out.println(serverInfo);

	    while (true) {
		// accept блокируется до тех пор, пока не будет получено новое подключение
		var clientSocket = serverSocket.accept();
		connectClientHandler(clientSocket);
	    }
	} catch (IOException e) {
	    System.out.println("Error: " + e.getMessage());
	    e.printStackTrace();
	}
    }

    /**
     * Рассылает сообщения от клиента
     * 
     * @param sender
     * @param message
     */
    public void broadcast(ClientHandler sender, String message) {
	System.out.println(sender.getInfo() + " Received: " + message);
	var senderName = sender.getName();
	chatHistory.add(senderName + ": " + message);
	// Broadcast message to all clients
	for (var client : getClients()) {
	    client.out.println(senderName + ": " + message);
	}
    }

    /**
     * Рассылает сообщения от сервера
     * 
     * @param sender
     * @param message
     */
    private void sendMessage(String message) {
	System.out.println("Server received: " + message);
	var senderName = "Server";
	chatHistory.add(senderName + ": " + message);
	// Broadcast message to all clients
	for (var client : getClients()) {
	    client.out.println(senderName + ": " + message);
	}
    }

    /**
     * Предоставляет доступ обработчикам клиенктов к истории
     * 
     * @return
     */
    public List<String> getChatHistory() {
	return chatHistory;
    }

    /**
     * 
     * @return
     */
    private List<ClientHandler> getClients() {
	return clients;
    }

    /**
     * Обработчик добавления обработчика-клиента
     * 
     * @param clientInfo
     */
    private void connectClientHandler(Socket clientSocket) {
	var clientHandler = new ClientHandler(clientSocket, this);
	clients.add(clientHandler);

	new Thread(clientHandler).start();
    }

    /**
     * Принимает подтверждение о коннекте обработчика клиента
     * 
     * @param clientInfo
     */
    public void confirmConnectClient(String clientInfo) {
	// TODO: добавить защиту от абюза
	clientListModel.addElement(clientInfo);
	System.out.println(clientInfo + " connected.");
    }

    /**
     * Обработчик дисконетка обработчика-клиента
     * 
     * @param clientInfo
     */
    public void disconnectClientHandler(String clientInfo) {
	var index = clientListModel.indexOf(clientInfo);
	var client = clients.get(index);
	clients.remove(index);
	clientListModel.remove(index);

	sendMessage(client.getName() + " left the chat.");
	System.out.println(clientInfo + " disconnected.");
    }

    public static void main(String[] args) {
	SwingUtilities.invokeLater(() -> {
	    ChatServer server = new ChatServer(12345);
	    new Thread(server).start();
	});
    }
}