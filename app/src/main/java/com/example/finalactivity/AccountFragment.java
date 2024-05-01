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

    private Button btn_Back, btn_update;
    private TextView fullname, email, contactNumber;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Find views
        fullname = view.findViewById(R.id.fullname);
        email = view.findViewById(R.id.email);
        contactNumber = view.findViewById(R.id.phoneNumber);

        // Sample user ID, replace this with the actual ID of the logged-in user
        String userId = "1";

        // Retrieve user data from server
        retrieveUserDataFromServer(userId);

        // This is for Button Back
        btn_Back = view.findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
                requireActivity().onBackPressed();
            }
        });
        return view;
    }

    public void openDashboard() {
        Intent intent = new Intent(requireActivity(), Dashboard.class);
        startActivity(intent);
    }

    private void retrieveUserDataFromServer(String userId) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "http://192.168.1.5/VisualGuro/display.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                JSONObject jsonObject = jsonArray.getJSONObject(0); // Assuming only one record is returned
                                String firstName = jsonObject.getString("firstname");
                                String userEmail = jsonObject.getString("email");
                                String userContactNumber = jsonObject.getString("contactNumber");

                                // Update TextViews with retrieved data
                                fullname.setText("Full Name: " + firstName);
                                email.setText("Email: " + userEmail);
                                contactNumber.setText("Contact Number: " + userContactNumber);
                            } else {
                                // No data found
                                Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId); // Pass the user ID
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
