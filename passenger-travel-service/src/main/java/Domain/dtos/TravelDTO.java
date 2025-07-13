package Domain.dtos;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;

public record TravelDTO(@Nullable Long id, String destination, BigDecimal basePrice) {
}
