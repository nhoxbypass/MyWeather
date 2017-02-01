package com.example.xataora.myweather;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.Serializable;
import java.net.URLEncoder;

import jp.wasabeef.blurry.Blurry;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyWeatherCallBack{

    //Khai bao bien
    TextView txtTempurature;
    TextView txtWindSpeed;
    TextView txtSunrise;
    TextView txtSunset;
    TextView txtLocation;
    TextView txtHighTemper;
    TextView txtLowTemper;
    TextView txtHumidity;
    TextView txtCloudiness;
    TextView txtCountry;
    TextView txtDate;
    TextView txtConditionText;
    TextView txtHelper;
    ImageView imgWeatherStatus;

    //Class main
    MyWeather weatherResult;

   //Default location
    String rootLink;
    String queryLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        rootLink = "http://api.openweathermap.org/data/2.5/weather?";
        queryLink = rootLink + "q=London,uk";

        //Lay thong tin cua view
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Lay thong tin view
        findAllView();

        Button btnGetLocation = (Button) findViewById(R.id.get_location_button);


        //Su kien click lay thong tin dia diem
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getLocation = new Intent(getBaseContext(), GetLocation.class);

                //request code 1 cho get location
                startActivityForResult(getLocation, 1);


                //setTitle("Test");
                /*Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        weatherResult = new MyWeather(this, queryLink);
        weatherResult.getData();

        /*
        txtTempurature.getRootView().post(new Runnable() {
            @Override
            public void run() {
                Blurry.with(getApplicationContext()).radius(25).sampling(2).onto((ViewGroup)findViewById(R.id.scrollView));
            }
        });
       */
}



    @Override
    public void UpdateView() {
        Resources res = getResources();
        String mDrawableName = weatherResult.getStatusImagePath();
        int resID = res.getIdentifier(mDrawableName, "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID);
        imgWeatherStatus.setImageDrawable(drawable);


        //imgWeatherStatus.setImageBitmap(weatherResult.getStatusImage());

        txtLocation.setText(weatherResult.getCityName());
        txtCountry.setText(weatherResult.getCountryName());
        txtTempurature.setText(weatherResult.getTempurature() + "°" + weatherResult.tempUnit );
        txtHighTemper.setText(weatherResult.getMaxTempurature()+ "°" + weatherResult.tempUnit);
        txtLowTemper.setText(weatherResult.getMinTempurature()+ "°" + weatherResult.tempUnit);
        txtWindSpeed.setText(weatherResult.getWindDetail());
        txtHumidity.setText(weatherResult.getHumidity() + "%");
        txtCloudiness.setText(weatherResult.getCloudiness() + "%");
        txtSunrise.setText(weatherResult.getSunriseTime());
        txtSunset.setText( weatherResult.getSunsetTime());
        txtConditionText.setText(weatherResult.getOverview());
        txtDate.setText(weatherResult.getDescription());


        if(Float.parseFloat(weatherResult.getTempurature()) < 30)
            txtHelper.setText("Hôm nay trời khá mát mẻ :)\nhay là thử ra đường dạo mát vài vòng bạn nhỉ?");
        else
            txtHelper.setText("Hôm nay trời khá nóng nhỉ?\nthử đi bơi thay vì ở nhà tránh nóng xem sao!");

    }


    private void findAllView()
    {
        //
        imgWeatherStatus = (ImageView)findViewById(R.id.weather_imageView);
        txtTempurature = (TextView)findViewById(R.id.temperature_textView);
        txtHighTemper = (TextView)findViewById(R.id.high_temper_textView);
        txtLowTemper = (TextView)findViewById(R.id.low_temper_textView);
        txtWindSpeed = (TextView)findViewById(R.id.wind_speed_textView);
        txtSunrise=(TextView)findViewById(R.id.sunrise_textView);
        txtSunset = (TextView)findViewById(R.id.sunset_textView);
        txtLocation = (TextView)findViewById(R.id.location_textView);
        txtHumidity = (TextView)findViewById(R.id.humidity_textView);
        txtCloudiness = (TextView)findViewById(R.id.cloud_textview);
        txtConditionText = (TextView) findViewById(R.id.conditionText_textView);
        txtCountry = (TextView)findViewById(R.id.country_textView);
        txtDate = (TextView)findViewById(R.id.status_textview);
        txtHelper = (TextView)findViewById(R.id.helper_textview);
    }


    //Cac su kien listener
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode != 0 && data != null) {
            //Lay du lieu tra ve tu clas child
            String woeid = data.getStringExtra("woeid");

            if (resultCode == 1)
                queryLink = rootLink + "id=" + woeid;
            else {

                try {
                    queryLink = rootLink + "q=" + URLEncoder.encode(woeid,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            //Lay thong tin tu API
            weatherResult = new MyWeather(this, queryLink);
            weatherResult.getData();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_today:
                break;

            case R.id.nav_forecast:
                Intent forecastIntent = new Intent(getBaseContext(),ForecaseActivity.class);
                forecastIntent.putExtra("weatherExtra", "http://api.openweathermap.org/data/2.5/forecast/daily?id=" + weatherResult.getCityID() + "&units=metric&cnt=10&appid=" + weatherResult.getApiKey());
                startActivity(forecastIntent);
                break;

            case R.id.nav_about:
                Intent aboutIntent = new Intent(getBaseContext(),AboutActivity.class);
                startActivity(aboutIntent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}

