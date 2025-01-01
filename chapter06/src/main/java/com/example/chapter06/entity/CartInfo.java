package com.example.chapter06.entity;

public class CartInfo {
    public int id;

    public int goodsId;

    public int count;

    public CartInfo() {}

    public CartInfo(int id, int goodsId, int count) {
        this.id = id;
        this.goodsId = goodsId;
        this.count = count;
    }
}
