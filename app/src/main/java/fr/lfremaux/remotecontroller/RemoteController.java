package fr.lfremaux.remotecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RemoteController extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
    }

    public void runAddControllerActivity(View view) {
        final Intent intent = new Intent(this, AddControllerActivity.class);
        startActivity(intent);
    }
}
