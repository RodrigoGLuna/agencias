import java.util.Collection;

public interface AgenciaService {
    Collection<Agency> getAgencias(String url) throws AgencyException;
    Collection<String> getCordenadas(String zipCode) throws AgencyException;
}
