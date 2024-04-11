/**
 * 
 */
package CreadorDeHorario;

import CreadorDeHorario.Novedades.Novedades;
import CreadorDeHorario.VerMasIconos.MasIconos;
import CreadorDeHorario.vistas.FalloDeConexion;

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
		AutoLogin autoLogin = new AutoLogin();
		Novedades novedades = new Novedades();
		FalloDeConexion falloDeConexion = new FalloDeConexion();
		MasIconos masIconos = new MasIconos();
		Controlador controlador = new Controlador(vista , calendario , configuracion , sql, borrarFila, update, login, autoLogin, novedades, falloDeConexion, masIconos);
		
		
	}

}
