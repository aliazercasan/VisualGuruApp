package com.example.finalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private Button button_create;
    private Button button_forgot;
    EditText Username;
    EditText Password;

    TextView errorMess;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_create = (Button)findViewById(R.id.create);


        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreate();
            }
        });

        button_forgot = (Button)findViewById(R.id.forgot);

        button_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgot();
            }
        });


        Username = findViewById(R.id.emailusername);
        Password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = Username.getText().toString();
                final String password = Password.getText().toString();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                }

                // Hash the password before sending it to the server
                final String hashedPassword = hashPassword(password);

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.1.8/VisualGuro/login.php";
//                Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {


                                try{
                                    JSONArray jsonArray = new JSONArray(response);
                                    for(int i = 0; i < jsonArray.length();i++){
                                        JSONObject jsonObject =  jsonArray.getJSONObject(i);
                                        String user_name = jsonObject.getString("username");
                                        String user_pass = jsonObject.getString("password");

                                        if (user_name.equals(username) && user_pass.equals(hashedPassword)) {
                                            // If match, navigate to another activity
                                            Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                            startActivity(intent);
                                            finish(); // Finish current activity

                                        } else {
                                            // If not match, display an error message

                                            Toast.makeText(MainActivity.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                }catch (JSONException e){

                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
        //                        Toast.makeText(MainActivity.this, stringRequest.toString(), Toast.LENGTH_SHORT).show();

                            }

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> paramV = new HashMap<>();
                                paramV.put("username",username);
                                paramV.put("password",hashedPassword); // Send hashed password
                                return paramV;
                            }
                };

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }

    private boolean validateInputs() {
        String username = Username.getText().toString().trim();
        String currentPassword = Password.getText().toString().trim();

        if (username.isEmpty()) {
            Username.setError("Username is required");
            return false;
        }

        if (currentPassword.isEmpty()) {
            Password.setError("Current password is required");
            return false;
        }
        return true;
    }

    // Hash function to hash the password
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void openDashboard(){
        Intent intent = new Intent(this,Dashboard.class);
        startActivity(intent);
    }

    public void openCreate(){
        Intent intent = new Intent(this, Create.class);
        startActivity(intent);
    }
    public void openForgot(){
        Intent intent = new Intent(this,ForgotPassword.class);
        startActivity(intent);
    }
}