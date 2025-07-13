package Mapper;

import Domain.Travel;
import Domain.dtos.TravelDTO;
import Domain.http.TravelHttp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta-cdi")
public interface TravelMapper {

    TravelDTO toDTO(Travel travel);

    Travel toEntity(TravelDTO travelDTO);

    TravelHttp toHttp(TravelDTO dto);

    TravelDTO httpToDTO(TravelHttp http);
}
