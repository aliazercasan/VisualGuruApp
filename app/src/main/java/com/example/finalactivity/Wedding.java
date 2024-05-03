package com.example.finalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Wedding extends AppCompatActivity {

    String[] item = {"9:00am - 11:00am", "1:00pm - 2:00pm", "3:00pm - 6:00pm", "6:30pm - 8:30pm"};

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    Button button, btn_back, btn_reviews; // Added initialization for btn_reviews

    // Assume you have access to the user ID here
    String userId = "your_user_id"; // Replace "your_user_id" with the actual user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding);
        button = findViewById(R.id.btn_wedding);
        btn_back = findViewById(R.id.btn_back);
        btn_reviews = findViewById(R.id.btn_reviews); // Initialize btn_reviews button

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, item);
        autoCompleteTextView.setAdapter(arrayAdapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });
        btn_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReviews(); // Call openReviews() when btn_reviews is clicked
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedTime = autoCompleteTextView.getText().toString();

                if (!selectedTime.isEmpty()) {
                    saveSelectedTime(selectedTime);
                } else {
                    Toast.makeText(Wedding.this, "Please select a time", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveSelectedTime(String selectedTime) {
        String url = "http://192.168.1.8/VisualGuro/wedding.php";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Time saved successfully!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("schedule", selectedTime);
                params.put("userId", userId);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void openDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void openReviews() {
        Intent intent = new Intent(this, Reviews.class);
        startActivity(intent);
    }
}
