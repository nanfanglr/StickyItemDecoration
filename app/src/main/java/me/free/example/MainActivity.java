package me.free.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
    }
}
