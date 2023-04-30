package ru.vsu.cs.zmaev.model;

public class Result {
    String topicTitle;
    String result;

    public Result(String topicTitle, String result) {
        this.topicTitle = topicTitle;
        this.result = result;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
