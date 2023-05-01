package ru.vsu.cs.zmaev.model;

public class Leader {
    private String username;
    private long total;

    public Leader(String username, long total) {
        this.username = username;
        this.total = total;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
