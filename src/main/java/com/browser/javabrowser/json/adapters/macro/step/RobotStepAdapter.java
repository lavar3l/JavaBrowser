package com.browser.javabrowser.json.adapters.macro.step;

import java.util.Locale;

public class RobotStepAdapter extends StepAdapter {
    @Override
    protected String getType(String type) {
        return switch (type) {
            case "BusyWaiting", "Timeout" -> this.commonSteps(type);
            default -> "robotStep." + type;
        };
    }

    private String commonSteps(String type) {
        String abstractSuperClass = type.substring(0, 1).toLowerCase(Locale.ROOT) + type.substring(1, type.length());
        return abstractSuperClass + ".Robot" + type;
    }
}
