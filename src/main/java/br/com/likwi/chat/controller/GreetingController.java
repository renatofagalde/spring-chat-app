package br.com.likwi.chat.controller;

/**
 * Power by Intellij IDEA
 * <p>
 * Version information (versionamento)
 * <p>
 * Date 26/03/18 - 11:17
 * <p>
 * author renatofagalde
 * <p>
 * What this class does renatofagalde ?
 **/

import br.com.likwi.chat.model.Greeting;
import br.com.likwi.chat.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	public GreetingController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/{sala}") //messageMapping é o que recebe
	@SendTo("/broadcast/{sala}") //send to é quem está escutando
	public Greeting greeting(HelloMessage message, @DestinationVariable String sala) throws Exception {


		sala = sala.replaceAll("\\s","-").trim().toUpperCase();

		Greeting greeting = new Greeting(sala+": "  + message.getName());
		this.template.convertAndSend("/"+sala, greeting);

		return greeting;
	}

}

