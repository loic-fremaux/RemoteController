package fr.lfremaux.remotecontroller.buttons;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

import fr.lfremaux.remotecontroller.EditButtonControllerActivity;
import fr.lfremaux.remotecontroller.RemoteController;

public abstract class AbstractButton<T extends AbstractButton<T>> implements Serializable {

    private String name;
    private UUID uuid;
    private ButtonType type;

    public AbstractButton() {
    }

    public AbstractButton(String name, ButtonType type) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.type = type;
    }

    public AbstractButton(String name, UUID uuid, ButtonType type) {
        this.name = name;
        this.uuid = uuid;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ButtonType getType() {
        return type;
    }

    public abstract JSONObject toJson() throws JSONException;

    public abstract T fromJson(JSONObject json) throws JSONException;

    public void bindAction(Button button) {
        button.setOnLongClickListener(v -> {
            System.out.println("long click");

            final Intent intent = new Intent(button.getContext(), EditButtonControllerActivity.class);
            final Bundle bundle = new Bundle();
            bundle.putSerializable("button", this);
            intent.putExtras(bundle);
            RemoteController.getInstance().startActivity(intent);

            return true;
        });
    }
}
