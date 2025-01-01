package com.example.chapter06.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chapter06.Dao.BookDao;
import com.example.chapter06.entity.BookInfo;

// entities 表示该数据库有那些表，version 表示该数据库的版本号
// exportSchema 表示是否导出数据库信息的 json 串，建议设置为 false，若设置为 true，还需要指定 json 文件路径
@Database(entities = {BookInfo.class}, version = 1, exportSchema = true)
public abstract class BookDatabase extends RoomDatabase {

    // 获取该数据库中某张表的持久化对象
    public abstract BookDao bookDao();

}
