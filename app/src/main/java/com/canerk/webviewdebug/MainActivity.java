/*
 * Copyright (c) 2018 Caner Kamburoglu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.canerk.webviewdebug;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        if (webView == null) return;
        outputTextView = findViewById(R.id.text_output);
        Button copyButton = findViewById(R.id.btn_copy);
        Button shareButton = findViewById(R.id.btn_share);
        Button changeButton = findViewById(R.id.btn_change);

        setData();

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOutputAction();
            }
        });
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyOutputAction();
            }

        });

        // WebView implementation selection is only available on Android API 24+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            changeButton.setVisibility(View.VISIBLE);
            changeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeActiveWebViewImplementation();
                }
            });
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setData();
    }

    private void setData() {
        String output = webView != null ? WebViewVersionHelper.getOutputText(this, webView.getSettings()) : "Can't load WebView";
        outputTextView.setText(output);
    }

    private void shareOutputAction() {
        Intent shareOutputIntent = new Intent(Intent.ACTION_SEND);
        shareOutputIntent.setType("text/plain");
        shareOutputIntent.putExtra(Intent.EXTRA_TEXT, outputTextView.getText().toString());
        startActivity(Intent.createChooser(shareOutputIntent, "Share the output"));
    }

    private void copyOutputAction() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copy the output", outputTextView.getText().toString());

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Copy failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeActiveWebViewImplementation() {
        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
        Toast.makeText(MainActivity.this, "Change the default WebView implementation under the developer options.",
                Toast.LENGTH_LONG).show();
    }
}