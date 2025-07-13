package patterns;

import Domain.amqp.PassengerAMQP;
import Domain.dtos.PassengerDTO;
import Domain.dtos.TravelDTO;
import Domain.enums.TravelStatus;
import Domain.http.TravelHttp;
import Exceptions.ResourceNotFoundException;
import Mapper.TravelMapper;
import Services.PassengerService;
import Services.TravelService;
import amqp.KafkaNotificationSender;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import rest.MilesService;

import java.util.concurrent.atomic.AtomicReference;

@RequestScoped
public class TravelBusinessFacade {

    @Channel("travel-confirmation")
    private final Emitter<PassengerAMQP> producer;
    private final TravelService travelService;
    private final PassengerService passengerService;
    private final MilesService milesService;
    private final TravelMapper travelMapper;
    private final KafkaNotificationSender kafkaNotificationSender;

    @Inject
    public TravelBusinessFacade(Emitter<PassengerAMQP> producer,
                                TravelService travelService, PassengerService passengerService, MilesService milesService, TravelMapper travelMapper, KafkaNotificationSender kafkaNotificationSender) {
        this.producer = producer;
        this.travelService = travelService;
        this.passengerService = passengerService;
        this.milesService = milesService;
        this.travelMapper = travelMapper;
        this.kafkaNotificationSender = kafkaNotificationSender;
    }

    @WithTransaction
    public Uni<Void> add(Uni<TravelDTO> uniTravel, Uni<PassengerDTO> passengerDTO) {
        return Uni.combine()
                .all()
                .unis(uniTravel, passengerDTO)
                .asTuple()
                .flatMap(tuple -> {
                    TravelDTO travelDTO = tuple.getItem1();
                    PassengerDTO passenger = tuple.getItem2();

                    TravelHttp travelHttp = travelMapper.toHttp(travelDTO);
                    Long passengerId = passenger.id();
                    Long travelId = travelDTO.id();

                    return milesService.updateMiles(travelHttp, passengerId)
                            .onItem().ifNull().failWith(new ResourceNotFoundException("Travel not found"))

                            .flatMap(updatedTravelHttp ->
                                    travelService.updateTravelStatusAndFinalPrice(travelId, updatedTravelHttp.status(), updatedTravelHttp.finalPrice())
                                            .call(() -> kafkaNotificationSender.publish(passenger, updatedTravelHttp.status()))
                            )
                            .replaceWithVoid();
                });
    }
}
