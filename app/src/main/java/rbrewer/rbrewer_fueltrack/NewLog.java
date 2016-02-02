package rbrewer.rbrewer_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;


public class NewLog extends AppCompatActivity {

    private EditText dateText;
    private EditText stationText;
    private EditText odometerText;
    private EditText fuelGradeText;
    private EditText fuelAmountText;
    private EditText fuelUnitCostText;

    private String date;
    private String station;
    private String odometer;
    private String fuelGrade;
    private String fuelAmount;
    private String fuelUnitCost;


    public void cancelNewLog(View view) {
        finish();
    }

    public void saveLog(View view) {

        date = dateText.getText().toString();
        station = stationText.getText().toString();
        odometer = odometerText.getText().toString();
        fuelGrade = fuelGradeText.getText().toString();
        fuelAmount = fuelAmountText.getText().toString();
        fuelUnitCost = fuelUnitCostText.getText().toString();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("date",date);
        returnIntent.putExtra("station",station);
        returnIntent.putExtra("odometer",odometer);
        returnIntent.putExtra("fuelGrade",fuelGrade);
        returnIntent.putExtra("fuelAmount",fuelAmount);
        returnIntent.putExtra("fuelUnitCost",fuelUnitCost);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_log_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dateText = (EditText) findViewById(R.id.setDate);
        stationText = (EditText) findViewById(R.id.setStation);
        odometerText = (EditText) findViewById(R.id.setOdometer);
        fuelGradeText = (EditText) findViewById(R.id.setFuelGrade);
        fuelAmountText = (EditText) findViewById(R.id.setFuelAmount);
        fuelUnitCostText = (EditText) findViewById(R.id.setFuelUnitCost);
    }

}
