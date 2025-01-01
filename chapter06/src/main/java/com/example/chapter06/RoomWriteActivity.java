package com.example.chapter06;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.Dao.BookDao;
import com.example.chapter06.entity.BookInfo;

import java.util.List;

public class RoomWriteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_name;
    EditText et_author;
    EditText et_press;
    EditText et_price;

    BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_write);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       et_name = findViewById(R.id.et_name);
       et_author = findViewById(R.id.et_author);
       et_press = findViewById(R.id.et_press);
       et_price = findViewById(R.id.et_price);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);

        // 从 APP 实例中获取唯一的书籍持久化对象
        bookDao = MyApplication.getInstance().getBookDB().bookDao();
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String press = et_press.getText().toString();
        String price = et_price.getText().toString();

        switch (v.getId()) {
            case R.id.btn_save:
                BookInfo b1 = new BookInfo();
                b1.setName(name);
                b1.setAuthor(author);
                b1.setPress(press);
                b1.setPrice(Double.parseDouble(price));
                bookDao.insert(b1);
                Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_delete:
                BookInfo b2 = new BookInfo();
                b2.setId(1);
                bookDao.delete(b2);
                Toast.makeText(this, "delete success", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_update:
                BookInfo b3 = new BookInfo();
                BookInfo b4 = bookDao.queryByName(name);
                b3.setId(b4.getId());
                b3.setName(name);
                b3.setAuthor(author);
                b3.setPress(press);
                b3.setPrice(Double.parseDouble(price));
                bookDao.update(b3);
                break;
            case R.id.btn_query:
                List<BookInfo> infoList = bookDao.queryAll();
                for (BookInfo book : infoList) {
                    Log.d("tb", book.toString());
                }
                break;
        }
    }
}