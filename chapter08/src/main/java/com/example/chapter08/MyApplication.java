package com.example.chapter08;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;


import com.example.chapter08.database.ShoppingDBHelper;
import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.util.FileUtil;
import com.example.chapter08.util.SharedPreferenceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {

    private static  MyApplication mApp;

    // 声明一个公共的信息映射对象，可当做全局变量使用
    public HashMap<String, String> infoMap = new HashMap<>();

    public  int goodsCount;


    public static MyApplication getInstance() {
        return  mApp;
    }

    // 在 app 启动时调用
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Log.d("tb", "MyApplication onCreate");


        initShoppingData();

    }

    public void initShoppingData() {
       boolean isFirst = SharedPreferenceUtils.getInstance(this).readBoolean("first", true);
        // 获取当前App的私有下载路径
        String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar;
        if (isFirst) {
           // 模拟网络图片下载
           List<GoodsInfo> list = GoodsInfo.getDefaultList();
           for (GoodsInfo info : list) {
              Bitmap bitmap = BitmapFactory.decodeResource(getResources(), info.pic);
              String path = directory + info.id + ".jpg";
              // 往存储卡保存商品图片
               FileUtil.saveImage(path, bitmap);
               // 回收位图对象
               bitmap.recycle();
               info.picPath = path;
           }
           // 打开数据库，把商品信息插入到表中
           ShoppingDBHelper dbHelper = ShoppingDBHelper.getInstance(this);
           dbHelper.openWriteLink();
           dbHelper.insertGoodsIngos(list);
           dbHelper.closeLink();
           // 把是否首次打开写入共享参数
           SharedPreferenceUtils.getInstance(this).writeBoolean("first", false);
       }
    }

    // 在 app 终止时调用
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("tb", "MyApplication onTerminate");

    }

    // 在设置改变时，调用
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("tb", "MyApplication onConfigurationChanged");

    }


}
