package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import dados.Mensagem;
import dados.Usuario;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Scrollbar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Chat extends JFrame implements ListDataListener, KeyListener{

	private JPanel contentPane;
	private String username;
	private JTextField tf_mensagem;
	private JTextField tf_destinatario;
	private DefaultListModel model;
	private Usuario usuarioConectado;
	
	/**
	 * Create the frame.
	 */
	public Chat(String username) {
		this.username = username;		
		usuarioConectado = new Usuario(username);
		this.setTitle(MainWindow.APP_WINDOW_TITLE);
		this.setVisible(true);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMensagem = new JLabel("Mensagem:");
		lblMensagem.setBounds(10, 152, 67, 14);
		contentPane.add(lblMensagem);
		
		tf_mensagem = new JTextField();
		tf_mensagem.addKeyListener(this);
		tf_mensagem.setBounds(137, 149, 272, 20);
		contentPane.add(tf_mensagem);
		tf_mensagem.setColumns(10);
		
		JLabel lblCodigoDestinatrio = new JLabel("Codigo destinat\u00E1rio: ");
		lblCodigoDestinatrio.setBounds(10, 177, 119, 14);
		contentPane.add(lblCodigoDestinatrio);
		
		tf_destinatario = new JTextField();
		tf_destinatario.setBounds(136, 174, 141, 20);
		
		tf_destinatario.addKeyListener(this);
		contentPane.add(tf_destinatario);
		tf_destinatario.setColumns(10);
		
		JButton bt_enviar = new JButton("Enviar");
		bt_enviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enviarMensagem();
			}
		});
		bt_enviar.setBounds(320, 173, 89, 23);
		contentPane.add(bt_enviar);
		
		model = new DefaultListModel<>();
		model.addListDataListener(this);		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 399, 119);
		contentPane.add(scrollPane);
		JList messagesList = new JList(model);
		scrollPane.setViewportView(messagesList);
		messagesList.setLayoutOrientation(JList.VERTICAL);
		messagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}
	
	private void enviarMensagem() {
		Mensagem msg = new Mensagem();
		msg.setRemetente(usuarioConectado);
		msg.setDestino(new Usuario(tf_destinatario.getText()));
		msg.setMensagem(tf_mensagem.getText());
		model.addElement(msg.toString());
	}
	
	@Override
	public void contentsChanged(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void intervalAdded(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void intervalRemoved(ListDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			enviarMensagem();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
