package com.example.endassignment.Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Item implements Serializable {
    Member member;

    private int itemCode;
    public Availability availability;
    private String title, author;
    private LocalDate lendindDate;
    private String lender;

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public Item(int itemCode, Availability availability, String title, String author) {
        this.availability = availability;
        this.itemCode = itemCode;
        this.title = title;
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLendindDate(LocalDate lendindDate) {
        this.lendindDate = lendindDate;
    }

    public LocalDate getLendindDate() {
        return lendindDate;
    }

    public int getItemCode() {
        return itemCode;
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
}
