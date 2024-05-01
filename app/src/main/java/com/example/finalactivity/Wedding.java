package com.example.finalactivity;

import androidx.appcompat.app.AppCompatActivity;

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
    Button button;

    // Assume you have access to the user ID here
    String userId = "your_user_id"; // Replace "your_user_id" with the actual user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding);
        button = findViewById(R.id.btn_wedding);

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, item);
        autoCompleteTextView.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected time
                String selectedTime = autoCompleteTextView.getText().toString();

                // Check if a time is selected
                if (!selectedTime.isEmpty()) {
                    // Make a request to save the selected time to the database
                    saveSelectedTime(selectedTime);
                } else {
                    // Show an error message if no time is selected
                    Toast.makeText(Wedding.this, "Please select a time", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Inside saveSelectedTime() method
    private void saveSelectedTime(String selectedTime) {
        // Construct the URL for your server
        String url = "http://192.168.1.5/VisualGuro/wedding.php";

        // Use Volley to send a POST request to your server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display a success message if the data is saved successfully
                        Toast.makeText(getApplicationContext(), "Time saved successfully!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Display an error message if there is an error
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Set parameters for your POST request
                Map<String, String> params = new HashMap<>();
                params.put("schedule", selectedTime);
                params.put("userId", userId);
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }

}

