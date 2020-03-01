package utils;

import config.Config;
import config.emoji.EmojiConfig;
import config.emoji.EmojiString;

import java.util.ArrayList;

/**
 * Created by Finn on 01.03.2020.
 */
public class EmojiParser {
    private Config config;
    private String text;
    private String resultIcon = "⚙";
    private String resultColor = "#4295f5";

    public EmojiParser(Config config, String text) {
        this.config = config;
        this.text = text;
    }

    public void parse() {
        ArrayList<EmojiConfig> emojiConfigs = config.emoji;

        emojiConfigs.forEach(emojiConfig -> {
            ArrayList<EmojiString> emojiStrings = emojiConfig.strings;

            emojiStrings.forEach(emojiString -> {
                if (emojiString.type.equalsIgnoreCase("startswith") && text.startsWith(emojiString.name)) {
                    setResultIcon(emojiConfig.icon);

                    if (!emojiConfig.color.equals("")) {
                        setResultColor(emojiConfig.color);
                    }
                }

                if (emojiString.type.equalsIgnoreCase("contains") && text.contains(emojiString.name)) {
                    setResultIcon(emojiConfig.icon);

                    if (!emojiConfig.color.equals("")) {
                        setResultColor(emojiConfig.color);
                    }
                }
            });
        });
    }

    public String getResultIcon() {
        return resultIcon;
    }

    public void setResultIcon(String resultIcon) {
        this.resultIcon = resultIcon;
    }

    public String getResultColor() {
        return resultColor;
    }

    public void setResultColor(String resultColor) {
        this.resultColor = resultColor;
    }
}
