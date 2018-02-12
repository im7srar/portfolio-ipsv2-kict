package com.kict.pocket;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ZoomControls;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.ipsv2.android.sdk.kict.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class KictFloorView extends AppCompatActivity implements SubsamplingScaleImageView.OnImageEventListener{

    ProgressBar progressBar;
    private SubsamplingScaleImageView imageView;
    private ZoomControls zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kict_floor_view);
        progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        final Button back = (Button)findViewById(R.id.back2);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(progressBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kict floor view");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        zoom = (ZoomControls) findViewById(R.id.zoomControls1);
        zoom.setOnZoomInClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                float x = imageView.getScaleX();
                float y = imageView.getScaleY();

                imageView.setScaleX((float) (x+1));
                imageView.setScaleY((float) (y+1));
            }
        });

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                float x = imageView.getScaleX();
                float y = imageView.getScaleY();

                imageView.setScaleX((float) (x-1));
                imageView.setScaleY((float) (y-1));
            }
        });

        String getImage = getIntent().getStringExtra("Resource_Image");
        imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);

        imageView.setOnImageEventListener(this);

        // Set the Drawable displayed
        switch (getImage){
            case "one":
                imageView.setImage(ImageSource.resource(R.drawable.level1));
                break;
            case "two":
                imageView.setImage(ImageSource.resource(R.drawable.level2));
                break;
            case "three":
                imageView.setImage(ImageSource.resource(R.drawable.level3));
                break;
            case "four":
                imageView.setImage(ImageSource.resource(R.drawable.level4));
                break;
            case "five":
                imageView.setImage(ImageSource.resource(R.drawable.level5));
                break;
            default:
                System.out.println("nothing to show");
        }

    }

    @Override
    public void onReady() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onImageLoaded() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTileLoadError(Exception e) {

    }

    @Override
    public void onPreviewLoadError(Exception e) {

    }

    @Override
    public void onImageLoadError(Exception e) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.floor_image_side_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.rotate_right) {
            imageView.setOrientation((imageView.getOrientation() + 90) % 360);


        }else if(item.getItemId() == R.id.rotate_left){
            if(imageView.getOrientation() == 0){
            imageView.setOrientation((360 - 90) % 360);
            }else{
                imageView.setOrientation((imageView.getOrientation() - 90) % 360);
            }
        }
        else{
            switch (item.getItemId()) {
                case android.R.id.home:
                    // app icon in action bar clicked; go home
                    this.finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
