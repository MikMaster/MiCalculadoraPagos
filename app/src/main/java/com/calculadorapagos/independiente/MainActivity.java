package com.calculadorapagos.independiente;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/** Aplicación local independiente: no requiere permisos de red ni accede a cuentas bancarias. */
public final class MainActivity extends Activity {
    private WebView calculator;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(12, 16, 28));
        getWindow().setNavigationBarColor(Color.rgb(12, 16, 28));
        getWindow().getDecorView().setSystemUiVisibility(0);

        calculator = new WebView(this);
        calculator.setBackgroundColor(Color.rgb(12, 16, 28));
        calculator.getSettings().setJavaScriptEnabled(true);
        calculator.getSettings().setDomStorageEnabled(false);
        calculator.getSettings().setAllowFileAccess(false);
        calculator.getSettings().setAllowContentAccess(false);
        calculator.getSettings().setAllowFileAccessFromFileURLs(false);
        calculator.getSettings().setAllowUniversalAccessFromFileURLs(false);
        calculator.getSettings().setBlockNetworkLoads(true);
        calculator.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        calculator.getSettings().setSupportMultipleWindows(false);
        calculator.getSettings().setDefaultTextEncodingName("UTF-8");
        calculator.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true; // No navegar a páginas externas.
            }
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        // Puente solo de copia al portapapeles; la página HTML es local y no admite navegación.
        calculator.addJavascriptInterface(new ClipboardBridge(), "AndroidCopy");
        setContentView(calculator);
        try {
            String html = readAsset("index.html");
            calculator.loadDataWithBaseURL(
                "https://appassets.androidplatform.net/",
                html,
                "text/html",
                "UTF-8",
                null
            );
        } catch (Exception ex) {
            calculator.loadDataWithBaseURL(
                "https://appassets.androidplatform.net/",
                "<html><meta charset='UTF-8'><body><h3>Error al abrir la calculadora.</h3></body></html>",
                "text/html", "UTF-8", null
            );
        }
    }

    private String readAsset(String name) throws Exception {
        try (InputStream input = getAssets().open(name);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[4096];
            int count;
            while ((count = input.read(bytes)) != -1) output.write(bytes, 0, count);
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        }
    }

    public final class ClipboardBridge {
        @JavascriptInterface
        public void copyText(String text) {
            if (text == null) return;
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                clipboard.setPrimaryClip(ClipData.newPlainText("Monto de recarga", text));
            }
        }
    }

    @Override protected void onDestroy() {
        if (calculator != null) {
            calculator.destroy();
            calculator = null;
        }
        super.onDestroy();
    }
}
