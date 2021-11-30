package br.com.likwi.chat.config;

/**
 * Power by Intellij IDEA
 * <p>
 * Version information (versionamento)
 * <p>
 * Date 26/03/18 - 11:21
 * <p>
 * author renatofagalde
 * <p>
 * What this class does renatofagalde ?
 **/
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/broadcast");
		config.setApplicationDestinationPrefixes("/chat");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws").withSockJS();

		RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
//		registry.addEndpoint("/likwi-chat")
//				.withSockJS();

		registry.addEndpoint("/likwi-chat")
				.setAllowedOrigins("*")
				.setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
				.withSockJS();
	}
}
