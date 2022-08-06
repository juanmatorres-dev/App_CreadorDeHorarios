package CreadorDeHorario;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;


import java.awt.BorderLayout;
import java.awt.Font;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Component;
import javax.swing.JMenuItem;
import java.awt.ComponentOrientation;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




public class Vista {

	public JFrame ventana_principal;
	public JPanel panelDiasDeLaSemanaEnTexto;
	public JPanel panelDiasDeLaSemanaEnNumero;
	public JButton btnNewButton;
	public JTextField textField;
	public JLabel lbl_reconectar;
	public JLabel lbl_reparar;
	public JLabel lbl_version;
	public JLabel lbl_insertarFila;
	public JLabel lbl_configuracion;
	public JLabel label;
	public JScrollPane scrollPane;
	public JTable table;
	public JLabel lbl_loading;
	public JPanel panel_bar_loader;
	public JLabel label_loading_reparacion;
	public JLabel label_loading_cambiosGuardados;
	public JLabel lbl_loading_1;
	public JSlider slider_horas;
	public JLabel lbl_hora;
	private JPanel panel_slider_dias;
	public JLabel lbl_tiempo_restante;
	public JLabel loading_configuracion;
	public JLabel lbl_home;
	public JLabel lbl_loading_home;
	public JLabel lbl_borrarFila;
	public JLabel lbl_loading_borrarFila;
	public JLabel lbl_nombre_horario;
	public JLabel lbl_imprimir;
	public JLabel lbl_loading_impresora;
	public JLabel lbl_bloquear;
	public JLabel lbl_loading_bloquear;
	
	Version_App version = new Version_App();
	
	
	public JSlider slider_dias;
	
	
	private static final DefaultTableCellRenderer  defaultTableCellRenderer = new DefaultTableCellRenderer();
	public JMenu nombre_usuario;
	public JMenuBar menuBar_usuario;
	public JLabel loading_user;
	public JMenuItem cerrar_sesion;
	
	
	
	
	
	
	
	
	
	
	

	
	


