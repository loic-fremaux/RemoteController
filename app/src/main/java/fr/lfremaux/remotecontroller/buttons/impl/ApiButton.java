package fr.lfremaux.remotecontroller.buttons.impl;

import android.widget.Button;

import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import fr.lfremaux.remotecontroller.RemoteController;
import fr.lfremaux.remotecontroller.buttons.AbstractButton;
import fr.lfremaux.remotecontroller.buttons.ButtonType;
import fr.lfremaux.remotecontroller.buttons.RequestType;

public class ApiButton extends AbstractButton<ApiButton> {

    private String url;
    private RequestType requestType;
    private String args;

    public ApiButton() {
    }

    public ApiButton(String name, UUID uuid, ButtonType type, String url, RequestType requestType, String args) {
        super(name, uuid, type);
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
                .put("uuid", this.getUuid())
                .put("type", this.getType().name())
                .put("url", this.url)
                .put("requestType", this.requestType.name())
                .put("args", this.args);
    }

    @Override
    public ApiButton fromJson(JSONObject json) throws JSONException {
        return new ApiButton(
                json.getString("name"),
                UUID.fromString(json.getString("uuid")),
                ButtonType.valueOf(json.getString("type")),
                json.getString("url"),
                RequestType.valueOf(json.getString("requestType")),
                json.getString("args")
        );
    }

    @Override
    public void bindAction(Button button) {
        super.bindAction(button);
        button.setOnClickListener(v -> {
            final StringRequest stringRequest = new StringRequest(getRequestType().getMethod(), url,
                    response -> {
                        System.out.println("resp: " + response);
                        Snackbar.make(
                                button,
                                response,
                                Snackbar.LENGTH_SHORT
                        ).show();
                    },
                    error -> {
                        System.out.println("request failed " + error.getMessage());
                        Snackbar.make(
                                button,
                                "Une erreur est survenue...\n" + error.getMessage(),
                                Snackbar.LENGTH_SHORT
                        ).show();
                    });

            RemoteController.getInstance().getRequestQueue().add(stringRequest);

            System.out.println("button clicked: " + url);
        });
    }
}
