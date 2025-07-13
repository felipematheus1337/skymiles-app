package Services;

import Domain.Passenger;
import Domain.dtos.PassengerDTO;
import Mapper.PassengerMapper;
import Repositories.PassengerRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PassengerService {

    private final PassengerRepository repository;
    private final PassengerMapper passengerMapper;

    @Inject
    public PassengerService(PassengerRepository repository, PassengerMapper passengerMapper) {
        this.repository = repository;
        this.passengerMapper = passengerMapper;
    }

    public Uni<Passenger> create(PassengerDTO passengerDTO) {
       Passenger passenger = this.passengerMapper.toEntity(passengerDTO);
       return repository.persist(passenger);
    }
}