	/**
	 * Create the application.
	 */
	public Vista() {
		initialize();
		ventana_principal.setVisible(false);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ventana_principal = new JFrame();
		ventana_principal.setResizable(false);
		ventana_principal.setIconImage(Toolkit.getDefaultToolkit().getImage("images/calendario (1).png"));
		ventana_principal.setBounds(100, 100, 1003, 818);
		ventana_principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana_principal.setLocationRelativeTo(null);
		ventana_principal.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelDiasDeLaSemanaEnTexto = new JPanel();
		ventana_principal.getContentPane().add(panelDiasDeLaSemanaEnTexto);
		ventana_principal.getContentPane().add(panelDiasDeLaSemanaEnTexto);
		panelDiasDeLaSemanaEnTexto.setLayout(null);
		
		JPanel panel_botones_inferiores = new JPanel();
		panel_botones_inferiores.setBounds(0, 622, 997, 157);
		panelDiasDeLaSemanaEnTexto.add(panel_botones_inferiores);
		panel_botones_inferiores.setLayout(null);
		
		lbl_reconectar = new JLabel("Reconectar");
		lbl_reconectar.setVisible(false);
		lbl_reconectar.setIcon(null);
		lbl_reconectar.setBounds(611, 99, 97, 36);
		panel_botones_inferiores.add(lbl_reconectar);
		lbl_reconectar.setBorder(BorderFactory.createRaisedBevelBorder());
		
		lbl_reconectar.setHorizontalAlignment(JLabel.CENTER); // Para centrar la imagen dentro del label
		lbl_reconectar.setVerticalAlignment(JLabel.CENTER);
		
		lbl_reparar = new JLabel("");
		lbl_reparar.setVisible(false);
		lbl_reparar.setIcon(new ImageIcon("images/reparar_(32x32).png"));
		lbl_reparar.setBounds(526, 52, 56, 36);
		panel_botones_inferiores.add(lbl_reparar);
		lbl_reparar.setBorder(BorderFactory.createRaisedBevelBorder());
		
		lbl_reparar.setHorizontalAlignment(JLabel.CENTER); // Para centrar la imagen dentro del label
		lbl_reparar.setVerticalAlignment(JLabel.CENTER);
		
		lbl_version = new JLabel(version.version);
		lbl_version.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_version.setBounds(36, 103, 97, 25);
		panel_botones_inferiores.add(lbl_version);
		
		lbl_insertarFila = new JLabel("Añadir Fila");
		lbl_insertarFila.setVisible(false);
		lbl_insertarFila.setVerticalAlignment(SwingConstants.CENTER);
		lbl_insertarFila.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_insertarFila.setBorder(BorderFactory.createRaisedBevelBorder());
		lbl_insertarFila.setBounds(611, 52, 97, 36);
		panel_botones_inferiores.add(lbl_insertarFila);
		
		lbl_configuracion = new JLabel("");
		lbl_configuracion.setVisible(false);
		lbl_configuracion.setIcon(new ImageIcon("images/configuracion_(24x24).png"));
		lbl_configuracion.setVerticalAlignment(SwingConstants.CENTER);
		lbl_configuracion.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_configuracion.setBorder(BorderFactory.createRaisedBevelBorder());
		lbl_configuracion.setBounds(736, 52, 56, 36);
		panel_botones_inferiores.add(lbl_configuracion);
		
		lbl_configuracion.setHorizontalAlignment(JLabel.CENTER); // Para centrar la imagen dentro del label
		lbl_configuracion.setVerticalAlignment(JLabel.CENTER);
		
		JLabel lbl_version_1 = new JLabel("Conexión con el servidor : ");
		lbl_version_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_version_1.setBounds(190, 59, 212, 25);
		panel_botones_inferiores.add(lbl_version_1);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon("images/Animated_loading_half-circle_(16x16).gif"));
		label.setBounds(383, 52, 32, 32);
		panel_botones_inferiores.add(label);
		
		lbl_loading = new JLabel("");
		lbl_loading.setIcon(new ImageIcon("images/bar_loader_(16x16).gif"));
		lbl_loading.setBounds(635, 62, 43, 11);
		panel_botones_inferiores.add(lbl_loading);
		
		label_loading_reparacion = new JLabel("");
		label_loading_reparacion.setVisible(false);
		label_loading_reparacion.setIcon(new ImageIcon("images/Animated_loading_half-circle_(16x16).gif"));
		label_loading_reparacion.setBounds(537, 56, 32, 32);
		panel_botones_inferiores.add(label_loading_reparacion);
		
		JLabel lbl_version_2 = new JLabel("Cambios guardados : ");
		lbl_version_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_version_2.setBounds(190, 101, 212, 25);
		panel_botones_inferiores.add(lbl_version_2);
		
		label_loading_cambiosGuardados = new JLabel("");
		label_loading_cambiosGuardados.setIcon(new ImageIcon("images/Animated_loading_half-circle_(16x16).gif"));
		label_loading_cambiosGuardados.setBounds(383, 95, 32, 32);
		panel_botones_inferiores.add(label_loading_cambiosGuardados);
		
		lbl_loading_1 = new JLabel("");
		lbl_loading_1.setIcon(new ImageIcon("images/bar_loader_(16x16).gif"));
		lbl_loading_1.setBounds(635, 113, 43, 11);
		panel_botones_inferiores.add(lbl_loading_1);
		
		lbl_hora = new JLabel("");
		lbl_hora.setIcon(new ImageIcon("images/bar_loader_(16x16).gif"));
		lbl_hora.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_hora.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_hora.setBounds(869, 93, 98, 42);
		panel_botones_inferiores.add(lbl_hora);
		
		lbl_tiempo_restante = new JLabel("");
		lbl_tiempo_restante.setIcon(new ImageIcon("images/bar_loader_(16x16).gif"));
		lbl_tiempo_restante.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lbl_tiempo_restante.setBounds(10, 11, 317, 25);
		panel_botones_inferiores.add(lbl_tiempo_restante);
		
