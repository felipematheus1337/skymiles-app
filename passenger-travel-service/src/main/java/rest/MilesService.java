package rest;


import Domain.http.TravelHttp;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/miles-service")
@RegisterRestClient(configKey = "mp-rest")
public interface MilesService {


    // TO DO, ALTER THE CREATED PASSENGER TO ADDED IN THE MONGO MS
    @PUT
    Uni<TravelHttp> updateMiles(TravelHttp travel, Long passengerId);


}
