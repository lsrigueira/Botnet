import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.*;

public class cuadrao extends JFrame {

	public cuadrao() {
		super();
		this.setBounds(300, 200, 1200, 1000);
		this.setTitle("Ventana Pra Facer Mal");
		this.setLocationRelativeTo(null); // centramos la ventana en la pantalla
		this.setLayout(null); // no usamos ningun layout, solo asi podremos dar posiciones a los componentes
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
