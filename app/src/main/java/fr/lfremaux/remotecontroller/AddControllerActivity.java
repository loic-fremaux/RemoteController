package fr.lfremaux.remotecontroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.wear.widget.BoxInsetLayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.stream.IntStream;

import fr.lfremaux.remotecontroller.buttons.ButtonType;
import fr.lfremaux.remotecontroller.buttons.RequestType;
import fr.lfremaux.remotecontroller.buttons.impl.ApiButton;

public class AddControllerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_controller);
        final LinearLayout vg = findViewById(R.id.activity_add_controller);
        vg.removeViewAt(0);

        createTypeSelector();
    }

    private void createTypeSelector() {
        final Spinner typeSpinner = new Spinner(this);
        final ArrayAdapter<CharSequence> elements = ArrayAdapter.createFromResource(this,
                R.array.type_spinner_values, android.R.layout.simple_spinner_item);
        elements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(elements);

        typeSpinner.setId(R.id.request_type_spinner);
        typeSpinner.setEnabled(true);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshType();
                System.out.println("refresh type");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final LinearLayout vg = findViewById(R.id.activity_add_controller);
        vg.addView(typeSpinner, 0);
    }

    private void refreshType() {
        final LinearLayout vg = findViewById(R.id.activity_add_controller);
        for (int i = 0; i < vg.getChildCount(); i++) {
            final View view = vg.getChildAt(i);
            if (view.getId() == R.id.request_type_spinner) continue;
            if (view.getId() == R.id.create_controller_submit) continue;

            vg.removeViewAt(i);
        }
    }

    public void submit(View view) {
        final Spinner typeSpinner = findViewById(R.id.request_type_spinner);
        System.out.println("selected str " + typeSpinner.getSelectedItem().toString());

        RemoteController.getInstance().getButtons().add(
                new ApiButton(
                        "testButton",
                        ButtonType.API_CALL,
                        "test.com",
                        RequestType.PUT,
                        "noargs"
                )
        );

        finish();
    }
}