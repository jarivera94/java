package co.gov.igac.utilerias;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;

import co.gov.igac.dao.ManagerDAO;

public class Calendario {

	public static String parseDataTime(String date) {
		try {
			if (date != null) {
				Duration d1 = Duration.parse(date);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.SECOND, (int)d1.get(java.time.temporal.ChronoUnit.SECONDS));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String formatted = format.format(calendar.getTime());
				return formatted; 
			}
		} catch (Exception e) {
			System.err.println("ERROR PARSE -->");
		}	
		return null;
	}
	
	public  static String getTimeActivity(String activityname){
		String result = null;
		try {
			Calendar fecha = Calendar.getInstance();
			Date fechainicial = fecha.getTime();
			ManagerDAO managerdao = new ManagerDAO();
			int holidays = managerdao.getHolidays(activityname);
			Instant fechainicialI=fechainicial.toInstant();
			Instant fechafinal= null;
			
			while (holidays!=0){
				fechafinal=calculateWeekend(fecha,holidays);
				holidays =managerdao.countHolidays(formatoIsnatnt(fechainicialI),formatoIsnatnt(fechafinal));
				if (holidays>0){
					fechainicialI=null;
					fechainicialI= fechafinal;
					
					new Date();
					Date d = Date.from(fechainicialI);
					fecha = null;
					fecha = Calendar.getInstance();
					fecha.setTime(d);
					fechafinal =calculateWeekend(fecha,holidays);
				}
				holidays =managerdao.countHolidays(formatoIsnatnt(fechainicialI),formatoIsnatnt(fechafinal));
			}
			result = fechafinal.toString(); 
			return result;
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Tarea No encontrada");
		} catch (PersistenceException e) {
			throw new PersistenceException("ERROR de consulta BD");
		}
	}
	
	public static Instant calculateWeekend(Calendar fecha,int holidays ){
		for(int i=1;i<=holidays;){  
			//add date if that is between monday and friday
			fecha.add(Calendar.DATE, 1);
			if(fecha.get(Calendar.DAY_OF_WEEK)!= Calendar.SUNDAY && fecha.get(Calendar.DAY_OF_WEEK)!= Calendar.SATURDAY){    
				i++;
			}
		}
		
		Date fechaFaux=fecha.getTime();
		Instant fechaFinal=fechaFaux.toInstant();
		
		return fechaFinal;
	}
	
	
	public static String formatoIsnatnt(Instant fecha){
		Date myDate = Date.from(fecha);
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String formattedDate = formatter.format(myDate);
    	return formattedDate;
	}
}
