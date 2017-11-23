package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import dados.Mensagem;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;

public class Chat extends JFrame implements
ListDataListener,
KeyListener,
MessageListener{
	
	//constantes
	
	private static final String TOPIC_FACTORY = "TopicCF";
	private static final String TOPIC_NAME = "topicoChat";
	
	//Objetos Swing
	private JPanel contentPane;
	private JLabel lblMensagem;
	private JLabel lblCodigoDestinatrio;
	private JTextField tf_mensagem;
	private JTextField tf_destinatario;
	private DefaultListModel model;
	private JButton bt_enviar;
	private JScrollPane scrollPane;
	private JList messagesList;
	
	//Objetos JMS
	private static TopicSession pubSession;
	private static TopicSession subSession;
	private static TopicPublisher publisher;
	private static TopicConnection connection;
		
	
	//Objetos usuario
	private String username;
	/**
	 * Create the frame.
	 */
	public Chat(String username) throws Exception{
		this.username = username;
		criarJanela();		
		criarWidgets();		
		initJMS();
	}
	
	/*
	 * Cria a janela do chat
	 */
	private void criarJanela() {
		this.setTitle(MainWindow.APP_WINDOW_TITLE + " : " + this.username);
		this.setVisible(true);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	/*
	 * Cria os widgets(JTextField, JList, etc)
	 */
	private void criarWidgets() {
		lblMensagem = new JLabel("Mensagem:");
		lblMensagem.setBounds(10, 152, 67, 14);
		contentPane.add(lblMensagem);
		
		tf_mensagem = new JTextField();
		tf_mensagem.addKeyListener(this); //listener para tecla pressionada
		tf_mensagem.setBounds(137, 149, 272, 20);
		contentPane.add(tf_mensagem);
		tf_mensagem.setColumns(10);
		
		lblCodigoDestinatrio = new JLabel("Codigo destinat\u00E1rio: ");
		lblCodigoDestinatrio.setBounds(10, 177, 119, 14);
		contentPane.add(lblCodigoDestinatrio);
				tf_destinatario = new JTextField();
		tf_destinatario.setBounds(136, 174, 141, 20);		
		tf_destinatario.addKeyListener(this);
		contentPane.add(tf_destinatario);
		tf_destinatario.setColumns(10);
		
		bt_enviar = new JButton("Enviar");
		bt_enviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					enviarMensagem();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		bt_enviar.setBounds(320, 173, 89, 23);
		contentPane.add(bt_enviar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 399, 119);
		contentPane.add(scrollPane);
		
		model = new DefaultListModel<>();
		model.addListDataListener(this);		
		
		messagesList = new JList(model);
		scrollPane.setViewportView(messagesList);
		messagesList.setLayoutOrientation(JList.VERTICAL);
		messagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void initJMS() throws Exception {
		InitialContext ctx = new InitialContext();
		
		TopicConnectionFactory conFactory = (TopicConnectionFactory) ctx.lookup(TOPIC_FACTORY);
		
		connection = conFactory.createTopicConnection();
		
		pubSession = connection.createTopicSession(false,  Session.AUTO_ACKNOWLEDGE);
		subSession = connection.createTopicSession(false,  Session.AUTO_ACKNOWLEDGE);
		
		Topic chatTopic = (Topic) ctx.lookup(TOPIC_NAME);
		
		publisher = pubSession.createPublisher(chatTopic);
		TopicSubscriber subscriber = subSession.createSubscriber(chatTopic);
		
		subscriber.setMessageListener(this);
		
		connection.start();
	}
	
	private void enviarMensagem() throws JMSException {		
		Mensagem msg = new Mensagem();
		msg.setRemetente(this.username);
		msg.setDestino(tf_destinatario.getText());
		msg.setMensagem(tf_mensagem.getText());
		
		TextMessage message = pubSession.createTextMessage();
		message.setText(msg.toString());
		publisher.publish(message);
	}
	
	private void closeConn() throws JMSException {
		connection.close();
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
			try {
				enviarMensagem();
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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

	@Override
	public void onMessage(Message message) {		
		try {
			TextMessage textMessage = (TextMessage) message;
			String []splitMessage = textMessage.getText().split(":");
			// essa lógica só permite que o texto apareça pro usuário se ele for remetente ou destinatário da mesma
			if(splitMessage[0].contains(this.username)) {
				model.addElement(textMessage.getText());
			}			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
