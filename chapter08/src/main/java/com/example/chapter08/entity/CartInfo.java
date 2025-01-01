package com.example.chapter08.entity;

import java.util.List;

public class CartInfo {
    public int id;

    public int goodsId;

    public int count;

    public GoodsInfo goods;

    public CartInfo() {}

    public CartInfo(int id, int goodsId, int count, GoodsInfo goods) {
        this.id = id;
        this.goodsId = goodsId;
        this.count = count;
        this.goods = goods;
    }
}
