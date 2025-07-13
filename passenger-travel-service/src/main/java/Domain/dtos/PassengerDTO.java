package Domain.dtos;


import jakarta.annotation.Nullable;

public record PassengerDTO(@Nullable Long id, String name, String email) {
}
