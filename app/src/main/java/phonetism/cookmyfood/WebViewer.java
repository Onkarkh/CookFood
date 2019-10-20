package phonetism.cookmyfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewer extends AppCompatActivity {
    private WebView webViewer;
    private ProgressBar progressBar;

    private String fetechedUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewer);
        webViewer = findViewById(R.id.webview);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        fetechedUrl = intent.getStringExtra("link");

        WebSettings webSettings = webViewer.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webViewer.setWebViewClient(new WebViewClient());

        webViewer.loadUrl(fetechedUrl);

        webViewer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(view.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(view.INVISIBLE);
                super.onPageFinished(view, url);
            }
        });
    }
}
