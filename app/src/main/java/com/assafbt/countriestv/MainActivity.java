package com.assafbt.countriestv;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String mUrl = "https://restcountries.eu/rest/v2/all";

    private RecyclerView mList;
    private String TAG = "MainActivity";
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Country> countryList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String functionTAG = TAG +" onCreate";

        dbHelper = new DatabaseHelper(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fetchData();

        mList = findViewById(R.id.main_list);

        countryList = new ArrayList<>();
        countryList = dbHelper.getSortedCountries("none","");

        Log.i(functionTAG, "DatabaseVersion " + dbHelper.getDatabaseVersion());
        updateRv();
        progressDialog.dismiss();

        final Button btnSortName, btnSortArea,btnShowData;
        btnSortName = (Button)findViewById(R.id.btn_sort_name);
        btnSortName.setText("name A->Z");
        btnSortName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                countryList.clear();
                String order = "ASC";
                if (btnSortName.getText() == "name A->Z") {
                    order = "DESC";
                    btnSortName.setText("name Z->A");
                }
                else {
                    order = "ASC";
                    btnSortName.setText("name A->Z");
                }

                countryList = dbHelper.getSortedCountries("name", order);
                updateRv();
            }
        });

        btnSortArea = (Button)findViewById(R.id.btn_sort_area);
        btnSortArea.setText("Area A->Z");
        btnSortArea.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                countryList.clear();
                String order = "ASC";
                if (btnSortArea.getText() == "Area A->Z") {
                    order = "DESC";
                    btnSortArea.setText("Area Z->A");
                }
                else {
                    order = "ASC";
                    btnSortArea.setText("Area A->Z");
                }

                countryList = dbHelper.getSortedCountries("area", order);
                updateRv();
            }
        });

        btnShowData = (Button)findViewById(R.id.btn_show_data);
        btnShowData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countryList = dbHelper.getSortedCountries("none", "none");
                updateRv();
                //btnShowData.setVisibility(View.GONE);
            }
        });

    }//onCreate

    private void fetchData() {
        final String functionTAG = TAG +" fetchData";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(mUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.i(functionTAG,"########### jsonObject " + i + " ########### ");
                        Log.i(functionTAG,"jsonObject " + jsonObject.toString());

                        String mName = jsonObject.getString("name");
                        Log.i(functionTAG,"mName " + mName);
                        String mNativeName = jsonObject.getString("nativeName");
                        Log.i(functionTAG,"mNativeName " + mNativeName);
                        int mArea = jsonObject.getInt("area");
                        //String mArea = jsonObject.getString("area");
                        Log.i(functionTAG,"area " + mArea);
                        long possition = dbHelper.insertCountry(new Country(mName, mNativeName,mArea));

                        // long possition = dbHelper.insertCountry(country);

                        Log.i(functionTAG, "added in possition: " + possition);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Error on JSONException", e.toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error on Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }//fetchData

    private void updateRv ()
    {
        adapter = new CountryAdapter(this,countryList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
    }
}//MainActivity
