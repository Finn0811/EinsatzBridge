package utils;

import config.Config;
import config.address.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finn on 01.03.2020.
 */
public class AddressParser {
    private Config config;
    private List<String> units;
    private String resultAddress;
    private String resultMessage;

    public AddressParser(Config config, List<String> units, String defaultAddress, String defaultMessage) {
        this.config = config;
        this.units = units;
        this.resultAddress = defaultAddress;
        this.resultMessage = defaultMessage;
    }

    public void parse() {
        ArrayList<Mapping> mappings = config.addressMapping.mappings;

        mappings.forEach(mapping -> {
            List<String> mappedUnits = mapping.units;

            units.forEach(unit -> {
                if (mappedUnits.contains(unit)) {
                    setResultAddress(mapping.address);
                    setResultMessage(mapping.message);
                }
            });
        });
    }

    public String getResultAddress() {
        return resultAddress;
    }

    public void setResultAddress(String resultAddress) {
        this.resultAddress = resultAddress;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
