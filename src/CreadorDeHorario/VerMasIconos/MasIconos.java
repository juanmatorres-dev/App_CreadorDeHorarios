package CreadorDeHorario.VerMasIconos;

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
import java.awt.ComponentOrientation;
import java.awt.Point;

public class MasIconos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private MasIconosText masIconosText = new MasIconosText();
	public JTextArea txtrA;
	/**
	 * Launch the application.
	 */
	public void lanzarVentana() {
		try {
			MasIconos dialog = new MasIconos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MasIconos() {
		setModal(true);
		setTitle("Más iconos utilizados en la aplicación");
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
		scrollPane.setAutoscrolls(true);
		panel_1.add(scrollPane);
		scrollPane.setToolTipText("");
		
		txtrA = new JTextArea();
		txtrA.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		txtrA.setText(masIconosText.masIconosText);
		txtrA.setWrapStyleWord(true);
		txtrA.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtrA.setMargin(new Insets(15, 15, 15, 15));
		txtrA.setLineWrap(true);
		txtrA.setEditable(false);
		txtrA.setSelectionStart(0); // Pone la barra de scroll al principio
		txtrA.setSelectionEnd(0);   // Pone la barra de scroll al principio
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
