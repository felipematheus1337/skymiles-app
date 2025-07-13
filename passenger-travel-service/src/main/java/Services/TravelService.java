package Services;

import Domain.Travel;
import Domain.dtos.TravelDTO;
import Domain.enums.TravelStatus;
import Exceptions.ResourceNotFoundException;
import Mapper.TravelMapper;
import Repositories.TravelRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

@ApplicationScoped
public class TravelService {

    private final TravelRepository travelRepository;
    private final TravelMapper travelMapper;

    @Inject
    public TravelService(TravelRepository travelRepository, TravelMapper travelMapper) {
        this.travelRepository = travelRepository;
        this.travelMapper = travelMapper;
    }

    @WithTransaction
    public Uni<Travel> create(TravelDTO travelDTO) {
        Travel entity = this.travelMapper.toEntity(travelDTO);
        entity.setDate(LocalDateTime.now());
        entity.setStatus(TravelStatus.PENDING);
        return travelRepository.persist(entity);
    }

    @WithSession
    public Uni<TravelDTO> findById(Long id) {
        return this.travelRepository.findById(id)
                .onItem()
                .ifNull()
                .failWith(new ResourceNotFoundException(Travel.class.getName() + "Not found"))
                .onItem()
                .transform(this.travelMapper::toDTO);
    }
}
