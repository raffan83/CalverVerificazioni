package it.calverDesktopVER.utl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class Utility {

	public static String[] convertString(ArrayList<String> tables) {
		
		if(tables!=null && tables.size()>0)
		{
			String[] toRet = new String[tables.size()];
			
			for (int i = 0; i < toRet.length; i++) {
				toRet[i]=tables.get(i).toString();
			}
			return toRet;
		}
		
		return null;
	}

	public static Integer getNumber(String string) {
		
		Integer toRet=null;
		
		if(string!=null)
		{
			try 
			{
				toRet=Integer.parseInt(string);
			} 
			catch (NumberFormatException e) 
			{
				return null;
			}
		}
		
		return toRet;
	}

	public static boolean isNumber(String nPunti) {
		boolean flag=true;
		
		try 
		{
		 Integer.parseInt(nPunti);		
		} 
		catch (NumberFormatException e) 
		{
			return false;
		}
		
		
		return flag;
	}

	public static String toString(Object valueAt) {
	
		if(valueAt!=null)
		{
			return valueAt.toString();
		}
		return "";
	}

	public static String[] getListaTemperature(String um_fond) {
		
		String[] lista = new String[3];
		
		if(um_fond.equals("°C"))
		{
			lista[0]="Seleziona Parametro....";
			lista[1]="K @ Kelvin";
			lista[2]="°F @ Grado Fahrenehit";
		}
		if(um_fond.equals("°F"))
		{
			lista[0]="Seleziona Parametro....";
			lista[1]="K @ Kelvin";
			lista[2]="°C @ Grado Celsius";
		}
		if(um_fond.equals("K"))
		{
			lista[0]="Seleziona Parametro....";
			lista[1]="°F @ Grado Fahrenehit";
			lista[2]="°C @ Grado Celsius";
		}
		
		return lista;	
	}

	public static String getLabelUMConvertita(String umCombo, String fmCombo) {
	
		String labelReturn="";
		
		String fm=fmCombo.substring(fmCombo.indexOf("(")+1,fmCombo.indexOf(")"));
	//	if(fm.length()==2)
	//	{
	//		fm="";
	//	}
		labelReturn=fm+umCombo.split("@")[0];
		
		return labelReturn;
	}

	public static BigDecimal getRisoluzione(String _nCifre) {
		
		int nCifre=Integer.parseInt(_nCifre);
		
		if(nCifre>0)
		{
			
		
		String dec=""; 
		
		
		for (int i = 1; i < nCifre; i++) {
			dec=dec+"0";
		}
		
		return new BigDecimal("0."+dec+"1");
		
		}
		else
		{
			return BigDecimal.ONE;
		}
	}

	

	public static Object BigDecimalStp(BigDecimal value) {
		
		if(value!=null)
		{
			return value.toPlainString();
		}
		return null;
	}

	

	public static int getScale(BigDecimal incertezza) {
		
		if(incertezza.intValue() > 0)
		{
			return 2;
		}
		else
		{
			int scale = incertezza.scale();
			int precision = incertezza.precision();
			
			return Math.abs(scale-precision)+2;
		}	

	}
	public static String[] getListaTemperature() {
		
	String[] lista = new String[4];

			
			lista[0]="Seleziona Parametro....";
			lista[1]="°F @ Grado Fahrenehit";
			lista[2]="°C @ Grado Celsius";
			lista[3]="K @ Kelvin";
		
		
		return lista;	
		
		
	}

	public static String getTipoStrumento(int id_tipo_strumento) {
	
		if(id_tipo_strumento==1) 
		{
			return "Bilancia a campo singolo";
		}
		
		if(id_tipo_strumento==2) 
		{
			return "Bilancia a divisioni plurime";
		}
		
		if(id_tipo_strumento==3) 
		{
			return "Bilancia a campi multipli";
		}
		if(id_tipo_strumento==4) 
		{
			return "Semiautomatiche con masse a corredo esterno";
		}
		if(id_tipo_strumento==5) 
		{
			return "Semiautomatiche con masse a corredo interno";
		}
		
		
		return "NON DEFINITO";
	}

	public static String getDate(Date data) {
	
		if(data!=null) 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			return sdf.format(data);
		}
			
			else 
			{
				return "";
			}
		
	}

	public static boolean isDate(String date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try 
		{
			sdf.parse(date);
			return true;
		} catch (Exception e) 
		{
			return false;
		}
	}

	public static boolean isDouble(String text) {
		try 
		{
			Double.parseDouble(text);
			return true;
		} catch (Exception e) 
		{
			return false;
		}
	}
}
