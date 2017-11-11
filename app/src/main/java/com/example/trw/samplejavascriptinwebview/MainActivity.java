package com.example.trw.samplejavascriptinwebview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String URL = "https://trwxch4mp.github.io/index.html";
    private EditText editText;
    private Button buttonSendData;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et_message);
        buttonSendData = findViewById(R.id.btn_send_data);
        buttonSendData.setOnClickListener(this);

        setWebView();
    }

    private void setWebView() {
        webView = findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl(URL);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSendData) {
            String message = editText.getText().toString();
            if (!message.isEmpty()) {
                String jsString = ("javascript:setTextField(" +
                        "'" + message + "')");
                webView.loadUrl(jsString);
            }
        }
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(mContext
                    , "Hello WebView"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}
