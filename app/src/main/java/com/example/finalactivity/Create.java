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

public class Create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        EditText firstname = findViewById(R.id.firstname);
        EditText lastname = findViewById(R.id.lastname);
        EditText gender = findViewById(R.id.gender);
        EditText address = findViewById(R.id.address);
        EditText contactNumber = findViewById(R.id.contactNumber);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button btn_create = findViewById(R.id.create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Firstname = firstname.getText().toString();
                String Lastname = lastname.getText().toString();
                String Gender = gender.getText().toString();
                String Address = address.getText().toString();
                String ContactNumber = contactNumber.getText().toString();
                String Username = username.getText().toString();
                String Password = password.getText().toString();

                if (Password.length() < 6) {
                    Toast.makeText(Create.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                if (Firstname.isEmpty() || Lastname.isEmpty()
                    || Gender.isEmpty() || Address.isEmpty()||
                    ContactNumber.isEmpty() || Username.isEmpty()
                    || Password.isEmpty()) {
                    Toast.makeText(Create.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                // Hash the password
                String hashedPassword = hashPassword(Password);

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.1.8/VisualGuro/create.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("Success")){
                                    Toast.makeText(Create.this,"Created Successfully",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(Create.this,"Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("firstname", Firstname);
                        params.put("lastname", Lastname);
                        params.put("gender", Gender);
                        params.put("address", Address);
                        params.put("contactNumber", ContactNumber);
                        params.put("username", Username);
                        params.put("password", hashedPassword); // Send hashed password
                        return params;
                    }
                };

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }

    // Method to hash the password using SHA-256 algorithm
    private String hashPassword(String password) {
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