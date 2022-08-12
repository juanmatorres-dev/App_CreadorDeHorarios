/**
 * 
 */
package CreadorDeHorario;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.print.PrinterException;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.synth.SynthSpinnerUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.w3c.dom.events.MouseEvent;

import CreadorDeHorario.libraries.BCrypt;




/**
 * @author Juan Manuel Torres Martínez
 *
 */
public class Controlador implements MouseListener , WindowListener , KeyListener , TableModelListener, ActionListener  {
	/**
	 * Contiene la información de la conexión de la base de datos
	 */
	DB dataBaseInfo = new DB();
	
	private String urlDelServidor_incompleta = dataBaseInfo.urlDelServidor_incompleta;
	private String user = dataBaseInfo.user; 
	private String pass = dataBaseInfo.pass;
	private String BD = dataBaseInfo.BD;
	
	
	private boolean actualizacion_necesaria = false;
	
	private String downloadedFileName;
	
	private boolean laConexionHaFallado = false;

	private Vista vista;
	private Calendario_Horiario calendario;
	private Configuracion configuracion;
	private BorrarFila borrarFila;
	private MySQL_Operations sql;
	private Update update;
	private Login login;
	private AutoLogin autoLogin;
	
	private int mesActual; // Guardan el mes y año actuales al abrir el Calendario
	private int anioActual;
	
	private int filaDeLaTabla;
	private int columnaDeLaTabla;
	
	int primeraHoraDelHorario;
	String primeraHoraDelHorario_enTexto;
	int ultimaHoraDelHorario;
	String ultimaHoraDelHorario_enTexto;
	
	int primerMinutoDelHorario;
	String primerMinutoDelHorario_enTexto;
	int ultimoMinutoDelHorario;
	String ultimoMinutoDelHorario_enTexto;
	
	private String diaActualEnTexto;
	
	private ArrayList<JButton> contenedorDeBotones_numDias = new ArrayList<JButton>();
	
	private Vector<Object> fila = new Vector<Object>();
	private ArrayList<String> asignaturas_enDia_Nombre_En_Texto = new ArrayList<String>();
	private ArrayList<String> id_intervalo_hora_asignatura = new ArrayList<String>();
	private ArrayList<String> id_intervalo_hora_asignatura_limpio = new ArrayList<String>();
	private ArrayList<Integer> id_intervalo_hora_asignatura_limpio_entero = new ArrayList<Integer>();
	
	private ArrayList<Integer> ids_horarios = new ArrayList<Integer>(); // Usamos este por si se borran horarios , ya que 
																		// en ese caso , ya no coinciden los ids con la fila de la tabla
	
	private ArrayList<Integer> ids_intervalosHora = new ArrayList<Integer>(); // Usamos este para asegurarnos de que coincidan los ids
																			  
	
	private ArrayList<String> nombresDeHorarios = new ArrayList<String>();
	
	private ArrayList<String> diasDeLaSemana = new ArrayList<String>();
	

	private boolean hayCampoNoValido = false;
	private int errorEn = 0;
	
	
	private Connection conexion;
	public boolean conexionEstablecida;
	public String tmp = "";
	DefaultTableModel modelo = new DefaultTableModel();
	
	
	LocalDateTime localDateTime;
	private LocalTime horaActual = LocalTime.now();
	DateTimeFormatter formateadorReloj = DateTimeFormatter.ofPattern("HH:mm:ss");
	DateTimeFormatter formateadorRelojCompacto = DateTimeFormatter.ofPattern("HH:mm");
	DateTimeFormatter formateadorHoras = DateTimeFormatter.ofPattern("HH");
	DateTimeFormatter formateadorMinutos = DateTimeFormatter.ofPattern("mm");
	DateTimeFormatter formateadorSegundos = DateTimeFormatter.ofPattern("ss");
	
	ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	Runnable comprobarSiSeEstaEditandoAlgunaCelda = () -> seEstaEditandoAlgunaCelda();
	
	ScheduledExecutorService serviceHora = Executors.newSingleThreadScheduledExecutor();
	Runnable actualizarHora = () -> actualizarHora();
	
	ScheduledExecutorService serviceEsperar = Executors.newSingleThreadScheduledExecutor();
	Runnable esperar = () -> hacerConsultaDeHorarios();
	Runnable esperarALimpiarDelTodoLaTabla = () -> limpiarDelTodoLaTabla();
	Runnable moverDatos = () -> moverDatos();
	
	ScheduledExecutorService serviceUpdate = Executors.newSingleThreadScheduledExecutor();
	Runnable esperarAntesDeActualizar = () -> prepararReinicioTrasActualizar();
	
	ScheduledExecutorService serviceRegister = Executors.newSingleThreadScheduledExecutor();
	Runnable esperarAntesDeRegistrarUsuario = () -> registrarUsuario();
	
	ScheduledExecutorService serviceLogin = Executors.newSingleThreadScheduledExecutor();
	Runnable esperarAntesDeIniciarSesion = () -> iniciarSesion();
	
	ScheduledExecutorService serviceRegister_btn = Executors.newSingleThreadScheduledExecutor();
	Runnable esperarAntesDeActivarBotonDeRegistro = () -> detenerCargaBotonRegistroUsuario();
	
	ScheduledExecutorService serviceLogin_btn = Executors.newSingleThreadScheduledExecutor();
	Runnable esperarAntesDeActivarBotonDeLogin = () -> detenerCargaBotonLogin();
	
	ScheduledExecutorService serviceCloseLogin = Executors.newSingleThreadScheduledExecutor();
	Runnable esperarAntesDeCerrarVentanaDeLogin = () -> loginCorrecto();
	
	private ArrayList<String> primarasHorasDeCadaIntervaloHorario_enTexto = new ArrayList<String>();
	private ArrayList<String> primerosMinutosDeCadaIntervaloHorario_enTexto = new ArrayList<String>();
	
	private ArrayList<String> ultimasHorasDeCadaIntervaloHorario_enTexto = new ArrayList<String>();
	private ArrayList<String> ultimosMinutosDeCadaIntervaloHorario_enTexto = new ArrayList<String>();
	
	private ArrayList<String> primarasHorasYMinutosDeCadaIntervaloHorario_enTexto = new ArrayList<String>();
	private ArrayList<String> ultimasHorasYMinutosDeCadaIntervaloHorario_enTexto = new ArrayList<String>();
	
	public static int filaParaSeleccionar = 0;
	private boolean mostrarNotificacion = true;
	private int asignaturaAnteriorMarcada;
	int tiempoTotalRestanteDeJornada;
	
	private String tipoDeBarraAlIniciar;
	
	private boolean mostrarNotificacionSoloUnaVez = true;
	
	
	private boolean alternar_estado_bloqueado = true;
	
	
	/*
	 * Estados de los ojos de los campos de contraseña
	 */
	private boolean visible_pass_register = false;
	private boolean visible_repeat_pass_register = false;
	private boolean visible_pass_login = false;
	
	/*
	 * Constantes para realizar las operaciones del usuario logueado
	 */
	private static String nombreUsuario_logueado;
	private static String tokenUsuario_logueado;
	
