import config.Config;
import org.junit.Test;
import utils.AddressParser;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Finn on 03.09.2020.
 */
public class AddressParserTest {
    private Config config;

    private void loadConfig() {
        ConfigManager configManager = new ConfigManager();
        configManager.saveDefaultConfig(EinsatzBridge.class.getResourceAsStream("config.yml"));
        configManager.loadConfig();

        this.config = configManager.getConfig();
    }

    @Test
    public void testAddressParser() {
        this.loadConfig();

        List<String> vehicles = new LinkedList<>();
        vehicles.add("TE FF Testfeuerwehr voll");

        AddressParser addressParser = new AddressParser(this.config, vehicles, "ff-beispiel", "Einsatzalarm FF-Beispiel");
        addressParser.parse();

        assertEquals("ff-beispiel", addressParser.getResultAddress());
        assertEquals("Einsatzalarm FF-Beispiel", addressParser.getResultMessage());
    }

    @Test
    public void testAddressParserOther() {
        this.loadConfig();

        List<String> vehicles = new LinkedList<>();
        vehicles.add("TE KFB FZ ELW2");
        vehicles.add("TE 00-00-1 ELW 2 Kreisfeuerwehr");

        AddressParser addressParser = new AddressParser(this.config, vehicles, "ff-beispiel", "Einsatzalarm FF-Beispiel");
        addressParser.parse();

        assertEquals("fz-elw2", addressParser.getResultAddress());
        assertEquals("Einsatzalarm ELW2", addressParser.getResultMessage());
    }
}
