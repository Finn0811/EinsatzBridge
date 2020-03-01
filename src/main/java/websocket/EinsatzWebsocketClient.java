package websocket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.Config;
import model.Einheit;
import model.Operation;
import model.Zusatzinfo;
import model.fe2.Alarm;
import model.fe2.AlarmData;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import utils.AddressParser;
import utils.EmojiParser;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Finn on 24.01.2020.
 */
public class EinsatzWebsocketClient extends WebSocketClient {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EinsatzWebsocketClient.class);
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();
    private Config config;

    public EinsatzWebsocketClient(URI serverUri, Config config) {
        super(serverUri);
        this.config = config;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.info("Opened WebSocket connection");
    }

    @Override
    public void onMessage(String message) {
        logger.info("Received: " + message);

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(message);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String type = jsonObject.get("type").getAsString();

        if (type.equals("new_einsatz")) {
            Gson gson = new Gson();
            Operation operation = gson.fromJson(jsonObject.get("einsatz").getAsString(), Operation.class);
            logger.info(operation);
            logger.info(operation.getStichwort());

            Alarm alarm = new Alarm();

            alarm.setSender("EinsatzBridge");
            alarm.setType("ALARM");

            AlarmData data = new AlarmData();
            data.setKeyword(operation.getStichwort());
            data.setKeyword_description(operation.getDescription());

            EmojiParser emojiParser = new EmojiParser(config, operation.getStichwort() + " " + operation.getDescription());
            emojiParser.parse();
            data.setKeyword_category(emojiParser.getResultIcon());
            data.setKeyword_color(emojiParser.getResultColor());

            data.setStreet(operation.getAdresse());
            data.setBuilding(operation.getObjekt());

            for (Zusatzinfo zusatzinfo : operation.getZusatzinfos()) {
                if (zusatzinfo.getName().equalsIgnoreCase("Einsatzort")) {
                    data.setLocation_additional(zusatzinfo.getValue());
                }

                if (zusatzinfo.getName().equalsIgnoreCase("Meldebild")) {
                    data.setKeyword_misc(zusatzinfo.getValue());
                }

                if (zusatzinfo.getName().equalsIgnoreCase("Eskalationsstufe")) {
                    data.setEscalation_additional(zusatzinfo.getValue());
                }
            }

            List<String> vehicles = new LinkedList<>();

            for (Einheit einheit : operation.getEinheiten()) {
                vehicles.add(einheit.getName());
            }

            AddressParser addressParser = new AddressParser(config, vehicles, "ff-ashausen", "Einsatzalarm FF-Ashausen");
            addressParser.parse();

            // Todo: Multi-Address switch to send multiple alarms to FE2 if more than one unit should be notified

            alarm.setMessage(addressParser.getResultMessage());
            alarm.setAddress(addressParser.getResultAddress());

            String newlineVehicles = StringUtils.join(vehicles, "\n");
            data.setVehicles(newlineVehicles);

            alarm.setData(data);

            String toSend = new Gson().toJson(alarm);
            logger.info("Sending " + toSend + " to FE2 Server");

            RequestBody body = RequestBody.create(JSON, toSend);
            long start = System.currentTimeMillis();
            String url = config.fe2.uri + "rest/external/http";
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(body).build();

            try (okhttp3.Response response = client.newCall(request).execute()) {
                long end = System.currentTimeMillis() - start;
                logger.info("[Status " + response.code() + "] Sent Alarm to FE2-Server; " + end + "ms");

                String respBody = response.body().string();
                logger.info(respBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
        new Thread(this::tryReconnect).start();
    }

    private void tryReconnect() {
        logger.info("Waiting 2000ms for reconnect");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Reconnecting now...");
        this.reconnect();
    }

    @Override
    public void onError(Exception e) {
        //e.printStackTrace();
        logger.error("Error connecting to WebSocket: " + e.getMessage());
    }
}