	public Controlador(Vista vista, Calendario_Horiario calendario , Configuracion configuracion , MySQL_Operations sql , BorrarFila borrarFila, Update update, Login login, AutoLogin autoLogin) {
		leerSesionDeUsuarioLogueado();
		//JOptionPane.showMessageDialog(null, nombreUsuario_logueado + "\n" + tokenUsuario_logueado);
		diasDeLaSemana.add("Lunes");
		diasDeLaSemana.add("Martes");
		diasDeLaSemana.add("Miércoles");
		diasDeLaSemana.add("Jueves");
		diasDeLaSemana.add("Viernes");
		
		
		this.vista = vista;
		this.calendario = calendario;
		this.configuracion = configuracion;
		this.borrarFila = borrarFila;
		this.sql = sql;
		this.update = update;
		this.login = login;
		this.autoLogin = autoLogin;
		
		vista.ventana_principal.addWindowListener(this);
		
		vista.lbl_reconectar.addMouseListener(this);
		vista.lbl_reparar.addMouseListener(this);
		vista.lbl_insertarFila.addMouseListener(this);
		vista.lbl_configuracion.addMouseListener(this);
		vista.lbl_home.addMouseListener(this);
		vista.lbl_borrarFila.addMouseListener(this);
		vista.lbl_imprimir.addMouseListener(this);
		vista.lbl_bloquear.addMouseListener(this);
		
		vista.cerrar_sesion.addActionListener(this);
		
		configuracion.chckbxMostrarSegundosReloj.addMouseListener(this);
		configuracion.addWindowListener(this);
		configuracion.addKeyListener(this);
		configuracion.test_notificaciones.addMouseListener(this);
		configuracion.comboBox_tipoDeBarra.addMouseListener(this);
		configuracion.btnBorraBD_yReconstruir.addMouseListener(this);
		configuracion.btn_check_update.addMouseListener(this);
		
		vista.table.addMouseListener(this);
		
		borrarFila.okButton.addMouseListener(this);
		
		configuracion.lanzarVentana();
		borrarFila.lanzarVentana();
		
		update.later.addMouseListener(this);
		update.update_button.addMouseListener(this);
		update.reiniciar_y_actualizar.addMouseListener(this);
		
		login.addWindowListener(this);
		login.btn_Registrarse.addMouseListener(this);
		login.btn_login.addMouseListener(this);
		login.ojo_pass_register.addMouseListener(this);
		login.ojo_repeat_pass_register.addMouseListener(this);
		login.ojo_pass_login.addMouseListener(this);
		
		if(!autoLogin()) {
			autoLogin.dispose();
			login.lanzarVentana();
			login.setLocationRelativeTo(vista.ventana_principal); // antes de hacer visible la ventana hay que centrarla
			login.setVisible(true);
		}
		
		
		
		
		//loginController.mostrarLogin();
		
		try {
			cargarConfiguracion();
		} catch (Exception e) {
			guardarConfiguracion(configuracion.chckbxMostrarSegundosReloj.isSelected() , configuracion.chckbxMostrarNotificaciones.isSelected());
		}
		
		vista.lbl_insertarFila.setVisible(false);
		//vista.ventana_principal.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR)); // Pone a todo lo que hay en la venntana el cursor Wait
		iniciar_Conexion_Con_Servidor();
		vista.ventana_principal.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Pone a todo lo que hay en la venntana el cursor Default
		if(conexionEstablecida) {
			vista.label.setIcon(new ImageIcon("images/conectado_(32x32).png"));
			vista.lbl_loading.setVisible(false);
			vista.panel_bar_loader.setVisible(false);
			vista.lbl_insertarFila.setVisible(true);
			vista.lbl_loading_1.setVisible(false);
			vista.lbl_reconectar.setVisible(true);
			vista.label_loading_cambiosGuardados.setIcon(new ImageIcon("images/conectado_(32x32).png"));
		}else {
			vista.label.setIcon(new ImageIcon("images/no_conectado_(32x32).png"));
			laConexionHaFallado = true;
		}
		
			
		
//		generarCabeceraConNombresDeLosDiasDeLaSemana(6);
		
	
		
		vista.table.setModel(modelo);
		
		/**
		 * Hora
		 */
		
		establecerZonaHorariaConcreta("+2:00");
		seleccionarLaBaseDeDatos();
		
		if(leerPantallaParaAbrir().equals("Todos los horarios")) {
			hacerConsultaDeHorarios();
			vista.slider_dias.setVisible(false);
			vista.slider_horas.setVisible(false);
			vista.lbl_tiempo_restante.setVisible(false);
			obtener_y_guardar_idsDeHorarios_enElOrdenCorrecto();
			
			System.out.println(ids_horarios);
			
			vista.lbl_loading_borrarFila.setVisible(false);
			vista.lbl_borrarFila.setVisible(true);
			
			vista.lbl_nombre_horario.setIcon(null);
			
			vista.lbl_loading_impresora.setVisible(false);
			vista.lbl_loading_bloquear.setVisible(false);
			
		}else if(leerPantallaParaAbrir().equals("Un horario")) {
			
		
			hacerConsultaDeIntervalosHorariosDeUnHorarioConcreto(Integer.valueOf(leerHorarioParaAbrir())); 
			
			hacerConsultas_pasandoDias(Integer.valueOf(leerHorarioParaAbrir()));
			
			obtener_y_guardar_idsDeIntervaloHorario_enElOrdenCorrecto(Integer.valueOf(leerHorarioParaAbrir()));

			vista.lbl_loading_borrarFila.setVisible(false);
			vista.lbl_borrarFila.setVisible(false);
			
			vista.lbl_nombre_horario.setIcon(null);
			vista.lbl_nombre_horario.setText(obtenerNombreDeHorarioActualmenteSeleccionado(Integer.valueOf(leerHorarioParaAbrir())));
			
			vista.lbl_loading_impresora.setVisible(false);
			vista.lbl_imprimir.setVisible(true);
			
			vista.lbl_loading_bloquear.setVisible(false);
			vista.lbl_bloquear.setVisible(true);
			
			if(leerEstadoDeBloqueo().equals("true")) {
				alternar_estado_bloqueado = true;
			}else if(leerEstadoDeBloqueo().equals("false")) {
				alternar_estado_bloqueado = false;
			}
			
			accionBotonBloquear();
			
			if(vista.table.getRowCount() > 8) {
		        JOptionPane.showMessageDialog(null, "Tu horario tiene más de 8 tramos horarios , la experiencia de usuario podría ser peor", "Advertencia", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
	
		
		
		ponerNombreDelMes_y_AnioActual();
		
		
		vista.table.getModel().addTableModelListener(this);
		
		diaActualEnTexto = calendario.diasSemana[calendario.getDiaSemana(calendario.getDia())];
		
		marcarDiaActual();
		
//		JOptionPane.showMessageDialog(vista.ventana_principal, calendario.getHora() + " ," + calendario.getMinutos() + " , " +  calendario.getSegundos());
		serviceHora.scheduleWithFixedDelay(actualizarHora, 1, 1, TimeUnit.SECONDS);
		
		try {
			if(leerPantallaParaAbrir().equals("Un horario")) {
				/*
				 * Horas
				 */
				primeraHoraDelHorario_enTexto = (String) vista.table.getValueAt(0, 0);
				primeraHoraDelHorario_enTexto = primeraHoraDelHorario_enTexto.substring(0, 2);
				System.out.println(primeraHoraDelHorario_enTexto);
				primeraHoraDelHorario = Integer.parseInt(primeraHoraDelHorario_enTexto);
				System.out.println(primeraHoraDelHorario);
				
				ultimaHoraDelHorario_enTexto = (String) vista.table.getValueAt(vista.table.getRowCount() - 1, 0);
				ultimaHoraDelHorario_enTexto = ultimaHoraDelHorario_enTexto.substring(8, 10);
				System.out.println("=== "  + ultimaHoraDelHorario_enTexto);
				ultimaHoraDelHorario = Integer.parseInt(ultimaHoraDelHorario_enTexto);
				System.out.println(ultimaHoraDelHorario);
				
				/*
				 * Minutos
				 */
				primerMinutoDelHorario_enTexto = (String) vista.table.getValueAt(0, 0);
				primerMinutoDelHorario_enTexto = primerMinutoDelHorario_enTexto.substring(3, 5);
				System.out.println("=> " + primerMinutoDelHorario_enTexto);
				primerMinutoDelHorario = Integer.parseInt(primerMinutoDelHorario_enTexto);
				System.out.println(primerMinutoDelHorario);
				
				ultimoMinutoDelHorario_enTexto = (String) vista.table.getValueAt(vista.table.getRowCount() - 1, 0);
				ultimoMinutoDelHorario_enTexto = ultimoMinutoDelHorario_enTexto.substring(11, 13);
				System.out.println("=== "  + ultimoMinutoDelHorario_enTexto);
				ultimoMinutoDelHorario = Integer.parseInt(ultimoMinutoDelHorario_enTexto);
				System.out.println(ultimoMinutoDelHorario);
				
				System.out.println(calcularDiferenciaHoraria_entre_primeraHoraDelHorario_Y_ultimaHoraDelHorario());
				obtener_yEstablecerMaximoDeLaBarra();
				
				
				mapearPrimerasHorasYPrimerosMinutos();
				mapearUltimasHorasYUltimosMinutos();
				mapearPrimerasY_UltimasHorasY_Minutos();
				try {
					comprobarYPintarLaAsignaturaActual();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		
		
		vista.loading_configuracion.setVisible(false);
		vista.lbl_configuracion.setVisible(true);
		
		vista.lbl_loading_home.setVisible(false);
		vista.lbl_home.setVisible(true);
		
		configuracion.comboBox_tipoDeBarra.setSelectedItem(vista.leerElTipoDeBarra());
		tipoDeBarraAlIniciar = (String) configuracion.comboBox_tipoDeBarra.getSelectedItem();
		
		
		
		readFileFromUrlAndCheckUpdate();
		
		leerUsuarioLogueado();
		
		//JOptionPane.showMessageDialog(null, nombreUsuario_logueado + "\n" + tokenUsuario_logueado);
	}
	

	/*
	 * 
	 */
	public void ponerNombreDelMes_y_AnioActual() {
		Calendario_Horiario calendario = new Calendario_Horiario();
		vista.ventana_principal.setTitle("Creador de horarios " + " | " 
				+ calendario.diasSemana[calendario.getDiaSemana(calendario.getDia())] + " , " +  
				calendario.getDia() + " de " + calendario.getMes().toLowerCase() + " de " + calendario.getAnio());
	}
	
	/*
	 * 
	 */
	public void generarCabeceraConNombresDeLosDiasDeLaSemana(int procesarCabeceraHasta) {

		String contenidoDeLaCabeceradiasDeLaSemana [] = {"Hora" , "Lunes" , "Martes" , "Miércoles" , "Jueves" , "Viernes" , "Sábado"  , "Domingo"};
		
		for (int i = 0; i < procesarCabeceraHasta; i++) {
			vista.btnNewButton = new JButton(contenidoDeLaCabeceradiasDeLaSemana[i]);
//			vista.panel_calendario.add(vista.btnNewButton);
			vista.btnNewButton.setMargin(new Insets(20, 40, 20, 40)); // Márgenes internos del texto
			
//			vista.btnNewButton.setMinimumSize(new Dimension(5 , 5));
//			vista.btnNewButton.setMaximumSize(new Dimension(10,10));
			vista.btnNewButton.setPreferredSize(new Dimension(159 , 50)); // Márgenes internos fijos
//			vista.btnNewButton.setEnabled(false);
			
		
			vista.btnNewButton.setBackground(new Color(153,180,209,255));
			vista.btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
			
		}
		
		
		// Antes de hacer repaint() hay que validar si hay más componentes en la ventana
		// https://stackoverflow.com/questions/8853397/repaint-in-java
		vista.ventana_principal.getContentPane().validate(); 
		vista.ventana_principal.getContentPane().repaint();
	}
	
	/*
	 * 
	 */
	public void generarAsignaturas(String Query , boolean escribirCabecera , String diaDeLaSemana , boolean sonIds) {

		String contenidoDeLaCabeceradiasDeLaSemana [] = {"Hora" , "Lunes" , "Martes" , "Miércoles" , "Jueves" , "Viernes" , "Sábado"  , "Domingo"};
		
		int numeroDeColumna = 0;
		
		try {
			
			Statement st = 	conexion.createStatement();
            ResultSet resultSet = st.executeQuery(Query);
            
            int c = resultSet.getMetaData().getColumnCount();
            
            if(escribirCabecera) {
            	for (int i = 0; i < contenidoDeLaCabeceradiasDeLaSemana.length - 2; i++) {
            		modelo.addColumn(contenidoDeLaCabeceradiasDeLaSemana[i]);
            	}
            }
            
            /* 
            for (int i = 1; i <= c; i++){
            	modelo.addColumn(resultSet.getMetaData().getColumnName(i)); // Escribe la cabecera nativa de SQL
            }
            */
            			            
            Vector<Object> fila = null;
            
           
            
            while(resultSet.next()) {
            	fila = new Vector<Object>();
                
                
            	for (int i = 1; i <= c; i++) {
                	fila.add(resultSet.getString(i));
//                	System.out.println(resultSet.getString(i));
                } 
            	
            	
            	
            	 if((diaDeLaSemana.equals("Lunes") || diaDeLaSemana.equals("Martes") || diaDeLaSemana.equals("Miércoles") || diaDeLaSemana.equals("Jueves") || diaDeLaSemana.equals("Viernes")) && sonIds == false) {
            		 asignaturas_enDia_Nombre_En_Texto.add(fila.toString());
            		 // Guardar id en el ArrayList y Usarlos para poner las asignaturas en el orden correcto
//            		 System.out.println(Integer.parseInt(fila.toString()).getClass().getSimpleName());
            	 }else if((diaDeLaSemana.equals("Lunes") || diaDeLaSemana.equals("Martes") || diaDeLaSemana.equals("Miércoles") || diaDeLaSemana.equals("Jueves") || diaDeLaSemana.equals("Viernes")) && sonIds == true) {
            		 id_intervalo_hora_asignatura.add(fila.toString());
            	 }else {
            		modelo.insertRow(modelo.getRowCount(),fila); 
            	 }
               
            	
	            
               
               
               
                
                System.out.println("=> " + modelo.getRowCount());
        	}
            
           
            
//			JOptionPane.showMessageDialog(null, "Consulta realizada con éxito");
          
    	} catch (Exception ex) {  
    		
//    		JOptionPane.showMessageDialog(null, "Error en la consulta"); 
    		
    		StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			
			JOptionPane.showMessageDialog(null, "Se ha producido un error :( " + "\r\n" + "\r\n" + "\r\n"  + errors.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
    		
    		System.out.println(ex.getMessage());
    		ex.printStackTrace();
    	}

		
			
		vista.centrarDatosDeTabla();
		
		System.out.println("id => " + id_intervalo_hora_asignatura);
		
        
		System.out.println("######");
		
		for (int i = 0; i < id_intervalo_hora_asignatura.size(); i++) {
			System.out.println("error " + id_intervalo_hora_asignatura.get(i));
			
		}
		
		for (int i = 0; i < id_intervalo_hora_asignatura.size(); i++) {
			id_intervalo_hora_asignatura_limpio.add(id_intervalo_hora_asignatura.get(i).substring(1, id_intervalo_hora_asignatura.get(i).length() - 1));
			
		}
		
		for (int i = 0; i < id_intervalo_hora_asignatura_limpio.size(); i++) {
			System.out.println("error " + id_intervalo_hora_asignatura_limpio.get(i));
			id_intervalo_hora_asignatura_limpio_entero.add(Integer.parseInt(id_intervalo_hora_asignatura_limpio.get(i)));
		}
		
		System.out.println("#");
		for (int i = 0; i < id_intervalo_hora_asignatura_limpio_entero.size() / 3; i++) {
			System.out.println(id_intervalo_hora_asignatura_limpio_entero.get(i));
		}
		
		
		if(diaDeLaSemana.equals("Lunes")) {
			numeroDeColumna = 1;
        } else if(diaDeLaSemana.equals("Martes")) {
			numeroDeColumna = 2;
        }else if(diaDeLaSemana.equals("Miércoles")) {
			numeroDeColumna = 3;
        }else if(diaDeLaSemana.equals("Jueves")) {
			numeroDeColumna = 4;
        }else if(diaDeLaSemana.equals("Viernes")) {
			numeroDeColumna = 5;
        }
		
		for (int i = 0; i < asignaturas_enDia_Nombre_En_Texto.size(); i++) {
			System.out.println("error " + asignaturas_enDia_Nombre_En_Texto);
			System.out.println("error " + id_intervalo_hora_asignatura_limpio_entero);
        	try {
        		vista.table.setValueAt(asignaturas_enDia_Nombre_En_Texto.get(i).substring(1, asignaturas_enDia_Nombre_En_Texto.get(i).length() - 1), id_intervalo_hora_asignatura_limpio_entero.get(i) - 1, numeroDeColumna);
			} catch (Exception e) {
				e.printStackTrace();
			}
					
		}
		
		
		
		System.out.println("oooooooooooo");
		System.out.println(asignaturas_enDia_Nombre_En_Texto);
		for (int i = 0; i < asignaturas_enDia_Nombre_En_Texto.size(); i++) {
			System.out.println("*" + asignaturas_enDia_Nombre_En_Texto.get(i));
		}
		
		
		// Antes de hacer repaint() hay que validar si hay más componentes en la ventana
		// https://stackoverflow.com/questions/8853397/repaint-in-java
		vista.ventana_principal.getContentPane().validate(); 
		vista.ventana_principal.getContentPane().repaint();
		
		
	}
	
	/*
	 * 
	 */
	public void consultarHorarios(String Query , boolean escribirCabecera) {

		String contenidoDeLaCabeceraDatosSelectivosDeLaTabla [] = {"Nombre" , "Fecha de creación" , "Fecha de modificación"};
		
		int numeroDeColumna = 0;
		
		try {
			
			Statement st = 	conexion.createStatement();
            ResultSet resultSet = st.executeQuery(Query);
            
            int c = resultSet.getMetaData().getColumnCount();
            
            
            if(escribirCabecera) {
            	for (int i = 0; i < contenidoDeLaCabeceraDatosSelectivosDeLaTabla.length; i++) {
            		modelo.addColumn(contenidoDeLaCabeceraDatosSelectivosDeLaTabla[i]);
            		System.out.println("----- > " + contenidoDeLaCabeceraDatosSelectivosDeLaTabla[i]);
            	}
            }
            
            /* 
            for (int i = 1; i <= c; i++){
            	modelo.addColumn(resultSet.getMetaData().getColumnName(i)); // Escribe la cabecera nativa de SQL
            }
            */
            			            
            Vector<Object> fila = null;
            
           
            
            while(resultSet.next()) {
            	fila = new Vector<Object>();
                
                
            	for (int i = 1; i <= c; i++) {
                	fila.add(resultSet.getString(i));
//                	System.out.println(resultSet.getString(i));
                } 
            	
            	
//            	
//            	 if((diaDeLaSemana.equals("Lunes") || diaDeLaSemana.equals("Martes") || diaDeLaSemana.equals("Miércoles") || diaDeLaSemana.equals("Jueves") || diaDeLaSemana.equals("Viernes")) && sonIds == false) {
//            		 asignaturas_enDia_Nombre_En_Texto.add(fila.toString());
//            		 // Guardar id en el ArrayList y Usarlos para poner las asignaturas en el orden correcto
////            		 System.out.println(Integer.parseInt(fila.toString()).getClass().getSimpleName());
//            	 }else if((diaDeLaSemana.equals("Lunes") || diaDeLaSemana.equals("Martes") || diaDeLaSemana.equals("Miércoles") || diaDeLaSemana.equals("Jueves") || diaDeLaSemana.equals("Viernes")) && sonIds == true) {
//            		 id_intervalo_hora_asignatura.add(fila.toString());
//            	 }else {
//            		modelo.insertRow(modelo.getRowCount(),fila); 
//            	 }
               
            	
            	modelo.insertRow(modelo.getRowCount(),fila); 
               
	          
               
                
                System.out.println("=> " + modelo.getRowCount());
        	}

//			Vector<JButton> tmp = new Vector<JButton>();
//			tmp.add(new JButton("Entrar"));
//			
			modelo.addColumn("Botones");
			
			
//			for (int i = 0; i < tmp.size(); i++) {
//				tmp.get(i).setText("Entrar");
//			}
			
			
			for (int i = 0; i < vista.table.getRowCount(); i++) {
//				vista.table.setValueAt(tmp, i, vista.table.getColumnCount() - 1);
				modelo.setValueAt(new JButton("Entrar").getText(), i, vista.table.getColumnCount() - 1);
				vista.table.getColumn("Botones").setCellRenderer(new ButtonRenderer());
				vista.table.getColumn("Botones").setCellEditor(new ButtonEditor(new JCheckBox()));
			}
			
			

//			JOptionPane.showMessageDialog(null, "Consulta realizada con éxito");
          
    	} catch (Exception ex) {  
    		
//    		JOptionPane.showMessageDialog(null, "Error en la consulta"); 
    		
    		StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			
			JOptionPane.showMessageDialog(null, "Se ha producido un error :( " + "\r\n" + "\r\n" + "\r\n"  + errors.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
    		
    		System.out.println(ex.getMessage());
    		ex.printStackTrace();
    	}

		
			
		vista.centrarDatosDeTabla();
		
//		System.out.println("id => " + id_intervalo_hora_asignatura);
//		
//        
//		System.out.println("######");
//		
//		for (int i = 0; i < id_intervalo_hora_asignatura.size(); i++) {
//			System.out.println(id_intervalo_hora_asignatura.get(i));
//			
//		}
//		
//		for (int i = 0; i < id_intervalo_hora_asignatura.size(); i++) {
//			id_intervalo_hora_asignatura_limpio.add(id_intervalo_hora_asignatura.get(i).substring(1, id_intervalo_hora_asignatura.get(i).length() - 1));
//			
//		}
//		
//		for (int i = 0; i < id_intervalo_hora_asignatura_limpio.size(); i++) {
//			System.out.println(id_intervalo_hora_asignatura_limpio.get(i));
//			id_intervalo_hora_asignatura_limpio_entero.add(Integer.parseInt(id_intervalo_hora_asignatura_limpio.get(i)));
//		}
//		
//		System.out.println("#");
//		for (int i = 0; i < id_intervalo_hora_asignatura_limpio_entero.size() / 3; i++) {
//			System.out.println(id_intervalo_hora_asignatura_limpio_entero.get(i));
//		}
//		
//		
//		if(diaDeLaSemana.equals("Lunes")) {
//			numeroDeColumna = 1;
//        } else if(diaDeLaSemana.equals("Martes")) {
//			numeroDeColumna = 2;
//        }else if(diaDeLaSemana.equals("Miércoles")) {
//			numeroDeColumna = 3;
//        }else if(diaDeLaSemana.equals("Jueves")) {
//			numeroDeColumna = 4;
//        }else if(diaDeLaSemana.equals("Viernes")) {
//			numeroDeColumna = 5;
//        }
//		
//		for (int i = 0; i < asignaturas_enDia_Nombre_En_Texto.size(); i++) {
//        	vista.table.setValueAt(asignaturas_enDia_Nombre_En_Texto.get(i).substring(1, asignaturas_enDia_Nombre_En_Texto.get(i).length() - 1), id_intervalo_hora_asignatura_limpio_entero.get(i) - 1, numeroDeColumna);		
//		}
//		
//		
//		
//		System.out.println("oooooooooooo");
//		System.out.println(asignaturas_enDia_Nombre_En_Texto);
//		for (int i = 0; i < asignaturas_enDia_Nombre_En_Texto.size(); i++) {
//			System.out.println("*" + asignaturas_enDia_Nombre_En_Texto.get(i));
//		}
//		
//		
		// Antes de hacer repaint() hay que validar si hay más componentes en la ventana
		// https://stackoverflow.com/questions/8853397/repaint-in-java
		vista.ventana_principal.getContentPane().validate(); 
		vista.ventana_principal.getContentPane().repaint();
		
		
	}


	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		if(e.getSource().equals(vista.lbl_reconectar)) {
			System.out.println("reconectar");
			
			try {
				restartApplication(null);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
		}else if(e.getSource().equals(vista.lbl_reparar)) {
			System.out.println("reparar");
			
			
			
			try {
				restartApplication(null);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
		}else if(e.getSource().equals(vista.lbl_insertarFila)) {
			
//			JOptionPane.showMessageDialog(vista.ventana_principal, "Insertar Fila");
			
			
			
			if(leerPantallaParaAbrir().equals("Todos los horarios")) {
				insertData_btn_insertarFila_deHorario();
			}else if(leerPantallaParaAbrir().equals("Un horario")) {
				insertData_btn_insertarFila(Integer.valueOf(leerHorarioParaAbrir()));
			}
			
			
			
		}else if(e.getSource().equals(vista.lbl_configuracion)) {
			
			configuracion.setLocationRelativeTo(vista.ventana_principal); // antes de hacer visible la ventana hay que centrarla
			configuracion.setVisible(true);
			
		}else if(e.getSource().equals(configuracion.chckbxMostrarSegundosReloj)) {
			System.out.println("Mostrar días del mes anterior y del mes siguiente");
			
			
		}else if(e.getSource().equals(vista.table)) {
			System.out.println("Tabla ");
			
			if(!service.isShutdown()) {
				service.shutdown();
				service = Executors.newSingleThreadScheduledExecutor();
			}
			
			service.scheduleWithFixedDelay(comprobarSiSeEstaEditandoAlgunaCelda, 400, 400, TimeUnit.MILLISECONDS);	
			
			if(hayCampoNoValido && (errorEn == filaDeLaTabla)) {
		        JOptionPane.showMessageDialog(vista.ventana_principal, "Hay un campo no válido , pulse en reparar para arreglarlo", "Advertencia", JOptionPane.WARNING_MESSAGE);
		        System.out.println(errorEn + " , " + filaDeLaTabla);
		        vista.label_loading_reparacion.setVisible(false);
		        vista.lbl_reparar.setVisible(true);
			}
		}else if(e.getSource().equals(configuracion.test_notificaciones)) {
			mostrarNotificacion("Test de notificación", "Si estas viendo esto, es que todo está bien 👍");
		}else if(e.getSource().equals(vista.lbl_home)) {
			
//			while(vista.table.getColumnCount() != 0) {
//				limpiarTabla(vista.table , true);
//			}
//			
//			serviceEsperar.schedule(esperar, 1, TimeUnit.SECONDS);
//
//			serviceEsperar.schedule(moverDatos, 3, TimeUnit.SECONDS);
//			
//			serviceEsperar.schedule(esperarALimpiarDelTodoLaTabla, 4, TimeUnit.SECONDS);
			
			guardarLaProximaPantallaQueSeAbre("Todos los horarios");
			
			try {
				restartApplication(null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if(e.getSource().equals(vista.lbl_borrarFila)) {
			System.out.println("borrar fila");
			obtener_e_insertar_nombresDeHorarios_enComboBoxDeBorrarFila();
			
			
			if(borrarFila.comboBox.getItemCount() == 0) {
				borrarFila.okButton.setEnabled(false);
			}else {
				borrarFila.okButton.setEnabled(true);
			}
			
			borrarFila.setLocationRelativeTo(vista.ventana_principal);
			borrarFila.setVisible(true);
		}else if(e.getSource().equals(borrarFila.okButton)) {
//	        JOptionPane.showMessageDialog(null, "Borrar");
			
			
//	        deleteById("horario", ids_horarios.get(borrarFila.comboBox.getSelectedIndex()));
	        deleteById_false_deleted("horario", ids_horarios.get(borrarFila.comboBox.getSelectedIndex()));
	        try {
				restartApplication(null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(e.getSource().equals(configuracion.btnBorraBD_yReconstruir)) {
			String[] botones = {"Sí , sé lo que hago", "No, sácame de aquí !"};
//			Icon icono = new ImageIcon(getClass().getResource("images/advertencia_(32x32).png"));
			int ventana = JOptionPane.showOptionDialog(null, 
							"¿ Estás seguro de que quieres hacer esto , esta opción sirve si no tenías la base de datos o esta está dañada o si quieres borrarla ( se borrarán todos los datos ) ?", 
							"Advertencia", 
							JOptionPane.DEFAULT_OPTION, 
							JOptionPane.WARNING_MESSAGE, new ImageIcon("images/advertencia_(32x32).png") ,
							botones ,
							botones[1]);
			if(ventana == 0) {
				if(laConexionHaFallado) {
					iniciar_Conexion_Con_Servidor_Si_La_BD_No_Existe();
				}
				borrar_y_reconstruir_BD();
			}
			
		}else if(e.getSource().equals(vista.lbl_imprimir)) {
//			JOptionPane.showMessageDialog(null, "Imprimir");
			
			if(vista.table.getRowCount() <= 7) {
				JOptionPane.showMessageDialog(null, "Para este horario , se recomienda impresión en horizontal en tamaño A4", "Advertencia", JOptionPane.WARNING_MESSAGE);
			}else {
				if(vista.table.getRowCount() >= 8) {
					JOptionPane.showMessageDialog(null, "Para este horario , se recomienda impresión en horizontal en tamaño Carta", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
	        
			
			utilJTablePrint(vista.table, obtenerNombreDeHorarioActualmenteSeleccionado(Integer.valueOf(leerHorarioParaAbrir())), "Creador de horarios", true);
		}else if(e.getSource().equals(vista.lbl_bloquear)) {
//			JOptionPane.showMessageDialog(null, "Bloquear");
			
			accionBotonBloquear();
			
		}else if(e.getSource().equals(update.update_button) && update.update_button.isEnabled()) {
			/*
			try {
				Desktop.getDesktop().browse(new URI(readFileFromUrlAndOpenUpdateDownloadLink()));
			} catch (Exception er) {
				JOptionPane.showInternalMessageDialog(null, "Error ");
			}
			*/
			update.downloading.setVisible(true);
			update.progressBar.setVisible(true);
			update.update_button.setEnabled(false);
			update.later.setEnabled(false);
			
			serviceUpdate.schedule(esperarAntesDeActualizar, 1, TimeUnit.MILLISECONDS);
			
			
			
		}else if(e.getSource().equals(update.later) && update.later.isEnabled()) {
			update.setVisible(false);
		}else if(e.getSource().equals(update.reiniciar_y_actualizar)) {
			//JOptionPane.showMessageDialog(null, "reiniciar_y_actualizar");
			
			/*
			 * Ejecuta el actualizador
			 */
			
			try {
				Runtime runTime = Runtime.getRuntime();
				
				String ruta = "download/" + downloadedFileName;
				String directorioDeEjecutable = ruta;

				Process process = runTime.exec(directorioDeEjecutable);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			System.exit(0);
			
		}else if(e.getSource().equals(configuracion.btn_check_update)) {
			//JOptionPane.showMessageDialog(null, "btn_check_update");
			update.last_version.setText("Última versión:   ");
			update.actual_version.setText("Versión actual:   ");
			readFileFromUrlAndCheckUpdate();
			if(!actualizacion_necesaria) {
				JOptionPane.showMessageDialog(null, "Tienes la última versión");
			}
		}else if(e.getSource().equals(login.btn_login) && login.btn_login.isEnabled()) {
			//JOptionPane.showMessageDialog(null, "login");
			
			serviceRegister.schedule(esperarAntesDeIniciarSesion, 1, TimeUnit.MILLISECONDS);
			
		}else if(e.getSource().equals(login.btn_Registrarse) && login.btn_Registrarse.isEnabled()) {
			//JOptionPane.showMessageDialog(null, "registrarse 😎");
			
			serviceRegister.schedule(esperarAntesDeRegistrarUsuario, 1, TimeUnit.MILLISECONDS);
			
			
			//🔥
			
		}else if(e.getSource().equals(login.ojo_pass_register)) {
			ver_ocultar_password(visible_pass_register, "ojo_pass_register");
		}else if(e.getSource().equals(login.ojo_repeat_pass_register)) {
			ver_ocultar_password(visible_repeat_pass_register, "ojo_repeat_pass_register");
		}else if(e.getSource().equals(login.ojo_pass_login)) {
			ver_ocultar_password(visible_pass_login, "ojo_pass_login");
		}
	}
	
	


	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(configuracion.btnBorraBD_yReconstruir)) {
//			JOptionPane.showMessageDialog(null, "Detelete ❗❗❗❗❗");
			configuracion.lbl_Montapuercos_Clash_Royale_izq.setVisible(true);
			configuracion.lbl_Montapuercos_Clash_Royale_derch.setVisible(true);
		}
		
	}


	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(configuracion.btnBorraBD_yReconstruir)) {
//			JOptionPane.showMessageDialog(null, "Detelete ❗❗❗❗❗");
			configuracion.lbl_Montapuercos_Clash_Royale_izq.setVisible(false);
			configuracion.lbl_Montapuercos_Clash_Royale_derch.setVisible(false);
		}
	}


	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		if(e.getSource().equals(vista.lbl_reconectar)) {
			System.out.println("flecha derecha");
			vista.lbl_reconectar.setBorder(BorderFactory.createLoweredBevelBorder());
		}else if(e.getSource().equals(vista.lbl_reparar)) {
			System.out.println("flecha izquierda");
			vista.lbl_reparar.setBorder(BorderFactory.createLoweredBevelBorder());
		}else if(e.getSource().equals(vista.lbl_insertarFila)) {
			System.out.println("hoy");
			vista.lbl_insertarFila.setBorder(BorderFactory.createLoweredBevelBorder());
		}else if(e.getSource().equals(vista.lbl_configuracion)) {
			System.out.println("configuracion");
			vista.lbl_configuracion.setBorder(BorderFactory.createLoweredBevelBorder());
		}else if(e.getSource().equals(vista.lbl_home)) {
			System.out.println("home");
			vista.lbl_home.setBorder(BorderFactory.createLoweredBevelBorder());
		}else if(e.getSource().equals(vista.lbl_borrarFila)) {
//			System.out.println("borrar fila");
			vista.lbl_borrarFila.setBorder(BorderFactory.createLoweredBevelBorder());
		}else if(e.getSource().equals(vista.lbl_imprimir)) {
			vista.lbl_imprimir.setBorder(BorderFactory.createLoweredBevelBorder());
		}
	}


	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		if(e.getSource().equals(vista.lbl_reconectar)) {
//			System.out.println("flecha derecha");
			vista.lbl_reconectar.setBorder(BorderFactory.createRaisedBevelBorder());
		}else if(e.getSource().equals(vista.lbl_reparar)) {
//			System.out.println("flecha izquierda");
			vista.lbl_reparar.setBorder(BorderFactory.createRaisedBevelBorder());
		}else if(e.getSource().equals(vista.lbl_insertarFila)) {
//			System.out.println("flecha izquierda");
			vista.lbl_insertarFila.setBorder(BorderFactory.createRaisedBevelBorder());
		}else if(e.getSource().equals(vista.lbl_configuracion)) {
//			System.out.println("configuracion");
			vista.lbl_configuracion.setBorder(BorderFactory.createRaisedBevelBorder());
		}else if(e.getSource().equals(vista.lbl_home)) {
//			System.out.println("home");
			vista.lbl_home.setBorder(BorderFactory.createRaisedBevelBorder());
		}else if(e.getSource().equals(vista.lbl_borrarFila)) {
			vista.lbl_borrarFila.setBorder(BorderFactory.createRaisedBevelBorder());
		}else if(e.getSource().equals(vista.lbl_imprimir)) {
			vista.lbl_imprimir.setBorder(BorderFactory.createRaisedBevelBorder());
		}
	}
	
	
	/*
	 * 
	 */
	public boolean puedoPintarElDiaDeHoy() {
		boolean puedoPintarElDiaDeHoy = false;
		
		if(mesActual == calendario.getMesEnNumero() && anioActual == calendario.getAnio()) {
			puedoPintarElDiaDeHoy = true;
		}
		
		return puedoPintarElDiaDeHoy;
	}
	
	

	
	/*
	 * 
	 */
	private void obtenerUltimosDiasDelMesAnterior() {
		int mesActualParaDeshacerLaAccion = calendario.getMesEnNumero();
		int anioActualParaDeshacerLaAccion = calendario.getAnio(); 
		
		int mesAnterior = 0;
		int ultimoDiaDelMesAnterior = 0;
		
		if(calendario.getMesEnNumero() < 1) { // Cambia de año ( en el pasado )
			calendario.setAnio(calendario.getAnio() - 1);
			calendario.setMes(11);
			
			mesAnterior = calendario.getMesEnNumero() - 1;
			calendario.setMes(mesAnterior);
			ultimoDiaDelMesAnterior = calendario.getDiasTotales();
			if(calendario.getMesEnNumero() == 10) { // Excepción del mes Enero
//				System.out.println("=>>>>>>" + calendario.getMesEnNumero());
				ultimoDiaDelMesAnterior++;
			}
		}else {
			mesAnterior = calendario.getMesEnNumero() - 1;
			calendario.setMes(mesAnterior);
			ultimoDiaDelMesAnterior = calendario.getDiasTotales();
			if(calendario.getMesEnNumero() == 10) { // Excepción del mes Enero
//				System.out.println("=>>>>>>" + calendario.getMesEnNumero());
				ultimoDiaDelMesAnterior++;
			}
		}
		
		
		
		
		System.out.println("----");
		System.out.println(mesActualParaDeshacerLaAccion);
		System.out.println(mesAnterior);
		System.out.println(ultimoDiaDelMesAnterior);
		
		
		calendario.setMes(mesActualParaDeshacerLaAccion); // Deshacer la accion
		calendario.setAnio(anioActualParaDeshacerLaAccion);
		
		int ultimaPosicionDelContenedorDondeHayBotonVacio = 0;
		

		
		for (int i = 0; i < contenedorDeBotones_numDias.size(); i++) {
			if(contenedorDeBotones_numDias.get(i).getText().equals("")) {
				ultimaPosicionDelContenedorDondeHayBotonVacio = contenedorDeBotones_numDias.indexOf(contenedorDeBotones_numDias.get(i));
			}
		}
		
		System.out.println("ultimaPosicionDelContenedorDondeHayBotonVacio : " + ultimaPosicionDelContenedorDondeHayBotonVacio);
		
		for (int i = ultimaPosicionDelContenedorDondeHayBotonVacio; i >= 0; i--) {
			
			if(contenedorDeBotones_numDias.get(i).getText().equals("")) {
				contenedorDeBotones_numDias.get(i).setText(String.valueOf(ultimoDiaDelMesAnterior));
				ultimoDiaDelMesAnterior--;
			}
			
		}
		
	}
	
	



	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
	}


	@Override
	public void windowClosing(WindowEvent e) {
		
		quitarDiasDelMesAnteriorYSiguiente();
		
		
		
		if(e.getSource().equals(vista.ventana_principal)) {
			closeConnection();
		}else if(e.getSource().equals(configuracion)) {
			configurarTipoDeBarra();	
		}else if(e.getSource().equals(login)) {
			System.exit(0);
		}
		
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {

		String pulsa = KeyEvent.getKeyText(e.getKeyCode());
		System.out.println(pulsa);
		if (pulsa.equals("Escape")) { // Para poder cerrar la ventana desde la tecla "Escape"
			configuracion.setVisible(false);
			quitarDiasDelMesAnteriorYSiguiente();
			configurarTipoDeBarra();
		}
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	/*
	 * 
	 */
	private void quitarDiasDelMesAnteriorYSiguiente() {
		if(!configuracion.isVisible()) {

			for (int i = 0; i < contenedorDeBotones_numDias.size(); i++) { 
				vista.ventana_principal.remove(contenedorDeBotones_numDias.get(i)); // Borramos todos los botones al cambiar de mes
				vista.ventana_principal.add(contenedorDeBotones_numDias.get(i)); // Volvemos a cargar el contenido del contenedor para que el mes anterior se borre
//				vista.panel_calendario.validate();
//				vista.panel_calendario.repaint();
			}
			
		
			
			contenedorDeBotones_numDias.clear(); // Limpiamos el contenedor
			
			
//			generarAsignaturas();
			
//			System.out.println(configuracion.chckbxMostrarDias.isSelected());
			
			
			guardarConfiguracion(configuracion.chckbxMostrarSegundosReloj.isSelected() , configuracion.chckbxMostrarNotificaciones.isSelected());
		}
	}
	
	
	/*
	 * => Guardar Configuración <=
	 */
	
	public void guardarConfiguracion(boolean estaSeleccionado_chckbxMostrarDias , boolean estaSeleccionado_chckbxMostrarNotificaciones) {
		/*
		 * Escanea el estado de la configuración actual
		 */
		String estado_chckbxMostrarDias = "true";
		String estado_chckbxMostrarNotificaciones = "true";
		
		if(!estaSeleccionado_chckbxMostrarDias) {
			estado_chckbxMostrarDias = "false";
			System.out.println("false");
		}
		
		if(!estaSeleccionado_chckbxMostrarNotificaciones) {
			estado_chckbxMostrarNotificaciones = "false";
			System.out.println("false");
		}
		
//		System.out.println(chckbxMostrarDias.isSelected());
		
		
		/*
		 * Ecribe el fichero con la configuración actual
		 */
		
		File ficheroSalida = new File("[Calendario - Opciones].txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print("[Calendario - Opciones]\r\n" + 
					"\r\n" + 
					"Mostrar segundos en el reloj = "+ estado_chckbxMostrarDias + "\r\n" + 
							"Mostrar notificación al cambiar de asignatura = " + estado_chckbxMostrarNotificaciones + "");
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "Configuración por defecto cargada ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(vista.ventana_principal, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	

	/*
	 * Carga la configuracion 
	 */
	public void cargarConfiguracion() {
		
		/*
		 * Lee el fichero con la configuración actual 
		 */
		
		Scanner lectorFichero;
		
		File ficheroSalida = new File("[Calendario - Opciones].txt");
		
		String [] datos = new String [4];
		int i = 0;
		
		try {
			lectorFichero = new Scanner(ficheroSalida);
			
			while (lectorFichero.hasNext()) {
//				System.out.println(lectorFichero.nextLine());
				datos[i] = lectorFichero.nextLine();
				i++;
			}
			
			lectorFichero.close();
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo de configuración , se creará automáticamente con la configuración por defecto" + "\r\n" + "\r\n" + "\r\n" + error.toString()  , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
			
		}
		
		/*
		 *  Carga la configuración actual 
		 */
		System.out.println(".....");
		for (int j = 0; j < datos.length; j++) {
			System.out.println(datos[j]);
			if(datos[j].equals("Mostrar segundos en el reloj = false") || 
					datos[j].equals("Mostrar segundos en el reloj = true")) {
				String tmp = datos[j];
				System.out.println(tmp.substring((tmp.indexOf("=") + 2 ), tmp.length()));
				if(tmp.substring((tmp.indexOf("=") + 2 ), tmp.length()).equals("true")) {
					configuracion.chckbxMostrarSegundosReloj.setSelected(true);
					System.out.println("hola ....................");
					
					System.out.println("------------" + configuracion.chckbxMostrarSegundosReloj.isSelected());
				}else if(tmp.substring((tmp.indexOf("=") + 2 ), tmp.length()).equals("false")) {
					configuracion.chckbxMostrarSegundosReloj.setSelected(false);
					
					System.out.println("------------" + configuracion.chckbxMostrarSegundosReloj.isSelected());
				}
			
			}else if(datos[j].equals("Mostrar notificación al cambiar de asignatura = false") || 
					datos[j].equals("Mostrar notificación al cambiar de asignatura = true")) {
				String tmp = datos[j];
				System.out.println(tmp.substring((tmp.indexOf("=") + 2 ), tmp.length()));
				if(tmp.substring((tmp.indexOf("=") + 2 ), tmp.length()).equals("true")) {
					configuracion.chckbxMostrarNotificaciones.setSelected(true);
					System.out.println("hola ....................");
					
					System.out.println("------------" + configuracion.chckbxMostrarSegundosReloj.isSelected());
				}else if(tmp.substring((tmp.indexOf("=") + 2 ), tmp.length()).equals("false")) {
					configuracion.chckbxMostrarNotificaciones.setSelected(false);
					
					System.out.println("------------" + configuracion.chckbxMostrarSegundosReloj.isSelected());
				}
			
			}
		}
		
	}
	
	
	
	/*
	 * SQL Operations
	 */
	public void iniciar_Conexion_Con_Servidor() {
		MySQLConnection(user, pass, BD);
	}
	
	/*
	 * 
	 */
	private void iniciar_Conexion_Con_Servidor_Si_La_BD_No_Existe() {
		MySQLConnection(user, pass, "");
	}
	
	
	public void MySQLConnection(String user, String pass, String db_name) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql:" + urlDelServidor_incompleta + db_name, user, pass);
			System.out.println("Se ha iniciado la conexión con el servidor");
			conexionEstablecida = true;
		} catch (Exception ex) {
			System.out.println("Error en la conexión.");
			conexionEstablecida = false;
		}
	}

	public void createDB(String name) {
		try {
			String Query = "CREATE DATABASE " + name;
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			MySQLConnection("root", "root", name);
			System.out.println("Se ha creado la base de datos " + name);
		} catch (Exception ex) {
			System.out.println("Error en la creación.");
		}
	}

	/*
	 * 
	 */
	public void insertData_btn_insertarFila(int id_horario) {
		
		
		
		String [] diasDeLaSemanaFijo = {"Lunes" , "Martes" , "Miércoles" , "Jueves" , "Viernes"}; 
		
		try {
			String Query = "INSERT INTO intervalo_hora(hora_inicio , hora_fin , id_horario) VALUES('00:00' , '00:00' , " + id_horario +");";
			
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			for (int i = 0; i < 5; i++) {
//				System.out.println("=>>>>>>>>>>>>>>>>>>" + vista.table.getRowCount());
				Query = "INSERT INTO asignatura(nombre_asignatura , dia , id_intervalo_hora , id_horario) VALUES('' , '" + diasDeLaSemanaFijo[i] + "' , " + (vista.table.getRowCount() + 1) + ", " + id_horario + ");";
				
				st = conexion.createStatement();
				st.executeUpdate(Query);
			}
			
			
			System.out.println("Datos almacenados correctamente.");
		}
		catch (Exception ex) {
			System.out.println("Error guardando datos.");
		}
		
		
		
		
//		modelo.insertRow(filaDeLaTabla, fila);
//		hacerConsultas_pasandoDias();
		try {
			restartApplication(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	/*
	 * 
	 */
	public void insertData_btn_insertarFila_deHorario() {
		
		try {
			String Query = "INSERT INTO horario(nombre_horario , fecha_de_creacion , fecha_de_modificacion) VALUES('' , NOW() , NOW());";
			
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			
			
			
			System.out.println("Datos almacenados correctamente.");
		}
		catch (Exception ex) {
			System.out.println("Error guardando datos.");
		}
		
		
		
		
//		modelo.insertRow(filaDeLaTabla, fila);
//		hacerConsultas_pasandoDias();
		try {
			restartApplication(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	public void deleteRecord(String table_name, String nombre_campo, String valor){
		try {
			String Query = "DELETE FROM " + table_name + " WHERE "+nombre_campo + "= \"" + valor + "\"";
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Datos eliminados correctamente.");
		} 
		catch (Exception ex) {
			 System.out.println("Error eliminando datos.");
		}
	}
	
	/*
	 * 
	 */
	private void deleteById(String table_name,int id){ // Borra más datos de la cuenta !!!!!
		try {
			String Query = "DELETE FROM " + table_name + " WHERE " + table_name + ".id = " + id +";";
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Datos eliminados correctamente.");
		} 
		catch (Exception ex) {
			 System.out.println("Error eliminando datos.");
		}
	}
	
	/*
	 * 
	 */
	private void deleteById_false_deleted(String table_name,int id){ 
		try {
			String Query = "UPDATE " + table_name + " SET borrado = true\r\n" + 
					"WHERE (id = " + id + ");";
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Datos eliminados correctamente.");
		} 
		catch (Exception ex) {
			 System.out.println("Error eliminando datos.");
		}
	}
	
	public void createTable(String name, String[] campos, String[] tipos) {
		try {
			String Query = "CREATE TABLE " + name + "(";
			Query += campos[0] + " " + tipos[0] + " PRIMARY KEY";
			for (int i = 1; i < campos.length; i++)
				Query += "," + campos[i] + " " + tipos[i];
			Query += ";)";
			System.out.println(Query);
			Statement st = conexion.createStatement();
			// st.executeUpdate(Query);
		} catch (Exception ex) {
			System.out.println("Error en la creación.");
		}
	}

	public void closeConnection() {
		try {
			conexion.close();
			System.out.println("Se ha finalizado la conexión con el servidor");
		} catch (Exception ex) {
			System.out.println("Error en la desconexión.");
		}
	}
	
	/*
	 * 
	 */
	public void updateData_asignatura(String table_name, String textoParaActualizar , String diaDeLaSemana , int id_intervalo_hora , int id_horario) {
		try {
			String Query = "UPDATE " + table_name +" SET nombre_asignatura = '" + textoParaActualizar + "'\r\n" + 
						  " WHERE (dia = '" + diaDeLaSemana + "' AND id_intervalo_hora = " + id_intervalo_hora + ") AND id_horario = " + id_horario +";";


			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Datos actualizados correctamente.");
			vista.label_loading_cambiosGuardados.setIcon(new ImageIcon("images/conectado_(32x32).png"));
		}
		catch (Exception ex) {
			vista.label_loading_cambiosGuardados.setIcon(new ImageIcon("images/no_conectado_(32x32).png"));
			System.out.println("Error guardando datos.");
			ex.printStackTrace();
		}
		
	}
	
	/*
	 * 
	 */
	public void updateData_horario(String table_name, String textoParaActualizar , int id) {
		try {
			String Query = "UPDATE " + table_name + " SET nombre_horario = '" + textoParaActualizar + "' , fecha_de_modificacion = NOW()\r\n" + 
					"WHERE (id = " + id + ");";


			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Datos actualizados correctamente.");
			vista.label_loading_cambiosGuardados.setIcon(new ImageIcon("images/conectado_(32x32).png"));
		}
		catch (Exception ex) {
			vista.label_loading_cambiosGuardados.setIcon(new ImageIcon("images/no_conectado_(32x32).png"));
			System.out.println("Error guardando datos.");
			ex.printStackTrace();
		}
		
	}
	
	/*
	 * 
	 */
	public String obtener_fecha_de_modificacion_horario(int id) {
		String data = "";
		try {
			String Query = "SELECT \r\n" + 
					"    DATE_FORMAT(fecha_de_modificacion , '%d/%m/%Y , %H:%i') AS fecha_de_modificacion\r\n" + 
					"FROM horario\r\n" + 
					"WHERE id = " + id + ";";


			Statement st = conexion.createStatement();
			
			ResultSet resultSet = st.executeQuery(Query);

			int c = resultSet.getMetaData().getColumnCount();

			Vector<Object> fila = null;

			while (resultSet.next()) {
				fila = new Vector<Object>();

				for (int i = 1; i <= c; i++) {
					fila.add(resultSet.getString(i));
					// System.out.println(resultSet.getString(i));
				}
			}
			
			for (int i = 0; i < fila.size(); i++) {
				data = (String) fila.get(i);
			}
			
			System.out.println("Datos actualizados correctamente.");
		}
		catch (Exception ex) {
			System.out.println("Error guardando datos.");
			ex.printStackTrace();
		}
		
		return data;
		
	}
	
	/*
	 * 
	 */
	public void obtener_y_guardar_idsDeHorarios_enElOrdenCorrecto() {
		
		try {
			String Query = "SELECT id\r\n" + 
					"FROM horario"
					+ " WHERE horario.borrado = false;";


			Statement st = conexion.createStatement();
			
			ResultSet resultSet = st.executeQuery(Query);

			int c = resultSet.getMetaData().getColumnCount();

			Vector<Object> fila = null;

			while (resultSet.next()) {
				fila = new Vector<Object>();

				for (int i = 1; i <= c; i++) {
					fila.add(resultSet.getString(i));
					// System.out.println(resultSet.getString(i));
				}
				
				for (int i = 0; i < fila.size(); i++) {
					ids_horarios.add(Integer.valueOf((String) fila.get(i)));
				}
			}
			
			
			
			System.out.println("Datos actualizados correctamente.");
		}
		catch (Exception ex) {
			System.out.println("Error guardando datos.");
			ex.printStackTrace();
		}
		
		
		
	}
	
	/*
	 * 
	 */
	public void obtener_y_guardar_idsDeIntervaloHorario_enElOrdenCorrecto(int id_horario) {
		
		try {
			String Query = "SELECT id\r\n" + 
					"FROM intervalo_hora\r\n" + 
					"WHERE id_horario = " + id_horario + ";";


			Statement st = conexion.createStatement();
			
			ResultSet resultSet = st.executeQuery(Query);

			int c = resultSet.getMetaData().getColumnCount();

			Vector<Object> fila = null;

			while (resultSet.next()) {
				fila = new Vector<Object>();

				for (int i = 1; i <= c; i++) {
					fila.add(resultSet.getString(i));
					// System.out.println(resultSet.getString(i));
				}
				
				for (int i = 0; i < fila.size(); i++) {
					ids_intervalosHora.add(Integer.valueOf((String) fila.get(i)));
				}
			}
			
			
			
			System.out.println("Datos actualizados correctamente.");
		}
		catch (Exception ex) {
			System.out.println("Error guardando datos.");
			ex.printStackTrace();
		}
		
		
		
	}
	
	
	/*
	 * 
	 */
	private void obtener_e_insertar_nombresDeHorarios_enComboBoxDeBorrarFila() {
		
		try {
			String Query = "SELECT nombre_horario\r\n" + 
					"FROM horario"
					+ " WHERE horario.borrado = false;";

			Statement st = conexion.createStatement();
			
			ResultSet resultSet = st.executeQuery(Query);

			int c = resultSet.getMetaData().getColumnCount();

			Vector<Object> fila = null;

			while (resultSet.next()) {
				fila = new Vector<Object>();

				for (int i = 1; i <= c; i++) {
					fila.add(resultSet.getString(i));
					// System.out.println(resultSet.getString(i));
				}
				
				for (int i = 0; i < fila.size(); i++) {
					nombresDeHorarios.add((String) fila.get(i));
				}
			}
			
			
			
			String[] tmp = new String[nombresDeHorarios.size()];
			
			for (int i = 0; i < nombresDeHorarios.size(); i++) {
				tmp[i] = nombresDeHorarios.get(i);
			}
			
			borrarFila.comboBox.setModel(new DefaultComboBoxModel(tmp));
			
			nombresDeHorarios.clear();
			
			System.out.println("Datos actualizados correctamente.");
		}
		catch (Exception ex) {
			System.out.println("Error guardando datos.");
			ex.printStackTrace();
		}
		
		
		
	}
	
	
	/*
	 * 
	 */
	private void establecerZonaHorariaConcreta(String zonaHoraria) {
		try {
			String Query = "SET GLOBAL time_zone = '"+ zonaHoraria +"';";
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			String Query2 = "SET time_zone = '" + zonaHoraria + "';";
			st.executeUpdate(Query2);
			
			System.out.println("Zona horaria actualizada correctamente.");
		}
		catch (Exception ex) {
			System.out.println("Error al cambiar la zona horaria :(");
			ex.printStackTrace();
		}
		
	}
	
	/*
	 * 
	 */
	private void seleccionarLaBaseDeDatos() {
		try {
			String Query = "USE " + BD + ";";
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("Base de datos seleccionada correctamente.");
		}
		catch (Exception ex) {
			System.out.println("Error al seleccionar la base de datos :(");
			JOptionPane.showMessageDialog(null, "No se ha podido seleccionar la base de datos , prueba con la opción de borrar y reconstruir ( Opciones - Acción de alto impacto ❗ ) ", "Se ha producido un error :(", 0);
			ex.printStackTrace();
		}
		
	}
	
	
	/*
	 * 
	 */
	private void borrar_y_reconstruir_BD() {
		try {
			String Query = "DROP DATABASE IF EXISTS creadorDe_horarios_dataBase;";
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			String Query2 = "CREATE DATABASE creadorDe_horarios_dataBase CHARACTER SET utf8mb4;";
			st.executeUpdate(Query2);
			
			String Query3 = "USE " + BD + ";";
			st.executeUpdate(Query3);
			
			String Query4 = "CREATE TABLE horario (\r\n" + 
					"	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY ,\r\n" + 
					"    nombre_horario VARCHAR(100) NOT NULL ,\r\n" + 
					"    fecha_de_creacion DATETIME NOT NULL ,\r\n" + 
					"    fecha_de_modificacion DATETIME NOT NULL ,\r\n" + 
					"    borrado BOOLEAN DEFAULT FALSE\r\n" + 
					");";
			st.executeUpdate(Query4);
			
			String Query5 = "CREATE TABLE intervalo_hora (\r\n" + 
					"	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY ,\r\n" + 
					"    hora_inicio TIME NOT NULL ,\r\n" + 
					"    hora_fin TIME NOT NULL ,\r\n" + 
					"	id_horario INT UNSIGNED ,\r\n" + 
					"    FOREIGN KEY (id_horario) REFERENCES horario(id)  \r\n" + 
					");";
			st.executeUpdate(Query5);
			
			String Query6 = "CREATE TABLE asignatura (\r\n" + 
					"	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY ,\r\n" + 
					"    nombre_asignatura VARCHAR(50) ,\r\n" + 
					"    dia VARCHAR(25) NOT NULL ,\r\n" + 
					"    id_intervalo_hora INT UNSIGNED ,\r\n" + 
					"    FOREIGN KEY (id_intervalo_hora) REFERENCES intervalo_hora(id) ,\r\n" + 
					"    id_horario INT UNSIGNED ,\r\n" + 
					"    FOREIGN KEY (id_horario) REFERENCES horario(id) \r\n" + 
					");";
			st.executeUpdate(Query6);
			
			System.out.println("BD borrada correctamente.");
			
	        JOptionPane.showMessageDialog(null, "Hecho , se va a reiniciar la aplicación", "Información", 1);
	        
	        guardarLaProximaPantallaQueSeAbre("Todos los horarios");
	        
	        
	        restartApplication(null);
	        
		}
		catch (Exception ex) {
			System.out.println("Error reiniciar la BD :(");
			ex.printStackTrace();
		}
		
	}
	
	
	public void updateData_intervalo_hora(LocalTime hora_inicio , LocalTime hora_fin , int id , int id_horario) {
		try {
			String Query = "UPDATE intervalo_hora SET hora_inicio = '" + hora_inicio + "' , hora_fin = '" + hora_fin + "'\r\n" + 
					"WHERE (id = " + id + " AND id_horario = " + id_horario + ");";


			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Datos actualizados correctamente.");
			vista.label_loading_cambiosGuardados.setIcon(new ImageIcon("images/conectado_(32x32).png"));
		}
		catch (Exception ex) {
			vista.label_loading_cambiosGuardados.setIcon(new ImageIcon("images/no_conectado_(32x32).png"));
			System.out.println("Error guardando datos.");
			ex.printStackTrace();
		}
		
	}
	

	


	
	/*
	 * 
	 */
	public void consulta(String Query){

	}
	
	
	
	
	/*
	 * 
	 */
	private void hacerConsultas_pasandoDias(int horario_id) {
		
		for (int j = 0; j < diasDeLaSemana.size(); j++) {
			/**
			 * id_intervalo_hora , asignaturas del día
			 */
			generarAsignaturas("SELECT \r\n"
					+ "	-- CONCAT_WS(' - ' ,intervalo_hora.hora_inicio ,intervalo_hora.hora_fin ) AS hora_inicio_y_fin,\r\n"
					+ "    -- \"\" ,\r\n" + "    -- asignatura.nombre_asignatura ,\r\n"
					+ "    asignatura.id_intervalo_hora\r\n" + "    \r\n"
					+ "FROM intervalo_hora INNER JOIN asignatura\r\n"
					+ "ON intervalo_hora.id = asignatura.id_intervalo_hora\r\n" + "INNER JOIN horario \r\n"
					+ "ON intervalo_hora.id_horario = horario.id\r\n" + "WHERE asignatura.dia = '"
					+ diasDeLaSemana.get(j) + "' AND asignatura.id_horario = " + horario_id +";", false, diasDeLaSemana.get(j), true);

			/**
			 * Asignaturas del día
			 */
			generarAsignaturas("\r\n" + "\r\n" + "SELECT \r\n"
					+ "	-- CONCAT_WS(' - ' ,intervalo_hora.hora_inicio ,intervalo_hora.hora_fin ) AS hora_inicio_y_fin,\r\n"
					+ "    -- \"\" ,\r\n" + "    asignatura.nombre_asignatura \r\n" + "    \r\n"
					+ "FROM intervalo_hora INNER JOIN asignatura\r\n"
					+ "ON intervalo_hora.id = asignatura.id_intervalo_hora\r\n" + "INNER JOIN horario \r\n"
					+ "ON intervalo_hora.id_horario = horario.id\r\n" + "WHERE asignatura.dia = '"
					+ diasDeLaSemana.get(j) + "' AND asignatura.id_horario = " + horario_id + ";\r\n" + "\r\n" + "", false, diasDeLaSemana.get(j), false);

			System.out.println("=>>>>" + diasDeLaSemana.get(j));
			System.out.println("=>>>>" + diasDeLaSemana.size());
			
			asignaturas_enDia_Nombre_En_Texto.clear();
			id_intervalo_hora_asignatura.clear();
			id_intervalo_hora_asignatura_limpio.clear();
			id_intervalo_hora_asignatura_limpio_entero.clear();

		}
	
		
		
	}
	
	/*
	 * 
	 * 
	 */
	private void hacerConsultaDeIntervalosHorariosDeUnHorarioConcreto(int horario_id) {
		generarAsignaturas("SELECT \r\n" + 
				"	CONCAT_WS(' - ' , DATE_FORMAT(intervalo_hora.hora_inicio ,  '%H:%i') , DATE_FORMAT(intervalo_hora.hora_fin ,  '%H:%i'))  AS hora_inicio_y_fin\r\n" + 
				"FROM intervalo_hora INNER JOIN horario \r\n" + 
				"ON intervalo_hora.id_horario = horario.id\r\n" + 
				"WHERE horario.id = " + horario_id + ";", true , "" , false);
	}


	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.println("//////////////////////////////");
		
		System.out.println("Tipo " + e.getType());
		System.out.println("Primera Fila " + e.getFirstRow());
		System.out.println("Columna " + e.getColumn());
		System.out.println("Última Fila " + e.getLastRow());
		System.out.println(e.ALL_COLUMNS);
		System.out.println(e.DELETE);
		System.out.println(e.HEADER_ROW);
		System.out.println(e.INSERT);
		System.out.println(e.UPDATE);
		
		if(e.getSource().equals(vista.table.getModel())) {
//			JOptionPane.showMessageDialog(vista.ventana_principal, "La tabla ha cambiado");
			try {
				
				if(leerPantallaParaAbrir().equals("Un horario")) {
					if(e.getType() == e.UPDATE) {
	//					JOptionPane.showMessageDialog(vista.ventana_principal, "La tabla ha cambiado");
						
						filaDeLaTabla = e.getFirstRow();
						columnaDeLaTabla = e.getColumn();
						String textoDelCampo = vista.table.getModel().getValueAt(filaDeLaTabla, columnaDeLaTabla).toString();
						String diaDeLaSemana = "?";
						
						LocalTime hora_inicio;
						LocalTime hora_fin;
						
						System.out.println(textoDelCampo);
						
						switch (columnaDeLaTabla) {
						case 1:
							diaDeLaSemana = "Lunes";
							break;
						case 2:
							diaDeLaSemana = "Martes";
							break;
						case 3:
							diaDeLaSemana = "Miércoles";
							break;
						case 4:
							diaDeLaSemana = "Jueves";
							break;
						case 5:
							diaDeLaSemana = "Viernes";
							break;
	
						default:
							break;
						}
						
						if(columnaDeLaTabla == 0) {
	//						System.out.println("hora_inicio " + textoDelCampo.substring(0, textoDelCampo.indexOf(' ')));
	//						System.out.println("hora_fin " + textoDelCampo.substring(textoDelCampo.indexOf('-') + 2, textoDelCampo.length()));
							
							
							try {
								hora_inicio = LocalTime.parse(textoDelCampo.substring(0, textoDelCampo.indexOf(' ')));
							
								hora_fin = LocalTime.parse(textoDelCampo.substring(textoDelCampo.indexOf('-') + 2, textoDelCampo.length()));
								
								updateData_intervalo_hora(hora_inicio, hora_fin, ids_intervalosHora.get(filaDeLaTabla), Integer.valueOf(leerHorarioParaAbrir()));
								
								vista.table.getColumnModel().getColumn(columnaDeLaTabla).setCellRenderer(new GestionCeldas("Todo Bien" , filaParaSeleccionar));
								
								hayCampoNoValido = false;
								
							} catch (Exception e2) {
								vista.label_loading_reparacion.setVisible(true);
								hayCampoNoValido = true;
								
								JOptionPane.showMessageDialog(null, "Se ha producido un error :( " + "\r\n" + "\r\n" + "El intervalo de horas no cumple con el formato válido " + "\r\n" + "\r\n" + "Formato válido : XX:XX - XX:XX" + "\r\n" + "\r\n" + "Horas : 00 - 23" + "\r\n" + "Minutos : 00 - 59" , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
								e2.printStackTrace();
								
								errorEn = filaDeLaTabla;
								
	//							vista.table.getColumnModel().getColumn(columnaDeLaTabla).setCellRenderer(new RenderCelda());
								
							
	//							vista.table.getColumnModel().getColumn(columnaDeLaTabla).setCellRenderer(new GestionCeldas("QuitarEdicion"));
									
								if(errorEn == filaDeLaTabla) {
									vista.table.getColumnModel().getColumn(columnaDeLaTabla).setCellRenderer(new GestionCeldas("Error de formato" , filaParaSeleccionar));
								}
								
								
								
								
								
								
	//							vista.table.setBackground(Color.RED);
								
							}
							
							
							
						}else {
							System.out.println(diaDeLaSemana);
							updateData_asignatura("asignatura", textoDelCampo, diaDeLaSemana, (filaDeLaTabla + 1) , Integer.valueOf(leerHorarioParaAbrir()));
						}
						
					}
				}else if(leerPantallaParaAbrir().equals("Todos los horarios")) {
					if(e.getType() == e.UPDATE) {
//						JOptionPane.showMessageDialog(vista.ventana_principal, "La tabla ha cambiado");
						
						filaDeLaTabla = e.getFirstRow();
						columnaDeLaTabla = e.getColumn();
						String textoDelCampo = vista.table.getModel().getValueAt(filaDeLaTabla, columnaDeLaTabla).toString();
						
						System.out.println(textoDelCampo);
						
						if(columnaDeLaTabla == 0) {
							updateData_horario("horario", textoDelCampo, ids_horarios.get((filaDeLaTabla)));	
							vista.table.setValueAt(obtener_fecha_de_modificacion_horario(ids_horarios.get((filaDeLaTabla))), filaDeLaTabla, 2);
						}else if(columnaDeLaTabla == 3) {
//							JOptionPane.showMessageDialog(vista.ventana_principal, filaDeLaTabla + " , " + columnaDeLaTabla);
							guardarElProximoHorarioQueSeAbre(String.valueOf(ids_horarios.get(filaDeLaTabla)));
							guardarLaProximaPantallaQueSeAbre("Un horario");
							restartApplication(null);
						}
						
						
						
					}
				}

			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("error ignorado");
				e2.printStackTrace();
			}

			
		}
		
		
	}
	


	/*
	 * Reinicia la Aplicación
	 * 
	 * Método por : https://dzone.com/articles/programmatically-restart-java
	 * 
	 */
	
	/**
	 * Sun property pointing the main class and its arguments. Might not be defined
	 * on non Hotspot VM implementations.
	 */
	public static final String SUN_JAVA_COMMAND = "sun.java.command";

	/**
	 * Restart the current Java application
	 * 
	 * @param runBeforeRestart
	 *            some custom code to be run before restarting
	 * @throws IOException
	 */
	public static void restartApplication(Runnable runBeforeRestart) throws IOException {
		try {
			// java binary
			String java = System.getProperty("java.home") + "/bin/java";
			// vm arguments
			List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
			StringBuffer vmArgsOneLine = new StringBuffer();
			for (String arg : vmArguments) {
				// if it's the agent argument : we ignore it otherwise the
				// address of the old application and the new one will be in conflict
				if (!arg.contains("-agentlib")) {
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(" ");
				}
			}
			// init the command to execute, add the vm args
			final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);

			// program main and program arguments
			String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
			// program main is a jar
			if (mainCommand[0].endsWith(".jar")) {
				// if it's a jar, add -jar mainJar
				cmd.append("-jar " + new File(mainCommand[0]).getPath());
				System.out.println("=>>>"  + cmd);
			} else {
				// else it's a .class, add the classpath and mainClass
				cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
				System.out.println("=>>>"  + cmd);
			}
			
			
			// finally add program arguments
			for (int i = 1; i < mainCommand.length; i++) {
				cmd.append(" ");
				cmd.append(mainCommand[i]);
			}
			// execute the command in a shutdown hook, to be sure that all the
			// resources have been disposed before restarting the application
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec(cmd.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			// execute some custom code before restarting
			if (runBeforeRestart != null) {
				runBeforeRestart.run();
			}
			// exit
			System.exit(0);
		} catch (Exception e) {
			// something went wrong
			throw new IOException("Error while trying to restart the application", e);
		}
	}



	/*
	 * 
	 */
	public void seEstaEditandoAlgunaCelda() {
		if(vista.table.isEditing()) {
			vista.label_loading_cambiosGuardados.setIcon(new ImageIcon("images/Animated_loading_half-circle_(16x16).gif"));
		}
		System.out.println("comprobando ...");
	}

	
	/*
	 * 
	 */
	public void actualizarHora() {
		/*
		 * Reloj
		 */
		vista.lbl_hora.setIcon(null);
		
		if(configuracion.chckbxMostrarSegundosReloj.isSelected()) {
			vista.lbl_hora.setText(formateadorReloj.format(LocalTime.now()));
		}else {
			vista.lbl_hora.setText(formateadorRelojCompacto.format(LocalTime.now()));
		}
		
		
		if(leerPantallaParaAbrir().equals("Un horario")) {
				
			/*
			 * Barra de horas
			 */
			if(Integer.parseInt(formateadorHoras.format(LocalDateTime.now())) < primeraHoraDelHorario) {
				vista.slider_horas.setValue(0);
			}else if(Integer.parseInt(formateadorHoras.format(LocalDateTime.now())) > ultimaHoraDelHorario) {
				vista.slider_horas.setValue(vista.slider_horas.getMaximum());
			}else {
				calcularYEstablecerPorcentajeDeTiempoRestanteParaFinalizarLaJornadaCompletaDelHorario();
			}
	
	//		calcularYEstablecerPorcentajeDeTiempoRestanteParaFinalizarLaJornadaCompletaDelHorario();
			
	//		vista.slider_horas.setValue(291 - 12);
			
	//		System.out.println(formateador.format(LocalTime.now()));
	//		System.out.println("hora");
			
	//		System.out.println(vista.slider_dias.getValue());
	//		System.out.println(vista.slider_horas.getValue());
	//		System.out.println(vista.slider_horas.getMaximum());
			
			/*
			 * Tiempo restante
			 */
			vista.lbl_tiempo_restante.setIcon(null);
			vista.lbl_tiempo_restante.setText(formatearMinutosEnHorasYMinutos(vista.slider_horas.getMaximum() - vista.slider_horas.getValue()) + " restantes");
			tiempoTotalRestanteDeJornada = vista.slider_horas.getMaximum() - vista.slider_horas.getValue();
			
			/*
			 * Cambiar día en tiempo real y la barra del día
			 */
			ponerNombreDelMes_y_AnioActual();
			marcarDiaActual();
			
			
			/*
			 * Pintar la asignatura actual
			 */
			try {
				comprobarYPintarLaAsignaturaActual();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
		}
		
	}
	
	
	/*
	 * 
	 */
	public void marcarDiaActual() {
		Calendario_Horiario calendario = new Calendario_Horiario();
		diaActualEnTexto = calendario.diasSemana[calendario.getDiaSemana(calendario.getDia())];
		
		int valor = 0;
		
		switch (diaActualEnTexto) {
		case "lunes":
			valor = 26;
			break;
		case "martes":
			valor = 43;
			break;
		case "miércoles":
			valor = 60;
			break;
		case "jueves":
			valor = 77;
			break;
		case "viernes":
			valor = 94;
			break;

		default:
			break;
		}
		
		vista.slider_dias.setValue(valor);
	}
	
	
	/*
	 * 
	 */
	public void obtener_yEstablecerMaximoDeLaBarra() {
//		double tmp = calcularDiferenciaHoraria_entre_primeraHoraDelHorario_Y_ultimaHoraDelHorario();
//		double porcentajeDeHoras = (tmp / 60) * 100;
//		System.out.println(porcentajeDeHoras);
//		vista.slider_horas.setEnabled(true);
		vista.slider_horas.setMaximum(calcularDiferenciaHoraria_entre_primeraHoraDelHorario_Y_ultimaHoraDelHorario());
	}
	
	/*
	 * 
	 */
	public int calcularDiferenciaHoraria_entre_primeraHoraDelHorario_Y_ultimaHoraDelHorario() {
		int diferenciaHoraria = 0;
		
		int primeraHoraDelHorario_enMinutos = primeraHoraDelHorario * 60;
		int primeraHoraDelHorario_masMinutos = primeraHoraDelHorario_enMinutos + primerMinutoDelHorario;
		
		int ultimaHoraDelHorario_enMinutos = ultimaHoraDelHorario * 60;
		int ultimaHoraDelHorario_masMinutos = ultimaHoraDelHorario_enMinutos + ultimoMinutoDelHorario;
		
		System.out.println(primeraHoraDelHorario_masMinutos + " -- " + ultimaHoraDelHorario_masMinutos);
		
		diferenciaHoraria = ultimaHoraDelHorario_masMinutos - primeraHoraDelHorario_masMinutos;
		
		return diferenciaHoraria;
	}

	/*
	 * 
	 */
	public void calcularYEstablecerPorcentajeDeTiempoRestanteParaFinalizarLaJornadaCompletaDelHorario() {
		int diferenciaHoraria = 0;
		
		int primeraHoraDelHorario_enMinutos = primeraHoraDelHorario * 60;
		int primeraHoraDelHorario_masMinutos = primeraHoraDelHorario_enMinutos + primerMinutoDelHorario;
		
		int HoraActual_enMinutos = (Integer.parseInt(formateadorHoras.format(LocalDateTime.now()))) * 60;
		int MinutoActual = Integer.parseInt(formateadorMinutos.format(LocalDateTime.now()));
		int SegundoActual_enMinutos = (Integer.parseInt(formateadorSegundos.format(LocalDateTime.now()))) / 60;
		
		int HoraActualDelHorario_masMinutos = HoraActual_enMinutos + MinutoActual + SegundoActual_enMinutos;
		
		System.out.println(primeraHoraDelHorario_masMinutos + " -- " + HoraActualDelHorario_masMinutos);
		
		diferenciaHoraria = HoraActualDelHorario_masMinutos - primeraHoraDelHorario_masMinutos;
		
		double porcentajeDeHorasRestante = ((diferenciaHoraria / 60) * 100);
		
		System.out.println(diferenciaHoraria);
		
		System.out.println(porcentajeDeHorasRestante);
		
//		vista.slider_horas.setValue((int) porcentajeDeHorasRestante);
		vista.slider_horas.setValue(diferenciaHoraria);
		
	}
	
	
	/*
	 * 
	 */
	private void mapearPrimerasHorasYPrimerosMinutos() {
		for (int i = 0; i <= vista.table.getRowCount() - 1; i++) {
			primarasHorasDeCadaIntervaloHorario_enTexto.add(((String) vista.table.getValueAt(i, 0)).substring(0, 2));
		}
		
		for (int i = 0; i <= vista.table.getRowCount() - 1; i++) {
			primerosMinutosDeCadaIntervaloHorario_enTexto.add(((String) vista.table.getValueAt(i, 0)).substring(3, 5));
		}
		
		System.out.println(primarasHorasDeCadaIntervaloHorario_enTexto);
		System.out.println(primerosMinutosDeCadaIntervaloHorario_enTexto);
		
		
	}
	
	/*
	 * 
	 */
	private void mapearUltimasHorasYUltimosMinutos() {
		for (int i = 0; i <= vista.table.getRowCount() - 1; i++) {
			ultimasHorasDeCadaIntervaloHorario_enTexto.add(((String) vista.table.getValueAt(i, 0)).substring(8, 10));
		}
		
		for (int i = 0; i <= vista.table.getRowCount() - 1; i++) {
			ultimosMinutosDeCadaIntervaloHorario_enTexto.add(((String) vista.table.getValueAt(i, 0)).substring(11, 13));
		}
		
		System.out.println(ultimasHorasDeCadaIntervaloHorario_enTexto);
		System.out.println(ultimosMinutosDeCadaIntervaloHorario_enTexto);
		
		
	}
	
	/*
	 * 
	 */
	private void mapearPrimerasY_UltimasHorasY_Minutos() {
		for (int i = 0; i <= vista.table.getRowCount() - 1; i++) {
			primarasHorasYMinutosDeCadaIntervaloHorario_enTexto.add(((String) vista.table.getValueAt(i, 0)).substring(0, 5));
		}
		
		for (int i = 0; i <= vista.table.getRowCount() - 1; i++) {
			ultimasHorasYMinutosDeCadaIntervaloHorario_enTexto.add(((String) vista.table.getValueAt(i, 0)).substring(8, 13));
		}
		System.out.println("Mapeo total : ");
		System.out.println(primarasHorasYMinutosDeCadaIntervaloHorario_enTexto);
		System.out.println(ultimasHorasYMinutosDeCadaIntervaloHorario_enTexto);
		
		
	}
	
	/*
	 * 
	 */
	public void comprobarYPintarLaAsignaturaActual() throws ParseException {
		boolean horaCoincide = false;
		boolean minutoCoincide = false;
		int posicionDelArrayListDondeEstaLaAsignaturaMarcada;
		
//		for (int i = 0; i < primarasHorasDeCadaIntervaloHorario_enTexto.size(); i++) {
////			if(primarasHorasDeCadaIntervaloHorario_enTexto.get(i).equals(formateadorHoras.format(LocalDateTime.now()))) { // Si el programa estaba abierto
////				horaCoincide = true;
////				filaParaSeleccionar = primarasHorasDeCadaIntervaloHorario_enTexto.indexOf(primarasHorasDeCadaIntervaloHorario_enTexto.get(i));
////			}
//				int primarasHoraDeIntervaloHorario_enNumero = Integer.parseInt(primarasHorasDeCadaIntervaloHorario_enTexto.get(i));
//				int ultimaHoraDeIntervaloHorario_enNumero = Integer.parseInt(ultimasHorasDeCadaIntervaloHorario_enTexto.get(i)); 
//				int horaActual = Integer.parseInt(formateadorHoras.format(LocalDateTime.now()));
//				
//				if(horaActual >= primarasHoraDeIntervaloHorario_enNumero && horaActual <= ultimaHoraDeIntervaloHorario_enNumero) {
//					horaCoincide = true;
//					filaParaSeleccionar = primarasHorasDeCadaIntervaloHorario_enTexto.indexOf(primarasHorasDeCadaIntervaloHorario_enTexto.get(i));
//				}
//			
////			System.out.println(primarasHorasDeCadaIntervaloHorario_enTexto.get(i) + " - " + formateadorHoras.format(LocalDateTime.now()));
//		}
//		
//		for (int j = 0; j < primerosMinutosDeCadaIntervaloHorario_enTexto.size(); j++) {
////			if(primerosMinutosDeCadaIntervaloHorario_enTexto.get(j).equals(formateadorMinutos.format(LocalDateTime.now()))) { // Si el programa estaba abierto
////				minutoCoincide = true;
////			} // Si se abrió después de la hora exacta
//				int primerosMinutosDeIntervaloHorario_enNumero = Integer.parseInt(primerosMinutosDeCadaIntervaloHorario_enTexto.get(j));
//				int ultimosMinutosDeIntervaloHorario_enNumero = Integer.parseInt(ultimosMinutosDeCadaIntervaloHorario_enTexto.get(j)); 
//				int minutoActual = Integer.parseInt(formateadorMinutos.format(LocalDateTime.now()));
//				
////				if(minutoActual > ultimosMinutosDeIntervaloHorario_enNumero) {
////					minutoCoincide = false;
////				}else {
////					minutoCoincide = true;
////				}
//				
//				minutoCoincide = true;
////				if(minutoActual >= primerosMinutosDeIntervaloHorario_enNumero && minutoActual <= ultimosMinutosDeIntervaloHorario_enNumero) {
////					minutoCoincide = true;
////				}
//				
////			System.out.println(primerosMinutosDeCadaIntervaloHorario_enTexto.get(j) + " - " + formateadorMinutos.format(LocalDateTime.now()));
//		}

		if(tiempoTotalRestanteDeJornada != 0) {
			for (int i = 0; i < primarasHorasYMinutosDeCadaIntervaloHorario_enTexto.size(); i++) {
				if (laHoraActualEstaEnElIntervaloSeleccionado(formateadorReloj.format(LocalDateTime.now()),
					primarasHorasYMinutosDeCadaIntervaloHorario_enTexto.get(i),
					ultimasHorasYMinutosDeCadaIntervaloHorario_enTexto.get(i))) {
					
					posicionDelArrayListDondeEstaLaAsignaturaMarcada = primarasHorasYMinutosDeCadaIntervaloHorario_enTexto.indexOf(primarasHorasYMinutosDeCadaIntervaloHorario_enTexto.get(i));
					filaParaSeleccionar = primarasHorasYMinutosDeCadaIntervaloHorario_enTexto.indexOf(primarasHorasYMinutosDeCadaIntervaloHorario_enTexto.get(i));
					
					System.out.println("Todo coincide");
					vista.table.getColumnModel().getColumn(diaDeLaSemana()).setCellRenderer(new GestionCeldas("Marcar Asignatura", filaParaSeleccionar));
					
					if((mostrarNotificacion == false) && (asignaturaAnteriorMarcada != posicionDelArrayListDondeEstaLaAsignaturaMarcada)) {
						mostrarNotificacion = true;
					}
					
					if (mostrarNotificacion) {
						if(configuracion.chckbxMostrarNotificaciones.isSelected()) {
							mostrarNotificacion("Ha comenzado", (String) vista.table.getValueAt(filaParaSeleccionar, diaDeLaSemana()) + "\r\r"
									+ "[" + vista.lbl_tiempo_restante.getText() + "]");
						}
						
						mostrarNotificacion = false;
						asignaturaAnteriorMarcada = posicionDelArrayListDondeEstaLaAsignaturaMarcada;
					}
	
					vista.table.validate();
					vista.table.repaint();
				}
			}
		}
		
		if(tiempoTotalRestanteDeJornada == 0) {
//			System.out.println("=>>>>..> " + vista.table.getColumnCount());
			
			filaParaSeleccionar = vista.table.getRowCount() + 1;
			vista.table.getColumnModel().getColumn(diaDeLaSemana()).setCellRenderer(new GestionCeldas("Marcar Asignatura", filaParaSeleccionar));
			
			vista.table.validate();
			vista.table.repaint();
			
			if(vista.lbl_tiempo_restante.getText().equals("0 minutos restantes") && mostrarNotificacionSoloUnaVez) {
				mostrarNotificacion("Enhorabuena", "Has terminado , por hoy 👍");
				mostrarNotificacionSoloUnaVez = false;
			}
			
		}
	
		
		
	}
	
	private int diaDeLaSemana() {
		int diaDeLaSemanaEnNumero = 0;

		switch (calendario.diasSemana[calendario.getDiaSemana(calendario.getDia())]) {
		case "lunes":
			diaDeLaSemanaEnNumero = 1;
			break;
		case "martes":
			diaDeLaSemanaEnNumero = 2;
			break;
		case "miércoles":
			diaDeLaSemanaEnNumero = 3;
			break;
		case "jueves":
			diaDeLaSemanaEnNumero = 4;
			break;
		case "viernes":
			diaDeLaSemanaEnNumero = 5;
			break;
		}

		return diaDeLaSemanaEnNumero;
	}
	
	
	/*
	 * 
	 */
	public void mostrarNotificacion(String tituloDeNotificacion , String textoDeLaNotificacion) {

		try{
		    // Obtener solamente una instancia del objeto SystemTray
		    SystemTray tray = SystemTray.getSystemTray();

		    // Si quieres crear un icono en la bandeja del sistemas como vista previa
		    Image image = Toolkit.getDefaultToolkit().createImage("images/calendario (1).png");
		    // Alternativamente (si el icono está en el directorio de la clase):
//		    Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("images/calendario (1).png"));

		    TrayIcon trayIcon = new TrayIcon(image, "Creador de horarios");
		    // Deja que el sistema auto escale si es necesario
		    trayIcon.setImageAutoSize(true);
		    // Definir texto de tooltip (descripción emergente)
		    trayIcon.setToolTip("Creador de horarios");
		    tray.add(trayIcon);

		    // Mostrar notificación de información:
		    trayIcon.displayMessage(tituloDeNotificacion, textoDeLaNotificacion, MessageType.INFO);
		    // Error:
		    // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.ERROR);
		    // Warning:
		    // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.WARNING);
		}catch(Exception ex){
		    System.err.print(ex);
		}
	}
	
	/*
	 * 	
	 */
	public boolean laHoraActualEstaEnElIntervaloSeleccionado(String horaActual, String horaInicial, String horaFinal) throws ParseException {
		boolean laHoraActualEnElIntervaloSeleccionado;
		Date actual = new SimpleDateFormat("HH:mm").parse(horaActual.trim());
		Date inicial = new SimpleDateFormat("HH:mm").parse(horaInicial.trim());
		Date hora_final = new SimpleDateFormat("HH:mm").parse(horaFinal.trim());
		
		System.out.println(actual);
		System.out.println(inicial);
		System.out.println(horaFinal);
		
		if (actual.after(inicial) && actual.before(hora_final)) {
			laHoraActualEnElIntervaloSeleccionado = true;
		} else {
			laHoraActualEnElIntervaloSeleccionado = false;
		}
		
		return laHoraActualEnElIntervaloSeleccionado;
	}

	/*
	 * 
	 */
	public String formatearMinutosEnHorasYMinutos(int minutos) {
		String resultado = "?";
		int horas = 0;
		int resta_tmp = 0;
		boolean continuar = true;
		
		while(continuar) {
			resta_tmp = minutos - 60;
			if(resta_tmp >= 0) {
				horas++;
				minutos = minutos - 60;
			}else {
				if(horas == 0) {
					resultado = minutos + " minutos";
				}else {
					if(minutos == 0) {
						resultado = horas + " hora";
					}else {
						resultado = horas + " horas y " + minutos + " minutos";
					}
					
				}
				continuar = false;
			}
		}
		
		return resultado;
	}
	
	/*
	 * 
	 */
	public void limpiarTabla(JTable tabla , boolean limpiarFilas){
        try {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            TableColumnModel tcm = tabla.getColumnModel();
            TableColumn comlumnaParaBorrar = tcm.getColumn(0);
            
            int filas = tabla.getRowCount();
            if(limpiarFilas) {
            	for (int i = 0; filas > i; i++) { // Limpia las filas 
            		modelo.removeRow(0);
            	}
            }
            
            
            
			for (int i = 0; i < tabla.getColumnCount(); i++) { // Limpia la cabecera ( las columnas )
				tabla.removeColumn(comlumnaParaBorrar);
			}

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
            e.printStackTrace();
        }
    }
	
	
	/*
	 * 
	 */
	public void hacerConsultaDeHorarios() {
		consultarHorarios("\r\n" + 
				"SELECT \r\n" + 
				"	nombre_horario , \r\n" + 
				"	DATE_FORMAT(fecha_de_creacion , '%d/%m/%Y , %H:%i') AS fecha_de_creacion, \r\n" + 
				"    DATE_FORMAT(fecha_de_modificacion , '%d/%m/%Y , %H:%i') AS fecha_de_modificacion\r\n" + 
				"FROM horario "
				+ " WHERE horario.borrado = false;\r\n" + 
				"", true);
	}
	
	/*
	 * 
	 */
	public void limpiarDelTodoLaTabla() {
		while(vista.table.getColumnCount() != 3) {
			limpiarTabla(vista.table , false);
		}
	}
	
	/*
	 * 
	 */
	public void moverDatos() {
		System.out.println("este " + vista.table.getColumnCount());
		vista.table.setValueAt(vista.table.getValueAt(0, 0), 0, vista.table.getColumnCount() - 3);
		vista.table.setValueAt(vista.table.getValueAt(0, 1), 0, vista.table.getColumnCount() - 2);
		vista.table.setValueAt(vista.table.getValueAt(0, 2), 0, vista.table.getColumnCount() - 1);
		
	
	}
	
	/*
	 * 
	 */
	public void guardarLaProximaPantallaQueSeAbre(String panatallaParaAbrir) {
		/*
		 * Ecribe el fichero con la configuración actual
		 */
		
		File ficheroSalida = new File("[Creador de horarios - Mini Datos].txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print(panatallaParaAbrir);
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "Configuración por defecto cargada ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(vista.ventana_principal, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*
	 * 
	 */
	public void guardarEstadoBotonBloquear(String bloqueado) {
		/*
		 * Ecribe el fichero con la configuración actual
		 */
		
		File ficheroSalida = new File("[Creador de horarios - Estado de bloqueo].txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print(bloqueado);
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "Configuración por defecto cargada ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(vista.ventana_principal, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*
	 * 
	 */
	public void guardarElProximoHorarioQueSeAbre(String id_horarioParaAbrir) {
		/*
		 * Ecribe el fichero con la configuración actual
		 */
		
		File ficheroSalida = new File("[Creador de horarios - Micro Dato].txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print(id_horarioParaAbrir);
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "Configuración por defecto cargada ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(vista.ventana_principal, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/*
	 * 
	 */
	public void guardarElTipoDeBarra(String tipoDeBarra) {
		/*
		 * Ecribe el fichero con la configuración actual
		 */
		
		File ficheroSalida = new File("[Creador de horarios - Datos Barra].txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print(tipoDeBarra);
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "Configuración por defecto cargada ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(vista.ventana_principal, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	
	/*
	 * 
	 */
	public String leerPantallaParaAbrir() {
		String pantalla = "?";
		
		/*
		 * Lee el fichero con la configuración actual 
		 */
		
		Scanner lectorFichero;
		
		File ficheroSalida = new File("[Creador de horarios - Mini Datos].txt");
		
		
		try {
			lectorFichero = new Scanner(ficheroSalida);
			
			while (lectorFichero.hasNext()) {
//				System.out.println(lectorFichero.nextLine());
				pantalla = lectorFichero.nextLine();
			}
			
			lectorFichero.close();
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo de configuración , se creará automáticamente con la configuración por defecto" + "\r\n" + "\r\n" + "\r\n" + error.toString()  , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
			
		}
		
		return pantalla;
		
	}
	
	/*
	 * 
	 */
	public String leerEstadoDeBloqueo() {
		String bloqueado = "?";
		
		/*
		 * Lee el fichero con la configuración actual 
		 */
		
		Scanner lectorFichero;
		
		File ficheroSalida = new File("[Creador de horarios - Estado de bloqueo].txt");
		
		
		try {
			lectorFichero = new Scanner(ficheroSalida);
			
			while (lectorFichero.hasNext()) {
//				System.out.println(lectorFichero.nextLine());
				bloqueado = lectorFichero.nextLine();
			}
			
			lectorFichero.close();
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo de configuración , se creará automáticamente con la configuración por defecto" + "\r\n" + "\r\n" + "\r\n" + error.toString()  , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
			
		}
		
		return bloqueado;
		
	}
	
	/*
	 * 
	 */
	public String leerHorarioParaAbrir() {
		String horario = "?";
		
		/*
		 * Lee el fichero con la configuración actual 
		 */
		
		Scanner lectorFichero;
		
		File ficheroSalida = new File("[Creador de horarios - Micro Dato].txt");
		
		
		try {
			lectorFichero = new Scanner(ficheroSalida);
			
			while (lectorFichero.hasNext()) {
//				System.out.println(lectorFichero.nextLine());
				horario = lectorFichero.nextLine();
			}
			
			lectorFichero.close();
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo de configuración , se creará automáticamente con la configuración por defecto" + "\r\n" + "\r\n" + "\r\n" + error.toString()  , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
			
		}
		
		return horario;
		
	}
	
	/*
	 * 
	 */
	public String obtenerNombreDeHorarioActualmenteSeleccionado(int id) {
		String nombreHorario = "?";
		
		try {
			String Query = "SELECT nombre_horario\r\n" + 
					"FROM horario\r\n" + 
					"WHERE id = " + id + ";";


			Statement st = conexion.createStatement();
			
			ResultSet resultSet = st.executeQuery(Query);

			int c = resultSet.getMetaData().getColumnCount();

			Vector<Object> fila = null;

			while (resultSet.next()) {
				fila = new Vector<Object>();

				for (int i = 1; i <= c; i++) {
					fila.add(resultSet.getString(i));
					// System.out.println(resultSet.getString(i));
				}
				
				for (int i = 0; i < fila.size(); i++) {
					nombreHorario = (String) fila.get(i);
				}
			}
			
			
			
			System.out.println("Datos actualizados correctamente.");
		}
		catch (Exception ex) {
			System.out.println("Error guardando datos.");
			ex.printStackTrace();
		}
		
		return nombreHorario;
		
	}


	/*
	 * 
	 */
	private void configurarTipoDeBarra() {
		if(configuracion.comboBox_tipoDeBarra.getSelectedItem().equals("Barra personalizada circular naranja")) {
			guardarElTipoDeBarra("Barra personalizada circular naranja");
		}else if(configuracion.comboBox_tipoDeBarra.getSelectedItem().equals("Barra básica ( nativa de Java )")) {
			guardarElTipoDeBarra("Barra básica ( nativa de Java )");
		}else if(configuracion.comboBox_tipoDeBarra.getSelectedItem().equals("Barra personalizada triangular morada")) {
			guardarElTipoDeBarra("Barra personalizada triangular morada");
		}
		
		if(!(tipoDeBarraAlIniciar.equals(vista.leerElTipoDeBarra()))) {
			String[] botones = {"Por supuesto", "Mejor no"};
			int ventana = JOptionPane.showOptionDialog(null, 
							"Esta acción requiere reiniciar la aplicación , ¿ quieres continuar ?", 
							"Elige una opción : ", 
							JOptionPane.DEFAULT_OPTION, 
							JOptionPane.QUESTION_MESSAGE, null, 
							botones, botones[0]);
			if(ventana == 0) {
				try {
					restartApplication(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(ventana == 1) {
				configuracion.comboBox_tipoDeBarra.setSelectedItem(tipoDeBarraAlIniciar);
			}
		}
		
//		JOptionPane.showMessageDialog(null, "Tipo de barra");
	}
	
	
	/*
	 * Impresión por la impresora
	 */
	
    /**
     * Standard method to print a JTable to the printer directly..
     * Método estándar para imprimir un JTable por la impresora directamente.
     * <h3>Example (Ejemplo)</h3>
     * <pre>
     *      utilJTablePrint(jTable2, getTitle(), "Código Xules", true);
     * </pre>
     *
     * @param jTable <code>JTable</code> 
     *      the JTable we are going to extract to excel 
     *      El Jtable que vamos a extraer a excel.
     * @param header <code>String</code>
     *      Header to print in the document.
     *      Cabecera que imprimiremos en el documento.
     * @param footer <code>String</code>
     *      Footer to print in the document.
     *      Pie de página que imprimiremos en el documento.
     * @param showPrintDialog  <code>boolean</code>
     *      To show or not the print dialog.
     *      Mostramos o no el diálogo de impresión.
     *      
     * @web https://codigoxules.org/imprimir-jtable-directamente-java-java-swing/#:~:text=Utilizando%20el%20editor%20gr%C3%A1fico%20a%C3%B1adimos,e%20incorporamos%20a%20la%20clase
     */
	public void utilJTablePrint(JTable jTable, String header, String footer, boolean showPrintDialog) {
		boolean fitWidth = true;
		boolean interactive = true;
		// We define the print mode (Definimos el modo de impresión)
		JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
		try {
			// Print the table (Imprimo la tabla)
			boolean complete = jTable.print(mode, new MessageFormat(header), new MessageFormat(footer), showPrintDialog,
					null, interactive);
			if (complete) {
				// Mostramos el mensaje de impresión existosa
				JOptionPane.showMessageDialog(jTable, "Print complete (Impresión completa)",
						"Print result (Resultado de la impresión)", JOptionPane.INFORMATION_MESSAGE);
			} else {
				// Mostramos un mensaje indicando que la impresión fue cancelada
				JOptionPane.showMessageDialog(jTable, "Print canceled (Impresión cancelada)",
						"Print result (Resultado de la impresión)", JOptionPane.WARNING_MESSAGE);
			}
		} catch (PrinterException pe) {
			JOptionPane.showMessageDialog(jTable, "Print fail (Fallo de impresión): " + pe.getMessage(),
					"Print result (Resultado de la impresión)", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	/*
	 * 
	 * 
	 */
	private void accionBotonBloquear() {
		/*
		 * Comprobar si bloquear tabla está activado
		 */
		
		
		
		if(alternar_estado_bloqueado) {
			vista.table.setEnabled(false);
			vista.lbl_bloquear.setIcon(new ImageIcon("images/bloqueado_(24x24).png"));
			alternar_estado_bloqueado = false;
			vista.lbl_bloquear.setBorder(BorderFactory.createLoweredBevelBorder());
			vista.lbl_insertarFila.setVisible(false);
			guardarEstadoBotonBloquear("true");
		}else {
			vista.table.setEnabled(true);
			vista.lbl_bloquear.setIcon(new ImageIcon("images/desbloqueado_(24x24).png"));
			alternar_estado_bloqueado = true;
			vista.lbl_bloquear.setBorder(BorderFactory.createRaisedBevelBorder());
			vista.lbl_insertarFila.setVisible(true);
			guardarEstadoBotonBloquear("false");
		}
	}
	
	
	
	
	
	
	/**
	 * Actualizador automático
	 */
	
	/**
	 * Leer un fichero de texto desde una url
	 */
	public void readFileFromUrlAndCheckUpdate() {
		String link = "https://juanmatorres-dev.me/App_CreadorDeHorarios/last_version_beta.txt";
		String tmp;
		
		try {
			URL url = new URL(link);
			
			System.out.println("-----");
			
			BufferedReader bfr = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while ((inputLine = bfr.readLine()) != null) {
				/*
				tmp = vista.textPane.getText() + inputLine + "\n";
				vista.textPane.setText(tmp);
				System.out.println(inputLine);
				*/
				//JOptionPane.showMessageDialog(null, inputLine);
				Version_App version = new Version_App();
				//JOptionPane.showMessageDialog(null, version.version);
				if(!version.version.equals(inputLine)) {
					update.actual_version.setText(update.actual_version.getText() + version.version);
					update.last_version.setText(update.last_version.getText() + inputLine);
					update.setLocationRelativeTo(vista.ventana_principal);
					update.setVisible(true);
					actualizacion_necesaria = true;
					//JOptionPane.showMessageDialog(null, "Hay una actulización disponible\nTu versión actual: " + version.version + "\nÚltima versión: " + inputLine);
				}else {
					actualizacion_necesaria = false;
				}
			}
			
			bfr.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(link);
		//end_loading();
	}
	
	
	/**
	 * Obtiene el enlace de descarga actualizado
	 */
	public String readFileFromUrlAndOpenUpdateDownloadLink() {
		String linkToUpdate = "?";
		String link = "https://juanmatorres-dev.me/App_CreadorDeHorarios/last_version_download_link_beta.txt";
		String tmp;
		
		try {
			URL url = new URL(link);
			
			System.out.println("-----");
			
			BufferedReader bfr = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
			while ((inputLine = bfr.readLine()) != null) {
				/*
				tmp = vista.textPane.getText() + inputLine + "\n";
				vista.textPane.setText(tmp);
				System.out.println(inputLine);
				*/
				//JOptionPane.showMessageDialog(null, inputLine);
				linkToUpdate = inputLine;
				
				
			}
			
			bfr.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(link);
		//end_loading();
		
		return linkToUpdate;
	}
	
	/**
	 * Descarga la nueva versión del programa
	 */
	public void downloadLatestVersion(String link) {


        URL fetchWebsite = null; 
		try {
			fetchWebsite = new URL(link);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		
		/*
		 * Crea el directorio para descargar el arhivo sino existe
		 */
		File directory = new File("download");
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		
		/*
		 * Obtiene el nombre del archivo de descarga usando el link de descarga
		 */
		int position_of_the_last_bar = link.lastIndexOf("/");
		
		//System.out.println("Posición de la última barra: " + position_of_the_last_bar);
		//System.out.println(link.length());
		
		downloadedFileName = link.substring((position_of_the_last_bar + 1), link.length());
		
		System.out.println("Nombre del archivo de descarga : " + downloadedFileName);
		
		
        Path path = Paths.get("download/" + downloadedFileName);
        try (InputStream inputStream = fetchWebsite.openStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
			
			e.printStackTrace();
		}

	
	}
	
	
	/**
	 * 
	 */
	public void prepararReinicioTrasActualizar() {
		downloadLatestVersion(readFileFromUrlAndOpenUpdateDownloadLink());
		
		update.progressBar.setIndeterminate(false);
		update.progressBar.setStringPainted(true);
		update.progressBar.setValue(100);
		update.downloading.setVisible(false);
		update.reiniciar_y_actualizar.setVisible(true);
	}
	
	
	/**
	 * 
	 */
	public void leerUsuarioLogueado() {
		vista.nombre_usuario.setIcon(new ImageIcon(""));
		vista.nombre_usuario.setText(nombreUsuario_logueado);
		//vista.nombre_usuario.setIcon(new ImageIcon("images/user/default/user_16_px.png"));
		vista.loading_user.setVisible(false);
		vista.menuBar_usuario.setVisible(true);
		
		guardarDatosDeUsuarioDespuesDeIniciarSesion();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(vista.cerrar_sesion)) {
			//JOptionPane.showMessageDialog(null, "Cerrar sesión");
			cerrarSesion();
		}
		
	}

	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void registrarUsuario() {
		/*
		 * Comienza la carga del botón del registro de usuario
		 */
		login.register_loading.setIcon(new ImageIcon("images/Loading.gif"));
		login.btn_Registrarse.setEnabled(false);
		login.btn_Registrarse.setText("");
		login.register_loading.setVisible(true);
		
		boolean registroValido = false;
		boolean todosLosCamposRellenos = false;
		boolean usuarioValido = false;
		boolean samePasswords = false;
		boolean passwordValida = false;
		
		/**
		 * Comprobar si los campos están completos
		 */
		// JOptionPane.showMessageDialog(null, login.input_password.getPassword());
		if (login.input_nombre_de_usuario.getText().equals("") || login.input_email.getText().equals("")
				|| login.input_password.getText().equals("") || login.input_repeat_password.getText().equals("")) {
			login.internalFrame.setVisible(true);
			login.completaTodosLosCamposLabel.setVisible(true);
			login.emailNoValido.setVisible(false);
			login.input_email.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(122, 138, 153)));
			try {
				login.internalFrame.setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		} else {
			login.internalFrame.setVisible(false);
			login.completaTodosLosCamposLabel.setVisible(false);
			todosLosCamposRellenos = true;
		}

		/**
		 * Comprobaciones de campos
		 */

		/*
		 * Email
		 */
		String emailRegexp = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		
		if(todosLosCamposRellenos) {
			if (Pattern.matches(emailRegexp, login.input_email.getText())) {
				login.input_email.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(122, 138, 153)));
				registroValido = true;
				login.emailNoValido.setVisible(false);
			} else {
				login.input_email.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), Color.RED));
				login.emailNoValido.setText("<=  El email no tiene un formato válido");
				login.internalFrame.setVisible(true);
				try {
					login.internalFrame.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				login.emailNoValido.setVisible(true);
			}
		}
		
		/*
		 * Usuario
		 */
		
		if(todosLosCamposRellenos) {
			if(login.input_nombre_de_usuario.getText().length() <= 30 && login.input_nombre_de_usuario.getText().length() >= 6) {
				login.input_nombre_de_usuario.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(122, 138, 153)));
				usuarioValido = true;
				login.usuarioNoValido.setVisible(false);
			}else {
				login.input_nombre_de_usuario.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), Color.RED));
				login.usuarioNoValido.setText("<= El usuario debe tener entre 6 y 30 caracteres");
				login.internalFrame.setVisible(true);
				try {
					login.internalFrame.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				login.usuarioNoValido.setVisible(true);
			}
		}
		
		
		/*
		 * Comprobamos los campos de contraseña y sus tipo visibles
		 */
		
		if(login.input_password.isVisible()) {
			System.out.println("pass visible");
			if(login.input_password.getText() != login.textField_pass_register.getText()) {
				login.textField_pass_register.setText(login.input_password.getText());
			}
		}
		if(login.textField_pass_register.isVisible()) {
			System.out.println("text visible");
			if(login.input_password.getText() != login.textField_pass_register.getText()) {
				login.input_password.setText(login.textField_pass_register.getText());
			}
		}
		if(login.input_repeat_password.isVisible()) {
			System.out.println("repeat pass visible");
			if(login.input_repeat_password.getText() != login.textField_repeat_pass_register.getText()) {
				login.textField_repeat_pass_register.setText(login.input_repeat_password.getText());
			}
		}
		if(login.textField_repeat_pass_register.isVisible()) {
			System.out.println("repeat text visible");
			if(login.input_repeat_password.getText() != login.textField_repeat_pass_register.getText()) {
				login.input_repeat_password.setText(login.textField_repeat_pass_register.getText());
			}
		}
		
		/*
		 * Contraseña válida
		 */
		if(todosLosCamposRellenos) {
			if(login.input_password.getText().length() <= 30 && login.input_password.getText().length() >= 8) {
				login.input_password.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
				login.input_repeat_password.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
				login.panel_ojo_pass.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(122, 138, 153)));
				login.panel_ojo_repeat_pass.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(122, 138, 153)));
				login.textField_pass_register.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
				login.textField_repeat_pass_register.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
				passwordValida = true;
				login.pass_valida.setVisible(false);
			}else {
				login.input_password.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 0, 0)));
				login.input_repeat_password.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 0, 0)));
				login.panel_ojo_pass.setBorder(new MatteBorder(2, 0, 2, 2, (Color) new Color(255, 0, 0)));
				login.panel_ojo_repeat_pass.setBorder(new MatteBorder(2, 0, 2, 2, (Color) new Color(255, 0, 0)));
				login.textField_pass_register.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 0, 0)));
				login.textField_repeat_pass_register.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 0, 0)));
				login.internalFrame.setVisible(true);
				try {
					login.internalFrame.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				login.pass_valida.setVisible(true);
			}
		}
		
		
		/*
		 * Contraseñas iguales
		 */
		if(todosLosCamposRellenos) {
			if(login.input_password.getText().equals(login.input_repeat_password.getText())) {
				login.input_password.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
				login.input_repeat_password.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
				login.panel_ojo_pass.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(122, 138, 153)));
				login.panel_ojo_repeat_pass.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(122, 138, 153)));
				login.textField_pass_register.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
				login.textField_repeat_pass_register.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
				samePasswords = true;
				login.notSamePass.setVisible(false);
			}else {
				login.input_password.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 0, 0)));
				login.input_repeat_password.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 0, 0)));
				login.panel_ojo_pass.setBorder(new MatteBorder(2, 0, 2, 2, (Color) new Color(255, 0, 0)));
				login.panel_ojo_repeat_pass.setBorder(new MatteBorder(2, 0, 2, 2, (Color) new Color(255, 0, 0)));
				login.textField_pass_register.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 0, 0)));
				login.textField_repeat_pass_register.setBorder(new MatteBorder(2, 2, 2, 0, (Color) new Color(255, 0, 0)));
				
				login.internalFrame.setVisible(true);
				try {
					login.internalFrame.setMaximum(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				login.notSamePass.setVisible(true);
			}
		}
		
		
		
		
		
		
		
		
		/**
		 * Obtenemos todos los datos de los campos
		 */
		String nombreUsuario = login.input_nombre_de_usuario.getText();
		String email = login.input_email.getText();
		String password = BCrypt.hashpw(login.input_password.getText(), BCrypt.gensalt(10));
		String idioma = login.comboBox_idioma.getSelectedItem().toString();
		String imagen = login.imagen_user.getIcon().toString();
		
		/*JOptionPane.showMessageDialog(null, "nombreUsuario : " + nombreUsuario + 
											"\nemail : " + email +
											"\npassword : " + password + 
											"\nidioma : " + idioma +
											"\nimagen : " + imagen);*/
		//System.out.print(imagen);
		
		if(registroValido && todosLosCamposRellenos && usuarioValido && samePasswords && passwordValida) {
			if(comprobarSiExisteUsuario(login.input_nombre_de_usuario.getText()) && comprobarSiExisteEmail(login.input_email.getText())) {
				insertarNuevoUsuario(nombreUsuario, email, password, imagen, idioma);
				
				/*
				 * Guardamos el usuario logueado
				 */
				nombreUsuario_logueado = nombreUsuario;
				
				serviceCloseLogin.schedule(esperarAntesDeCerrarVentanaDeLogin, 1500, TimeUnit.MILLISECONDS);
				
			}else {
				login.register_loading.setIcon(new ImageIcon("images/no_conectado_(32x32).png"));
				serviceRegister_btn.schedule(esperarAntesDeActivarBotonDeRegistro, 2, TimeUnit.SECONDS);
			}
		}else {
			login.register_loading.setIcon(new ImageIcon("images/no_conectado_(32x32).png"));
			serviceRegister_btn.schedule(esperarAntesDeActivarBotonDeRegistro, 2, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * Detiene la carga del botón del registro de usuario
	 */
	public void detenerCargaBotonRegistroUsuario() {
		login.btn_Registrarse.setEnabled(true);
		login.btn_Registrarse.setText("Registrarse");
		login.register_loading.setVisible(false);
	}
	
	/**
	 * Crear el nuevo usuario en la BD
	*/
	public void insertarNuevoUsuario(String nombreUsuario, String email ,String password, String imagen, String idioma) {
		String noTotalRandomToken = BCrypt.hashpw("user_register", BCrypt.gensalt(10));
		String finalTokenGenerated = BCrypt.hashpw(noTotalRandomToken, BCrypt.gensalt(10));
		tokenUsuario_logueado = finalTokenGenerated;
		closeConnection();
		iniciar_Conexion_Con_Servidor();
		try {
			String Query = "INSERT INTO usuario(nombre_usuario, email, password, imagen, idioma, numero_horarios, miembro_desde, bloqueado, token_usuario)"
					+ " VALUES (?, ?, ? , ?, ?, 0 , now(), 0, ?);";
			
			//Statement st = conexion.createStatement();
			//st.executeUpdate(Query);
			
			PreparedStatement pStmt = conexion.prepareStatement(Query);
			pStmt.setString(1, nombreUsuario);
			pStmt.setString(2, email);
			pStmt.setString(3, password);
			pStmt.setString(4, imagen);
			pStmt.setString(5, idioma);
			pStmt.setString(6, finalTokenGenerated);
			
			int numUpd = pStmt.executeUpdate();
			
			System.out.println("Usuario creado correctamente. (" + numUpd + ")");
			login.register_loading.setIcon(new ImageIcon("images/conectado_(32x32).png"));
		}
		catch (Exception ex) {
			System.out.println(ex);
			System.out.println("Error al crear usuario");
		}
	}
	
	
	/**
	 * Hace visible u oculta la contraseña de los campos de contraseña
	 */
	public void ver_ocultar_password(boolean estado_ojo, String id_ojo) {
		if(estado_ojo) {
			if(id_ojo.equals("ojo_pass_register")) {
				login.ojo_pass_register.setIcon(new ImageIcon("images/login/no_visible_16px.png"));
				visible_pass_register = false;
				login.input_password.setText(login.textField_pass_register.getText());
				login.input_password.setVisible(true);
				login.textField_pass_register.setVisible(false);
				login.input_password.requestFocus();
			}else if(id_ojo.equals("ojo_repeat_pass_register")) {
				login.ojo_repeat_pass_register.setIcon(new ImageIcon("images/login/no_visible_16px.png"));
				visible_repeat_pass_register = false;
				login.input_repeat_password.setText(login.textField_repeat_pass_register.getText());
				login.input_repeat_password.setVisible(true);
				login.textField_repeat_pass_register.setVisible(false);
				login.input_repeat_password.requestFocus();
			}else if(id_ojo.equals("ojo_pass_login")) {
				login.ojo_pass_login.setIcon(new ImageIcon("images/login/no_visible_16px.png"));
				visible_pass_login = false;
				login.password_input_login.setText(login.textField_pass_login.getText());
				login.password_input_login.setVisible(true);
				login.textField_pass_login.setVisible(false);
				login.password_input_login.requestFocus();
			}
		}else {
			if(id_ojo.equals("ojo_pass_register")) {
				login.ojo_pass_register.setIcon(new ImageIcon("images/login/visible_16px.png"));
				visible_pass_register = true;
				login.textField_pass_register.setText(login.input_password.getText());
				login.textField_pass_register.setVisible(true);
				login.input_password.setVisible(false);
				login.textField_pass_register.requestFocus();
			}else if(id_ojo.equals("ojo_repeat_pass_register")) {
				login.ojo_repeat_pass_register.setIcon(new ImageIcon("images/login/visible_16px.png"));
				visible_repeat_pass_register = true;
				login.textField_repeat_pass_register.setText(login.input_repeat_password.getText());
				login.textField_repeat_pass_register.setVisible(true);
				login.input_repeat_password.setVisible(false);
				login.textField_repeat_pass_register.requestFocus();
			}else if(id_ojo.equals("ojo_pass_login")) {
				login.ojo_pass_login.setIcon(new ImageIcon("images/login/visible_16px.png"));
				visible_pass_login = true;
				login.textField_pass_login.setText(login.password_input_login.getText());
				login.textField_pass_login.setVisible(true);
				login.password_input_login.setVisible(false);
				login.textField_pass_login.requestFocus();
			}
		}
	}
	
	
	/**
	 * Comprueba si existe el usuario
	 */
	public boolean comprobarSiExisteUsuario(String usuario) {
		boolean existe = false;
		String usuarioEnBD = "-";
		
		closeConnection();
		iniciar_Conexion_Con_Servidor();
		try {
			String Query = "SELECT nombre_usuario FROM usuario WHERE nombre_usuario = ? ;";
			
			//Statement st = conexion.createStatement();
			//st.executeUpdate(Query);
			
			PreparedStatement pStmt = conexion.prepareStatement(Query);
			pStmt.setString(1, usuario);
			
			
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("(" + rs.getMetaData().getColumnCount() + ")");
				System.out.println("(" + rs.getString(1) + ")");
				usuarioEnBD = rs.getString(1);
			}
			pStmt.close();
			rs.close();
			
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		
		if(!usuario.equals(usuarioEnBD)){
			System.out.println("Usuario no encontrado .");
			existe = true;
			login.input_nombre_de_usuario.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(122, 138, 153)));
		}else {
			login.input_nombre_de_usuario.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), Color.RED));
			login.internalFrame.setVisible(true);
			try {
				login.internalFrame.setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			
			login.usuarioNoValido.setText("<= Este nombre de usuario ya está en uso");
			login.usuarioNoValido.setVisible(true);
		}
		
		return existe;
	}
	
