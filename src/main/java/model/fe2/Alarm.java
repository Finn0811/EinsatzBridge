package model.fe2;

/**
 * Created by Finn on 31.01.2020.
 */
public class Alarm {
    private String message;
    private String address;
    private String type;
    private String sender;
    private String timestamp;
    private AlarmData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public AlarmData getData() {
        return data;
    }

    public void setData(AlarmData data) {
        this.data = data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
