package fr.lfremaux.remotecontroller.buttons;

import com.android.volley.Request;

public enum RequestType {

    POST("POST", Request.Method.POST),
    GET("GET", Request.Method.GET),
    PUT("PUT", Request.Method.PUT),
    DELETE("DELETE", Request.Method.DELETE);

    private final String type;
    private final int method;

    RequestType(String type, int method) {
        this.type = type;
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public int getMethod() {
        return method;
    }
}
