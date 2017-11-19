package com.ipsv2.android.sdk.kict;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.ipsv2.android.sdk.kict.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class KictFloorView extends AppCompatActivity {

    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kict_floor_view);
        String getImage = getIntent().getStringExtra("Resource_Image");
        ImageView imageView = (ImageView) findViewById(R.id.displayFloorMap);
        Drawable bitmap;
        // Set the Drawable displayed
        switch (getImage){
            case "one":
                bitmap = getResources().getDrawable(R.drawable.level1);
                imageView.setImageDrawable(bitmap);
                break;
            case "two":
                bitmap = getResources().getDrawable(R.drawable.level2);
                imageView.setImageDrawable(bitmap);
                break;
            case "five":
                bitmap = getResources().getDrawable(R.drawable.level5);
                imageView.setImageDrawable(bitmap);
                break;
            default:
                System.out.println("nothing to show");
        }

// Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        mAttacher = new PhotoViewAttacher(imageView);

//        Picasso.with(this)
//                .load(R.drawable.level1)
//                .into(imageView);
    }
}
