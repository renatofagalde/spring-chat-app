package br.com.likwi.chat.client;

import br.com.likwi.chat.model.HelloMessage;
import com.google.gson.Gson;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Power by Intellij IDEA
 * <p>
 * Version information (versionamento)
 * <p>
 * Date 10/04/18 - 19:35
 * <p>
 * author renatofagalde
 * <p>
 * What this class does renatofagalde Client websocket para atualizar a web
 *
 * https://github.com/jaysridhar/spring-websocket-client/blob/master/src/main/java/sample/Application.java
 **/
public class SendMessage {

	static public class MyStompSessionHandler extends StompSessionHandlerAdapter {

		private String userId;

		public MyStompSessionHandler(String userId) {
			this.userId = userId;
		}

		private void showHeaders(StompHeaders headers) {
			for (Map.Entry<String, List<String>> e : headers.entrySet()) {
				System.err.print("  " + e.getKey() + ": ");
				boolean first = true;
				for (String v : e.getValue()) {
					if (!first) {
						System.err.print(", ");
					}
					System.err.print(v);
					first = false;
				}
				System.err.println();
			}
		}

		private void sendJsonMessage(StompSession session) {


//			HelloMessage msg2 = new HelloMessage("teste via java client 2");
//			session.send("/broadcast/SALA", msg2);
//			session.send("/chat/SALA", msg2);

		}

		private void subscribeTopic(String topic, StompSession session) {

			session.subscribe(topic, new StompFrameHandler() {



				@Override
				public Type getPayloadType(StompHeaders headers) {
					return ServerMessage.class;
				}

				@Override
				public void handleFrame(StompHeaders headers, Object payload) {

					System.err.println(payload.toString());
				}
			});
		}

		@Override
		public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

//			System.err.println("Connected! Headers:");
//			showHeaders(connectedHeaders);
//
			subscribeTopic("/broadcast/SALA", session);

			sendJsonMessage(session);
		}
	}

	public static void main(String args[]) throws Exception {

		WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(simpleWebSocketClient));

		SockJsClient sockJsClient = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		String url = "ws://localhost:8080/likwi-chat";
		String userId = "spring-" + ThreadLocalRandom.current().nextInt(1, 99);
		StompSessionHandler sessionHandler = new MyStompSessionHandler(userId);
		StompSession session = stompClient.connect(url, sessionHandler).get();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


//		session.subscribe("/broadcast/SALA", new Greeting("broadcast direto"));



		for (; ; ) {
			System.out.print(userId + " >> ");
			System.out.flush();
			String line = in.readLine();
			if (line == null) {
				break;
			}
			if (line.length() == 0) {
				continue;
			}

			final HelloMessage helloMessage = new HelloMessage(line);
			Gson gson = new Gson();
			String helloGson = gson.toJson(helloMessage);

			System.out.println("helloGson = " + helloGson);

			session.send("/chat/SALA", helloMessage);

		}
	}
}
