package Domain;

import Domain.enums.TravelStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Travel {

    private Long id;

    private String destination;

    private LocalDateTime date;

    private TravelStatus status;

    @ManyToMany(mappedBy = "travels")
    private List<Passenger> passengers;

    public Travel() {
    }

    public Travel(Long id, String destination, LocalDateTime date, TravelStatus status, List<Passenger> passengers) {
        this.id = id;
        this.destination = destination;
        this.date = date;
        this.status = status;
        this.passengers = passengers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TravelStatus getStatus() {
        return status;
    }

    public void setStatus(TravelStatus status) {
        this.status = status;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}
