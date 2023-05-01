package ru.vsu.cs.zmaev.model;

import java.io.Serializable;
import java.util.Map;

public class User implements Serializable {
    private String name;
    private long total;
    private Map<String, String> results;

    public User(String name, Map<String, String> results) {
        this.name = name;
        this.results = results;
    }

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public Map<String, String> getResults() {
        return results;
    }

    public void setResults(Map<String, String> results) {
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
