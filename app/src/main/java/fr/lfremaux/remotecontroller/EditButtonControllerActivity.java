package fr.lfremaux.remotecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import fr.lfremaux.remotecontroller.buttons.AbstractButton;

public class EditButtonControllerActivity extends Activity {

    private AbstractButton<?> button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_button_controller);

        this.button = (AbstractButton<?>) getIntent().getExtras().getSerializable("button");
    }

    public void submit(View view) {


        RemoteController.getInstance().saveButtons();
        RemoteController.getInstance().refreshButtons();
        finish();
    }

    public void delete(View view) {
        RemoteController.getInstance().getButtons().removeIf(b -> b.getUuid().equals(button.getUuid()));
        RemoteController.getInstance().saveButtons();
        RemoteController.getInstance().refreshButtons();
        finish();
    }
}