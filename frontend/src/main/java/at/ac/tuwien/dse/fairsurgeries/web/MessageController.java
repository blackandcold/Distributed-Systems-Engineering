package at.ac.tuwien.dse.fairsurgeries.web;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import at.ac.tuwien.dse.fairsurgeries.general.Constants;

public abstract class MessageController {

	public static void sendMessage(AmqpTemplate template, Constants.Queue queue, Object objectToSend) {
		SimpleMessageConverter messageConverter = new SimpleMessageConverter();
		MessageProperties properties = new MessageProperties();
		properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		Message message = messageConverter.toMessage(objectToSend, properties);
		
		template.send(queue.toString(), message);
	}

}
