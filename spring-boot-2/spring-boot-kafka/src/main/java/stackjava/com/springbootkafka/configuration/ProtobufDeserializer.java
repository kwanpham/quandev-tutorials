package stackjava.com.springbootkafka.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

//@Slf4j
//public class ProtobufDeserializer implements Deserializer<KafkaMesageProtobuf> {
//
//	@Override
//	public KafkaMesageProtobuf deserialize(String s, byte[] bytes) {
//		try {
//			return KafkaMesageProtobuf.parseFrom(bytes);
//		} catch (Exception e) {
//			log.error(e.toString());
//			return null;
//		}
//	}
//}