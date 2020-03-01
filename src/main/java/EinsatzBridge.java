import com.google.gson.Gson;
import io.undertow.Handlers;
import io.undertow.Undertow;
import model.FeedbackId;
import model.WebsocketFeedbackIdMessage;
import websocket.EinsatzWebsocketClient;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Finn on 24.01.2020.
 */
public class EinsatzBridge {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EinsatzBridge.class);

    private static EinsatzWebsocketClient einsatzWebsocketClient = null;

    public static void main(String[] args) throws URISyntaxException {
        ConfigManager configManager = new ConfigManager();
        configManager.saveDefaultConfig(EinsatzBridge.class.getResourceAsStream("config.yml"));
        configManager.loadConfig();

        if (configManager.getConfig().websocket.enabled) {
            logger.info("Verbinde mit Websocket...");
            einsatzWebsocketClient = new EinsatzWebsocketClient(new URI(configManager.getConfig().websocket.uri), configManager.getConfig());
            einsatzWebsocketClient.connect();
        }

        Undertow server = Undertow.builder()
                .setIoThreads(50)
                .addHttpListener(85, "127.0.0.1")
                .setHandler(Handlers.routing()
                        .get("/", exchange -> {
                            exchange.getResponseSender().send("EinsatzBridge v0.0.2-SNAPSHOT");
                        })
                        .get("/sendFeedbackID/", exchange -> {
                            if (exchange.getQueryParameters().containsKey("FE2dbId")) {
                                //  float id = Float.parseFloat(exchange.getQueryParameters().get("FE2dbId").getFirst());
                                String id = exchange.getQueryParameters().get("FE2dbId").getFirst();
                                logger.info("Sending feedback ID: " + id + " to WebSocket");

                                sendIdToWebsocket(id);

                                exchange.getResponseSender().send("Sent feedback ID " + id);
                            }
                            exchange.getResponseSender().send("Missing FE2dbId query parameter.");
                        })
                ).build();
        server.start();

        logger.info("EinsatzBridge erfolgreich gestartet...");
    }

    private static void sendIdToWebsocket(String id) {
        if (einsatzWebsocketClient == null) {
            return;
        }

        FeedbackId feedbackId = new FeedbackId(id);

        WebsocketFeedbackIdMessage websocketFeedbackIdMessage = new WebsocketFeedbackIdMessage();
        websocketFeedbackIdMessage.setType("new_feedbackId");
        websocketFeedbackIdMessage.setFeedbackId(feedbackId);

        String toSend = new Gson().toJson(websocketFeedbackIdMessage);
        logger.info("Sending: " + toSend + " to WebSocket");

        einsatzWebsocketClient.send(toSend);
    }
}
