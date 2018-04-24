package technologies.setnumd.com.setnumdtech;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringJoiner;


import technologies.setnumd.com.setnumdtech.adapter.ProductAdapter;
import technologies.setnumd.com.setnumdtech.model.Products;
import technologies.setnumd.com.setnumdtech.utils.Helper;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    ArrayList<Products> productsList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        setTitle("Product's List");


        try {
            createList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (productsList != null){


        }
        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(),productsList);
            recyclerView.setAdapter(productAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    public ArrayList<Products>createList() throws IOException, JSONException {
         String name,description,category,price,inStock,id,imagePath;
        Helper helper = new Helper(getApplicationContext());
        String jsonSting = helper.readJsonDataFromFile();

        JSONObject json = new JSONObject(jsonSting);
        JSONArray jsonArray = json.getJSONArray("products");

        int length = jsonArray.length();
        productsList = new ArrayList<>();

        for (int i = 0; i < length; i++) {

            JSONObject menuItemObject = jsonArray.getJSONObject(i);


            id = menuItemObject.getString("id");
            name = menuItemObject.getString("name");
            description = menuItemObject.getString("description");
            price = menuItemObject.getString("price");
            inStock = menuItemObject.getString("instock");
            category = menuItemObject.getString("category");
            imagePath = menuItemObject.getString("photo");
            productsList.add(new Products(id,name,description,price,inStock,category,imagePath));


        }

        return productsList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.map:
               address();
                return true;
            /*case R.id.help:
                showHelp();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void address() {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);


    }
}
