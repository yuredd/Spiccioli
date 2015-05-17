package it.yuredd.spiccioli;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Iterator;

import it.yuredd.spiccioli.database.SpiccioliDB;
import it.yuredd.spiccioli.database.Transaction;


public class ReportActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        PieChart chart = (PieChart) findViewById(R.id.chart);

        chart.setCenterText("$$$$");

        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(20.0f, 1));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Vaccate");




        SpiccioliDB spiccioliDB = new SpiccioliDB(this, null, null, 1);
        ArrayList<Transaction> transactions = spiccioliDB.findTransactions();

        if(transactions != null) {
            Iterator<Transaction> iter = transactions.iterator();
            while (iter.hasNext()) {
                Transaction transaction = iter.next();

                entries.add(new Entry(transaction.getAmount().floatValue(), entries.size()));
                labels.add(transaction.getDescription());

            }
        } else {
            Log.d("refreshList", "No items from DB");
        }




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
