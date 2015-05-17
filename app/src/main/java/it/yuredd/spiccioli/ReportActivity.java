package it.yuredd.spiccioli;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;


public class ReportActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        PieChart chart = (PieChart) findViewById(R.id.chart);

        chart.setCenterText("$$$$");

        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(20.0f, 1));
        entries.add(new Entry(20.0f, 1));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Stupidad");
        labels.add("Vaccate");

        PieDataSet dataSet = new PieDataSet(entries, "dataset");
        dataSet.setSliceSpace(5.0f);

        PieData pieData = new PieData(labels, dataSet);
        chart.setData(pieData);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
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
}
