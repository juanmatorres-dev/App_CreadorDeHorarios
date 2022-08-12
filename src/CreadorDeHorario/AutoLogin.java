package CreadorDeHorario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;

public class AutoLogin extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public void lanzarVentana() {
		try {
			AutoLogin dialog = new AutoLogin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AutoLogin() {
		setTitle("Por favor espera ...");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/calendario (1).png"));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		setBounds(100, 100, 352, 102);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("images/calendario (1).png"));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(252, 0, 84, 61);
		contentPanel.add(logo);
		
		JLabel loading = new JLabel("  Iniciando sesión ...");
		loading.setFont(new Font("Source Serif Pro Semibold", Font.PLAIN, 19));
		loading.setIcon(new ImageIcon("images/Loading.gif"));
		loading.setHorizontalAlignment(SwingConstants.CENTER);
		loading.setBounds(0, 0, 267, 61);
		contentPanel.add(loading);
	}
}
