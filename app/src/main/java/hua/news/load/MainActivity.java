package hua.news.load;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hua.multilayout_core.MultiLayout;

public class MainActivity extends AppCompatActivity {

    private MultiLayout multiLayout;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //must call after setContentView
        multiLayout = MultiLayout.wrap(this);

        multiLayout.setOnClickListener(R.id.error, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadData();
            }
        });

        multiLayout.setOnClickListener(R.id.jump, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "jump baidu", Toast.LENGTH_SHORT).show();
                startLoadData();
            }
        });

        startLoadData();
    }

    private void startLoadData() {
        multiLayout.showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i == 0) {
                    multiLayout.showError();
                } else if (i == 1) {
                    multiLayout.showWithType(1);
                } else {
                    multiLayout.showContent();
                }
                i++;
            }
        }).start();
    }
}
