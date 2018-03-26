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


/*
	@MessageMapping("/hello")
	@SendTo("/topic/greetings/escutando")
	public Greeting greeting(HelloMessage message) throws Exception {

//		Thread.sleep(1000); // simulated delay

		return new Greeting("Hello, " + message.getName() + "!");
	}
*/

	//messageMapping é o que recebe
	@MessageMapping("/{sala}")

	//send to é quem está escutando
	@SendTo("/broadcast/{sala}")
	public Greeting greeting(HelloMessage message, @DestinationVariable String sala) throws Exception {


		Greeting greeting = new Greeting("Sala "+sala+": " + message.getName());
		this.template.convertAndSend("/"+sala, greeting);

		return greeting;
	}

}

