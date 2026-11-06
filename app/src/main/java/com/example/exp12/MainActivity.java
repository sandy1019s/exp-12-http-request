package com.example.exp12;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Button btnGet, btnPost;
    private TextView btnClear, btnCopy;
    private LinearLayout layoutMetaBar, layoutEmptyState, layoutParsedResults, layoutRawJson;
    private TextView tvStatusBadge, tvMethodIndicator, tvResponseTime;
    private TextView tvUserId, tvPostId, tvTitle, tvBody, tvRawJson;
    private ProgressBar progressBar;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Views
        btnGet = findViewById(R.id.btnGet);
        btnPost = findViewById(R.id.btnPost);
        btnClear = findViewById(R.id.btnClear);
        btnCopy = findViewById(R.id.btnCopy);

        layoutMetaBar = findViewById(R.id.layoutMetaBar);
        layoutEmptyState = findViewById(R.id.layoutEmptyState);
        layoutParsedResults = findViewById(R.id.layoutParsedResults);
        layoutRawJson = findViewById(R.id.layoutRawJson);

        tvStatusBadge = findViewById(R.id.tvStatusBadge);
        tvMethodIndicator = findViewById(R.id.tvMethodIndicator);
        tvResponseTime = findViewById(R.id.tvResponseTime);

        tvUserId = findViewById(R.id.tvUserId);
        tvPostId = findViewById(R.id.tvPostId);
        tvTitle = findViewById(R.id.tvTitle);
        tvBody = findViewById(R.id.tvBody);
        tvRawJson = findViewById(R.id.tvRawJson);

        progressBar = findViewById(R.id.progressBar);

        // GET Request Click Listener
        btnGet.setOnClickListener(v -> executeGetRequest());

        // POST Request Click Listener
        btnPost.setOnClickListener(v -> executePostRequest());

        // Clear Response Click Listener
        btnClear.setOnClickListener(v -> resetResponseDashboard());

        // Copy Response Click Listener
        btnCopy.setOnClickListener(v -> {
            String jsonText = tvRawJson.getText().toString();
            if (!jsonText.isEmpty()) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("API Response", jsonText);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Response copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(boolean isLoading) {
        runOnUiThread(() -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
                layoutEmptyState.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void executeGetRequest() {
        showLoading(true);
        executorService.execute(() -> {
            long startTime = System.currentTimeMillis();
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                int responseCode = conn.getResponseCode();
                long responseTime = System.currentTimeMillis() - startTime;

                BufferedReader reader;
                if (responseCode >= 200 && responseCode < 300) {
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String jsonString = response.toString();
                JSONObject jsonObject = new JSONObject(jsonString);

                String userId = jsonObject.optString("userId", "1");
                String id = jsonObject.optString("id", "1");
                String title = jsonObject.optString("title", "No Title");
                String body = jsonObject.optString("body", "No Body");

                // Format JSON nicely with indentation
                String formattedJson = jsonObject.toString(2);

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    layoutMetaBar.setVisibility(View.VISIBLE);
                    layoutEmptyState.setVisibility(View.GONE);
                    layoutParsedResults.setVisibility(View.VISIBLE);
                    layoutRawJson.setVisibility(View.VISIBLE);

                    tvStatusBadge.setText(responseCode + " OK");
                    tvStatusBadge.setTextColor(getResources().getColor(R.color.success_green, null));
                    tvMethodIndicator.setText("GET");
                    tvMethodIndicator.setTextColor(getResources().getColor(R.color.badge_get_text, null));
                    tvResponseTime.setText(responseTime + " ms");

                    tvUserId.setText(userId);
                    tvPostId.setText(id);
                    tvTitle.setText(title);
                    tvBody.setText(body);
                    tvRawJson.setText(formattedJson);

                    Toast.makeText(MainActivity.this, "GET Request Successful!", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                long responseTime = System.currentTimeMillis() - startTime;
                String errorMsg = e.getMessage() != null ? e.getMessage() : "Network Error";
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    layoutMetaBar.setVisibility(View.VISIBLE);
                    layoutEmptyState.setVisibility(View.GONE);
                    layoutParsedResults.setVisibility(View.VISIBLE);
                    layoutRawJson.setVisibility(View.VISIBLE);

                    tvStatusBadge.setText("Error");
                    tvStatusBadge.setTextColor(getResources().getColor(R.color.error_red, null));
                    tvMethodIndicator.setText("GET");
                    tvResponseTime.setText(responseTime + " ms");

                    tvUserId.setText("-");
                    tvPostId.setText("-");
                    tvTitle.setText("Request Failed");
                    tvBody.setText(errorMsg);
                    tvRawJson.setText("{\n  \"error\": \"" + errorMsg + "\"\n}");

                    Toast.makeText(MainActivity.this, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void executePostRequest() {
        showLoading(true);
        executorService.execute(() -> {
            long startTime = System.currentTimeMillis();
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/posts");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                JSONObject payload = new JSONObject();
                payload.put("userId", 42);
                payload.put("title", "Web Service Explorer Studio");
                payload.put("body", "Experiment 12: Successfully executed live HTTP POST request via HttpURLConnection.");

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = payload.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                long responseTime = System.currentTimeMillis() - startTime;

                BufferedReader reader;
                if (responseCode >= 200 && responseCode < 300) {
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String jsonString = response.toString();
                JSONObject jsonObject = new JSONObject(jsonString);

                String userId = jsonObject.optString("userId", "42");
                String id = jsonObject.optString("id", "101");
                String title = jsonObject.optString("title", "Web Service Explorer Studio");
                String body = jsonObject.optString("body", "Experiment 12: Successfully executed live HTTP POST request via HttpURLConnection.");

                String formattedJson = jsonObject.toString(2);

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    layoutMetaBar.setVisibility(View.VISIBLE);
                    layoutEmptyState.setVisibility(View.GONE);
                    layoutParsedResults.setVisibility(View.VISIBLE);
                    layoutRawJson.setVisibility(View.VISIBLE);

                    tvStatusBadge.setText(responseCode + " Created");
                    tvStatusBadge.setTextColor(getResources().getColor(R.color.success_green, null));
                    tvMethodIndicator.setText("POST");
                    tvMethodIndicator.setTextColor(getResources().getColor(R.color.badge_post_text, null));
                    tvResponseTime.setText(responseTime + " ms");

                    tvUserId.setText(userId);
                    tvPostId.setText(id);
                    tvTitle.setText(title);
                    tvBody.setText(body);
                    tvRawJson.setText(formattedJson);

                    Toast.makeText(MainActivity.this, "POST Request Successful (201 Created)!", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                long responseTime = System.currentTimeMillis() - startTime;
                String errorMsg = e.getMessage() != null ? e.getMessage() : "Network Error";
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    layoutMetaBar.setVisibility(View.VISIBLE);
                    layoutEmptyState.setVisibility(View.GONE);
                    layoutParsedResults.setVisibility(View.VISIBLE);
                    layoutRawJson.setVisibility(View.VISIBLE);

                    tvStatusBadge.setText("Error");
                    tvStatusBadge.setTextColor(getResources().getColor(R.color.error_red, null));
                    tvMethodIndicator.setText("POST");
                    tvResponseTime.setText(responseTime + " ms");

                    tvUserId.setText("-");
                    tvPostId.setText("-");
                    tvTitle.setText("POST Request Failed");
                    tvBody.setText(errorMsg);
                    tvRawJson.setText("{\n  \"error\": \"" + errorMsg + "\"\n}");

                    Toast.makeText(MainActivity.this, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void resetResponseDashboard() {
        layoutMetaBar.setVisibility(View.GONE);
        layoutParsedResults.setVisibility(View.GONE);
        layoutRawJson.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        layoutEmptyState.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Dashboard cleared", Toast.LENGTH_SHORT).show();
    }
}