package Resources;

import Services.BusinessService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

@Path("/business")
public class BusinessResource {

    private final BusinessService service;

    @Inject
    public BusinessResource(BusinessService service) {
        this.service = service;
    }
}
