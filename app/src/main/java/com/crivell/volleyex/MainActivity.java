package com.crivell.volleyex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crivell.volleyex.GitHubUserInfo;
import com.crivell.volleyex.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = (TextView)findViewById(R.id.info);
        initSimpleRequestBtn();
        initJsonRequestBtn();
        initGsonRequestBtn();
    }

    private TextView initTextView(){
        return (TextView)findViewById(R.id.info);
    }

    private void initSimpleRequestBtn() {
        Button simpleRequestBtn = ((Button) findViewById(R.id.btnSimpleRequest));
        simpleRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleRequest();
            }
        });
    }

    private void initJsonRequestBtn() {
        Button simpleRequestBtn = ((Button) findViewById(R.id.btnJsonRequest));
        simpleRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonRequest();
            }
        });
    }

    private void initGsonRequestBtn() {
        Button simpleRequestBtn = ((Button) findViewById(R.id.btnGsonRequest));
        simpleRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gsonRequest();
            }
        });
    }

    private void simpleRequest() {
        // 1. Uzyskanie referencji do kolejki
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.github.com/users/Crivell";

        // 2. Utworzenie żądania
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        VolleyLog.d(response);
                        v.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(error.getMessage());
            }
        });

        // 3. Dodanie żądania na kolejkę.
        queue.add(stringRequest);
    }

    private void jsonRequest() {
        // 1. Uzyskanie referencji do kolejki
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.github.com/users/Crivell";

        // 2. Utworzenie żądania
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String login = response.get("login").toString();
                            String repoCount = response.get("public_repos").toString();
                            VolleyLog.d("Witaj %s! Posiadasz %s publicznych repozytoriów!", login, repoCount);
                            v.setText("Witaj "+ login +" Posiadasz " + repoCount + " publicznych repozytoriów!");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(error.getMessage());
            }
        });

        // 3. Dodanie żądania na kolejkę.
        queue.add(jsonObjectRequest);
    }

    private void gsonRequest() {
        // 1. Uzyskanie referencji do kolejki
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.github.com/users/Crivell";

        // 2. Utworzenie żądania
        GsonRequest<GitHubUserInfo> gsonRequest = new GsonRequest<>(url, GitHubUserInfo.class,
                new Response.Listener<GitHubUserInfo>() {
                    @Override
                    public void onResponse(GitHubUserInfo response) {
                        VolleyLog.d("Witaj %s! Posiadasz %s publicznych repozytoriów!", response.getLogin(), response.getPublicRepos());
                        v.setText("Witaj "+ response.getLogin() +" Posiadasz "+  response.getPublicRepos() + " publicznych repozytoriów!");
                        VolleyLog.d("Twój identyfikator to %d! Mieszkasz w: %s", response.getId(), response.getLocation());
                        v.setText("Twój identyfikator to "+ response.getId() +" Mieszkasz w: "+ response.getLocation());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(error.getMessage());
            }
        });

        // 3. Dodanie żądania na kolejkę.
        queue.add(gsonRequest);
    }
}