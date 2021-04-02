package com.semicolons.masco.pk.dataModels;

import java.util.ArrayList;

public class HomeDataModel {

    private ArrayList<Product> topSellingList = new ArrayList<>();
    private ArrayList<Product> latestList = new ArrayList<>();
    private ArrayList<Product> allProductList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();

    public ArrayList<Product> getTopSellingList() {
        return topSellingList;
    }

    public void setTopSellingList(ArrayList<Product> topSellingList) {
        this.topSellingList = topSellingList;
    }

    public ArrayList<Product> getLatestList() {
        return latestList;
    }

    public void setLatestList(ArrayList<Product> latestList) {
        this.latestList = latestList;
    }

    public ArrayList<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(ArrayList<String> titleList) {
        this.titleList = titleList;
    }

    public ArrayList<Product> getAllProductList() {
        return allProductList;
    }

    public void setAllProductList(ArrayList<Product> allProductList) {
        this.allProductList = allProductList;
    }
}
