package me.free.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.free.example.edittext_1.RecyclerViewEditTextActivity;
import me.free.example.edittext_2.RecyclerViewEditTextActivity2;
import me.free.example.edittext_3.RecyclerViewEditTextActivity3;
import me.free.example.multi.MultiSelectActivity;
import me.free.example.single.SingleSelectActivity;
import me.free.example.top.RvTopActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.btn1).setOnClickListener(v -> {
            startActivity(new Intent(this, RvTopActivity.class));
        });

        findViewById(R.id.button1).setOnClickListener(v -> {
            startActivity(new Intent(this, SingleSelectActivity.class));
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            startActivity(new Intent(this, MultiSelectActivity.class));
        });

        findViewById(R.id.button3).setOnClickListener(v -> {
            startActivity(new Intent(this, RecyclerViewEditTextActivity.class));
        });

        findViewById(R.id.button4).setOnClickListener(v -> {
            startActivity(new Intent(this, RecyclerViewEditTextActivity2.class));
        });
        findViewById(R.id.button5).setOnClickListener(v -> {
            startActivity(new Intent(this, RecyclerViewEditTextActivity3.class));
        });
    }
}
