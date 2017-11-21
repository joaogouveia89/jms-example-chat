package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow {

	private JFrame frmChat;
	private JTextField textField;
	public static final String APP_WINDOW_TITLE = "Chat";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChat = new JFrame();
		frmChat.setTitle(APP_WINDOW_TITLE);
		frmChat.setBounds(100, 100, 350, 200);
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.setResizable(false);
		frmChat.getContentPane().setLayout(null);
		
		JLabel lblDigiteSeuNome = new JLabel("Digite seu nome");
		lblDigiteSeuNome.setBounds(123, 48, 94, 14);
		frmChat.getContentPane().add(lblDigiteSeuNome);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					enterInChat();
				}
			}
		});
		textField.setBounds(101, 73, 132, 20);
		frmChat.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enterInChat();
			}
		});
		btnNewButton.setBounds(123, 102, 89, 23);
		frmChat.getContentPane().add(btnNewButton);
	}
	
	private void enterInChat() {
		String name = textField.getText();
		new Chat(name);
		frmChat.dispose();
	}
}
