package com.kict.pocket;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ipsv2.android.sdk.kict.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "IAExample";
    final Context context = this;
    private static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;
    Intent intent;
    String coordinates_lat;
    String coordinates_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("MyKICT Navigator");

        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        ImageButton navigation = (ImageButton) findViewById(R.id.navigation);
        final ImageButton floormap = (ImageButton) findViewById(R.id.search);
        ImageButton qibla = (ImageButton) findViewById(R.id.mecca);
        ImageButton city = (ImageButton) findViewById(R.id.city);

        //obtain GPS location
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            MyLocationListener myLocationListener = new MyLocationListener();
            coordinates_lat = myLocationListener.get_lat;
            coordinates_lon = myLocationListener.get_long;

        }else{
            coordinates_lat = "0";
            coordinates_lon = "0";
        }

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AnyplaceView.class);
                MainActivity.this.startActivity(intent);

//                String url = "https://anyplace.rayzit.com/anyplace/navigation/pois/id";
//                String access_token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjhkYmYwYjVkNjcwMTYxOTIyMDIxNDkyOTg3ZGZiOTNjM2FkYWYzMTcifQ.eyJhenAiOiI1ODc1MDA3MjM5NzEtb3NmYXJuYzFnN3Z2cG8yODBmb2FqdTFvaWowZjU5cnIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1ODc1MDA3MjM5NzEtb3NmYXJuYzFnN3Z2cG8yODBmb2FqdTFvaWowZjU5cnIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTM4OTI3NTE3OTkxMTAxODQ0NDEiLCJlbWFpbCI6ImtpY3QuaXBzdjIuYW55cGxhY2UuZGV2ZWxvcGVyQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiMzBzZHlObzlialVTbXpIQThYdWtGQSIsImlzcyI6ImFjY291bnRzLmdvb2dsZS5jb20iLCJqdGkiOiJlNTAxZTNkMGNjZWQ5ZDRlMTc1MDkwNWI0YmVkNGEzZGZiYjdlZjE0IiwiaWF0IjoxNTExNTkyOTkxLCJleHAiOjE1MTE1OTY1OTEsIm5hbWUiOiJraWN0IGRldmVsb3BlciIsInBpY3R1cmUiOiJodHRwczovL2xoNS5nb29nbGV1c2VyY29udGVudC5jb20vLWh3cTRCMzhzSkRVL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FGaVlvZjJRcW4zR0VkT3BmRUtGMDFsdExVblZHQXlJRGcvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6ImtpY3QiLCJmYW1pbHlfbmFtZSI6ImRldmVsb3BlciIsImxvY2FsZSI6ImVuIn0.BxbxQWhrYLyQ13MVCCHESAmbYNY0PetPYFZkAyt7nFYKrXoPBq_B0SElzclADqIGpufswOVupSoko2aApB_ikXebAITNjkkuL4-Q1xQkw5NujSqt8Z45urWO0P80Lj4yeYBECZZTPSIqONDvOXhAJb_A8sB8m3bsLZJ_qjdcAqZunlo7CVOr9jYxEC4cg3iY4Jz4DIkEC9o0pBlXPZYV9nhX7-4RDGxTK8pJTmMomG5HACEL9tloORAgeZUudsr_lKSW_qyUsk1Ma5avY-1BkMRceBd0JEpMN13FpnMyfsIOqTB0U8ZweRzs-wS1p7E8od9CgkU1OjCyff3ADmP2Xg";
//                String pois_to = "ICT cafe";
//                String buid = "KICT";
//                String floor_number = "1";
//                String html = "<!DOCTYPE html>" +
//                        "<html>" +
//                        "<body onload='document.form.submit()'>" +
//                        "<form action='"+ url +"' method='post' name='form'>" +
//                        "  <input type='hidden' name='access_token' value='" + access_token +"'><br>" +
//                        "  <input type='hidden' name='pois_to' value='" + pois_to +"'><br>" +
//                        "  <input type='hidden' name='buid' value='" + buid + "'><br>" +
//                        "  <input type='hidden' name='floor_number' value='" + floor_number + "'><br>" +
//                        "  <input type='hidden' name='coordinates_lat' value='" + coordinates_lat + "'><br>" +
//                        "  <input type='hidden' name='coordinates_lon' value='" + coordinates_lon + "'><br>" +
//                        "</form>" +
//                        "</body>" +
//                        "</html>";
//                new FinestWebView.Builder(context)
//                        .showSwipeRefreshLayout(false)
//                        .showUrl(false)
//                        .webViewSupportZoom(true)
//                        .webViewBuiltInZoomControls(true)
//                        .webViewDisplayZoomControls (true)
//                        .webViewSupportMultipleWindows (true)
//                        .webViewGeolocationEnabled (true)
//                        .webViewAppCacheEnabled (true)
//                        .webViewDatabaseEnabled (true)
//                        .webViewDomStorageEnabled (true)
//                        .webViewJavaScriptEnabled (true)
//                        .webViewLoadsImagesAutomatically (true)
//                        .webViewLoadWithOverviewMode (true)
//                        .show("https://anyplace.rayzit.com/anyplace/navigation/pois/id");

            }
        });

        floormap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, floormap);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.image_floor_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.one:
                                intent = new Intent(MainActivity.this, KictFloorView.class);
                                intent.putExtra("Resource_Image", "one");
                                MainActivity.this.startActivity(intent);
                                return true;
                            case R.id.two:
                                intent = new Intent(MainActivity.this, KictFloorView.class);
                                intent.putExtra("Resource_Image", "two");
                                MainActivity.this.startActivity(intent);
                                return true;
                            case R.id.three:
                                intent = new Intent(MainActivity.this, KictFloorView.class);
                                intent.putExtra("Resource_Image", "three");
                                MainActivity.this.startActivity(intent);
                                return true;
                            case R.id.four:
                                intent = new Intent(MainActivity.this, KictFloorView.class);
                                intent.putExtra("Resource_Image", "four");
                                MainActivity.this.startActivity(intent);
                                return true;
                            case R.id.five:
                                intent = new Intent(MainActivity.this, KictFloorView.class);
                                intent.putExtra("Resource_Image", "five");
                                MainActivity.this.startActivity(intent);
                                return true;
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        qibla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QiblaView.class);
                MainActivity.this.startActivity(intent);
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PhoneContactList.class);
                MainActivity.this.startActivity(intent);
            }
        });

        if (!isSdkConfigured()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.configuration_incomplete_title)
                    .setMessage(R.string.configuration_incomplete_message)
                    .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
            return;
        }

        ensurePermissions();

    }

    /**
     * Checks that we have access to required information, if not ask for users permission.
     */
    private void ensurePermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // we don't have access to coarse locations, hence we have not access to wifi either
            // check if this requires explanation to user
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.location_permission_request_title)
                        .setMessage(R.string.location_permission_request_rationale)
                        .setPositiveButton(R.string.permission_button_accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "request permissions");
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        REQUEST_CODE_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton(R.string.permission_button_deny, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this,
                                        R.string.location_permission_denied_message,
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

            } else {

                // ask user for permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ACCESS_COARSE_LOCATION);

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ACCESS_COARSE_LOCATION:

                if (grantResults.length == 0
                        || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, R.string.location_permission_denied_message,
                            Toast.LENGTH_LONG).show();
                }
                break;
        }

    }



    private boolean isSdkConfigured() {
        return !"api-key-not-set".equals(getString(R.string.indooratlas_api_key))
                && !"api-secret-not-set".equals(getString(R.string.indooratlas_api_secret));
    }




}