	/**
	 * Comprueba si existe el email
	 */
	public boolean comprobarSiExisteEmail(String email) {
		boolean existe = false;
		String emailEnBD = "-";
		
		closeConnection();
		iniciar_Conexion_Con_Servidor();
		try {
			String Query = "SELECT email FROM usuario WHERE email = ? ;";
			
			//Statement st = conexion.createStatement();
			//st.executeUpdate(Query);
			
			PreparedStatement pStmt = conexion.prepareStatement(Query);
			pStmt.setString(1, email);
			
			
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("(" + rs.getMetaData().getColumnCount() + ")");
				System.out.println("(" + rs.getString(1) + ")");
				emailEnBD = rs.getString(1);
			}
			pStmt.close();
			rs.close();
			
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		
		if(!email.equals(emailEnBD)){
			System.out.println("Email no encontrado .");
			existe = true;
			login.input_email.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(122, 138, 153)));
		}else {
			login.input_email.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), Color.RED));
			login.internalFrame.setVisible(true);
			try {
				login.internalFrame.setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			
			login.emailNoValido.setText("<= Este email ya está en uso");
			login.emailNoValido.setVisible(true);
		}
		
		return existe;
	}
	
	/**
	 * Comprueba si existe el usuario en el login
	 */
	public boolean comprobarSiExisteUsuario_login(String usuario) {
		boolean encontrado = false;
		String usuarioEnBD = "-";
		
		closeConnection();
		iniciar_Conexion_Con_Servidor();
		try {
			String Query = "SELECT nombre_usuario FROM usuario WHERE nombre_usuario = ? ;";
			
			//Statement st = conexion.createStatement();
			//st.executeUpdate(Query);
			
			PreparedStatement pStmt = conexion.prepareStatement(Query);
			pStmt.setString(1, usuario);
			
			
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("(" + rs.getMetaData().getColumnCount() + ")");
				System.out.println("(" + rs.getString(1) + ")");
				usuarioEnBD = rs.getString(1);
			}
			pStmt.close();
			rs.close();
			
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		
		if(usuario.equals(usuarioEnBD)){
			System.out.println("Usuario encontrado .");
			login.portada_img.setIcon(new ImageIcon("images/Portada de lanzamiento Creador de horarios .png"));
			encontrado = true;
			//login.input_nombre_de_usuario.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(122, 138, 153)));
		}else {
			System.out.println("Usuario no encontrado .");
			//login.input_nombre_de_usuario.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), Color.RED));
			/*
			login.internalFrame.setVisible(true);
			try {
				login.internalFrame.setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}*/
			
			
			//login.usuarioNoValido.setText("<= Este nombre de usuario ya está en uso");
			//login.usuarioNoValido.setVisible(true);
		}
		return encontrado;
	}
	
	/**
	 * Comprueba si existe el email en el login
	 */
	public boolean comprobarSiExisteEmail_login(String email) {
		boolean encontrado = false;
		String emailEnBD = "-";
		
		closeConnection();
		iniciar_Conexion_Con_Servidor();
		try {
			String Query = "SELECT email FROM usuario WHERE email = ? ;";
			
			//Statement st = conexion.createStatement();
			//st.executeUpdate(Query);
			
			PreparedStatement pStmt = conexion.prepareStatement(Query);
			pStmt.setString(1, email);
			
			
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("(" + rs.getMetaData().getColumnCount() + ")");
				System.out.println("(" + rs.getString(1) + ")");
				emailEnBD = rs.getString(1);
			}
			pStmt.close();
			rs.close();
			
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		
		if(email.equals(emailEnBD)){
			System.out.println("Email encontrado .");
			login.portada_img.setIcon(new ImageIcon("images/Portada de lanzamiento Creador de horarios .png"));
			encontrado = true;
			//login.input_email.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(122, 138, 153)));
		}else {
			/*
			login.input_email.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 0, 0), Color.RED));
			login.internalFrame.setVisible(true);
			try {
				login.internalFrame.setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			
			login.emailNoValido.setText("<= Este email ya está en uso");
			login.emailNoValido.setVisible(true);
			*/
			System.out.println("Email no encontrado .");
			//login.portada_img.setIcon(new ImageIcon("images/login/login_falied/no_5.gif"));
		}
		return encontrado;
	}
	
	/**
	 * Comprueba si la contraseña del usuario al logearse
	 */
	public boolean comprobarPass_login_usuario(String usuario) {
		boolean encontrado = false;
		String pass = "-";
		String token_usuario = "-";
		nombreUsuario_logueado = usuario;
		
		closeConnection();
		iniciar_Conexion_Con_Servidor();
		try {
			String Query = "SELECT password, token_usuario FROM usuario WHERE nombre_usuario = ? ;";
			
			//Statement st = conexion.createStatement();
			//st.executeUpdate(Query);
			
			PreparedStatement pStmt = conexion.prepareStatement(Query);
			pStmt.setString(1, usuario);
			
			
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("(" + rs.getMetaData().getColumnCount() + ")");
				System.out.println("(" + rs.getString(1) + ")");
				pass = rs.getString(1);
				token_usuario = rs.getString(2);
			}
			pStmt.close();
			rs.close();
			
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		
		tokenUsuario_logueado = token_usuario;
		
		if(BCrypt.checkpw(login.password_input_login.getText(), pass)){
			System.out.println("Contraseña correcta .");
			login.portada_img.setIcon(new ImageIcon("images/Portada de lanzamiento Creador de horarios .png"));
			encontrado = true;
			
		}else {
			
			System.out.println("Contraseña incorrecta .");
			
		}
		return encontrado;
	}
	
	/**
	 * Comprueba la contraseña del usuario (usando el email) al logearse
	 */
	public boolean comprobarPass_login_email(String email) {
		boolean encontrado = false;
		String pass = "-";
		String token_usuario = "-";
		String usuario = "-";
		
		
		closeConnection();
		iniciar_Conexion_Con_Servidor();
		try {
			String Query = "SELECT password, token_usuario, nombre_usuario  FROM usuario WHERE email = ? ;";
			
			//Statement st = conexion.createStatement();
			//st.executeUpdate(Query);
			
			PreparedStatement pStmt = conexion.prepareStatement(Query);
			pStmt.setString(1, email);
			
			
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("(" + rs.getMetaData().getColumnCount() + ")");
				System.out.println("(" + rs.getString(1) + ")");
				pass = rs.getString(1);
				token_usuario = rs.getString(2);
				usuario = rs.getString(3);
			}
			pStmt.close();
			rs.close();
			
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		
		tokenUsuario_logueado = token_usuario;
		nombreUsuario_logueado = usuario;
		
		if(BCrypt.checkpw(login.password_input_login.getText(), pass)){
			System.out.println("Contraseña correcta .");
			login.portada_img.setIcon(new ImageIcon("images/Portada de lanzamiento Creador de horarios .png"));
			encontrado = true;
			
		}else {
			
			System.out.println("Contraseña incorrecta .");
			
		}
		return encontrado;
	}
	
	/**
	 * 
	 */
	public void iniciarSesion() {
		/*
		 * Comienza la carga del botón de iniciar sesión
		 */
		login.login_loading.setIcon(new ImageIcon("images/Loading.gif"));
		login.btn_login.setEnabled(false);
		login.btn_login.setText("");
		login.login_loading.setVisible(true);
		
		//JOptionPane.showMessageDialog(null, "iniciando sesión ...");
		
		//BCrypt.hashpw("ª", BCrypt.gensalt(15)); 
		
		/*
		 * Comprobamos los campos de contraseña y sus tipo visibles
		 */
		
		if(login.password_input_login.isVisible()) {
			System.out.println("pass visible login");
			if(login.password_input_login.getText() != login.textField_pass_login.getText()) {
				login.textField_pass_login.setText(login.password_input_login.getText());
			}
		}
		if(login.textField_pass_login.isVisible()) {
			System.out.println("text visible login");
			if(login.password_input_login.getText() != login.textField_pass_login.getText()) {
				login.password_input_login.setText(login.textField_pass_login.getText());
			}
		}
		
		boolean existeElUsuario = comprobarSiExisteUsuario_login(login.user_input.getText()) ;
		boolean existeElEmail = comprobarSiExisteEmail_login(login.user_input.getText());
		boolean passValida = false;
		
		if(existeElUsuario) {
			passValida = comprobarPass_login_usuario(login.user_input.getText());
		}else if(existeElEmail) {
			passValida = comprobarPass_login_email(login.user_input.getText());
		}
		
		if((existeElUsuario || existeElEmail) && passValida) {
			
			login.login_loading.setIcon(new ImageIcon("images/conectado_(32x32).png"));
			//login.setVisible(false);
			//login.dispose();
			serviceCloseLogin.schedule(esperarAntesDeCerrarVentanaDeLogin, 1500, TimeUnit.MILLISECONDS);
		}else {
			login.login_loading.setIcon(new ImageIcon("images/no_conectado_(32x32).png"));
			login.portada_img.setIcon(new ImageIcon("images/login/login_falied/no_5.gif"));
			serviceLogin_btn.schedule(esperarAntesDeActivarBotonDeLogin, 3, TimeUnit.SECONDS);
		}
		
		
		
		
	}
	
	public void detenerCargaBotonLogin() {
		/*
		 * Detiene la carga del botón de iniciar sesión
		 */
		login.btn_login.setEnabled(true);
		login.btn_login.setText("Iniciar sesión");
		login.login_loading.setVisible(false);
		login.portada_img.setIcon(new ImageIcon("images/Portada de lanzamiento Creador de horarios .png"));
	}
	
	
	/**
	 * Guarda los datos después de iniciar sesión
	 */
	public void guardarDatosDeUsuarioDespuesDeIniciarSesion() {
		
		/*
		 * Crea el directorio para guardar los datos del usuario logeado
		 */
		File directory = new File("logged_user");
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		/*
		 * Ecribe el fichero con el nombre de usuario
		 */
		
		File ficheroSalida = new File("logged_user/nombre_usuario.txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print(nombreUsuario_logueado);
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "nombre de usuario logeado guardado ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(vista.ventana_principal, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
		
		/*
		 * Ecribe el fichero con el token de usuario
		 */
		
		File ficheroSalida_token_usuario = new File("logged_user/token_usuario.txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida_token_usuario);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print(tokenUsuario_logueado);
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "token de usuario logeado guardado ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(vista.ventana_principal, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	
	/**
	 * 
	 */
	public void loginCorrecto() {
		login.dispose();
		vista.ventana_principal.setVisible(true);
		autoLogin.dispose();
	}
	
	/**
	 * 
	 * @return
	 */
	public void leerSesionDeUsuarioLogueado() {
		/*
		 * Lee el nombre_usuario 
		 */
		
		String nombre_usuario_local_stored = "?";
		
		Scanner lectorFichero;
		
		File ficheroSalida = new File("logged_user/nombre_usuario.txt");
		
		
		try {
			lectorFichero = new Scanner(ficheroSalida);
			
			while (lectorFichero.hasNext()) {
//				System.out.println(lectorFichero.nextLine());
				nombre_usuario_local_stored = lectorFichero.nextLine();
			}
			
			lectorFichero.close();
			nombreUsuario_logueado = nombre_usuario_local_stored;
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			
			
			//JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo de configuración , se creará automáticamente con la configuración por defecto" + "\r\n" + "\r\n" + "\r\n" + error.toString()  , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
			
		}
		
		
		/*
		 * Lee el tokenUsuario_logueado 
		 */
		
		String tokenUsuario_logueado_local_stored = "?";
		
		Scanner lectorFichero_token;
		
		File ficheroSalida_token = new File("logged_user/token_usuario.txt");
		
		
		try {
			lectorFichero_token = new Scanner(ficheroSalida_token);
			
			while (lectorFichero_token.hasNext()) {
//				System.out.println(lectorFichero.nextLine());
				tokenUsuario_logueado_local_stored = lectorFichero_token.nextLine();
			}
			
			lectorFichero_token.close();
			tokenUsuario_logueado = tokenUsuario_logueado_local_stored;
		} catch (Exception e) {
			
			
			e.printStackTrace();
			
			StringWriter error = new StringWriter();
			e.printStackTrace(new PrintWriter(error));
			
			
			//JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo de configuración , se creará automáticamente con la configuración por defecto" + "\r\n" + "\r\n" + "\r\n" + error.toString()  , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
			
		}
		
		
	}
	
	/**
	 * Inicia sesión automáticamente usando la sesión de usuario guardada
	 */
	public boolean autoLogin() {
		autoLogin.lanzarVentana();
		autoLogin.setLocationRelativeTo(null);
		autoLogin.setVisible(true);
		
		boolean valido = false;
		
		/*
		 * Comienza la carga del botón de iniciar sesión
		 */
		/*
		login.login_loading.setIcon(new ImageIcon("images/Loading.gif"));
		login.btn_login.setEnabled(false);
		login.btn_login.setText("");
		login.login_loading.setVisible(true);
		*/
		
		boolean existeElUsuario = comprobarSiExisteUsuario_login(nombreUsuario_logueado) ;
		boolean tokenValido = false;
		
		if(existeElUsuario) {
			tokenValido = comprobarSiExisteToken_Usuario_login(nombreUsuario_logueado, tokenUsuario_logueado);
		}
		
		if(existeElUsuario && tokenValido) {
			login.login_loading.setIcon(new ImageIcon("images/conectado_(32x32).png"));
			serviceCloseLogin.schedule(esperarAntesDeCerrarVentanaDeLogin, 1500, TimeUnit.MILLISECONDS);
			valido = true;
		}else {
			/*
			login.login_loading.setIcon(new ImageIcon("images/no_conectado_(32x32).png"));
			login.portada_img.setIcon(new ImageIcon("images/login/login_falied/no_5.gif"));
			serviceLogin_btn.schedule(esperarAntesDeActivarBotonDeLogin, 3, TimeUnit.SECONDS);
			*/
		}
		
		return valido;
	}
	
	
	/**
	 * Comprueba si existe el token de usuario en el login
	 */
	public boolean comprobarSiExisteToken_Usuario_login(String usuario, String token_usuario) {
		boolean encontrado = false;
		String token_usuarioEnBD = "-";
		
		closeConnection();
		iniciar_Conexion_Con_Servidor();
		try {
			String Query = "SELECT token_usuario FROM usuario WHERE nombre_usuario = ? ;";
			
			//Statement st = conexion.createStatement();
			//st.executeUpdate(Query);
			
			PreparedStatement pStmt = conexion.prepareStatement(Query);
			pStmt.setString(1, usuario);
			
			
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("(" + rs.getMetaData().getColumnCount() + ")");
				System.out.println("(" + rs.getString(1) + ")");
				token_usuarioEnBD = rs.getString(1);
			}
			pStmt.close();
			rs.close();
			
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		
		if(token_usuario.equals(token_usuarioEnBD)){
			System.out.println("Token de usuario encontrado .");
			login.portada_img.setIcon(new ImageIcon("images/Portada de lanzamiento Creador de horarios .png"));
			encontrado = true;
		}else {
			System.out.println("Token de usuario no encontrado .");
		}
		return encontrado;
	}
	
	/**
	 * Cierra la sesión borrando el nombre de usuario y su token guardado
	 */
	public void cerrarSesion() {
		
		/*
		 * Crea el directorio para guardar los datos del usuario logeado
		 */
		File directory = new File("logged_user");
		if(!directory.exists()) {
			directory.mkdir();
		}
		
		/*
		 * Ecribe el fichero con el nombre de usuario
		 */
		
		File ficheroSalida = new File("logged_user/nombre_usuario.txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print("");
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "nombre de usuario logeado guardado ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(vista.ventana_principal, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
		
		/*
		 * Ecribe el fichero con el token de usuario
		 */
		
		File ficheroSalida_token_usuario = new File("logged_user/token_usuario.txt");
		
		try {
			FileWriter escrituraFichero = new FileWriter(ficheroSalida_token_usuario);
			PrintWriter pw = new PrintWriter(escrituraFichero);
			
			pw.print("");
			
			escrituraFichero.close();
			pw.close();
			
//			JOptionPane.showMessageDialog(null, "token de usuario logeado guardado ");

			
		} catch (IOException e2) {
			e2.printStackTrace();
			
			StringWriter error = new StringWriter();
			e2.printStackTrace(new PrintWriter(error));
			
			
			JOptionPane.showMessageDialog(vista.ventana_principal, error.toString() , "Se ha producido un error :(" , JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			restartApplication(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
