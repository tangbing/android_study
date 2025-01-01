package com.example.chapter06.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chapter06.entity.BookInfo;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(BookInfo... book);

    // 删除所有书籍信息
    @Query("DELETE FROM BookInfo")
    void deleteAll();

    @Delete
    void delete(BookInfo... book);


    @Update
    int update(BookInfo... book);

    // 查询所有书籍信息
    @Query("SELECT * FROM BookInfo")
    List<BookInfo> queryAll();

    // 根据书籍名称删除书籍
    @Query("SELECT * FROM bookinfo WHERE name = :name ORDER BY id DESC Limit 1")
    BookInfo queryByName(String name);

}
