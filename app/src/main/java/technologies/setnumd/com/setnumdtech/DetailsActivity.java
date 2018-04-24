package technologies.setnumd.com.setnumdtech;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;

public class DetailsActivity extends AppCompatActivity {
    private TextView idTextView, nameTextview, categoryTextView, priceTextView, descriptionTextView, instockTextView;
    private ImageView imageView;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        idTextView = findViewById(R.id.product_code);
        nameTextview = findViewById(R.id.product_name);
        categoryTextView = findViewById(R.id.product_category);
        priceTextView = findViewById(R.id.priceTextView);
        descriptionTextView = findViewById(R.id.product_description);
        imageView = findViewById(R.id.productImageView);
        instockTextView = findViewById(R.id.inStockTextView);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String category = intent.getStringExtra("category");
        String price = intent.getStringExtra("price");
        //String instock = intent.getStringExtra("instock");
        String description = intent.getStringExtra("description");
        String image = intent.getStringExtra("image");


        idTextView.setText("SET" + id);
        nameTextview.setText(name);
        categoryTextView.setText(category);
        descriptionTextView.setText(description);
        instockTextView.setText("₦"+price);


        try {
            // get input stream
            InputStream ims = getAssets().open(image);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);
            ims.close();
        } catch (IOException ex) {
            return;
        }

    }
}
