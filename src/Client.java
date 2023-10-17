
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
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame {

	JTextField entrada;
	JTextArea saida;
	Socket socket;
	PrintWriter w;
	BufferedReader leitor;

	Client() throws Exception {
		super("Chat - Cliente");

		Font fonte = new Font("SansSerif", Font.PLAIN, 16);
		entrada = new JTextField();
		entrada.setFont(fonte);

		saida = new JTextArea();
		saida.setEditable(false);
		saida.setFont(fonte);

		JButton botao = new JButton("Enter");
		botao.addActionListener(new EnviarListener());
		Container envio = new JPanel();
		envio.setLayout(new BorderLayout());
		envio.add(BorderLayout.CENTER, entrada);
		envio.add(BorderLayout.NORTH, saida);
		envio.add(BorderLayout.EAST, botao);

		getContentPane().add(BorderLayout.SOUTH, envio);
		getContentPane().add(BorderLayout.CENTER, saida);

		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		configuraRede();
	}

	private class EnviarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String t2 = entrada.getText();
			w.println(entrada.getText());
			w.flush();
			saida.append("Voc� diz: " + t2 + "\n");
			entrada.setText("");
			entrada.requestFocus();

		}

	}

	private void configuraRede() throws Exception {
		socket = new Socket("localhost", 6000);
		OutputStream out = socket.getOutputStream();
		w = new PrintWriter(out);
		leitor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String texto = null;

		try {
			while (true) {
				if ((texto = leitor.readLine()) != null) {

					saida.append("Jo�o diz: " + texto + "\n");
				}
			}
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) throws Exception {
		new Client();

	}

}