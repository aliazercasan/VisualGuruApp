package com.example.finalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText usernameEditText = findViewById(R.id.username);
        EditText newPasswordEditText = findViewById(R.id.new_password);
        Button changePasswordButton = findViewById(R.id.btn_change_password);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (newPasswordEditText.length() < 6) {
                    Toast.makeText(ForgotPassword.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }


                // Check if username or password fields are empty
                if (username.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ForgotPassword.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                // Hash the new password
                String hashedNewPassword = hashPassword(newPassword);

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.137.185/VisualGuro/update.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Success")) {
                                    Toast.makeText(ForgotPassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                } else if (response.equals("NoUsername")) {
                                    Toast.makeText(ForgotPassword.this, "Username not found", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgotPassword.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(ForgotPassword.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", username);
                        params.put("new_password", hashedNewPassword);
                        return params;
                    }
                };

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
// Method to hash the password using SHA-256 algorithm
        private String hashPassword (String password){
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = digest.digest(password.getBytes());
                StringBuilder stringBuilder = new StringBuilder();
                for (byte hashedByte : hashedBytes) {
                    stringBuilder.append(String.format("%02x", hashedByte));
                }
                return stringBuilder.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                // Handle exception
                return null;
            }
        }
    }