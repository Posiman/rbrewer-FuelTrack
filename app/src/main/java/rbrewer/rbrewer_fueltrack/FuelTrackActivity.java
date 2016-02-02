package rbrewer.rbrewer_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FuelTrackActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<LogEntry> logs;

    private PrevLogList listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private Integer i;
    private TextView totalCostText;
    private ArrayList<Double> totalCostList;
    private TotalCost totalCost;
    private Double totalCostNumber;

    private LogEntry logEntry;
    private Date date;
    private String station;
    private Double odometer;
    private String fuelGrade;
    private Double fuelAmount;
    private Double fuelUnitCost;



    public void newLog(View view) {
        Intent intent = new Intent(this, NewLog.class);
        startActivityForResult(intent, 1);
    }


    // Template from http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent extras) {
        if (requestCode == 1) {
            if(resultCode  == RESULT_OK) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                try { date = dateFormat.parse(extras.getStringExtra("date")); }
                catch(ParseException dateError) { dateError.printStackTrace(); }

                station = extras.getStringExtra("station");
                odometer = Double.parseDouble(extras.getStringExtra("odometer"));
                fuelGrade = extras.getStringExtra("fuelGrade");
                fuelAmount = Double.parseDouble(extras.getStringExtra("fuelAmount"));
                fuelUnitCost = Double.parseDouble(extras.getStringExtra("fuelUnitCost"));

                logEntry = new LogEntry(date, station, odometer, fuelGrade, fuelAmount, fuelUnitCost);

                createNewLog();
                listAdapter = new PrevLogList(this, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);
                //saveInFile();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_track_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadFromFile();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.prevLogsList);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        logs = new ArrayList<LogEntry>();
        i = 0;

        totalCostList = new ArrayList<Double>();
        totalCost = new TotalCost(totalCostList);
        totalCostText = (TextView) findViewById(R.id.fuelCostNumber);
        totalCostNumber = totalCost.getTotalCost();
        totalCostText.setText(String.format("%.2f", totalCostNumber));

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type headerType = new TypeToken<ArrayList<String>>() {}.getType();
            Type childType = new TypeToken<HashMap<String, List<String>>>() {}.getType();
            Type listType = new TypeToken<PrevLogList>() {}.getType();
            listDataHeader = gson.fromJson(in, headerType);
            listDataChild = gson.fromJson(in, childType);
            listAdapter = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            listAdapter = new PrevLogList(this, listDataHeader, listDataChild);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(listDataHeader, out);
            gson.toJson(listDataChild, out);
            gson.toJson(listAdapter, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void createNewLog() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        listDataHeader.add(dateFormat.format(logEntry.getDate()));

        List<String> log = new ArrayList<>();
        log.add("Station:  " + logEntry.getStation());
        log.add("Odometer:  " + String.format("%.1f",logEntry.getOdometer()) + "km");
        log.add("Fuel Grade:  " + logEntry.getFuelGrade());
        log.add("Fuel Amount:  " + String.format("%.3f",logEntry.getFuelAmount()) + "L");
        log.add("Price:  " + String.format("%.1f",logEntry.getFuelUnitCost()) + "c/L");
        log.add("Cost:  $" + String.format("%.2f",logEntry.getFuelCost()));

        totalCostList.add(logEntry.getFuelCost());
        totalCostNumber = totalCost.getTotalCost();
        totalCostText.setText(String.format("%.2f", totalCostNumber));

        listDataChild.put(listDataHeader.get(i), log);
        i++;
    }

}
