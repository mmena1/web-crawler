package org.demo.webcrawler.entity;

public class NewsEntry {
    private int numberOfOrder;
    private String title;
    private int score;
    private int commentsAmount;

    public NewsEntry(int numberOfOrder,
                     String title,
                     int score,
                     int commentsAmount) {
        this.numberOfOrder = numberOfOrder;
        this.title = title;
        this.score = score;
        this.commentsAmount = commentsAmount;
    }

    public NewsEntry() {
    }

    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(int numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCommentsAmount() {
        return commentsAmount;
    }

    public void setCommentsAmount(int commentsAmount) {
        this.commentsAmount = commentsAmount;
    }
}
