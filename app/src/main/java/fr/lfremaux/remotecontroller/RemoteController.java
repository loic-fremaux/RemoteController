package fr.lfremaux.remotecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

import fr.lfremaux.remotecontroller.buttons.AbstractButton;
import fr.lfremaux.remotecontroller.helpers.Buttons;

public class RemoteController extends AppCompatActivity {

    private List<AbstractButton<?>> buttons;
    private RequestQueue requestQueue;

    private static RemoteController instance;

    @Override
    public void onCreate(Bundle bundle) {
        instance = this;
        super.onCreate(bundle);
        this.requestQueue = Volley.newRequestQueue(this);
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
        final LinearLayout vg = findViewById(R.id.buttons_container);
        for (AbstractButton<?> button : buttons) {
            final Button b = new Button(this);
            b.setText(button.getName());
            button.bindAction(b);
            vg.addView(b);
        }
    }

    public void refreshButtons() {
        final LinearLayout vg = findViewById(R.id.buttons_container);
        vg.removeAllViews();
        addInitialButtons();
    }

    public void saveButtons() {
        Buttons.saveButtons(getApplicationContext(), getButtons());
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public List<AbstractButton<?>> getButtons() {
        return buttons;
    }

    public static RemoteController getInstance() {
        return instance;
    }
}
