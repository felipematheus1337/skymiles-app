package Domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private BigDecimal miles = BigDecimal.ZERO;

    @ManyToMany
    @JoinTable(
            name = "passanger_travel",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "travel_id")
    )
    private List<Travel> travels;

    public Passenger(Long id, String name, String email, List<Travel> travels) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.travels = travels;
    }

    public Passenger() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Travel> getTravels() {
        if (this.travels == null) {
            this.travels = new ArrayList<>();
        }
        return travels;
    }

    public void setTravels(List<Travel> travels) {
        this.travels = travels;
    }

    public BigDecimal getMiles() {
        return miles;
    }

    public void setMiles(BigDecimal miles) {
        this.miles = miles;
    }
}
