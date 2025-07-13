package Services;

import Domain.dtos.PassengerDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public interface BusinessService {

    Uni<Void> addPassengerToTravel(Long passengerId, Long travelId);


}
