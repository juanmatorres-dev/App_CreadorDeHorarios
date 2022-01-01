package CreadorDeHorario;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;

public class Update extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JButton update_button;
	public JButton later;
	public JLabel actual_version;
	public JLabel last_version;
	public JLabel downloading;
	public JProgressBar progressBar;
	public JButton reiniciar_y_actualizar;

	/**
	 * Launch the application.
	 */
	public void lanzarVentana() {
		try {
			Update dialog = new Update();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Update() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/calendario (1).png"));
		setTitle("¡Hay una actualización disponible!");
		setBounds(100, 100, 450, 243);
		BorderLayout borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			actual_version = new JLabel("Versión actual:   ");
			actual_version.setFont(new Font("Tahoma", Font.PLAIN, 17));
			actual_version.setHorizontalAlignment(SwingConstants.CENTER);
			actual_version.setBounds(10, 11, 414, 25);
			contentPanel.add(actual_version);
		}
		{
			last_version = new JLabel("Última versión:   ");
			last_version.setFont(new Font("Tahoma", Font.PLAIN, 17));
			last_version.setHorizontalAlignment(SwingConstants.CENTER);
			last_version.setBounds(10, 47, 414, 28);
			contentPanel.add(last_version);
		}
		
		downloading = new JLabel("Descargando actualización ...");
		downloading.setVisible(false);
		downloading.setHorizontalAlignment(SwingConstants.CENTER);
		downloading.setFont(new Font("Tahoma", Font.PLAIN, 17));
		downloading.setBounds(10, 95, 414, 28);
		contentPanel.add(downloading);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setForeground(new Color(34, 139, 34));
		progressBar.setIndeterminate(true);
		progressBar.setBounds(108, 134, 220, 16);
		contentPanel.add(progressBar);
		
		reiniciar_y_actualizar = new JButton("Reincia para actualizar");
		reiniciar_y_actualizar.setVisible(false);
		reiniciar_y_actualizar.setBounds(128, 95, 177, 23);
		contentPanel.add(reiniciar_y_actualizar);
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setVgap(10);
			fl_buttonPane.setHgap(50);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				update_button = new JButton("Actualizar");
				update_button.setActionCommand("OK");
				buttonPane.add(update_button);
				getRootPane().setDefaultButton(update_button);
			}
			{
				later = new JButton("Más tarde");
				later.setActionCommand("Cancel");
				buttonPane.add(later);
			}
		}
	}
}
