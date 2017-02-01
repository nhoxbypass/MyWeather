package com.example.xataora.myweather;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class GetLocation extends AppCompatActivity {

    RadioGroup group;
    Spinner spinner;
    EditText cityInput;
    String woeid;
    String cityName;
    int resultCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        //Lay cac view
        group = (RadioGroup)findViewById(R.id.radio_group);
        spinner = (Spinner)findViewById(R.id.provinceSpinner);
        cityInput = (EditText)findViewById(R.id.editText_city);
        spinner.setEnabled(false);

        setTitle("LẤY ĐỊA ĐIỂM");
        woeid = "1581130"; //mac dinh la ha noi

        //Su kien phat hien check thay doi cua cac radio button
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.haNoi:
                        woeid = "1581130";
                        resetState();
                        //((RadioButton)findViewById(checkedId)).setTextColor(Color.WHITE);
                        break;
                    case R.id.daNang:
                        woeid = "1583992";
                        resetState();
                        //((RadioButton)findViewById(checkedId)).setTextColor(Color.WHITE);
                        break;
                    case R.id.haiPhong:
                        woeid = "1581298";
                        resetState();
                       // ((RadioButton)findViewById(checkedId)).setTextColor(Color.WHITE);
                        break;
                    case R.id.canTho:
                        woeid = "1586203";
                        resetState();
                       // ((RadioButton)findViewById(checkedId)).setTextColor(Color.WHITE);
                        break;
                    case R.id.hoChiMinh:
                        woeid = "1566083";
                        resetState();
                        ((RadioButton)findViewById(checkedId)).setTextColor(Color.WHITE);
                        break;
                    case R.id.other:
                        resultCode = 1;
                        spinner.setEnabled(true);
                        setSpinnerHandler();
                        if (cityInput.isEnabled())
                            cityInput.setEnabled(false);
                        //((RadioButton)findViewById(checkedId)).setTextColor(Color.WHITE);
                        break;
                    case R.id.manualInput:
                        resultCode = 2;
                        cityInput.setEnabled(true);
                        if (spinner.isEnabled())
                            spinner.setEnabled(false);
                       // ((RadioButton)findViewById(checkedId)).setTextColor(Color.WHITE);
                        break;
                }
            }
        });
    }

    private void setSpinnerHandler()
    {
        //su kien chon cua spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Toast.makeText(getBaseContext(),"Bạn phải lựa chọn địa điểm",Toast.LENGTH_SHORT);
                        break;
                    case 1:
                        woeid = "1568043";
                        break;
                    case 2:
                        woeid = "1567681";
                        break;
                    case 3:
                        woeid = "1573517";
                        break;
                    case 4:
                        woeid = "1566166";
                        break;
                    case 5:
                        woeid = "1562798";
                        break;
                    case 6:
                        woeid = "1581047";
                        break;
                    case 7:
                        woeid = "1568770";
                        break;

                    case 8:
                        woeid = "1568574";
                        break;
                    case 9:
                        woeid = "1563281";
                        break;
                    case 10:
                        woeid = "1586896";
                        break;
                    case 11:
                        woeid = "1569684";
                        break;
                    case 12:
                        woeid = "1580240";
                        break;
                    case 13:
                        woeid = "1571058";
                        break;

                    case 14:
                        woeid = "1587923";
                        break;
                    case 15:
                        woeid = "1572151";
                        break;
                    case 16:
                        woeid = "1562414";
                        break;
                    case 17:
                        woeid = "1574023";
                        break;
                    case 18:
                        woeid = "1587976";
                        break;
                    case 19:
                        woeid = "1563926";
                        break;
                    case 20:
                        woeid = "1566559";
                        break;
                    case 21:
                        woeid = "1567788";
                        break;
                    case 22:
                        woeid = "1586443";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(),"Bạn phải lựa chọn địa điểm",Toast.LENGTH_SHORT);
            }
        });
    }

    public void resetState()
    {
        resultCode = 1;
        if(spinner.isEnabled())
            spinner.setEnabled(false);
        if(cityInput.isEnabled())
            cityInput.setEnabled(false);
    }

    //Su kien click cua button
    public void getLocation(View v)
    {
        if (resultCode == 2)
            woeid = cityInput.getText().toString();

        //Tro ve main activity
        Intent output = new Intent();
        output.putExtra("woeid",woeid);

        setResult(resultCode, output);
        finish(); //ket thuc
    }
}
