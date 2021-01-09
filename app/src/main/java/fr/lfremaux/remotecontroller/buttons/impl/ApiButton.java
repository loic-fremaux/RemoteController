package fr.lfremaux.remotecontroller.buttons.impl;

import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import fr.lfremaux.remotecontroller.buttons.AbstractButton;
import fr.lfremaux.remotecontroller.buttons.ButtonType;
import fr.lfremaux.remotecontroller.buttons.RequestType;

public class ApiButton extends AbstractButton<ApiButton> {

    private String url;
    private RequestType requestType;
    private String args;

    public ApiButton() {
    }

    public ApiButton(String name, ButtonType type, String url, RequestType requestType, String args) {
        super(name, type);
        this.url = url;
        this.requestType = requestType;
        this.args = args;
    }

    public String getUrl() {
        return url;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getArgs() {
        return args;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("typeId", this.getClass().getName())
                .put("name", this.getName())
                .put("type", this.getType().name())
                .put("url", this.url)
                .put("requestType", this.requestType.name())
                .put("args", this.args);
    }

    @Override
    public ApiButton fromJson(JSONObject json) throws JSONException {
        return new ApiButton(
                json.getString("name"),
                ButtonType.valueOf(json.getString("type")),
                json.getString("url"),
                RequestType.valueOf(json.getString("requestType")),
                json.getString("args")
        );
    }

    @Override
    public void bindAction(Button button) {
        button.setOnClickListener(v -> {
            System.out.println("button clicked: " + url);
        });
    }
}
