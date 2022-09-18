package CreadorDeHorario.Novedades;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import CreadorDeHorario.Version_App;

import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Novedades extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public Version_App version = new Version_App();
	public Novedades_text_v_9_0_0_beta novedades_text_v_9_0_0_beta = new Novedades_text_v_9_0_0_beta();
	/**
	 * Launch the application.
	 */
	public void lanzarVentana() {
		try {
			Novedades dialog = new Novedades();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Novedades() {
		setModal(true);
		setTitle("Novedades de esta versión (" + version.version + ")");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/calendario (1).png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(100, 100, 666, 307);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		contentPanel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		scrollPane.setAutoscrolls(true);
		scrollPane.setToolTipText("");
		
		JTextArea txtrA = new JTextArea();
		txtrA.setText(novedades_text_v_9_0_0_beta.novedades_text_v_9_0_0_beta);
		txtrA.setWrapStyleWord(true);
		txtrA.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtrA.setMargin(new Insets(15, 15, 15, 15));
		txtrA.setLineWrap(true);
		txtrA.setEditable(false);
		scrollPane.setViewportView(txtrA);
		
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.SOUTH);
		
		JButton btn_cerrar = new JButton("Cerrar");
		panel.add(btn_cerrar);
		btn_cerrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btn_cerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
}
