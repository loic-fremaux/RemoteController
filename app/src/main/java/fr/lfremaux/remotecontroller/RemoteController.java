package fr.lfremaux.remotecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import fr.lfremaux.remotecontroller.buttons.AbstractButton;
import fr.lfremaux.remotecontroller.helpers.Buttons;
import fr.lfremaux.remotecontroller.helpers.Files;

public class RemoteController extends AppCompatActivity {

    private List<AbstractButton<?>> buttons;

    private static RemoteController instance;

    @Override
    public void onCreate(Bundle bundle) {
        instance = this;
        super.onCreate(bundle);
        try {
            this.buttons = Buttons.getAllButtons(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        addInitialButtons();
    }

    public void runAddControllerActivity(View view) {
        final Intent intent = new Intent(this, AddControllerActivity.class);
        startActivity(intent);
    }

    public void addInitialButtons() {
        final LinearLayout vg = findViewById(R.id.background);
        buttons.forEach(button -> {
            final Button b = new Button(this);
            b.setText(button.getName());
            button.bindAction(b);
            vg.addView(b);
        });
    }

    public List<AbstractButton<?>> getButtons() {
        return buttons;
    }

    public static RemoteController getInstance() {
        return instance;
    }
}
