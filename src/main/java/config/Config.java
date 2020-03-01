package config;

import config.address.AddressMappingConfig;
import config.emoji.EmojiConfig;

import java.util.ArrayList;

/**
 * Created by Finn on 24.01.2020.
 */
public class Config {
    public WebsocketConfig websocket;
    public Fe2Config fe2;
    public ArrayList<EmojiConfig> emoji;
    public AddressMappingConfig addressMapping;
}
