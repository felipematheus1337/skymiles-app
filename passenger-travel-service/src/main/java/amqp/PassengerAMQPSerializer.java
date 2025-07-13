package amqp;

import Domain.amqp.PassengerAMQP;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class PassengerAMQPSerializer implements Serializer<PassengerAMQP> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public byte[] serialize(String s, PassengerAMQP data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error Serializing to JSON", e);
        }
    }
}
