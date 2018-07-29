package com.gavynzhang.precomuptedtextviewtest;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gavynzhang.precomuptedtextviewtest.ui.NormalTextActivity;
import com.gavynzhang.precomuptedtextviewtest.ui.PrecomputedTextActivity;
import com.gavynzhang.precomuptedtextviewtest.util.FpsCalculator;
import com.gavynzhang.precomuptedtextviewtest.util.GhostThread;
import com.gavynzhang.precomuptedtextviewtest.util.TestSpan;

public class MainActivity extends AppCompatActivity {

    private Button normalEntranceBtn;
    private Button precomputedEntranceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        normalEntranceBtn = findViewById(R.id.normal_btn);
        precomputedEntranceBtn = findViewById(R.id.precomputed_btn);

        GhostThread.start();

        normalEntranceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NormalTextActivity.class);
                startActivity(intent);
            }
        });

        precomputedEntranceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PrecomputedTextActivity.class);
                startActivity(intent);
            }
        });

        FpsCalculator.instance().start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                TestSpan.init(MainActivity.this);
//                StaticLayoutManager.getInstance().initLayout(MainActivity.this, TestSpan.getSpanString(), TestSpan.getLongSpanString());


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "init span finish", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }
}
