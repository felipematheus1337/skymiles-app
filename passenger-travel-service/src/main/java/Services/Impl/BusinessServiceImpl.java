package Services.Impl;

import Domain.Passenger;
import Domain.dtos.PassengerDTO;
import Domain.dtos.TravelDTO;
import Exceptions.ResourceNotFoundException;
import Mapper.PassengerMapper;
import Repositories.PassengerRepository;
import Repositories.TravelRepository;
import Services.BusinessService;
import Services.PassengerService;
import Services.TravelService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import patterns.TravelBusinessFacade;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class BusinessServiceImpl implements BusinessService  {

    private final TravelService travelService;
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;
    private final TravelBusinessFacade travelBusinessFacade;

    @Inject
    public BusinessServiceImpl(TravelService travelService, PassengerService passengerService, PassengerMapper passengerMapper, TravelBusinessFacade travelBusinessFacade) {
        this.travelService = travelService;
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
        this.travelBusinessFacade = travelBusinessFacade;
    }

    @Override
    @WithTransaction
    public Uni<Void> addPassengerToTravel(Long id, Long travelId) {
        try {
            Uni<TravelDTO> uniTravel = this.travelService.findById(travelId);
            Uni<PassengerDTO> passengerDTO = this.passengerService.getById(id);
            var uniPassenger = passengerDTO
                    .onItem()
                    .ifNull()
                    .failWith(new ResourceNotFoundException("Passenger not found to add"))
                    .onItem()
                    .transform(this.passengerMapper::toEntity);

            return this.travelBusinessFacade.add(uniTravel, passengerDTO);


        } catch (RuntimeException ex) {
            ex.getStackTrace();
       }
       return Uni.createFrom().nullItem().replaceWithVoid();
    }


}
