package fr.lfremaux.remotecontroller.buttons;

public enum RequestType {

    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String type;

    RequestType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
