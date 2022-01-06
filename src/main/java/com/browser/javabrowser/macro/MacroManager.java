package com.browser.javabrowser.macro;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MacroManager {
    private List<IMacro> macros;
    private final String path;

    public MacroManager(String path) {
        this.path = path;
    }

    private void traversePath() {
        File dir = new File(this.path);

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (f.getName().contains("script"))
                this.scriptMacros(f);

            if (f.getName().contains("robot"))
                this.robotMacros(f);
        }
    }

    private void scriptMacros(File f) {
        ScriptMacro macro = ScriptMacro.deserialize(f.getPath());
        this.macros.add(macro);
    }

    private void robotMacros(File f) {
        RobotMacro macro = RobotMacro.deserialize(f.getPath());
        this.macros.add(macro);
    }

    public void loadMacros() {
        this.macros = new LinkedList<>();
        this.traversePath();
    }

    public List<IMacro> getMacros() { return this.macros; }
}
