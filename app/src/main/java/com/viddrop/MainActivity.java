package com.viddrop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private EditText urlInput;
    private EditText cmdInput;
    private TextView logView;
    private ExecutorService executor;
    private PyBridge pyBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlInput = findViewById(R.id.urlInput);
        cmdInput = findViewById(R.id.cmdInput);
        logView = findViewById(R.id.logView);
        Button downloadButton = findViewById(R.id.downloadButton);
        Button cmdButton = findViewById(R.id.cmdButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        executor = Executors.newSingleThreadExecutor();
        pyBridge = new PyBridge(this);

        preloadSharedUrl(getIntent());

        downloadButton.setOnClickListener(v -> startDownload());
        cmdButton.setOnClickListener(v -> runCommand());
        settingsButton.setOnClickListener(v ->
                Toast.makeText(this, "Settings can be added next", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        preloadSharedUrl(intent);
    }

    private void preloadSharedUrl(Intent intent) {
        if (intent == null) {
            return;
        }

        String shared = null;
        if (Intent.ACTION_SEND.equals(intent.getAction()) && "text/plain".equals(intent.getType())) {
            shared = intent.getStringExtra(Intent.EXTRA_TEXT);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction()) && intent.getData() != null) {
            shared = intent.getData().toString();
        }

        if (shared != null && !shared.isBlank()) {
            urlInput.setText(shared.trim());
            appendLog("Received shared URL");
        }
    }

    private void startDownload() {
        String url = urlInput.getText().toString().trim();
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            return;
        }

        appendLog("Starting download...");
        File movies = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File outDir = new File(movies, "VidDrop");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        executor.execute(() -> {
            String result = pyBridge.download(url, outDir.getAbsolutePath(), "bv*+ba/b");
            runOnUiThread(() -> appendLog(result));
        });
    }

    private void runCommand() {
        String cmd = cmdInput.getText().toString().trim();
        if (cmd.isEmpty()) {
            Toast.makeText(this, "Enter command: version | extractors | update", Toast.LENGTH_SHORT).show();
            return;
        }

        appendLog("> " + cmd);
        executor.execute(() -> {
            String result = pyBridge.runCommand(cmd);
            runOnUiThread(() -> appendLog(result));
        });
    }

    private void appendLog(String text) {
        String existing = logView.getText().toString();
        logView.setText(existing + "\n" + text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdownNow();
        }
    }
}
