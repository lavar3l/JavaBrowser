package com.browser.javabrowser.macro;

import com.browser.javabrowser.BrowserController;
import com.browser.javabrowser.json.adapters.macro.step.ScriptStepAdapter;
import com.browser.javabrowser.json.serializer.Serializer;
import com.browser.javabrowser.macro.step.IStep;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import javafx.concurrent.Worker.State;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

public class ScriptMacro extends Macro<StringBuilder> {
    public ScriptMacro() { super(); }

    public ScriptMacro(String name, Integer initialDelay, String url, LinkedList<IStep<StringBuilder>> list) {
        super(name, initialDelay, url, list);
    }

    @Override
    public void execute(BrowserController controller) {
        // create a script from steps
        StringBuilder medium = new StringBuilder();
        medium.append("setTimeout(() => {\n");

        for (IStep<StringBuilder> step : this.steps) {
            step.append(medium);
        }

        medium
                .append("\n}, ")
                .append(this.initialDelay)
                .append(");");

        WebEngine engine = controller.navigateActiveTab(url);

        // execute script on a proper thread, when the url loads
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue ov, State oldState, State newState) {
                if (newState != State.SUCCEEDED) return;

                engine.executeScript(medium.toString());
            }
        });
    }

    public static ScriptMacro deserialize(String path) {
        Serializer serializer = new Serializer();
        return serializer.deserialize(ScriptMacro.class, path, serializer.getCustomGson(IStep.class, new ScriptStepAdapter()));
    }
}
