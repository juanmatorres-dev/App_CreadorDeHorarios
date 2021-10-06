package CreadorDeHorario;

import java.awt.Component;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderCelda extends DefaultTableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		/*
		 * Centra los datos
		 * 
		 * https://es.stackoverflow.com/questions/369087/c%C3%B3mo-centrar-todos-los-datos-de-una-columna-en-un-jtable-java
		 * 
		 * https://www.lawebdelprogramador.com/foros/Java/1553980-alinear-datos-en-un-jtable.html
		 * 
		 */
		
		
		((JLabel) cell).setHorizontalAlignment(SwingConstants.CENTER);
		cell.setBackground(Color.RED);
		cell.setForeground(Color.WHITE);
		
		return cell;
	}
	
}
