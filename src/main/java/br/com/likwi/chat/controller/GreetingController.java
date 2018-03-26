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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {


	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {

//		Thread.sleep(1000); // simulated delay

		return new Greeting("Hello, " + message.getName() + "!");
	}

}

