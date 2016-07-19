package com.example.vipin.spinner_db;

import java.io.IOException;
import android.app.Activity;
import android.content.res.Resources;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import com.example.vipin.spinner_db.R;

public class MainActivity extends Activity {
    private String[] mystring;
    Spinner samplespinner;
    String[] DataToDB;
    String[]result_array;
    String Selecteditem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayAdapter sampleadapter;

        Resources res = getResources();

        mystring = res.getStringArray(R.array.Sex);

        samplespinner= (Spinner) findViewById(R.id.spinner);

        sampleadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mystring);
        samplespinner.setAdapter(sampleadapter);

        samplespinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                Selecteditem = samplespinner.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });}


    public void RunDatabse(View view)
    {
        DatabaseHelper myDbHelper = new DatabaseHelper(this);

        try {

            myDbHelper.createDataBase();
            DataToDB = myDbHelper.ReadFromDB(Selecteditem.trim());
            TableLayout tablelayout= (TableLayout)findViewById(R.id.tableLayout);
            tablelayout.removeAllViews();

            for(int i=0;i < DataToDB.length;i++)
            {
                TableRow tableRow = new TableRow(this);
                result_array = DataToDB[i].split(",");

                tableRow.setPadding(5,5,5,5);
                TextView txt1 = new TextView(this);
                TextView txt2 = new TextView(this);
                TextView txt3 = new TextView(this);

                txt1.setText(result_array[0]);
                txt2.setText(result_array[1]);
                txt3.setText(result_array[2]);

                tableRow.addView(txt1);
                tableRow.addView(txt2);
                tableRow.addView(txt3);

                tablelayout.addView(tableRow);
            }

        }
        catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            try {
                myDbHelper.openDataBase();
            } catch (java.sql.SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }catch(SQLException sqle){

            throw sqle;

        }
    }
}
