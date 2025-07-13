package Mapper;

import Domain.Travel;
import Domain.dtos.TravelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi")
public interface TravelMapper {

    TravelDTO toDTO(Travel travel);

    Travel toEntity(TravelDTO travelDTO);
}
