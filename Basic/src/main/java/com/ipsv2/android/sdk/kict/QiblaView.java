package com.ipsv2.android.sdk.kict;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.LabeledIntent;
import android.content.res.Resources;
import android.graphics.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class QiblaView extends Activity {

    private static final String TAG = "Compass";

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SampleView kiblat;
    private float[] mValues;
    private double lonMosque;
    private double latMosque;
    private LocationManager lm;
    private LocationListener locListenD;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    //for finding north direction
    private final SensorEventListener mListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
            if (Config.DEBUG) Log.d(TAG,
                    "sensorChanged (" + event.values[0] + ", " + event.values[1] + ", " + event.values[2] + ")");
            mValues = event.values;
            if (kiblat != null) {
                kiblat.invalidate();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        kiblat = new SampleView(this);
        setContentView(kiblat);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // for calling the gps
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location loc = lm.getLastKnownLocation("gps");

            // ask the Location Manager to send us location updates
            locListenD = new DispLocListener();
            lm.requestLocationUpdates("gps", 30000L, 10.0f, locListenD);

            locListenD = new DispLocListener();
            lm.requestLocationUpdates("gps", 30000L, 10.0f, locListenD);
        }else {

            // The ACCESS_COARSE_LOCATION is denied, then I request it and manage the result in
            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
            }
            // The ACCESS_FINE_LOCATION is denied, then I request it and manage the result in
            // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        MY_PERMISSION_ACCESS_FINE_LOCATION);
            }

        }


    }

    //finding kabah location
    private double QiblaCount(double lngMasjid, double latMasjid) {
        double lngKabah=39.82616111;
        double latKabah=21.42250833;
        double lKlM=(lngKabah-lngMasjid);
        double sinLKLM= Math.sin(lKlM*2.0*Math.PI/360);
        double cosLKLM= Math.cos(lKlM*2.0*Math.PI/360);
        double sinLM = Math.sin(latMasjid *2.0 * Math.PI/360);
        double cosLM =  Math.cos(latMasjid *2.0 * Math.PI/360);
        double tanLK = Math.tan(latKabah*2*Math.PI/360);
        double denominator= (cosLM*tanLK)-sinLM*cosLKLM;

        double Qibla;
        double direction;

        Qibla = Math.atan2(sinLKLM, denominator)*180/Math.PI;
        direction= Qibla<0 ? Qibla+360 : Qibla;
        return direction;

    }

    //resume location update when we are resume
    @Override
    protected void onResume()
    {
        if (Config.DEBUG) Log.d(TAG, "onResume");
        super.onResume();

        mSensorManager.registerListener(mListener, mSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    //stop location update when we are stop
    @Override
    protected void onStop()
    {
        if (Config.DEBUG) Log.d(TAG, "onStop");
        mSensorManager.unregisterListener(mListener);
        super.onStop();
    }

    private class SampleView extends View {
        private Paint   mPaint = new Paint();
        private Path    mPath = new Path();
        private boolean mAnimate;


        public SampleView(Context context) {
            super(context);

            // Construct a wedge-shaped path
            mPath.moveTo(0, -100);
            mPath.lineTo(20, 120);
            mPath.lineTo(0, 100);
            mPath.lineTo(-20, 120);
            mPath.close();
        }

        //make an arrow for pointing direction

        protected void onDraw(Canvas canvas) {
            Paint paint = mPaint;

            this.setBackgroundResource(R.drawable.mecca);

            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);

            int w = canvas.getWidth();
            int h = canvas.getHeight();
            int cx = w / 2;
            int cy = h / 2;
            float Qibla=(float) QiblaCount(lonMosque,latMosque);
            //   float Qiblat = mValues [0] + Qibla;
            canvas.translate(cx, cy);
            if (mValues != null) {
                canvas.rotate(-(mValues[0]+ Qibla));
            }
            canvas.drawPath(mPath, mPaint);
        }

        @Override
        protected void onAttachedToWindow() {
            mAnimate = true;
            if (Config.DEBUG) Log.d(TAG, "onAttachedToWindow. mAnimate=" + mAnimate);
            super.onAttachedToWindow();
        }

        @Override
        protected void onDetachedFromWindow() {
            mAnimate = false;
            if (Config.DEBUG) Log.d(TAG, "onDetachedFromWindow. mAnimate=" + mAnimate);
            super.onDetachedFromWindow();
        }

    }

    private class DispLocListener implements LocationListener {
        public void onLocationChanged(Location loc) {
            // update TextViews
            latMosque = loc.getLatitude();
            lonMosque = loc.getLongitude();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
}
