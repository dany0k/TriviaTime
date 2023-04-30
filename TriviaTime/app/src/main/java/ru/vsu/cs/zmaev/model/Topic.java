package ru.vsu.cs.zmaev.model;

public class Topic {
    private int uid;
    private String topicName;

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
