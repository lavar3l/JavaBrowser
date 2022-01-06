package com.browser.javabrowser.json.adapters.macro.step;

import java.util.Locale;

public class ScriptStepAdapter extends StepAdapter {
    @Override
    protected String getType(String type) {
        if (!type.equals("Script")) {
            String abstractSuperClass = type.substring(0, 1).toLowerCase(Locale.ROOT) + type.substring(1, type.length());
            type = abstractSuperClass + ".Script" + type;
        }

        return type;
    }
}
