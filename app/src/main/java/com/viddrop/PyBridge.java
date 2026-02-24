package com.viddrop;

import android.content.Context;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class PyBridge {
    private final PyObject module;

    public PyBridge(Context context) {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python python = Python.getInstance();
        module = python.getModule("viddrop_bridge");
    }

    public String runCommand(String cmd) {
        return module.callAttr("run_command", cmd).toString();
    }

    public String download(String url, String outDir, String format) {
        return module.callAttr("download_video", url, outDir, format).toString();
    }
}
