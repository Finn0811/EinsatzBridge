package model;

/**
 * Created by Finn on 29.01.2020.
 */
public class WebsocketFeedbackIdMessage {
    private String type;
    private FeedbackId feedbackId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FeedbackId getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(FeedbackId feedbackId) {
        this.feedbackId = feedbackId;
    }
}
