package CreadorDeHorario.vistas;

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

public class FalloDeConexion extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public void lanzarVentana() {
		try {
			FalloDeConexion dialog = new FalloDeConexion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FalloDeConexion() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setTitle("Se ha producido un error");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/calendario (1).png"));
		setResizable(false);
		setAlwaysOnTop(true);
		setBounds(100, 100, 508, 102);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("images/calendario (1).png"));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(407, 0, 84, 61);
		contentPanel.add(logo);
		
		JLabel loading = new JLabel("  No se ha podido conectar con el servidor");
		loading.setFont(new Font("Source Serif Pro Semibold", Font.PLAIN, 19));
		loading.setIcon(new ImageIcon("images/no_conectado_(32x32).png"));
		loading.setHorizontalAlignment(SwingConstants.CENTER);
		loading.setBounds(0, 0, 419, 61);
		contentPanel.add(loading);
	}
}
