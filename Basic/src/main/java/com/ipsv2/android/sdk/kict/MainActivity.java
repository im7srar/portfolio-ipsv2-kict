package com.ipsv2.android.sdk.kict;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import com.ipsv2.android.sdk.kict.Navigation.ImageViewActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "IAExample";
    final Context context = this;
    private static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Kict Pocket V2");

        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        ImageButton navigation = (ImageButton) findViewById(R.id.navigation);
        final ImageButton floormap = (ImageButton) findViewById(R.id.search);
        ImageButton qibla = (ImageButton) findViewById(R.id.mecca);
        ImageButton city = (ImageButton) findViewById(R.id.city);

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImageViewActivity.class);
                MainActivity.this.startActivity(intent);

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

                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:0355453130"));
                    startActivity(callIntent);
                }
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
