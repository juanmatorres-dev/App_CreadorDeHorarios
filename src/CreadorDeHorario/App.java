/**
 * 
 */
package CreadorDeHorario;

/**
 * @author Juan Manuel Torres Martínez
 *
 */
public class App {

	
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		
		Vista vista = new Vista();
		Calendario_Horiario calendario = new Calendario_Horiario();
		Configuracion configuracion = new Configuracion();
		BorrarFila borrarFila = new BorrarFila();
		MySQL_Operations sql = new MySQL_Operations();
		Update update = new Update();
		Login login = new Login();
		Controlador controlador = new Controlador(vista , calendario , configuracion , sql, borrarFila, update, login);
		
		
	}

}
