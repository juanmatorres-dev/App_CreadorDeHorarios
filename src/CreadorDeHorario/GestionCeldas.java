package CreadorDeHorario;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import CreadorDeHorario.Controlador;

public class GestionCeldas extends DefaultTableCellRenderer implements filaNoValida{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tipo = "";
	private int filaParaSeleccionar = 0;
//	private ArrayList<Integer> filasNoValidas = new ArrayList<Integer>();
	private boolean filaNoValida_guardada = false;  

	private ArrayList<String> datosFichero = new ArrayList<String>();
	
	// se definen por defecto los tipos de datos a usar
	private Font normal = new Font("SanSerif", Font.PLAIN, 15);
	private Font bold = new Font("SanSerif", Font.BOLD, 15);

	public GestionCeldas() {

	}

	//
	// constructor explicito con el tipo de dato que tendrá la celda
	// @param tipo
	//
	public GestionCeldas(String tipo , int filaParaSeleccionar) {
		this.tipo = tipo;
		this.filaParaSeleccionar = filaParaSeleccionar;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column ) {
		
		if(filaNoValida_guardada == false) {
			filasNoValidas.add(row);
			filaNoValida_guardada = true;
		}
		
		
		//
		// Este metodo controla toda la tabla, podemos obtener el valor que contiene
		// definir que celda est� seleccionada, la fila y columna al tener el foco en
		// ella.
		//
		// cada evento sobre la tabla invocar� a este metodo
		//

		// definimos colores por defecto
		Color colorFondo = null;
		Color colorFondoPorDefecto = Color.WHITE;
		Color colorFondoSeleccion = Color.GRAY;

		

		/*
		 * Se definen los tipos de datos que contendr�n las celdas basado en la
		 * instancia que se hace en la ventana de la tabla al momento de construirla
		 */
		switch (tipo) {
		case "Todo Bien":
			// si es tipo texto define el color de fondo del texto y de la celda as� como la alineaci�n
			if (focused) {
				colorFondo = colorFondoSeleccion;
			} else {
				colorFondo = colorFondoPorDefecto;
			}
			this.setHorizontalAlignment(JLabel.CENTER);
			this.setText((String) value);
			this.setForeground(Color.BLACK);
			this.setBackground((selected) ? colorFondo : Color.WHITE);
			this.setFont(normal);
			break;
		case "Error de formato":
			
			if (focused) {
				colorFondo = colorFondoSeleccion;
				
			} else {
				colorFondo = colorFondoPorDefecto;
			}
//			this.setEnabled(true);
			System.out.println("==== "  + row);
			System.out.println("==== "  + column);
			
			for (int i = 0; i < filasNoValidas.size(); i++) {
				if(row == filasNoValidas.get(i)) {
					this.setHorizontalAlignment(JLabel.CENTER);
					this.setText((String) value);
					this.setForeground(Color.WHITE);
					this.setBackground((selected) ? colorFondo : Color.red);
					this.setFont(bold);
				}else {
					this.setHorizontalAlignment(JLabel.CENTER);
					this.setText((String) value);
					this.setForeground(Color.BLACK);
					this.setBackground((selected) ? colorFondo : Color.WHITE);
					this.setFont(normal);
				}
			}
			
			guardarFilasNoValidas();
			
			break;
		case "Marcar Asignatura":
			if (focused) {
				colorFondo = colorFondoSeleccion;
			} else {
				colorFondo = colorFondoPorDefecto;
			}
			
			
			if (row == filaParaSeleccionar) {
				this.setText((String) value);
				this.setHorizontalAlignment(JLabel.CENTER);

				// this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
				this.setFont(bold);
				this.setBackground((selected) ? colorFondo : new Color(0, 185, 255));
				this.setForeground(Color.WHITE);
			}else {
				this.setHorizontalAlignment(JLabel.CENTER);
				this.setText((String) value);
				this.setForeground(Color.BLACK);
				this.setBackground((selected) ? colorFondo : Color.WHITE);
				this.setFont(normal);
			}

		}

		return this;
	}
	
	
	
	/**
	 * Operaciones con Ficheros
	 */
	/*
	 * => Guardar Configuración <=
	 */
	
	public void guardarFilasNoValidas() {	
		
		/*
		 * Ecribe el fichero con la configuración actual
		 */
		
		File ficheroSalida = new File("[Creador de horarios - Datos de la app].txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print("[Creador de horarios - Datos de la app]\r\n" + 
					"\r\n" + 
					"Filas No Validas (columna 0 , Horas)\r\n" + 
					"");
			
			leerElFichero();
			
			for (int i = 0; i < datosFichero.size(); i++) {
				pw.println(datosFichero.get(i) + "\r\n");
			}
			
			for (int i = 0; i < filasNoValidas.size(); i++) {
				pw.println(filasNoValidas.get(i));
			}
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "Configuración por defecto cargada ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(null, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	

	/*
	 * Leer el fichero
	 */
	private void leerElFichero() {
		
		/*
		 * Lee el fichero con los datos actuales 
		 */
		
		Scanner lectorFichero;
		
		File ficheroSalida = new File("[Creador de horarios - Datos de la app].txt");
		
		try {
			lectorFichero = new Scanner(ficheroSalida);
			
			while (lectorFichero.hasNext()) {
//				System.out.println(lectorFichero.nextLine());
				datosFichero.add(lectorFichero.nextLine());
				
			}
			
			lectorFichero.close();
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo de configuración , se creará automáticamente con la configuración por defecto" + "\r\n" + "\r\n" + "\r\n" + error.toString()  , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
			
		}
		
	}
	
	
}