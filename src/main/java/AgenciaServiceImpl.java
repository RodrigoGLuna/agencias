import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class AgenciaServiceImpl implements AgenciaService {
    @Override
    public Collection<Agency> getAgencias(String url) throws AgencyException {

        crearLog(url);
        String agenciasLeidas = null;
        try {
            agenciasLeidas = readUrl(url);
        } catch (IOException e) {
            throw new AgencyException("Ocurrio un error al traer las agencias");

        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(agenciasLeidas, JsonObject.class);
            Agency[] agencias= gson.fromJson((jsonObject.get("results")), Agency[].class);
            return Arrays.asList(agencias);

    }

    @Override
    public Collection<String> getCordenadas(String zipCode) throws AgencyException {
        String apiKey="AIzaSyBK4U6ddWKkrzDtVwwRpZm5Q4J0SYup6cM";
        String google=null;
        try {
           google = readUrl("https://maps.googleapis.com/maps/api/geocode/json?address="+zipCode+"&key="+apiKey);
        } catch (IOException e) {
            throw new AgencyException("Ocurrio un error al conectar con la api de google");
        }


        Respuesta respuesta= new Gson().fromJson(google,Respuesta.class);
        //Agency[] agencias= gson.fromJson((jsonObject.get("location")), Agency[].class);
        return null;

    }

    private static void crearLog(String url) throws AgencyException{
        File file = new File("./api.log");
        boolean append = true;

        try {

            if(!file.exists()){
                file.createNewFile();
                append=false;
                System.out.println("Archivo log creado");
            }

            Date fecha = new Date();
            String log =  "\n "+ fecha + " | " + url;

            FileWriter fileWriter = new FileWriter(file,append);
            fileWriter.write(log);
            fileWriter.flush();
            fileWriter.close();



        }

        catch(IOException e){
            throw new AgencyException("Ocurrio un error al crear el archivo");
        }

    }


    private static String readUrl(String urlString) throws IOException {
        BufferedReader reader=null;
        try {
            URL url= new URL(urlString);
            URLConnection urlConnection=url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0");
            reader= new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            StringBuffer buffer=new StringBuffer();
            int read=0;
            char [] chars=new char[1024];
            while ((read=reader.read(chars))!=-1){
                buffer.append(chars,0,read);
            }
            return buffer.toString();

        } finally {
            if (reader!=null){
                reader.close();
            }
        }


    }
}