		loading_configuracion = new JLabel("");
		loading_configuracion.setIcon(new ImageIcon("images/Animated_loading_half-circle_(16x16).gif"));
		loading_configuracion.setBounds(748, 56, 32, 32);
		panel_botones_inferiores.add(loading_configuracion);
		
		lbl_home = new JLabel("");
		lbl_home.setVisible(false);
		lbl_home.setIcon(new ImageIcon("images/home-page_(24x24).png"));
		lbl_home.setVerticalAlignment(SwingConstants.CENTER);
		lbl_home.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_home.setBorder(BorderFactory.createRaisedBevelBorder());
		lbl_home.setBounds(736, 99, 56, 36);
		panel_botones_inferiores.add(lbl_home);
		lbl_home.setBorder(BorderFactory.createRaisedBevelBorder());
		
		lbl_loading_home = new JLabel("");
		lbl_loading_home.setIcon(new ImageIcon("images/Animated_loading_half-circle_(16x16).gif"));
		lbl_loading_home.setBounds(748, 105, 32, 32);
		panel_botones_inferiores.add(lbl_loading_home);
		
		lbl_borrarFila = new JLabel("Borrar Fila");
		lbl_borrarFila.setVisible(false);
		lbl_borrarFila.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_borrarFila.setBounds(485, 99, 97, 36);
		panel_botones_inferiores.add(lbl_borrarFila);
		lbl_borrarFila.setBorder(BorderFactory.createRaisedBevelBorder());
		lbl_borrarFila.setHorizontalAlignment(JLabel.CENTER); // Para centrar la imagen dentro del label
		lbl_borrarFila.setVerticalAlignment(JLabel.CENTER);
		
		lbl_loading_borrarFila = new JLabel("");
		lbl_loading_borrarFila.setIcon(new ImageIcon("images/bar_loader_(16x16).gif"));
		lbl_loading_borrarFila.setBounds(511, 110, 46, 14);
		panel_botones_inferiores.add(lbl_loading_borrarFila);
		
		lbl_nombre_horario = new JLabel("");
		lbl_nombre_horario.setIcon(new ImageIcon("images/bar_loader_(16x16).gif"));
		lbl_nombre_horario.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_nombre_horario.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_nombre_horario.setBounds(10, 10, 977, 30);
		panel_botones_inferiores.add(lbl_nombre_horario);
		
		lbl_imprimir = new JLabel("");
		lbl_imprimir.setVisible(false);
		lbl_imprimir.setIcon(new ImageIcon("images/impresora_(24x24).png"));
		lbl_imprimir.setBounds(802, 99, 58, 36);
		panel_botones_inferiores.add(lbl_imprimir);
		lbl_imprimir.setVerticalAlignment(SwingConstants.CENTER);
		lbl_imprimir.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_imprimir.setBorder(BorderFactory.createRaisedBevelBorder());
		
		lbl_loading_impresora = new JLabel("");
		lbl_loading_impresora.setIcon(new ImageIcon("images/Animated_loading_half-circle_(16x16).gif"));
		lbl_loading_impresora.setBounds(818, 107, 25, 25);
		panel_botones_inferiores.add(lbl_loading_impresora);
		
		lbl_bloquear = new JLabel("");
		lbl_bloquear.setVisible(false);
		lbl_bloquear.setIcon(new ImageIcon("images/desbloqueado_(24x24).png"));
		lbl_bloquear.setBounds(802, 52, 58, 36);
		panel_botones_inferiores.add(lbl_bloquear);
		lbl_bloquear.setVerticalAlignment(SwingConstants.CENTER);
		lbl_bloquear.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_bloquear.setBorder(BorderFactory.createRaisedBevelBorder());
		
