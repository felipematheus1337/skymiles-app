package Domain.http;

import Domain.enums.TravelStatus;

import java.math.BigDecimal;

public record TravelHttp(BigDecimal finalPrice, TravelStatus status) {
}
