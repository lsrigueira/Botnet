import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {

	public static void main(String[] arg) {

		File folder = new File("C:\\RegistroOrdenesBotnet");
		folder.mkdirs();

		int conexPort = Integer.parseInt(arg[0]);
		ThreadTCP threadTCP = new ThreadTCP(conexPort);
		Thread aux = new Thread(threadTCP);
		aux.start();
	}
}
