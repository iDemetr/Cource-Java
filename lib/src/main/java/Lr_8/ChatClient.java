package Lr_8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * 
 */
public class ChatClient extends JFrame {

    private JTextField messageField;
    private JTextArea chatArea;
    private JPanel controlPanel;
    private JButton sendButton;

    private BufferedReader in;
    private PrintWriter out;

    private Socket socket;
    private String nickname;

    /**
     * 
     * @param name
     */
    public ChatClient(String name) {

	nickname = name;

	initUI();
	initLayout();
	initEvents();
    }

    /**
     * 
     */
    private void initUI() {
	setTitle("Chat Client - " + nickname);
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
	messageField = new JTextField();
	chatArea = new JTextArea();
	chatArea.setEditable(false);

	controlPanel = new JPanel();
	controlPanel.setLayout(new BorderLayout());
	controlPanel.add(messageField, BorderLayout.CENTER);
	sendButton = new JButton("Send");
	controlPanel.add(sendButton, BorderLayout.EAST);

	add(new JScrollPane(chatArea), BorderLayout.CENTER);
	add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * 
     */
    private void initEvents() {
	sendButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		String message = messageField.getText();
		out.println(message);
		messageField.setText("");
	    }
	});
	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		disconnect();
	    }
	});
    }

    /**
     * 
     * @param serverAddress
     * @param serverPort
     */
    public void connect(String serverAddress, int serverPort) {
	try {
	    socket = new Socket(serverAddress, serverPort);
	    out = new PrintWriter(socket.getOutputStream(), true);
	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    out.println(nickname);

	    new Thread(() -> runner()).start();
	} catch (IOException e) {
	    System.out.println("Error client during connection initialization: " + e.getMessage());
	}
    }

    /**
     * 
     */
    private void runner() {
	try {
	    String message;
	    while ((message = in.readLine()) != null) {
		chatArea.append(message + "\n");
	    }
	} catch (IOException e) {
	    System.out.println("Client disconnected unexpectedly: " + e.getMessage());
	} finally {
	    disconnect();
	    showDisconnectNotification();
	}

    }

    /**
     * 
     */
    public void disconnect() {
	try {
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
	} catch (IOException e) {
	    System.out.println("Ошибка при закрытии соединения: " + e.getMessage());
	}
    }

    /**
     * 
     */
    private void showDisconnectNotification() {
	JOptionPane.showMessageDialog(this, "Connection to the server has been lost.", "Disconnected",
		JOptionPane.WARNING_MESSAGE);
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
	SwingUtilities.invokeLater(() -> {

	    String nickname = "";
	    String serverAddress = "";
	    boolean isContinue = false;

	    if (args.length == 0) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));

		JTextField nicknameField = new JTextField();
		JTextField serverAddressField = new JTextField();

		panel.add(new JLabel("Nickname:"));
		panel.add(nicknameField);
		panel.add(new JLabel("Server Address:"));
		panel.add(serverAddressField);

		int result = JOptionPane.showConfirmDialog(null, panel, "Enter Chat Client Details",
			JOptionPane.OK_CANCEL_OPTION);

		isContinue = result == JOptionPane.OK_OPTION;
		nickname = nicknameField.getText();
		serverAddress = serverAddressField.getText();
	    } else {
		isContinue = true;
		nickname = args[0];
		serverAddress = args[1];
	    }

	    if (isContinue) {
		if (serverAddress == "") {
		    serverAddress = "localhost";
		}

		ChatClient client = new ChatClient(nickname);
		client.connect(serverAddress, 12345);
	    }
	});
    }
}
