package Repositories;

import Domain.Travel;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TravelRepository implements PanacheRepository<Travel> {
}
