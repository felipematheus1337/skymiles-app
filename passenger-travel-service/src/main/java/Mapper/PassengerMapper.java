package Mapper;

import Domain.Passenger;
import Domain.dtos.PassengerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi")
public interface PassengerMapper {

    Passenger toEntity(PassengerDTO dto);
    PassengerDTO toDTO(Passenger passenger);
}
