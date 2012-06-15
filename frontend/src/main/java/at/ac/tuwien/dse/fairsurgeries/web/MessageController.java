package at.ac.tuwien.dse.fairsurgeries.web;

import java.io.Serializable;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import at.ac.tuwien.dse.fairsurgeries.general.Constants;

public abstract class MessageController {

	/**
	 * Posts a persistent message to the given queue 
	 * @param template the template used to posting the message
	 * @param queue the identifier of the queue to post to
	 * @param objectToSend the serializable object we post to the queue
	 */
	public static void sendMessage(AmqpTemplate template, Constants.Queue queue, Serializable objectToSend) {
		SimpleMessageConverter messageConverter = new SimpleMessageConverter();
		MessageProperties properties = new MessageProperties();
		properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		Message message = messageConverter.toMessage(objectToSend, properties);
		
		template.send(queue.toString(), message);
	}

}
