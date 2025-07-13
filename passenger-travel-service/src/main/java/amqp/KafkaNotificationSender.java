package amqp;


import Domain.amqp.PassengerAMQP;
import Domain.dtos.PassengerDTO;
import Domain.enums.TravelStatus;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@RequestScoped
public class KafkaNotificationSender {

    private final Emitter<PassengerAMQP> publisher;

    public KafkaNotificationSender(Emitter<PassengerAMQP> publisher) {
        this.publisher = publisher;
    }

    public Uni<Void> publish(PassengerDTO passenger, TravelStatus status) {

        PassengerAMQP passengerAMQP = new PassengerAMQP(
                passenger.name(), passenger.email(), status.toString()
        );

        return Uni.createFrom()
                .completionStage(() -> this.publisher.send(passengerAMQP))
                .onFailure().invoke(t -> Log.error("Error sending to kafka", t))
                .replaceWithVoid();
    }
}
