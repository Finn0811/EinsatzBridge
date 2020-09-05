import config.Config;
import config.address.AddressMappingConfig;
import config.address.Mapping;
import org.junit.Before;
import org.junit.Test;
import utils.AddressParser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Finn on 03.09.2020.
 */
public class AddressParserTest {
    private final Config config = new Config();

    @Before
    public void loadConfig() {
        AddressMappingConfig addressMappingConfig = new AddressMappingConfig();

        addressMappingConfig.defaultAddress = "ff-beispiel";
        addressMappingConfig.defaultMessage = "Einsatzalarm FF-Beispiel";

        ArrayList<Mapping> mappings = new ArrayList<>();

        Mapping mapping = new Mapping();
        mapping.address = "fz-elw2";
        mapping.message = "Einsatzalarm ELW2";

        List<String> units = new LinkedList<>();
        units.add("ELW 2");
        units.add("FZ ELW2");
        mapping.units = units;

        mappings.add(mapping);
        addressMappingConfig.mappings = mappings;

        config.addressMapping = addressMappingConfig;
    }

    @Test
    public void testAddressParser() {
        List<String> vehicles = new LinkedList<>();
        vehicles.add("TE FF Testfeuerwehr voll");

        AddressParser addressParser = new AddressParser(this.config, vehicles, "ff-beispiel", "Einsatzalarm FF-Beispiel");
        addressParser.parse();

        assertEquals("ff-beispiel", addressParser.getResultAddress());
        assertEquals("Einsatzalarm FF-Beispiel", addressParser.getResultMessage());
    }

    @Test
    public void testAddressParserOther() {
        List<String> vehicles = new LinkedList<>();
        vehicles.add("TE KFB FZ ELW2");
        vehicles.add("TE 00-00-1 ELW 2 Kreisfeuerwehr");

        AddressParser addressParser = new AddressParser(this.config, vehicles, "ff-beispiel", "Einsatzalarm FF-Beispiel");
        addressParser.parse();

        assertEquals("fz-elw2", addressParser.getResultAddress());
        assertEquals("Einsatzalarm ELW2", addressParser.getResultMessage());
    }
}
