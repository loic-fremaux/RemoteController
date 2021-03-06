package fr.lfremaux.remotecontroller;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.lfremaux.remotecontroller.buttons.ButtonType;
import fr.lfremaux.remotecontroller.buttons.RequestType;
import fr.lfremaux.remotecontroller.buttons.impl.ApiButton;

public class AddControllerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_controller);
        final LinearLayout vg = findViewById(R.id.activity_add_controller);
        vg.removeView(findViewById(R.id.request_type_spinner));

        createTypeSelector();
    }

    private void createTypeSelector() {
        final LinearLayout vg = findViewById(R.id.activity_add_controller);

        final TextView spinnerTitle = new TextView(this);
        spinnerTitle.setPadding(50, 50, 0, 5);
        spinnerTitle.setText(R.string.request_type_title);
        spinnerTitle.setId(R.id.request_type_spinner_title);
        vg.addView(spinnerTitle, 1);

        final Spinner typeSpinner = new Spinner(this);
        final ArrayAdapter<CharSequence> elements = ArrayAdapter.createFromResource(this,
                R.array.type_spinner_values, android.R.layout.simple_spinner_item);
        elements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(elements);

        typeSpinner.setId(R.id.request_type_spinner);
        typeSpinner.setPadding(50, 0, 50, 0);
        typeSpinner.setEnabled(true);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vg.addView(typeSpinner, 2);
    }

    private void refreshType() {
        final LinearLayout vg = findViewById(R.id.activity_add_controller);
        List<Integer> removeList = new LinkedList<>();
        int i = 0;
        View view;
        while ((view = vg.getChildAt(i)) != null) {
            i++;
            if (view.getId() == R.id.request_type_spinner) continue;
            if (view.getId() == R.id.request_type_spinner_title) continue;
            if (view.getId() == R.id.create_controller_submit) continue;
            if (view.getId() == R.id.button_name_input) continue;

            removeList.add(i - 1);
        }

        List<Integer> list = new ArrayList<>();
        for (Integer integer : removeList) {
            list.add(integer);
        }
        Collections.sort(list, Collections.reverseOrder());
        removeList = list;
        for (Integer integer : removeList) {
            vg.removeViewAt(integer);
        }

        switch (getCurrentButtonType()) {
            case API_CALL: {
                final TextView textTitle = new TextView(this);
                textTitle.setPadding(50, 20, 0, 5);
                textTitle.setText(R.string.url_title);
                vg.addView(textTitle, vg.getChildCount() - 1);

                final EditText text = new EditText(this);
                text.setId(R.id.url_input);

//              text.setWidth((int) (getWindowManager().getDefaultDisplay().getWidth() * 0.8));
                text.setPadding(50, 0, 50, 20);
                text.setHint(R.string.url_hint);
                vg.addView(text, vg.getChildCount() - 1);

                final TextView spinnerTitle = new TextView(this);
                spinnerTitle.setPadding(50, 20, 0, 5);
                spinnerTitle.setText(R.string.request_method_title);
                vg.addView(spinnerTitle, vg.getChildCount() - 1);

                final Spinner typeSpinner = new Spinner(this);
                final ArrayAdapter<CharSequence> elements = ArrayAdapter.createFromResource(this,
                        R.array.request_type_spinner_values, android.R.layout.simple_spinner_item);
                elements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinner.setAdapter(elements);

                typeSpinner.setId(R.id.api_request_type_spinner);
                typeSpinner.setPadding(50, 0, 50, 0);
                typeSpinner.setEnabled(true);
                vg.addView(typeSpinner, vg.getChildCount() - 1);
                break;
            }
            default: {

            }
        }
    }

    public void submit(View view) {
        final ButtonType type = getCurrentButtonType();

        switch (type) {
            case API_CALL: {
                RemoteController.getInstance().getButtons().add(
                        new ApiButton(
                                getCurrentButtonName(),
                                UUID.randomUUID(),
                                getCurrentButtonType(),
                                getCurrentButtonUrl(),
                                getCurrentRequestType(),
                                ""
                        )
                );
                break;
            }
            default: {

            }
        }

        RemoteController.getInstance().saveButtons();
        RemoteController.getInstance().refreshButtons();
        finish();
    }

    private String getCurrentButtonName() {
        final EditText nameInput = findViewById(R.id.button_name_input);
        return nameInput.getText().toString();
    }

    private ButtonType getCurrentButtonType() {
        final Spinner typeSpinner = findViewById(R.id.request_type_spinner);
        final ButtonType type;
        switch (typeSpinner.getSelectedItem().toString()) {
            case "API call": {
                type = ButtonType.API_CALL;
                break;
            }
            case "Bluetooth request": {
                type = ButtonType.BLUETOOTH_REQUEST;
                break;
            }
            case "NFC tag": {
                type = ButtonType.NFC_TAG;
                break;
            }
            default: {
                type = ButtonType.API_CALL;
            }
        }
        return type;
    }

    private RequestType getCurrentRequestType() {
        final Spinner typeSpinner = findViewById(R.id.api_request_type_spinner);
        return RequestType.valueOf(typeSpinner.getSelectedItem().toString());
    }


    private String getCurrentButtonUrl() {
        final EditText nameInput = findViewById(R.id.url_input);
        return nameInput.getText().toString();
    }
}