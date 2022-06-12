package CreadorDeHorario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Dialog.ModalityType;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.awt.ComponentOrientation;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField user_input;
	private JPasswordField password_input;
	private JTextField input_nombre_de_usuario;
	private JPasswordField input_password;
	private JPasswordField input_repeat_password;
	private JTextField input_email;
	private JLabel imagen_user;
	private JPanel registro;
	private JPanel login;
	private JPanel user_image;
	private JLabel lbl_Registrarse;
	private JButton btn_Registrarse;

	/**
	 * Launch the application.
	 */
	public void lanzarVentana() {
		try {
			Login dialog = new Login();
			
			dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			dialog.setVisible(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login() {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 807, 569);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		registro = new JPanel();
		registro.setBackground(Color.WHITE);
		registro.setVisible(false);
		registro.setBounds(0, 0, 350, 519);
		contentPanel.add(registro);
		registro.setLayout(null);
		
		JLabel lb_nombre_de_usuario = new JLabel("Nombre de usuario: ");
		lb_nombre_de_usuario.setHorizontalAlignment(SwingConstants.CENTER);
		lb_nombre_de_usuario.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lb_nombre_de_usuario.setBounds(10, 50, 314, 40);
		registro.add(lb_nombre_de_usuario);
		
		JLabel lb_password = new JLabel("Contraseña:");
		lb_password.setHorizontalAlignment(SwingConstants.CENTER);
		lb_password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lb_password.setBounds(10, 214, 314, 39);
		registro.add(lb_password);
		
		input_nombre_de_usuario = new JTextField();
		input_nombre_de_usuario.setColumns(10);
		input_nombre_de_usuario.setBounds(10, 88, 314, 39);
		registro.add(input_nombre_de_usuario);
		
		input_password = new JPasswordField();
		input_password.setBounds(10, 251, 314, 40);
		registro.add(input_password);
		
		JLabel lb_repeat_password = new JLabel("Repetir Contraseña:");
		lb_repeat_password.setHorizontalAlignment(SwingConstants.CENTER);
		lb_repeat_password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lb_repeat_password.setBounds(10, 289, 314, 39);
		registro.add(lb_repeat_password);
		
		input_repeat_password = new JPasswordField();
		input_repeat_password.setBounds(10, 327, 314, 40);
		registro.add(input_repeat_password);
		
		input_email = new JTextField();
		input_email.setColumns(10);
		input_email.setBounds(10, 164, 314, 39);
		registro.add(input_email);
		
		JLabel lb_email = new JLabel("Email:");
		lb_email.setHorizontalAlignment(SwingConstants.CENTER);
		lb_email.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lb_email.setBounds(10, 126, 314, 40);
		registro.add(lb_email);
		
		JLabel lb_idioma = new JLabel("Idioma:");
		lb_idioma.setHorizontalAlignment(SwingConstants.CENTER);
		lb_idioma.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lb_idioma.setBounds(10, 378, 101, 39);
		registro.add(lb_idioma);
		
		JComboBox comboBox_idioma = new JComboBox();
		comboBox_idioma.setModel(new DefaultComboBoxModel(new String[] {"español [es]"}));
		comboBox_idioma.setBounds(10, 428, 113, 22);
		registro.add(comboBox_idioma);
		
		JLabel lb_imagen = new JLabel("Imagen:");
		lb_imagen.setHorizontalAlignment(SwingConstants.CENTER);
		lb_imagen.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lb_imagen.setBounds(211, 378, 101, 39);
		registro.add(lb_imagen);
		
		imagen_user = new JLabel("");
		imagen_user.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				imagen_user.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				registro.setVisible(false);
				user_image.setVisible(true);
				
			}
		});
		imagen_user.setHorizontalAlignment(SwingConstants.CENTER);
		imagen_user.setIcon(new ImageIcon("images/user/default/user_32_px.png"));
		imagen_user.setBounds(211, 418, 101, 32);
		registro.add(imagen_user);
		
		btn_Registrarse = new JButton("Registrarse");
		btn_Registrarse.setBounds(110, 468, 135, 40);
		registro.add(btn_Registrarse);
		
		JLabel atras_crear_cuenta = new JLabel("");
		atras_crear_cuenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				atras_crear_cuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				registro.setVisible(false);
				login.setVisible(true);
				
			}
		});
		atras_crear_cuenta.setIcon(new ImageIcon("images/flecha_atras.png"));
		atras_crear_cuenta.setBounds(20, 11, 32, 32);
		registro.add(atras_crear_cuenta);
		
		JLabel lblCrearCuenta = new JLabel("Crear cuenta");
		lblCrearCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		lblCrearCuenta.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrearCuenta.setBounds(0, 0, 346, 50);
		registro.add(lblCrearCuenta);
		
		login = new JPanel();
		login.setBackground(Color.WHITE);
		login.setBounds(0, 0, 346, 437);
		contentPanel.add(login);
		login.setLayout(null);
		{
			JLabel login_1 = new JLabel("Iniciar sesión");
			login_1.setBounds(0, 11, 346, 124);
			login.add(login_1);
			login_1.setFont(new Font("Tahoma", Font.BOLD, 14));
			login_1.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		JLabel user_lb = new JLabel("Usuario: ");
		user_lb.setBounds(21, 168, 101, 40);
		login.add(user_lb);
		user_lb.setHorizontalAlignment(SwingConstants.LEFT);
		user_lb.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel password_lb = new JLabel("Contraseña:");
		password_lb.setBounds(21, 236, 101, 39);
		login.add(password_lb);
		password_lb.setHorizontalAlignment(SwingConstants.LEFT);
		password_lb.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		user_input = new JTextField();
		user_input.setBounds(132, 172, 164, 39);
		login.add(user_input);
		user_input.setColumns(10);
		
		password_input = new JPasswordField();
		password_input.setBounds(132, 238, 164, 40);
		login.add(password_input);
		
		JButton btn_login = new JButton("Iniciar sesión");
		btn_login.setBounds(102, 310, 135, 40);
		login.add(btn_login);
		
		lbl_Registrarse = new JLabel("Crear una cuenta");
		lbl_Registrarse.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_Registrarse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_Registrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Font font = new Font("Tahoma", Font.BOLD, 13);
				Map attributes = font.getAttributes();
				attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				lbl_Registrarse.setFont(font.deriveFont(attributes));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				login.setVisible(false);
				registro.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Font font = new Font("Tahoma", Font.BOLD, 13);
				lbl_Registrarse.setFont(font);
			}
		});
		lbl_Registrarse.setForeground(Color.BLUE);
		lbl_Registrarse.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Registrarse.setBounds(102, 374, 135, 40);
		login.add(lbl_Registrarse);
		
		user_image = new JPanel();
		user_image.setBackground(Color.WHITE);
		user_image.setVisible(false);
		user_image.setLayout(null);
		user_image.setBounds(0, 0, 346, 437);
		contentPanel.add(user_image);
		
		JLabel hombre_reloj = new JLabel("");
		hombre_reloj.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hombre_reloj.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				user_image.setVisible(false);
				registro.setVisible(true);
				
				imagen_user.setIcon(new ImageIcon("images/user/watches/32_px/clock_man_32_px.png"));
			}
		});
		hombre_reloj.setIcon(new ImageIcon("images/user/watches/64_px/clock_man_64_px.png"));
		hombre_reloj.setBounds(134, 125, 64, 64);
		user_image.add(hombre_reloj);
		
		JLabel reloj_sin_numeros = new JLabel("");
		reloj_sin_numeros.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				reloj_sin_numeros.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				user_image.setVisible(false);
				registro.setVisible(true);
				
				imagen_user.setIcon(new ImageIcon("images/user/watches/32_px/wall-clock_32_px.png"));
			}
		});
		reloj_sin_numeros.setIcon(new ImageIcon("images/user/watches/64_px/wall-clock_64_px.png"));
		reloj_sin_numeros.setBounds(242, 125, 64, 64);
		user_image.add(reloj_sin_numeros);
		
		JLabel reloj_con_pendulo = new JLabel("");
		reloj_con_pendulo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				reloj_con_pendulo.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				user_image.setVisible(false);
				registro.setVisible(true);
				
				imagen_user.setIcon(new ImageIcon("images/user/watches/32_px/clock_32_px.png"));
			}
		});
		reloj_con_pendulo.setIcon(new ImageIcon("images/user/watches/64_px/clock_64_px.png"));
		reloj_con_pendulo.setBounds(32, 125, 64, 64);
		user_image.add(reloj_con_pendulo);
		
		JLabel atras_img_user = new JLabel("");
		atras_img_user.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				atras_img_user.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				user_image.setVisible(false);
				registro.setVisible(true);
			}
		});
		atras_img_user.setIcon(new ImageIcon("images/flecha_atras.png"));
		atras_img_user.setBounds(20, 11, 32, 32);
		user_image.add(atras_img_user);
		
		JLabel login_1_1 = new JLabel("Selecciona una imagen:");
		login_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		login_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		login_1_1.setBounds(0, 0, 346, 124);
		user_image.add(login_1_1);
		
		JPanel btns = new JPanel();
		btns.setBackground(Color.WHITE);
		btns.setBounds(0, 493, 346, 37);
		contentPanel.add(btns);
		btns.setLayout(null);
		{
			JLabel img = new JLabel("");
			img.setBounds(347, 0, 447, 500);
			img.setBackground(new Color(230, 230, 250));
			img.setIcon(new ImageIcon("images/Portada de lanzamiento Creador de horarios .png"));
			contentPanel.add(img);
		}
	}
}
