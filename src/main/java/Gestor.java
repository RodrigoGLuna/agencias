import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import static spark.Spark.*;

public class Gestor {

    public static void main(String[] args) {
        final AgenciaService agenciaService = new AgenciaServiceImpl();
        get("/agencias", ((request, response) -> {
            response.type("application/json");
            try {
                String site_id = request.queryParams("site_id");
                String payment_method_id = request.queryParams("payment_method_id");
                String latitud = request.queryParams("latitud");
                String longitud = request.queryParams("longitud");
                String criterio = request.queryParams("criterio");
                String limite = request.queryParams("limite");
                String radius = request.queryParams("radius");
                String offset = request.queryParams("offset");

                if (site_id == null || payment_method_id == null || latitud == null || longitud == null) {

                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Ni el sitio, ni el metodo de pago, ni latitud ni longitud pueden ser nulo"));
                }
                else {

                    String urlAgencias = "https://api.mercadolibre.com/sites/" + site_id + "/payment_methods/" + payment_method_id + "/agencies?near_to=" + latitud + "," + longitud;

                    if(radius!=null){
                        urlAgencias=urlAgencias.concat(","+radius);
                    }
                    if(limite!=null){
                        urlAgencias=urlAgencias.concat("&limit="+limite);
                    }
                    if(offset!=null){
                        urlAgencias=urlAgencias.concat("&offset="+offset);
                    }

                    if (criterio == null) {
                        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(agenciaService.getAgencias(urlAgencias))));
                    } else {
                        switch (criterio) {
                            case "ADDRESS_LINE":
                                Agency.criterio = Agency.Criterio.ADDRESS_LINE;
                                break;
                            case "AGENCY_CODE":
                                Agency.criterio = Agency.Criterio.AGENCY_CODE;
                                break;
                            case "DISTANCE":
                                Agency.criterio = Agency.Criterio.DISTANCE;
                                break;

                        }
                        Collection<Agency> agencies = agenciaService.getAgencias(urlAgencias);
                        Agency[] agencies1 = (Agency[]) agencies.toArray();
                        Operador.ordenarArray(agencies1);
                        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(agencies1)));
                    }
                }

            }
            catch (AgencyException a)
            {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,a.getMessage()));
            }
        }));

        get("/agencias/:zipcode",(((request, response) -> {

            response.type("application/json");
            try {
                String site_id = request.queryParams("site_id");
                String payment_method_id = request.queryParams("payment_method_id");
                String zipcode = request.params(":zipcode");
                String criterio = request.queryParams("criterio");
                String limite = request.queryParams("limite");
                String radius = request.queryParams("radius");
                String offset = request.queryParams("offset");
                ArrayList<String> corrdenadas= new ArrayList<String>();
                if (site_id == null || payment_method_id == null || zipcode == null) {

                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Ni el sitio, ni el metodo de pago, ni latitud ni longitud pueden ser nulo"));
                } else {

                    if (criterio == null) {
                        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(agenciaService.getCordenadas(zipcode))));
                    } else {
                        switch (criterio) {
                            case "ADDRESS_LINE":
                                Agency.criterio = Agency.Criterio.ADDRESS_LINE;
                                break;
                            case "AGENCY_CODE":
                                Agency.criterio = Agency.Criterio.AGENCY_CODE;
                                break;
                            case "DISTANCE":
                                Agency.criterio = Agency.Criterio.DISTANCE;
                                break;

                        }

                        return  null;
                    }
                }

            }
            catch (AgencyException a)
            {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,a.getMessage()));
            }

        })));
    }

}
