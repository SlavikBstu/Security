package com.example.apache.hash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final String TAG = MainActivity.class.getName();
    File directory;
    private LocationManager lm;
    Location location_;
    final int REQUEST_CODE_VIDEO = 2;
    final String TAG_ = "myLogs";
    public static boolean flag=false;
    ImageView ivPhoto;
    public static Uri uri;
    public static TextView latlan,hash_t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDirectory();
        latlan=(TextView)findViewById(R.id.LatLan);
        hash_t=(TextView)findViewById(R.id.Hash);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        lm =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }
    public void onClickLatLan(View view) {
        latlan.setText("Your position is: "+"\n"+ "Latitude:"+location_.getLatitude()+"\n"+"Longitude:"+location_.getLongitude());
    }
    public void onClickStop(View view) {
        flag=false;  }
    @Override
    public void onLocationChanged(Location location)
    {
        if (location != null)
        {
            location_=location;

        }
    }
    @Override
    public void onProviderDisabled(String provider)
    {
    }

    @Override
    public void onProviderEnabled(String provider)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }



    public void onClickVideo(View view) {
        uri = generateFileUri();
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE_VIDEO);
    }
    public void onClickHash(View view) {
        getHash(uri);
    }
    public void onClickPost(View view) throws InterruptedException {
        flag=true;

        SaveCoordToServer cord_to_server = new SaveCoordToServer();

        cord_to_server.execute();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE_VIDEO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG_, "Intent is null");
                } else {

                    Log.d(TAG_, "Video uri: " + intent.getData());
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG_, "Canceled");
            }
        }
    }

    private Uri generateFileUri() {
        File file = new File(directory.getPath() + "/" + "video_" + System.currentTimeMillis() + ".mp4");
        Log.d(TAG_, "fileName = " + file);
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "Lab2");
        if (!directory.exists())
            directory.mkdirs();
    }
    class SaveCoordToServer extends AsyncTask<Void,Void, String> {


        @Override
        protected String doInBackground(Void... params) {
            flag = true;
            while (flag) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    String android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.accumulate("device_id", android_id);
                    jsonObject.accumulate("coords", location_.getLatitude() +"," + location_.getLongitude());
                    jsonObject.accumulate("is_hash", false);


                    String data = jsonObject.toString();
                    Log.d("json data", data);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://openlab.hopto.org:3000/coords");
                    StringEntity se = new StringEntity(data);
                    httpPost.setEntity(se);
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");
                    HttpResponse httpResponse = httpclient.execute(httpPost);
                    Log.d("RESPONSE FROM SERVER", httpResponse.getEntity().getContent().toString());


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }return null;
        }

    }
    private void getHash(Uri uri){

        try {
            Log.d("Hash", "start");
            Date d = new Date();
            long startTime = d.getTime();
            hash_t.setText("Hashing video....");

            File file = new File(uri.getPath());
            byte[] data = Hash_Function.getHashFromFile(file);
            String value = new String(data, "UTF8");
            System.out.println(value);
            // hash_t.setText("Hash: "+(Hex.decodeHex(value)));
            d = new Date();
            long time = d.getTime() - startTime;
            Log.d("Hash", "end " + time + " s");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
