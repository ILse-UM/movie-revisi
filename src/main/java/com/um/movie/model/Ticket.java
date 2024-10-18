package com.um.movie.model;

public class Ticket {
    private String ticketNum;
    private String title;
    private double total;
    private String date;
    private String time;

    public Ticket(String ticketNum, String title, double total, String date, String time) {
        this.ticketNum = ticketNum;
        this.title = title;
        this.total = total;
        this.date = date;
        this.time = time;
    }

    // Getter and Setter methods
    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
