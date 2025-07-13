package Resources;


import Domain.dtos.TravelDTO;
import Services.TravelService;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/travel")
public class TravelResource {

    private final TravelService travelService;

    @Inject
    public TravelResource(TravelService travelService) {
        this.travelService = travelService;
    }

    @POST
    @NonBlocking
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Uni<RestResponse<Void>> create(TravelDTO travelDTO, @Context UriInfo uriInfo) {
        return travelService.create(travelDTO)
                .replaceWith(RestResponse.created(uriInfo.getAbsolutePath()));
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<TravelDTO>> getById(Long id) {
        return travelService.findById(id)
                .onItem()
                .transform(RestResponse::ok);
    }


}
