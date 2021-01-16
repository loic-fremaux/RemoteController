package fr.lfremaux.remotecontroller.buttons.impl;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Button;

import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import fr.lfremaux.remotecontroller.R;
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
                        final Snackbar snackbar = Snackbar.make(
                                button,
                                response,
                                Snackbar.LENGTH_SHORT
                        );

                        snackbar.setAction(R.string.copy, v1 -> {
                            System.out.println("clicked");
                        });
                    },
                    error -> {
                        System.out.println("request failed " + error.getMessage());

                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        error.printStackTrace(pw);
                        String sStackTrace = sw.toString();

                        final Snackbar snackbar = Snackbar.make(
                                button,
                                "Une erreur est survenue...\n" + sStackTrace,
                                Snackbar.LENGTH_LONG
                        );

                        snackbar.setAction(R.string.copy, v1 -> {
                            System.out.println("clicked");

                            ClipboardManager clipboard = (ClipboardManager) RemoteController.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("error", sStackTrace);
                            clipboard.setPrimaryClip(clip);
                        });

                        snackbar.show();

                    });

            RemoteController.getInstance().getRequestQueue().add(stringRequest);

            System.out.println("button clicked: " + url);
        });
    }
}
