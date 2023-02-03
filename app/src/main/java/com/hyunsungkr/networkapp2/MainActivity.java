package com.hyunsungkr.networkapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hyunsungkr.networkapp2.adapter.PostAdapter;
import com.hyunsungkr.networkapp2.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // RecyclerView를 사용할 때
    // RecyclerView,Adapter,ArrayList를 쌍으로 적어라.
    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Post> postList = new ArrayList<>();

    final String URL = "https://jsonplaceholder.typicode.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView를 화면에 연결하고
        // 쌍으로 같이 다니는 코드도 작성.
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        // 데이터를 네트워크에서 받아온다.
        // Volley로 네트워크 통신한다.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // request 만든다.
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                URL + "/posts", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0; i<response.length(); i++){

                            try {
                                JSONObject data = response.getJSONObject(i);

                                Post post = new Post(data.getInt("userId"),data.getInt("id"),data.getString("title"),data.getString("body"));

                                postList.add(post);



                            } catch (JSONException e) {

                                return;

                            }

                        }

                        adapter = new PostAdapter(MainActivity.this,postList);
                        recyclerView.setAdapter(adapter);


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        queue.add(request);

    }
}