package com.example.chapter06.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.chapter06.entity.CartInfo;
import com.example.chapter06.entity.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDBHelper extends SQLiteOpenHelper {
    private static  final String DB_NAME = "shooping.db";
    // 商品信息表
    private static  final String TABLE_GOODS_INFO = "goods_info";
    // 购物车信息表
    private static  final String TABLE_CART_INFO = "cart_info";
    private static final int DB_VERSION = 1;
    private static  ShoppingDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;


    public ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static ShoppingDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new ShoppingDBHelper(context);
        }
        return mHelper;
    }

    //打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    //打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    //关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_GOODS_INFO +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " description VARCHAR NOT NULL," +
                " price FLOAT NOT NULL," +
                " pic_path VARCHAR NOT NULL);";

        db.execSQL(sql);


        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CART_INFO +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " goods_id INTEGER NOT NULL," +
                " count INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<GoodsInfo> queryAllGoodsInfo() {
        String sql = "SELECT * from " + TABLE_GOODS_INFO;
        List<GoodsInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            GoodsInfo info = new GoodsInfo();
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
            list.add(info);
        }
        cursor.close();
        return list;
    }

    public void insertGoodsIngos(List<GoodsInfo> list) {
        // 插入多条记录，要么全部成功，要么全部失败
        try {
            mWDB.beginTransaction();
            for (GoodsInfo info : list) {
                ContentValues values = new ContentValues();
                values.put("name", info.name);
                values.put("description", info.description);
                values.put("price", info.price);
                values.put("pic_Path", info.picPath);
                mWDB.insert(TABLE_GOODS_INFO, null, values);
            }
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }


    public void insertCartInfo(int goodsId) {
        // 如果数据库有，则更新数量
        CartInfo cartInfo = queryCartByGoodId(goodsId);
        ContentValues values = new ContentValues();
        values.put("goods_id", goodsId);

        if (cartInfo == null) {
            values.put("count", 1);
            mWDB.insert(TABLE_CART_INFO, null, values);
        } else {// 如果数据库没有，则数据库插入
           values.put("_id", cartInfo.id);
           values.put("count", ++ cartInfo.count);
           mWDB.update(TABLE_CART_INFO, values, "_id=?", new String[]{String.valueOf(cartInfo.id)});
        }
    }

    public int queryCartInfoCount() {
        int count = 0;
        String sql = "select sum(count) from " + TABLE_CART_INFO;
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
       return count;
    }

    private CartInfo queryCartByGoodId(int goodsId) {
        String sql = "SELECT * from " + TABLE_CART_INFO + " WHERE goods_id = " + goodsId;
        Cursor cursor = mRDB.rawQuery(sql, null);
        CartInfo info = null;
        while (cursor.moveToNext()) {
            info = new CartInfo();
            info.id = cursor.getInt(0);
            info.goodsId = cursor.getInt(1);
            info.count = cursor.getInt(2);
        }
        cursor.close();
        return info;
    }

    // 查询购物车数据库中所有的商品记录
    public List<CartInfo> queryAllCartInfo() {
        List<CartInfo> list = new ArrayList<>();
       Cursor cursor = mRDB.query(TABLE_CART_INFO, null, null, null, null, null, null);
       while (cursor.moveToNext()) {
           CartInfo info = new CartInfo();
           info.id = cursor.getInt(0);
           info.goodsId = cursor.getInt(1);
           info.count = cursor.getInt(2);
           list.add(info);
       }
       return list;
    }

    public GoodsInfo queryGoodsInfoById(int goodsId) {
        GoodsInfo info = null; // 初始化为 null，防止返回默认值
        String sql = "SELECT * FROM " + TABLE_GOODS_INFO + " WHERE _id = ?";
        Cursor cursor = mRDB.rawQuery(sql, new String[]{String.valueOf(goodsId)});
        while (cursor.moveToNext()) {
            info = new GoodsInfo(); // 有结果时初始化对象
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
        }
        return info;
    }

    public void deleteCartInfoByGoodsId(int goodsId) {
        mWDB.delete(TABLE_CART_INFO, "goods_id=?", new String[]{String.valueOf(goodsId)});
    }

    public void deleteALlCartInfo() {
        mWDB.delete(TABLE_CART_INFO, "1=1", null);
    }
}
