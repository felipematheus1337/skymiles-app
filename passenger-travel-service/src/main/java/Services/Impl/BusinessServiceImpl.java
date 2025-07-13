package Services.Impl;

import Domain.Passenger;
import Domain.dtos.PassengerDTO;
import Exceptions.ResourceNotFoundException;
import Mapper.PassengerMapper;
import Repositories.PassengerRepository;
import Repositories.TravelRepository;
import Services.BusinessService;
import Services.PassengerService;
import Services.TravelService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class BusinessServiceImpl implements BusinessService  {

    private final TravelService travelService;
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    @Inject
    public BusinessServiceImpl(TravelService travelService, PassengerService passengerService, PassengerMapper passengerMapper) {
        this.travelService = travelService;
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
    }

    @Override
    @WithSession
    public Uni<Void> addPassengerToTravel(Long id) {
        try {
            Uni<PassengerDTO> passengerDTO = this.passengerService.getById(id);
            var passengerEntity = passengerDTO
                    .onItem()
                    .ifNull()
                    .failWith(new ResourceNotFoundException("Passenger not found to add"))
                    .onItem()
                    .transformToUni(this::verifyPassenger)
                    .onItem()
                    .transform(this.passengerMapper::toEntity);


        } catch (RuntimeException ex) {
            ex.getStackTrace();
       }
       return Uni.createFrom().nullItem().replaceWithVoid();
    }

    private Uni<PassengerDTO> verifyPassenger(PassengerDTO item) {
        int comparableInteger = item.miles().compareTo(BigDecimal.ZERO);
        BigDecimal newMiles = BigDecimal.valueOf(50L);

        if (comparableInteger > 0) {
            // calling future API..
        }

       return Uni.createFrom().item(new PassengerDTO(item.name(), item.email(), newMiles));
    }


    @Override
    public Uni<Void> addPassengersInLote(List<Long> ids) {
       List<Uni<PassengerDTO>> unis = ids.stream()
               .map(passengerService::getById)
               .map(uni -> uni.flatMap(this::verifyPassenger))
               .toList();

       return Uni.combine().all()
               .unis(unis)
               .with(passengers -> {
                   return null;
               });
    }
}
