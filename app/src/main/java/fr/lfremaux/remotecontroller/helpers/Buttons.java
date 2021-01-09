package fr.lfremaux.remotecontroller.helpers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import fr.lfremaux.remotecontroller.buttons.AbstractButton;

public class Buttons {

    public static List<AbstractButton<?>> getAllButtons(Context context) throws JSONException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final JSONArray array = new JSONArray(Files.readData(
                context.getFilesDir().getAbsolutePath(),
                "buttons.json"
        ));

        final List<AbstractButton<?>> buttons = new LinkedList<>();
        for (int i = 0; i < array.length(); i++) {
            final JSONObject json = array.getJSONObject(i);
            final Class<? extends AbstractButton<?>> clazz = (Class<? extends AbstractButton<?>>) Class.forName(json.getString("typeId"));
            final AbstractButton<?> button = clazz.getConstructor().newInstance();
            buttons.add(button.fromJson(json));
        }

        return buttons;
    }

    public static void saveButtons(Context context, List<AbstractButton<?>> buttons) {
        final JSONArray array = new JSONArray();
        buttons.forEach(button -> {
            try {
                array.put(button.toJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        Files.writeData(
                context.getFilesDir().getAbsolutePath(),
                "buttons.json",
                array.toString()
        );
    }
}
