package it.calverDesktopVER.bo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import it.calverDesktopVER.utl.Costanti;

	
public class VersionHTTPBO {




	    // http://localhost:8080/RESTfulExample/json/product/post
	    public static String checkVersion() {

	        URL url;
	        String ver = "";
	        try {
	            url = new URL("https://www.calver.it/version.do");

	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setReadTimeout(15000);
	            conn.setConnectTimeout(15000);
	            conn.setRequestMethod("POST");
	            conn.setDoInput(true);
	            conn.setDoOutput(true);


	            OutputStream os = conn.getOutputStream();
	            BufferedWriter writer = new BufferedWriter(
	                    new OutputStreamWriter(os, "UTF-8"));
	         //   writer.write(getPostDataString(postDataParams));

	            writer.flush();
	            writer.close();
	            os.close();
	            int responseCode=conn.getResponseCode();

	            if (responseCode == HttpsURLConnection.HTTP_OK) {
	                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
	                String output;
	    	        output = br.readLine(); 
	    	        JSONParser parser = new JSONParser(); 
	    			Object obj = parser.parse(output);
	    			JSONObject jsonObj = (JSONObject) obj;
	    			String version = (String) jsonObj.get("DASMTARVER");
	    	        
	    			if(!version.equals(Costanti.VERSION))
	    			
	    			{
	    				ver="Versioni differenti: "+"Disponibile ["+version+"] - In uso ["+Costanti.VERSION+"]";
	    				
	    			}
	    			else 
	    			{
	    				ver="OK_VER";
	    				
	    			}
	            }
	            else {
	            	System.out.println("Connection Error:"+responseCode);
	                ver=null;
	            }
	        }
	        catch (java.net.UnknownHostException uex) 
	        {
	        	System.out.println("connessione non disponibile");
	        	return null;
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }

	        return ver;
	    }
	    

	
}
