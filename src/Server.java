import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame {

	JTextArea saida;
	JTextField entrada;

	BufferedReader leitor;
	PrintWriter w;
	private Socket socket1;

	public Server() throws Exception {
		super("Chat - Server");

		Font fonte = new Font("SansSerif", Font.PLAIN, 16);

		entrada = new JTextField();
		entrada.setFont(fonte);

		saida = new JTextArea();
		saida.setFont(fonte);
		saida.setEditable(false);

		JButton botao = new JButton("Enter");
		botao.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String t2 = entrada.getText();
				w.println(entrada.getText());
				w.flush();
				saida.append("Voc� diz: " + t2 + "\n");
				entrada.setText("");
				entrada.requestFocus();
			}
		});
		Container recebimento = new JPanel();
		recebimento.setLayout(new BorderLayout());
		recebimento.add(BorderLayout.NORTH, saida);
		recebimento.add(BorderLayout.CENTER, entrada);
		recebimento.add(BorderLayout.EAST, botao);

		getContentPane().add(BorderLayout.SOUTH, recebimento);
		getContentPane().add(BorderLayout.CENTER, saida);

		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		configuraRede();
	}

	public static void main(String[] args) throws Exception {

		new Server();

	}

	public void configuraRede() throws Exception {

		ServerSocket servSocket = new ServerSocket(6000);
		Socket socket1 = servSocket.accept();
		BufferedReader leitor = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
		OutputStream out = socket1.getOutputStream();
		w = new PrintWriter(out);

		String texto = null;

		try {
			while (true) {

				if ((texto = leitor.readLine()) != null) {

					saida.append("Pedro diz: " + texto + "\n");
				}
			}
		} catch (IOException e) {
		}

	}
}