		lbl_loading_bloquear = new JLabel("");
		lbl_loading_bloquear.setIcon(new ImageIcon("images/Animated_loading_half-circle_(16x16).gif"));
		lbl_loading_bloquear.setBounds(818, 60, 25, 25);
		panel_botones_inferiores.add(lbl_loading_bloquear);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("images/Animated_loading_half-circle_(16x16).gif"));
		lblNewLabel.setBounds(869, 52, 98, 36);
		panel_botones_inferiores.add(lblNewLabel);
		
		JLabel donate = new JLabel("");
		donate.setIcon(new ImageIcon("images/main_window/coffee-cup_32_px.png"));
		donate.setHorizontalAlignment(SwingConstants.CENTER);
		donate.setBounds(31, 46, 46, 46);
		panel_botones_inferiores.add(donate);
		donate.setBorder(BorderFactory.createRaisedBevelBorder());
		
		JLabel bug = new JLabel("");
		bug.setIcon(new ImageIcon("images/main_window/bug_32_px.png"));
		bug.setHorizontalAlignment(SwingConstants.CENTER);
		bug.setBorder(BorderFactory.createRaisedBevelBorder());
		bug.setBounds(87, 46, 46, 46);
		panel_botones_inferiores.add(bug);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 63, 954, 560);
		panelDiasDeLaSemanaEnTexto.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		panel_bar_loader = new JPanel();
		panel_bar_loader.setBounds(24, 70, 939, 457);
		panelDiasDeLaSemanaEnTexto.add(panel_bar_loader);
		panel_bar_loader.setLayout(null);
		
		JLabel lbl_bar_table_loader = new JLabel("Cargando elementos ...");
		lbl_bar_table_loader.setIcon(new ImageIcon("images/Loading.gif"));
		lbl_bar_table_loader.setBounds(400, 35, 220, 19);
		panel_bar_loader.add(lbl_bar_table_loader);
		
		JPanel panel_slider_horas = new JPanel();
		panel_slider_horas.setBounds(3, 73, 25, 550);
		panelDiasDeLaSemanaEnTexto.add(panel_slider_horas);
		panel_slider_horas.setLayout(null);
		

		

		
		switch (leerElTipoDeBarra()) {
		case "Barra personalizada circular naranja":
			// Estilos personalizados
			slider_horas = new JSlider() {
				@Override
				public void updateUI() {
					setUI(new Slider_personalizado(this));
				}
			};
			break;
		case "Barra básica ( nativa de Java )":
			slider_horas = new JSlider();
			break;
		case "Barra personalizada triangular morada":
			// Estilos personalizados
			slider_horas = new JSlider() {
				@Override
				public void updateUI() {
					setUI(new Slider_personalizado_Triangular(this));
				}
			};
			break;
		default:
			// Estilos personalizados
			slider_horas = new JSlider() {
				@Override
				public void updateUI() {
					setUI(new Slider_personalizado(this));
				}
			};
			break;
		}
		
		slider_horas.setEnabled(false);
		slider_horas.setInverted(true);
		slider_horas.setValue(0);
		slider_horas.setBounds(0, 23, 24, panel_slider_horas.getHeight() -22);
		panel_slider_horas.add(slider_horas);
		slider_horas.setOrientation(SwingConstants.VERTICAL);
		
		panel_slider_dias = new JPanel();
		panel_slider_dias.setBounds(10, 32, 968, 27);
		panelDiasDeLaSemanaEnTexto.add(panel_slider_dias);
		panel_slider_dias.setLayout(null);
		
		
		switch (leerElTipoDeBarra()) {
		case "Barra personalizada circular naranja":
			// Estilos personalizados
			slider_dias = new JSlider() {
				@Override
				public void updateUI() {
					setUI(new Slider_personalizado(this));
				}
			};
			break;
		case "Barra básica ( nativa de Java )":
			slider_dias = new JSlider();
			break;
		case "Barra personalizada triangular morada":
			// Estilos personalizados
			slider_dias = new JSlider() {
				@Override
				public void updateUI() {
					setUI(new Slider_personalizado_Triangular(this));
				}
			};
			break;
		default:
			// Estilos personalizados
			slider_dias = new JSlider() {
				@Override
				public void updateUI() {
					setUI(new Slider_personalizado(this));
				}
			};
			break;
		}
		

		

		
		slider_dias.setEnabled(false);
		slider_dias.setPaintTrack(false);
		slider_dias.setFocusable(false);
		slider_dias.setBounds(10, 5, 944, 22);
		panel_slider_dias.add(slider_dias);
		
		JPanel panel_menu = new JPanel();
		panel_menu.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel_menu.setBounds(0, 0, 987, 27);
		panelDiasDeLaSemanaEnTexto.add(panel_menu);
		FlowLayout fl_panel_menu = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		panel_menu.setLayout(fl_panel_menu);
		
		menuBar_usuario = new JMenuBar();
		menuBar_usuario.setVisible(false);
		menuBar_usuario.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		menuBar_usuario.setBackground(new Color(255, 250, 250));
		menuBar_usuario.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		menuBar_usuario.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		panel_menu.add(menuBar_usuario);
		
		nombre_usuario = new JMenu("");
		nombre_usuario.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		nombre_usuario.setFont(new Font("Segoe UI", Font.BOLD, 12));
		nombre_usuario.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nombre_usuario.setAlignmentY(0.1f);
		nombre_usuario.setHorizontalAlignment(SwingConstants.LEFT);
		nombre_usuario.setHorizontalTextPosition(SwingConstants.LEFT);
		menuBar_usuario.add(nombre_usuario);
		
		cerrar_sesion = new JMenuItem("Cerrar sesión");
		cerrar_sesion.setVerticalTextPosition(SwingConstants.TOP);
		cerrar_sesion.setVerticalAlignment(SwingConstants.TOP);
		nombre_usuario.add(cerrar_sesion);
		
		loading_user = new JLabel("");
		loading_user.setIcon(new ImageIcon("images/Loading.gif"));
		panel_menu.add(loading_user);
		slider_dias.setValue(15);
		
		
		// Para que no se puedan mover las columnas de posición de table_datos.
		table.getTableHeader().setReorderingAllowed(false);
				

				
		/*
		 * Centra los datos
		 * 
		 * https://es.stackoverflow.com/questions/369087/c%C3%B3mo-centrar-todos-los-datos-de-una-columna-en-un-jtable-java
		 * 
		 * https://www.lawebdelprogramador.com/foros/Java/1553980-alinear-datos-en-un-jtable.html
		 * 
		 */
		
		
		defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
	}
	
	
	
	/*
	 * Centra los datos
	 */
	public void centrarDatosDeTabla() {
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setColumnSelectionAllowed(false);
			table.setCellSelectionEnabled(false);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			table.getColumnModel().getColumn(i).setPreferredWidth(777);
			
			table.setRowHeight(64); // 50 , 30
			table.setFocusable(false);
			
	        table.getTableHeader().setPreferredSize(new java.awt.Dimension(40, 40));
	        table.getTableHeader().setResizingAllowed(false);
			
			table.setFont(new Font("SanSerif", Font.PLAIN, 15)); // 15
		}
	}
	
	/*
	 * Parte que gestiona el tipo de barra
	 */
	
	/*
	 * 
	 */
	public String leerElTipoDeBarra() {
		String barra = "?";
		
		/*
		 * Lee el fichero con la configuración actual 
		 */
		
		Scanner lectorFichero;
		
		File ficheroSalida = new File("[Creador de horarios - Datos Barra].txt");
		
		
		try {
			lectorFichero = new Scanner(ficheroSalida);
			
			while (lectorFichero.hasNext()) {
//				System.out.println(lectorFichero.nextLine());
				barra = lectorFichero.nextLine();
			}
			
			lectorFichero.close();
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo de configuración , se creará automáticamente con la configuración por defecto" + "\r\n" + "\r\n" + "\r\n" + error.toString()  , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
			
		}
		
		return barra;
		
	}
}
