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
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.awt.ComponentOrientation;
import javax.swing.JInternalFrame;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.JCheckBox;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JTextField user_input;
	public JPasswordField password_input_login;
	public JTextField input_nombre_de_usuario;
	public JPasswordField input_password;
	public JPasswordField input_repeat_password;
	public JTextField input_email;
	public JLabel imagen_user;
	private JPanel registro;
	private JPanel login;
	private JPanel user_image;
	private JLabel lbl_Registrarse;
	public JButton btn_Registrarse;
	public JButton btn_login;
	public JInternalFrame internalFrame;
	public JComboBox comboBox_idioma;
	public JLabel completaTodosLosCamposLabel;
	public JLabel emailNoValido;
	public JLabel ojo_pass_register;
	public JTextField textField_pass_register;
	public JLabel ojo_repeat_pass_register;
	public JTextField textField_repeat_pass_register;
	private JPanel panel_ojo_login;
	public JTextField textField_pass_login;
	public JLabel ojo_pass_login;
	public JLabel usuarioNoValido;
	public JLabel notSamePass;
	public JPanel panel_ojo_pass;
	public JPanel panel_ojo_repeat_pass;
	public JLabel pass_valida;

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
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 810, 569);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		registro = new JPanel();
		registro.setBackground(Color.WHITE);
		registro.setVisible(false);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(347, 0, 447, 530);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		internalFrame = new JInternalFrame("");
		internalFrame.setFrameIcon(new ImageIcon("images/login/info_16px.png"));
		internalFrame.setBounds(0, 0, 447, 530);
		panel.add(internalFrame);
		internalFrame.setEnabled(false);
		internalFrame.getContentPane().setLayout(null);
		
		completaTodosLosCamposLabel = new JLabel("Debes completar todos los campos");
		completaTodosLosCamposLabel.setForeground(Color.RED);
		completaTodosLosCamposLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		completaTodosLosCamposLabel.setHorizontalAlignment(SwingConstants.CENTER);
		completaTodosLosCamposLabel.setBounds(0, 11, 264, 27);
		internalFrame.getContentPane().add(completaTodosLosCamposLabel);
		
		emailNoValido = new JLabel("<=  El email no tiene un formato válido");
		emailNoValido.setVisible(false);
		emailNoValido.setHorizontalAlignment(SwingConstants.LEFT);
		emailNoValido.setForeground(Color.RED);
		emailNoValido.setFont(new Font("Tahoma", Font.BOLD, 13));
		emailNoValido.setBounds(10, 140, 277, 27);
		internalFrame.getContentPane().add(emailNoValido);
		
		usuarioNoValido = new JLabel("<= El usuario debe tener entre 6 y 30 caracteres");
		usuarioNoValido.setVisible(false);
		usuarioNoValido.setFont(new Font("Tahoma", Font.BOLD, 13));
		usuarioNoValido.setForeground(Color.RED);
		usuarioNoValido.setBounds(10, 62, 345, 34);
		internalFrame.getContentPane().add(usuarioNoValido);
		
		pass_valida = new JLabel("<= La contraseña debe tener entre 8 y 30 caracteres");
		pass_valida.setVisible(false);
		pass_valida.setFont(new Font("Tahoma", Font.BOLD, 13));
		pass_valida.setForeground(Color.RED);
		pass_valida.setBounds(10, 228, 400, 34);
		internalFrame.getContentPane().add(pass_valida);
		
		notSamePass = new JLabel("<= Las contraseñas no coinciden");
		notSamePass.setVisible(false);
		notSamePass.setForeground(Color.RED);
		notSamePass.setFont(new Font("Tahoma", Font.BOLD, 13));
		notSamePass.setBounds(10, 228, 376, 111);
		internalFrame.getContentPane().add(notSamePass);
		registro.setBounds(0, 0, 350, 519);
		contentPanel.add(registro);
		registro.setLayout(null);
		
		textField_pass_register = new JTextField();
		textField_pass_register.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
		textField_pass_register.setVisible(false);
		
		textField_repeat_pass_register = new JTextField();
		textField_repeat_pass_register.setVisible(false);
		textField_repeat_pass_register.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textField_repeat_pass_register.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
		textField_repeat_pass_register.setBounds(10, 327, 275, 40);
		registro.add(textField_repeat_pass_register);
		textField_repeat_pass_register.setColumns(10);
		textField_pass_register.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textField_pass_register.setBounds(10, 251, 275, 40);
		registro.add(textField_pass_register);
		textField_pass_register.setColumns(10);
		
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
		input_nombre_de_usuario.setFont(new Font("Tahoma", Font.PLAIN, 17));
		input_nombre_de_usuario.setColumns(10);
		input_nombre_de_usuario.setBounds(10, 88, 314, 39);
		registro.add(input_nombre_de_usuario);
		
		input_password = new JPasswordField();
		input_password.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
		input_password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		input_password.setBounds(10, 251, 275, 40);
		registro.add(input_password);
		
		JLabel lb_repeat_password = new JLabel("Repetir Contraseña:");
		lb_repeat_password.setHorizontalAlignment(SwingConstants.CENTER);
		lb_repeat_password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lb_repeat_password.setBounds(10, 289, 314, 39);
		registro.add(lb_repeat_password);
		
		input_repeat_password = new JPasswordField();
		input_repeat_password.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
		input_repeat_password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		input_repeat_password.setBounds(10, 327, 275, 40);
		registro.add(input_repeat_password);
		
		input_email = new JTextField();
		input_email.setFont(new Font("Tahoma", Font.PLAIN, 17));
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
		
		comboBox_idioma = new JComboBox();
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
		
		panel_ojo_pass = new JPanel();
		panel_ojo_pass.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(122, 138, 153)));
		panel_ojo_pass.setBackground(Color.WHITE);
		panel_ojo_pass.setBounds(284, 251, 39, 40);
		registro.add(panel_ojo_pass);
		panel_ojo_pass.setLayout(null);
		
		ojo_pass_register = new JLabel("");
		ojo_pass_register.setHorizontalAlignment(SwingConstants.CENTER);
		ojo_pass_register.setIcon(new ImageIcon("images/login/no_visible_16px.png"));
		ojo_pass_register.setBounds(0, 0, 39, 40);
		panel_ojo_pass.add(ojo_pass_register);
		
		panel_ojo_repeat_pass = new JPanel();
		panel_ojo_repeat_pass.setLayout(null);
		panel_ojo_repeat_pass.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(122, 138, 153)));
		panel_ojo_repeat_pass.setBackground(Color.WHITE);
		panel_ojo_repeat_pass.setBounds(285, 327, 39, 40);
		registro.add(panel_ojo_repeat_pass);
		
		ojo_repeat_pass_register = new JLabel("");
		ojo_repeat_pass_register.setIcon(new ImageIcon("images/login/no_visible_16px.png"));
		ojo_repeat_pass_register.setHorizontalAlignment(SwingConstants.CENTER);
		ojo_repeat_pass_register.setBounds(0, 0, 39, 40);
		panel_ojo_repeat_pass.add(ojo_repeat_pass_register);
		
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
		user_lb.setBounds(10, 119, 314, 40);
		login.add(user_lb);
		user_lb.setHorizontalAlignment(SwingConstants.CENTER);
		user_lb.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel password_lb = new JLabel("Contraseña:");
		password_lb.setBounds(10, 212, 314, 39);
		login.add(password_lb);
		password_lb.setHorizontalAlignment(SwingConstants.CENTER);
		password_lb.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		user_input = new JTextField();
		user_input.setFont(new Font("Tahoma", Font.PLAIN, 17));
		user_input.setBounds(10, 162, 314, 39);
		login.add(user_input);
		user_input.setColumns(10);
		
		password_input_login = new JPasswordField();
		password_input_login.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
		password_input_login.setFont(new Font("Tahoma", Font.PLAIN, 17));
		password_input_login.setBounds(10, 248, 275, 40);
		login.add(password_input_login);
		
		btn_login = new JButton("Iniciar sesión");
		btn_login.setBounds(104, 322, 135, 40);
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
		lbl_Registrarse.setBounds(104, 386, 135, 40);
		login.add(lbl_Registrarse);
		
		panel_ojo_login = new JPanel();
		panel_ojo_login.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(122, 138, 153)));
		panel_ojo_login.setBackground(Color.WHITE);
		panel_ojo_login.setBounds(284, 248, 40, 40);
		login.add(panel_ojo_login);
		panel_ojo_login.setLayout(null);
		
		ojo_pass_login = new JLabel("");
		ojo_pass_login.setHorizontalAlignment(SwingConstants.CENTER);
		ojo_pass_login.setIcon(new ImageIcon("images/login/no_visible_16px.png"));
		ojo_pass_login.setBounds(0, 0, 40, 40);
		panel_ojo_login.add(ojo_pass_login);
		
		textField_pass_login = new JTextField();
		textField_pass_login.setBorder(new MatteBorder(1, 1, 1, 0, (Color) new Color(122, 138, 153)));
		textField_pass_login.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textField_pass_login.setBounds(10, 248, 275, 40);
		login.add(textField_pass_login);
		textField_pass_login.setColumns(10);
		
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
