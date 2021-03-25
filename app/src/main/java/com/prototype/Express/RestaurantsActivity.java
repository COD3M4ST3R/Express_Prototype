package com.prototype.Express;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class RestaurantsActivity extends AppCompatActivity
{
    //  VARIABLES
    RequestQueue requestQueue;

    // XML VARIABLES
    TextView textView3, textView5;
    ImageView imageView2;
    Button button;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        // CASTING XML VARIABLES
        textView3 = findViewById(R.id.textView3);
        imageView2 = findViewById(R.id.imageView2);
        button = findViewById(R.id.button);
        textView5 = findViewById(R.id.textView5);

        // GET INTENT EXTRA
        String key = getIntent().getStringExtra("key");

        // REQUEST NEW VOLLEY
        requestQueue = Volley.newRequestQueue(this);
        jsonParse(key);
    }


    public void jsonParse(final String key)
    {
        String url_address = "http://104.248.207.133:5000/api/v1/restaurants";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_address, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject restaurant = jsonArray.getJSONObject(i);
                        String name = restaurant.getString("name");

                        if(key.equals(name))
                        {
                            Picasso.with(getApplicationContext()).load("https://backgroundcheckall.com/wp-content/uploads/2017/12/mcdonalds-logo-transparent-background.png").into(imageView2);

                            String photo = restaurant.getString("photo");
                            String description = restaurant.getString("description");
                            String address = restaurant.getString("address");
                            textView5.setText(name);
                            textView3.append("\n" + description);
                            textView3.append("\n" + address);

                            button.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    Toast.makeText(RestaurantsActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
