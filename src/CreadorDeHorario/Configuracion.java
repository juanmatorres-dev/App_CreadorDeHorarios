package CreadorDeHorario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class Configuracion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel panel_iconos_1;
	public JCheckBox chckbxMostrarSegundosReloj;
	public JButton test_notificaciones;
	public JCheckBox chckbxMostrarNotificaciones;
	public JComboBox comboBox_tipoDeBarra;
	public JButton btnBorraBD_yReconstruir;
	public JLabel lbl_Montapuercos_Clash_Royale_izq;
	public JLabel lbl_Montapuercos_Clash_Royale_derch;
	public JButton btn_check_update;

	/**
	 * Launch the application.
	 */
	public void lanzarVentana() {
		try {
			Configuracion dialog = new Configuracion();

			
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.setVisible(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Configuracion() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		setFocusable(true);
		
		setModal(true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/calendario (1).png"));
		setTitle("Configuracion");
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 709, 536);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		Version_App version = new Version_App();

		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(0, 0, 693, 497);
			contentPanel.add(tabbedPane);
			{
				JPanel panel = new JPanel();
				panel.setVisible(false);
				tabbedPane.addTab("Configuración", null, panel, null);
				panel.setLayout(null);
				
				chckbxMostrarSegundosReloj = new JCheckBox("Mostrar segundos en el reloj");
				chckbxMostrarSegundosReloj.setSelected(true);
				chckbxMostrarSegundosReloj.setBounds(32, 43, 506, 23);
				panel.add(chckbxMostrarSegundosReloj);
				chckbxMostrarSegundosReloj.setFocusable(false);
				
				test_notificaciones = new JButton("Testear notificaciones");
				test_notificaciones.setFocusable(false);
				test_notificaciones.setBounds(32, 135, 194, 23);
				panel.add(test_notificaciones);
				if(!(System.getProperty("os.name").equals("Windows 10"))) {
					test_notificaciones.setEnabled(false);
				}
				
				chckbxMostrarNotificaciones = new JCheckBox("Mostrar notificación al cambiar de asignatura");
				chckbxMostrarNotificaciones.setFocusable(false);
				chckbxMostrarNotificaciones.setBounds(32, 84, 348, 23);
				panel.add(chckbxMostrarNotificaciones);
				if(!(System.getProperty("os.name").equals("Windows 10"))) {
					chckbxMostrarNotificaciones.setEnabled(false);
				}
				
				JLabel lblNewLabel = new JLabel("(Sólo funciona en Windows 10)");
				lblNewLabel.setVisible(false);
				lblNewLabel.setBounds(394, 88, 194, 14);
				panel.add(lblNewLabel);
				if(!(System.getProperty("os.name").equals("Windows 10"))) {
					lblNewLabel.setVisible(true);
				}
				
				JPanel panel_autor_1 = new JPanel();
				panel_autor_1.setBounds(66, 273, 556, 149);
				panel.add(panel_autor_1);
				
				TitledBorder title_autor2; // Título en forma de borde
				title_autor2 = BorderFactory.createTitledBorder("Datos de tu sistema operativo :");
				panel_autor_1.setBorder(title_autor2);
				panel_autor_1.setLayout(null);
				{
					JLabel lblNewLabel_1 = new JLabel("Nombre : " + System.getProperty("os.name"));
					lblNewLabel_1.setBounds(10, 21, 536, 39);
					panel_autor_1.add(lblNewLabel_1);
					lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
				}
				{
					JLabel lblNewLabel_1 = new JLabel("Arquitectura : " + System.getProperty("os.arch"));
					lblNewLabel_1.setBounds(10, 62, 536, 39);
					panel_autor_1.add(lblNewLabel_1);
					lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
				}
				{
					JLabel lblNewLabel_1 = new JLabel("Versión : " + System.getProperty("os.version"));
					lblNewLabel_1.setBounds(10, 97, 536, 39);
					panel_autor_1.add(lblNewLabel_1);
					lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
					lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
				}
				
				JLabel lblNewLabel_1 = new JLabel("(Sólo funciona en Windows 10)");
				lblNewLabel_1.setVisible(false);
				lblNewLabel_1.setBounds(394, 139, 194, 14);
				panel.add(lblNewLabel_1);
				
				comboBox_tipoDeBarra = new JComboBox();
				comboBox_tipoDeBarra.setFocusable(false);
				comboBox_tipoDeBarra.setModel(new DefaultComboBoxModel(new String[] {"Barra personalizada circular naranja", "Barra básica ( nativa de Java )", "Barra personalizada triangular morada"}));
				comboBox_tipoDeBarra.setBounds(129, 182, 266, 20);
				panel.add(comboBox_tipoDeBarra);
				
				JLabel lblNewLabel_2 = new JLabel("Tipo de barra : ");
				lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel_2.setBounds(32, 185, 119, 14);
				panel.add(lblNewLabel_2);
				if(!(System.getProperty("os.name").equals("Windows 10"))) {
					lblNewLabel_1.setVisible(true);
				}
			}
			
			JPanel panel_1 = new JPanel();
			tabbedPane.addTab("Acción de alto impacto ❗", null, panel_1, null);
			panel_1.setLayout(null);
			
			btnBorraBD_yReconstruir = new JButton("Borrar y reconstruir la base de datos");
			btnBorraBD_yReconstruir.setFocusable(false);
			btnBorraBD_yReconstruir.setBackground(Color.RED);
			btnBorraBD_yReconstruir.setForeground(Color.WHITE);
			btnBorraBD_yReconstruir.setFont(new Font("Tahoma", Font.BOLD, 18));
			btnBorraBD_yReconstruir.setBounds(68, 55, 546, 67);
			panel_1.add(btnBorraBD_yReconstruir);
			
			JPanel panel_autor_1_1 = new JPanel();
			panel_autor_1_1.setLayout(null);
			panel_autor_1_1.setBounds(66, 173, 556, 237);
			panel_1.add(panel_autor_1_1);
			
			TitledBorder title_masInfo; // Título en forma de borde
			title_masInfo = BorderFactory.createTitledBorder("Más información : ");
			panel_autor_1_1.setBorder(title_masInfo);
			
			JLabel lblNewLabel_3 = new JLabel("Esta acción podría ser útil por varios motivos : ");
			lblNewLabel_3.setBounds(10, 24, 536, 44);
			panel_autor_1_1.add(lblNewLabel_3);
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3.setIcon(null);
			
			JLabel lblNewLabel_3_1 = new JLabel("- Acabas de instalar el programa y aún no tienes la base de datos creada");
			lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_3_1.setBounds(10, 62, 536, 44);
			panel_autor_1_1.add(lblNewLabel_3_1);
			
			JLabel lblNewLabel_3_1_1 = new JLabel("- La base de datos se ha dañado y necesitas reconstruirla");
			lblNewLabel_3_1_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_3_1_1.setBounds(10, 94, 536, 44);
			panel_autor_1_1.add(lblNewLabel_3_1_1);
			
			JLabel lblNewLabel_3_1_1_1 = new JLabel("- Quieres borrar todos los datos de la base de datos");
			lblNewLabel_3_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_3_1_1_1.setBounds(10, 125, 536, 44);
			panel_autor_1_1.add(lblNewLabel_3_1_1_1);
			
			lbl_Montapuercos_Clash_Royale_izq = new JLabel("");
			lbl_Montapuercos_Clash_Royale_izq.setVisible(false);
			lbl_Montapuercos_Clash_Royale_izq.setIcon(new ImageIcon("images/Montapuercos_Clash_Royale.gif"));
			lbl_Montapuercos_Clash_Royale_izq.setBounds(8, 67, 50, 48);
			panel_1.add(lbl_Montapuercos_Clash_Royale_izq);
			
			lbl_Montapuercos_Clash_Royale_derch = new JLabel("");
			lbl_Montapuercos_Clash_Royale_derch.setVisible(false);
			lbl_Montapuercos_Clash_Royale_derch.setIcon(new ImageIcon("images/Montapuercos_Clash_Royale.gif"));
			lbl_Montapuercos_Clash_Royale_derch.setBounds(628, 67, 50, 48);
			panel_1.add(lbl_Montapuercos_Clash_Royale_derch);
			{
				JPanel panel = new JPanel();
				tabbedPane.addTab("Acerca De ...", null, panel, null);
				tabbedPane.setFocusable(false);
				panel.setLayout(null);
				{
					JPanel panel_autor_1 = new JPanel();
					panel_autor_1.setBounds(112, 11, 463, 96);
					panel.add(panel_autor_1);
					panel_autor_1.setLayout(null);
					{
						JLabel lblAutorJuan = new JLabel("Autor (de la App ) : Juan Manuel Torres Martínez");
						lblAutorJuan.setBounds(82, 21, 293, 17);
						lblAutorJuan.setFont(new Font("Tahoma", Font.PLAIN, 14));
						panel_autor_1.add(lblAutorJuan);
					}

					TitledBorder title_autor2; // Título en forma de borde
					title_autor2 = BorderFactory.createTitledBorder("Autor");
					panel_autor_1.setBorder(title_autor2);
					
					JLabel lbl_linkedin = new JLabel("");
					lbl_linkedin.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							lbl_linkedin.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}
						@Override
						public void mousePressed(MouseEvent e) {
							lbl_linkedin.setBorder(BorderFactory.createLoweredBevelBorder());
						}
						@Override
						public void mouseReleased(MouseEvent e) {
							lbl_linkedin.setBorder(BorderFactory.createRaisedBevelBorder());
						}
						@Override
						public void mouseClicked(MouseEvent e) {
							try {
								Desktop.getDesktop().browse(new URI(
										"https://www.linkedin.com/in/juanmanueltorresmart%C3%ADnez/"));
							} catch (Exception er) {
								JOptionPane.showInternalMessageDialog(null, "Error ");
							}
						}
					});
					lbl_linkedin.setHorizontalAlignment(SwingConstants.CENTER);
					lbl_linkedin.setIcon(new ImageIcon("images/linkedin_(24x24).png"));
					lbl_linkedin.setBounds(159, 49, 50, 36);
					panel_autor_1.add(lbl_linkedin);
					lbl_linkedin.setVerticalAlignment(SwingConstants.CENTER);
					lbl_linkedin.setHorizontalAlignment(SwingConstants.CENTER);
					lbl_linkedin.setBorder(BorderFactory.createRaisedBevelBorder());
					
					JLabel lbl_github = new JLabel("");
					lbl_github.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							lbl_github.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}
						@Override
						public void mousePressed(MouseEvent e) {
							lbl_github.setBorder(BorderFactory.createLoweredBevelBorder());
						}
						@Override
						public void mouseReleased(MouseEvent e) {
							lbl_github.setBorder(BorderFactory.createRaisedBevelBorder());
						}
						@Override
						public void mouseClicked(MouseEvent e) {
							try {
								Desktop.getDesktop().browse(new URI(
										"https://github.com/juanmatorres-dev"));
							} catch (Exception er) {
								JOptionPane.showInternalMessageDialog(null, "Error ");
							}
						}
					});
					lbl_github.setIcon(new ImageIcon("images/github_(24x24).png"));
					lbl_github.setHorizontalAlignment(SwingConstants.CENTER);
					lbl_github.setBounds(237, 49, 50, 36);
					panel_autor_1.add(lbl_github);
					lbl_github.setVerticalAlignment(SwingConstants.CENTER);
					lbl_github.setHorizontalAlignment(SwingConstants.CENTER);
					lbl_github.setBorder(BorderFactory.createRaisedBevelBorder());
				}
				{
					JPanel panel_version_1 = new JPanel();
					panel_version_1.setBounds(112, 118, 463, 96);
					panel.add(panel_version_1);
					panel_version_1.setLayout(null);
					{
						JLabel lblVersin = new JLabel("Versión :  " + version.version);
						lblVersin.setBounds(153, 24, 156, 17);
						lblVersin.setFont(new Font("Tahoma", Font.BOLD, 14));
						panel_version_1.add(lblVersin);
					}
					TitledBorder title_version2; // Título en forma de borde
					title_version2 = BorderFactory.createTitledBorder("Versión");
					panel_version_1.setBorder(title_version2);
					
					btn_check_update = new JButton("Comprobar si hay actualizaciones");
					btn_check_update.setBounds(109, 62, 244, 23);
					panel_version_1.add(btn_check_update);
				}

				panel_iconos_1 = new JPanel();
				panel_iconos_1.setLayout(null);
				panel_iconos_1.setBounds(65, 223, 557, 235);
				panel.add(panel_iconos_1);

				JLabel lblLink = new JLabel("<HTML><U>(logo) </U></FONT></HTML>");
				lblLink.setHorizontalAlignment(SwingConstants.CENTER);
				lblLink.setBounds(42, 23, 46, 39);
				panel_iconos_1.add(lblLink);
				lblLink.setForeground(new Color(0, 0, 153, 1));
				

				lblLink.setHorizontalTextPosition(JLabel.CENTER); // Para centrar la imagen dentro del label
				lblLink.setVerticalTextPosition(JLabel.CENTER);

				lblLink.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							Desktop.getDesktop().browse(new URI(
									"https://www.flaticon.es/icono-gratis/calendario_2721227?term=horario&page=1&position=8&page=1&position=8&related_id=2721227&origin=search"));
						} catch (Exception er) {
							JOptionPane.showInternalMessageDialog(null, "Error ");
						}
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						lblLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
				});
				lblLink.setFont(new Font("Tahoma", Font.PLAIN, 14));
			}

			TitledBorder title_iconos; // Título en forma de borde
			title_iconos = BorderFactory.createTitledBorder("Iconos usados en la App");
			panel_iconos_1.setBorder(title_iconos);

			{
				JLabel lbliconoUsadoflecha = new JLabel("<HTML><U>(check verde) </U></FONT></HTML>");
				lbliconoUsadoflecha.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent arg0) {
						lbliconoUsadoflecha.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							Desktop.getDesktop().browse(new URI(
									"https://www.flaticon.es/icono-gratis/cheque_391175?term=aceptar&page=1&position=3&page=1&position=3&related_id=391175&origin=search"));
						} catch (Exception er) {
							JOptionPane.showInternalMessageDialog(null, "Error ");
						}
					}
				});
				lbliconoUsadoflecha.setBounds(126, 23, 89, 39);
				panel_iconos_1.add(lbliconoUsadoflecha);
				lbliconoUsadoflecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lbliconoUsadoflecha.setForeground(new Color(0, 0, 153, 1));
			}
			{
				JLabel lbliconoUsadoflecha_1 = new JLabel("<HTML><U>(X roja) </U></FONT></HTML>");
				lbliconoUsadoflecha_1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						lbliconoUsadoflecha_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							Desktop.getDesktop().browse(new URI(
									"https://www.flaticon.es/icono-gratis/cancelar_594598?term=marca%20de%20cruz&page=1&position=3&page=1&position=3&related_id=594598&origin=search"));
						} catch (Exception er) {
							JOptionPane.showInternalMessageDialog(null, "Error ");
						}
					}
				});
				lbliconoUsadoflecha_1.setBounds(249, 23, 56, 39);
				panel_iconos_1.add(lbliconoUsadoflecha_1);
				lbliconoUsadoflecha_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lbliconoUsadoflecha_1.setForeground(new Color(0, 0, 153, 1));
			}
			{
				JLabel lbliconoUsadoconfigurain = new JLabel("<HTML><U> (configuración) </U></FONT></HTML>");
				lbliconoUsadoconfigurain.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						lbliconoUsadoconfigurain.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							Desktop.getDesktop().browse(new URI(
									"https://www.flaticon.es/icono-gratis/configuraciones_561135?term=configuracion&page=1&position=20&page=1&position=20&related_id=561135&origin=search"));
						} catch (Exception er) {
							JOptionPane.showInternalMessageDialog(null, "Error ");
						}
					}
				});
				lbliconoUsadoconfigurain.setBounds(339, 23, 96, 39);
				panel_iconos_1.add(lbliconoUsadoconfigurain);
				lbliconoUsadoconfigurain.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lbliconoUsadoconfigurain.setForeground(new Color(0, 0, 153, 1));
			}
			
			JPanel panel = new JPanel();
			tabbedPane.addTab("Información sobre el icono extra ", null, panel, null);
			panel.setLayout(null);
			
			JPanel panel_origen = new JPanel();
			panel_origen.setLayout(null);
			panel_origen.setBounds(65, 11, 557, 77);
			panel.add(panel_origen);
			
			TitledBorder title_panel_origen; // Título en forma de borde
			title_panel_origen = BorderFactory.createTitledBorder("Origen");
			panel_origen.setBorder(title_panel_origen);
			
			JLabel lblLink = new JLabel("<HTML><U>(Origen) </U></FONT></HTML>");
			lblLink.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					lblLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(
								"https://giphy.com/gifs/Clash-Royale-clash-royale-supercell-hog-rider-JoIpcS8zWffG6cO1Py"));
					} catch (Exception er) {
						JOptionPane.showInternalMessageDialog(null, "Error ");
					}
				}
			});
			lblLink.setVerticalTextPosition(SwingConstants.CENTER);
			lblLink.setHorizontalTextPosition(SwingConstants.CENTER);
			lblLink.setHorizontalAlignment(SwingConstants.CENTER);
			lblLink.setForeground(new Color(0, 0, 153, 1));
			lblLink.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLink.setBounds(250, 27, 61, 39);
			panel_origen.add(lblLink);
			
			JPanel panel_propietario = new JPanel();
			panel_propietario.setLayout(null);
			panel_propietario.setBounds(65, 110, 557, 77);
			panel.add(panel_propietario);
			
			TitledBorder title_panel_propietario; // Título en forma de borde
			title_panel_propietario = BorderFactory.createTitledBorder("Propietario");
			panel_propietario.setBorder(title_panel_propietario);
			
			JLabel lblLink_1 = new JLabel("<HTML><U>(Propietario) </U></FONT></HTML>");
			lblLink_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lblLink_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(
								"https://supercell.com/en/"));
					} catch (Exception er) {
						JOptionPane.showInternalMessageDialog(null, "Error ");
					}
				}
			});
			lblLink_1.setVerticalTextPosition(SwingConstants.CENTER);
			lblLink_1.setHorizontalTextPosition(SwingConstants.CENTER);
			lblLink_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblLink_1.setForeground(new Color(0, 0, 153, 1));
			lblLink_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLink_1.setBounds(233, 27, 89, 39);
			panel_propietario.add(lblLink_1);
			
			JPanel panel_videoJuego_al_que_pertenece = new JPanel();
			panel_videoJuego_al_que_pertenece.setLayout(null);
			panel_videoJuego_al_que_pertenece.setBounds(65, 215, 557, 77);
			panel.add(panel_videoJuego_al_que_pertenece);
			
			TitledBorder title_panel_videoJuego_al_que_pertenece; // Título en forma de borde
			title_panel_videoJuego_al_que_pertenece = BorderFactory.createTitledBorder("Videojuego al que pertenece");
			panel_videoJuego_al_que_pertenece.setBorder(title_panel_videoJuego_al_que_pertenece);
			
			JLabel lblLink_1_1 = new JLabel("<HTML><U>(Videojuego al que pertenece) </U></FONT></HTML>");
			lblLink_1_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lblLink_1_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(
								"https://clashroyale.com/es/"));
					} catch (Exception er) {
						JOptionPane.showInternalMessageDialog(null, "Error ");
					}
				}
			});
			lblLink_1_1.setVerticalTextPosition(SwingConstants.CENTER);
			lblLink_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
			lblLink_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblLink_1_1.setForeground(new Color(0, 0, 153, 1));
			lblLink_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLink_1_1.setBounds(175, 27, 211, 39);
			panel_videoJuego_al_que_pertenece.add(lblLink_1_1);
			
			JPanel panel_mas_informacion_acerca_del_personaje = new JPanel();
			panel_mas_informacion_acerca_del_personaje.setLayout(null);
			panel_mas_informacion_acerca_del_personaje.setBounds(65, 315, 557, 77);
			panel.add(panel_mas_informacion_acerca_del_personaje);
			
			TitledBorder title_panel_mas_informacion_acerca_del_personaje; // Título en forma de borde
			title_panel_mas_informacion_acerca_del_personaje = BorderFactory.createTitledBorder("Más información acerca del personaje");
			panel_mas_informacion_acerca_del_personaje.setBorder(title_panel_mas_informacion_acerca_del_personaje);
			
			JLabel lblLink_1_1_1 = new JLabel("<HTML><U>(Más información acerca del personaje) </U></FONT></HTML>");
			lblLink_1_1_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lblLink_1_1_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(
								"https://clash-royale.fandom.com/es/wiki/Montapuercos"));
					} catch (Exception er) {
						JOptionPane.showInternalMessageDialog(null, "Error ");
					}
				}
			});
			lblLink_1_1_1.setVerticalTextPosition(SwingConstants.CENTER);
			lblLink_1_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
			lblLink_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblLink_1_1_1.setForeground(new Color(0, 0, 153, 1));
			lblLink_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLink_1_1_1.setBounds(162, 27, 255, 39);
			panel_mas_informacion_acerca_del_personaje.add(lblLink_1_1_1);
			
			JPanel panel_2 = new JPanel();
			tabbedPane.addTab("Ayuda", null, panel_2, null);
			panel_2.setLayout(null);
			
			JPanel panel_web = new JPanel();
			panel_web.setLayout(null);
			panel_web.setBounds(65, 37, 557, 77);
			panel_2.add(panel_web);
			
			TitledBorder title_panel_web; // Título en forma de borde
			title_panel_web = BorderFactory.createTitledBorder("Web");
			panel_web.setBorder(title_panel_web);
			
			JLabel lblLink_2 = new JLabel("<HTML><U>(Ver) </U></FONT></HTML>");
			lblLink_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					lblLink_2.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(
								"https://practicasbyjmtm1.firebaseapp.com/proyectos/CreadorDeHorarios/CreadorDeHorarios.html"));
					} catch (Exception er) {
						JOptionPane.showInternalMessageDialog(null, "Error ");
					}
				}
			});
			lblLink_2.setVerticalTextPosition(SwingConstants.CENTER);
			lblLink_2.setHorizontalTextPosition(SwingConstants.CENTER);
			lblLink_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblLink_2.setForeground(new Color(0, 0, 153, 1));
			lblLink_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLink_2.setBounds(250, 27, 61, 39);
			panel_web.add(lblLink_2);
			
			JPanel panel_ayuda = new JPanel();
			panel_ayuda.setLayout(null);
			panel_ayuda.setBounds(65, 146, 557, 77);
			panel_2.add(panel_ayuda);
			
			TitledBorder title_panel_ayuda; // Título en forma de borde
			title_panel_ayuda = BorderFactory.createTitledBorder("Ayuda");
			panel_ayuda.setBorder(title_panel_ayuda);
			
			JLabel lblLink_1_2 = new JLabel("<HTML><U>(Ver) </U></FONT></HTML>");
			lblLink_1_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lblLink_1_2.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(
								"https://practicasbyjmtm1.firebaseapp.com/proyectos/CreadorDeHorarios/CreadorDeHorarios.html#Ayuda"));
					} catch (Exception er) {
						JOptionPane.showInternalMessageDialog(null, "Error ");
					}
				}
			});
			lblLink_1_2.setVerticalTextPosition(SwingConstants.CENTER);
			lblLink_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
			lblLink_1_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblLink_1_2.setForeground(new Color(0, 0, 153, 1));
			lblLink_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLink_1_2.setBounds(233, 27, 89, 39);
			panel_ayuda.add(lblLink_1_2);
			
			JPanel panel_comprobar_si_hay_actualizaciones = new JPanel();
			panel_comprobar_si_hay_actualizaciones.setLayout(null);
			panel_comprobar_si_hay_actualizaciones.setBounds(65, 266, 557, 77);
			panel_2.add(panel_comprobar_si_hay_actualizaciones);
			
			TitledBorder title_panel_comprobar_si_hay_actualizaciones; // Título en forma de borde
			title_panel_comprobar_si_hay_actualizaciones = BorderFactory.createTitledBorder("Ver historial de cambios");
			panel_comprobar_si_hay_actualizaciones.setBorder(title_panel_comprobar_si_hay_actualizaciones);
			
			JLabel lblLink_1_1_2 = new JLabel("<HTML><U>(Ver) </U></FONT></HTML>");
			lblLink_1_1_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lblLink_1_1_2.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(
								"https://practicasbyjmtm1.firebaseapp.com/proyectos/CreadorDeHorarios/CreadorDeHorarios.html#version_actual"));
					} catch (Exception er) {
						JOptionPane.showInternalMessageDialog(null, "Error ");
					}
				}
			});
			lblLink_1_1_2.setVerticalTextPosition(SwingConstants.CENTER);
			lblLink_1_1_2.setHorizontalTextPosition(SwingConstants.CENTER);
			lblLink_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblLink_1_1_2.setForeground(new Color(0, 0, 153, 1));
			lblLink_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLink_1_1_2.setBounds(175, 27, 211, 39);
			panel_comprobar_si_hay_actualizaciones.add(lblLink_1_1_2);

		}
		
		JLabel lbliconoDeReparacin = new JLabel("<HTML><U> (icono de reparación) </U></FONT></HTML>");
		lbliconoDeReparacin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lbliconoDeReparacin.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://www.flaticon.es/icono-gratis/reparando_252102?term=reparar&related_id=252102"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lbliconoDeReparacin.setHorizontalAlignment(SwingConstants.CENTER);
		lbliconoDeReparacin.setForeground(new Color(0, 0, 153, 1));
		lbliconoDeReparacin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbliconoDeReparacin.setBounds(35, 73, 144, 39);
		panel_iconos_1.add(lbliconoDeReparacin);
		
		JLabel lblhome = new JLabel("<HTML><U> (home) </U></FONT></HTML>");
		lblhome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblhome.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://www.flaticon.com/free-icon/home-page_2144?term=home&page=1&position=46&page=1&position=46&related_id=2144&origin=search"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lblhome.setHorizontalAlignment(SwingConstants.CENTER);
		lblhome.setForeground(new Color(0, 0, 153, 1));
		lblhome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblhome.setBounds(475, 23, 56, 39);
		panel_iconos_1.add(lblhome);
		
		JLabel lbladvertencia = new JLabel("<HTML><U> (advertencia) </U></FONT></HTML>");
		lbladvertencia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbladvertencia.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://www.flaticon.es/icono-gratis/advertencia_564619?term=advertencia&page=1&position=6&page=1&position=6&related_id=564619&origin=style"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lbladvertencia.setHorizontalAlignment(SwingConstants.CENTER);
		lbladvertencia.setForeground(new Color(0, 0, 153, 1));
		lbladvertencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbladvertencia.setBounds(225, 73, 96, 39);
		panel_iconos_1.add(lbladvertencia);
		
		JLabel lblloaderDeTipo = new JLabel("<HTML><U> (loader de tipo barra) </U></FONT></HTML>");
		lblloaderDeTipo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblloaderDeTipo.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://commons.wikimedia.org/wiki/File:Ajax-loader(2).gif"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lblloaderDeTipo.setHorizontalAlignment(SwingConstants.CENTER);
		lblloaderDeTipo.setForeground(new Color(0, 0, 153, 1));
		lblloaderDeTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblloaderDeTipo.setBounds(358, 73, 144, 39);
		panel_iconos_1.add(lblloaderDeTipo);
		
		JLabel lblloaderCircularMediano = new JLabel("<HTML><U> (loader circular mediano) </U></FONT></HTML>");
		lblloaderCircularMediano.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblloaderCircularMediano.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://commons.wikimedia.org/wiki/File:Animated_loading_half-circle.gif"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lblloaderCircularMediano.setHorizontalAlignment(SwingConstants.CENTER);
		lblloaderCircularMediano.setForeground(new Color(0, 0, 153, 1));
		lblloaderCircularMediano.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblloaderCircularMediano.setBounds(25, 123, 168, 39);
		panel_iconos_1.add(lblloaderCircularMediano);
		
		JLabel lblloaderCircularMini = new JLabel("<HTML><U> (loader circular mini) </U></FONT></HTML>");
		lblloaderCircularMini.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblloaderCircularMini.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://commons.wikimedia.org/wiki/File:Loading_hhh.gif"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lblloaderCircularMini.setHorizontalAlignment(SwingConstants.CENTER);
		lblloaderCircularMini.setForeground(new Color(0, 0, 153, 1));
		lblloaderCircularMini.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblloaderCircularMini.setBounds(225, 123, 144, 39);
		panel_iconos_1.add(lblloaderCircularMini);
		
		JLabel lblcandadoAbierto = new JLabel("<HTML><U> (candado abierto) </U></FONT></HTML>");
		lblcandadoAbierto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblcandadoAbierto.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://www.flaticon.es/icono-gratis/desbloquear_747315"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lblcandadoAbierto.setHorizontalAlignment(SwingConstants.CENTER);
		lblcandadoAbierto.setForeground(new Color(0, 0, 153, 1));
		lblcandadoAbierto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblcandadoAbierto.setBounds(387, 123, 136, 39);
		panel_iconos_1.add(lblcandadoAbierto);
		
		JLabel lblcandadoCerrado = new JLabel("<HTML><U> (candado cerrado) </U></FONT></HTML>");
		lblcandadoCerrado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblcandadoCerrado.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://www.flaticon.es/icono-gratis/candado_747305"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lblcandadoCerrado.setHorizontalAlignment(SwingConstants.CENTER);
		lblcandadoCerrado.setForeground(new Color(0, 0, 153, 1));
		lblcandadoCerrado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblcandadoCerrado.setBounds(43, 173, 136, 39);
		panel_iconos_1.add(lblcandadoCerrado);
		
		JLabel lblimpresora = new JLabel("<HTML><U> (impresora) </U></FONT></HTML>");
		lblimpresora.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblimpresora.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://www.flaticon.es/icono-gratis/impresora_3234829?term=impresora&related_id=3233468&origin=search"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lblimpresora.setHorizontalAlignment(SwingConstants.CENTER);
		lblimpresora.setForeground(new Color(0, 0, 153, 1));
		lblimpresora.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblimpresora.setBounds(225, 173, 83, 39);
		panel_iconos_1.add(lblimpresora);
		
		JLabel lbllinkedin = new JLabel("<HTML><U> (linkedin)</U></FONT></HTML>");
		lbllinkedin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbllinkedin.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://www.flaticon.es/icono-gratis/linkedin_1051333?term=linkedin&page=1&position=21&page=1&position=21&related_id=1051333&origin=style"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lbllinkedin.setHorizontalAlignment(SwingConstants.CENTER);
		lbllinkedin.setForeground(new Color(0, 0, 153, 1));
		lbllinkedin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbllinkedin.setBounds(345, 173, 68, 39);
		panel_iconos_1.add(lbllinkedin);
		
		JLabel lblgithub = new JLabel("<HTML><U> (github) </U></FONT></HTML>");
		lblgithub.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblgithub.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(
							"https://www.flaticon.es/icono-gratis/github_733609?term=github&page=1&position=1&page=1&position=1&related_id=733609&origin=search"));
				} catch (Exception er) {
					JOptionPane.showInternalMessageDialog(null, "Error ");
				}
			}
		});
		lblgithub.setHorizontalAlignment(SwingConstants.CENTER);
		lblgithub.setForeground(new Color(0, 0, 153, 1));
		lblgithub.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblgithub.setBounds(441, 173, 61, 39);
		panel_iconos_1.add(lblgithub);

	}
}
