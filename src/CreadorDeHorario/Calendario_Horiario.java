package CreadorDeHorario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Calendario_Horiario {
	private int dia;
	private int mes;
	private int anio;
	private int[] dias = {31,28,31,30,31,30,31,31,30,31,30,31};
	public String[] diasSemana = {"", "lunes","martes","miércoles","jueves","viernes","sábado","domingo"};
	private String[] meses = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	private Calendar d = GregorianCalendar.getInstance();
	
	
	/*
	 * Gets y Sets
	 */
	public String[] getDiasSemana() {
		return diasSemana;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}
	
	public int getMesEnNumero() {
		return mes;
	}
	

	// Constructores sin parámetros, con 1 parámetro (año), con 2 parámetros (mes, año) y con 3 parámetros (día, mes, año) 
	public Calendario_Horiario(){
		dia = d.get(Calendar.DATE);
		mes = d.get(Calendar.MONTH);
		anio = d.get(Calendar.YEAR);
	}

	public Calendario_Horiario(int anio){
		dia = 1;
		this.mes = 0;
		this.anio = anio;
	}
	
	public Calendario_Horiario(int mes, int anio){
		dia = 1;
		this.mes = mes - 1;
		this.anio = anio;
	}
	
	public Calendario_Horiario(int dia, int mes, int anio){
		this.dia = dia;
		this.mes = mes - 1;
		this.anio = anio;
	}
	
	public int getDiaSemana(int diaActual) { 			// devuelve el número de la semana de 1 a 7
		d.set(anio, mes, diaActual);
		int dia = 7 ;
		if (d.get(Calendar.DAY_OF_WEEK) != 1)			// por defecto el 1 corresponde al domingo
	        	dia = d.get(Calendar.DAY_OF_WEEK) - 1;	// si es domingo devuelve 7, el resto posición--
	    return dia;
	}

	public boolean bisiesto() {
		return (anio % 400 == 0)||((anio % 4 == 0) && (anio % 100 != 0));
	}
	
	public int getDia() {

	    return dia;
	}
	
	public String getMes() {

	    return meses[mes];
	}
	
	public int getAnio() {

	    return anio;
	}
	
	public int getDiaInicial() {

	    return getDiaSemana(1);
	}
	
	public int getDiasTotales() {
		int diasMes = 0;
		if ((mes == 1) && bisiesto())
				diasMes = 29;
		 else
				diasMes = dias[mes];
		return diasMes;
	}

	@Override
	public String toString() {
		return "Calendario_Horiario [dia=" + dia + ", mes=" + mes + ", anio=" + anio + ", dias=" + Arrays.toString(dias)
				+ ", diasSemana=" + Arrays.toString(diasSemana) + ", meses=" + Arrays.toString(meses) + ", d=" + d
				+ "]";
	}

	

	

	
}
