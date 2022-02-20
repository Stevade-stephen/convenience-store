package com.conveniencestore.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private String name;
    private List<Applicant> applicants;
    private List<Staff> staffs;
    private List<Transaction> transactions;
    private Map<Product, Integer> products;

    public Store(String name) {
        this.name = name;
        this.applicants = new ArrayList<>();
        this.staffs = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.products = new HashMap<>();
    }


    public Map<Product, Integer> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setName(String name) {
        this.name = name;
    }
}
