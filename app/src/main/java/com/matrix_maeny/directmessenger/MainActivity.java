package com.matrix_maeny.directmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {


    private AppCompatButton sendBt;
    private EditText numberEt;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBt = findViewById(R.id.sendBt);
        numberEt = findViewById(R.id.numberEt);

        sendBt.setOnClickListener(sendBtListener);
    }

    View.OnClickListener sendBtListener = v -> {

        if (checkNumber()) {

            PackageManager packageManager = getPackageManager();

            try {
                packageManager.getPackageGids("com.whatsapp");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Please install whatsapp", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            try {
                String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode("The Matrix", "UTF-8");

                intent.setPackage("com.whatsapp");
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    private boolean checkNumber() {
        try {
            phoneNumber = numberEt.getText().toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Please enter valid number", Toast.LENGTH_SHORT).show();
            return false;

        }

        if (phoneNumber.equals("")) {
            Toast.makeText(this, "Please enter valid number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));

        return super.onOptionsItemSelected(item);
    }
}