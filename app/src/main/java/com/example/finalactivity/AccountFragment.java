package com.example.finalactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class AccountFragment extends Fragment {

    private Button btn_Back;
    private TextView fullname, email, contactNumber;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Initialize UI elements
        fullname = view.findViewById(R.id.fullname);
        email = view.findViewById(R.id.email);
        contactNumber = view.findViewById(R.id.phoneNumber);

        // Button Back
        btn_Back = view.findViewById(R.id.drawer_layout);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
                requireActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Load user data when the fragment resumes
        loadUserData();
    }

    private void loadUserData() {
        // Clear previous user data
        clearUserData();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://192.168.1.8/VisualGuro/display.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String full_name = jsonObject.getString("firstname");
                                String user_name = jsonObject.getString("username");
                                String contact_number = jsonObject.getString("contactNumber");

                                // Set retrieved data to TextViews
                                fullname.setText(full_name);
                                email.setText(user_name);
                                contactNumber.setText(contact_number);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void clearUserData() {
        // Clear previous user data
        fullname.setText("");
        email.setText("");
        contactNumber.setText("");
    }

    public void openDashboard() {
        Intent intent = new Intent(requireActivity(), Dashboard.class);
        startActivity(intent);
    }
}

