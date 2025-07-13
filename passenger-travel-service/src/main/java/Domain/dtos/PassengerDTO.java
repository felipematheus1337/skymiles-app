package Domain.dtos;

import java.math.BigDecimal;

public record PassengerDTO(String name, String email, BigDecimal miles) {
}
