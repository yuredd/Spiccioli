package it.yuredd.spiccioli;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import it.yuredd.spiccioli.database.SpiccioliDB;
import it.yuredd.spiccioli.database.Transaction;


public class SpiccioliActivity extends ActionBarActivity {

    ListView transactionList;
    ListAdapter transactionListAdapter;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiccioli);
        transactionList = (ListView) findViewById(R.id.transactionList);
        transactionListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        transactionList.setAdapter(transactionListAdapter);
        refreshTransactionsList();
        transactionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String description = ((TextView) view).getText().toString();

                loadTransaction(description);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spiccioli, menu);
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

    public void loadTransaction(String description) {
        Intent intent = new Intent(SpiccioliActivity.this, TransactionActivity.class);
        intent.putExtra("description", description);
        SpiccioliActivity.this.startActivityForResult(intent, 1);
    }

    public void addTransaction(View view) {
        Intent intent = new Intent(SpiccioliActivity.this, TransactionActivity.class);
        //intent.putExtra("_id", 2);
        SpiccioliActivity.this.startActivityForResult(intent, 1);
    }

    public void refreshTransactionsList() {

        SecureRandom random = new SecureRandom();

        list.clear();
//        list.add("asd-" + new BigInteger(130, random).toString(5));

        SpiccioliDB spiccioliDB = new SpiccioliDB(this, null, null, 1);
        ArrayList<Transaction> transactions = spiccioliDB.findTransactions();

        if(transactions != null) {
            Iterator<Transaction> iter = transactions.iterator();
            while (iter.hasNext()) {
                Transaction transaction = iter.next();
                list.add(transaction.getDescription());
            }
        } else {
            Log.d("refreshList", "No items from DB");
        }

        ((BaseAdapter) transactionListAdapter).notifyDataSetChanged();

        spiccioliDB.close();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            //String newid = data.getStringExtra("newid");
        }
        refreshTransactionsList();
    }

}
