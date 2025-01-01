package com.example.chapter08;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter08.entity.Planet;
import com.example.chapter08.adapter.PlentBaseAdapter;
import com.example.chapter08.util.Utils;

import java.util.List;

public class ListViewMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListView listView;
    List<Planet> planets;

    CheckBox ck_divider;
    CheckBox ck_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ck_divider = findViewById(R.id.ck_divider);
        ck_bg = findViewById(R.id.ck_bg);

        ck_divider.setOnClickListener(this);
        ck_bg.setOnClickListener(this);


        listView = findViewById(R.id.lv_listview);
        planets = Planet.getDefaultList();
        PlentBaseAdapter adapter = new PlentBaseAdapter(this, planets);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(this, "selected " + planets.get(position).name, Toast.LENGTH_SHORT).show();
                Log.d("tb", "selected " + planets.get(position).name);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ck_divider:
                if (ck_divider.isChecked()) {
                    Drawable drawable = getResources().getDrawable(R.color.purple_200, getTheme());
                    listView.setDivider(drawable);
                    listView.setDividerHeight(Utils.dip2px(this, 1));
                } else {
                    Drawable drawable = getResources().getDrawable(R.color.translate, getTheme());
                    listView.setDivider(drawable);
                    listView.setDividerHeight(Utils.dip2px(this, 0));
                }
                break;
            case R.id.ck_bg:
                if (ck_bg.isChecked()) {
                    listView.setSelector(R.drawable.list_selector);
                } else  {
                    Drawable drawable = getResources().getDrawable(R.color.translate, getTheme());
                    listView.setSelector(drawable);

                }
                break;

        }
    }
}