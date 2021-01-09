package fr.lfremaux.remotecontroller.buttons;

import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractButton<T extends AbstractButton<T>> {

    private String name;
    private ButtonType type;

    public AbstractButton() {
    }

    public AbstractButton(String name, ButtonType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ButtonType getType() {
        return type;
    }

    public abstract JSONObject toJson() throws JSONException;

    public abstract T fromJson(JSONObject json) throws JSONException;

    public abstract void bindAction(Button button);
}
