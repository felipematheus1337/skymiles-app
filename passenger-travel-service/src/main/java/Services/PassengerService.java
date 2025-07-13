package Services;

import Domain.Passenger;
import Domain.dtos.PassengerDTO;
import Exceptions.ResourceNotFoundException;
import Mapper.PassengerMapper;
import Repositories.PassengerRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
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

    @WithTransaction
    public Uni<Passenger> create(PassengerDTO passengerDTO) {
       Passenger passenger = this.passengerMapper.toEntity(passengerDTO);
       return repository.persist(passenger);
    }

    @WithSession
    public Uni<PassengerDTO> getById(Long id) {
        return repository.findById(id)
                .onItem()
                .ifNull()
                .failWith(new ResourceNotFoundException(Passenger.class.getName() + "Not Found"))
                .onItem()
                .transform(this.passengerMapper::toDTO);

    }
}
