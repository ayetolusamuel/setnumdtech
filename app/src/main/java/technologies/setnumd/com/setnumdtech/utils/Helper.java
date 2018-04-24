package technologies.setnumd.com.setnumdtech.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


import technologies.setnumd.com.setnumdtech.R;
import technologies.setnumd.com.setnumdtech.model.Products;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 4/8/2018.
 */

public class Helper {
    private  Resources mResources;
    ArrayList<Products> productsArrayList;
    private String name,description,category,price,instock,id,imagePath;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public String getInstock() {
        return instock;
    }

    public String getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Helper(Context context){
        mResources = context.getResources();
    }



    public  String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString;
            inputStream = mResources.openRawResource(R.raw.products);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }


    public void readJsonData() throws IOException, JSONException {
        String jsonSting = readJsonDataFromFile();

        JSONObject json = new JSONObject(jsonSting);
        JSONArray jsonArray = json.getJSONArray("products");

        int length = jsonArray.length();
        productsArrayList = new ArrayList<>();
        Products products = null;
        for (int i = 0; i < length; i++) {

            JSONObject menuItemObject = jsonArray.getJSONObject(i);


            id = menuItemObject.getString("id");
            name = menuItemObject.getString("name");
            description = menuItemObject.getString("description");
            price = menuItemObject.getString("price");
            instock = menuItemObject.getString("instock");
            category = menuItemObject.getString("category");
            imagePath = menuItemObject.getString("photo");
            products = new Products(id,name,description,price,instock,category,imagePath);






        }

       Log.d("ID ", id);
        Log.d(TAG, "name  " + name);
        Log.d(TAG, "Description  " + description);
        Log.d(TAG, "Category  " + category);
        Log.d(TAG, "inStock   " + instock);
        Log.d(TAG, "Price  " + price);
        Log.d(TAG, "Image  " + imagePath);
        productsArrayList.add(products);



    }
    public  ArrayList<Products> createProductsList() throws IOException, JSONException {
        ArrayList<Products> contacts = new ArrayList<Products>();




        String jsonSting = readJsonDataFromFile();

        JSONObject json = new JSONObject(jsonSting);
        JSONArray jsonArray = json.getJSONArray("products");

        int length = jsonArray.length();
        productsArrayList = new ArrayList<>();
        Products products = null;
        for (int i = 0; i < length; i++) {

            JSONObject menuItemObject = jsonArray.getJSONObject(i);


            id = menuItemObject.getString("id");
            name = menuItemObject.getString("name");
            description = menuItemObject.getString("description");
            price = menuItemObject.getString("price");
            instock = menuItemObject.getString("instock");
            category = menuItemObject.getString("category");
            imagePath = menuItemObject.getString("photo");
            products = new Products(id,name,description,price,instock,category,imagePath);

            productsArrayList.add(products);



        }





        return productsArrayList;
    }
}