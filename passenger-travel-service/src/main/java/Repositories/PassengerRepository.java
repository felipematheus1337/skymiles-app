package Repositories;


import Domain.Passenger;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PassengerRepository implements PanacheRepository<Passenger> {
}
