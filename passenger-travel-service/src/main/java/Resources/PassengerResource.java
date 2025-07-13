package Resources;

import Domain.dtos.PassengerDTO;
import Services.PassengerService;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/passenger")
public class PassengerResource {

    private final PassengerService passengerService;

    @Inject
    PassengerResource(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @POST
    @NonBlocking
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Uni<RestResponse<Void>> create(PassengerDTO passengerDTO, @Context UriInfo uri) {
        return this.passengerService.create(passengerDTO)
                .replaceWith(RestResponse.created(uri.getAbsolutePath()));
    }
}
