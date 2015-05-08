package it.yuredd.spiccioli;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;
import java.util.ArrayList;

import it.yuredd.spiccioli.database.SpiccioliDB;
import it.yuredd.spiccioli.database.Transaction;


public class TransactionActivity extends ActionBarActivity {

    EditText descriptionText;
    EditText amountText;
    int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Intent intent = getIntent();
        String description = intent.getStringExtra("description");

        descriptionText = (EditText) findViewById(R.id.descriptionText);
        amountText = (EditText) findViewById(R.id.amountText);

        if(description != null) {
            SpiccioliDB db = new SpiccioliDB(this, null, null, 1);
            ArrayList<Transaction> transactions = db.findTransactions(description);
            if(transactions != null) {
                if(!transactions.isEmpty()) {
                    Transaction transaction = transactions.iterator().next();
                    descriptionText.setText(transaction.getDescription());
                    amountText.setText(transaction.getAmount().toPlainString());
                }
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaction, menu);
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

    public void saveTransaction(View view) {
        SpiccioliDB spiccioliDB = new SpiccioliDB(this, null, null, 1);

        Transaction transaction = new Transaction(descriptionText.getText().toString(), new BigDecimal(amountText.getText().toString()));

        spiccioliDB.addTransaction(transaction);

        spiccioliDB.close();

        Intent resultData = new Intent();
        //resultData.putExtra("newid",2);
        setResult(Activity.RESULT_OK);
        finish();

    }

    public void deleteTransaction(View view) {
        SpiccioliDB spiccioliDB = new SpiccioliDB(this, null, null, 1);
        spiccioliDB.deleteTransaction(descriptionText.getText().toString());
        spiccioliDB.close();

        Intent resultData = new Intent();
        //resultData.putExtra("newid",2);
        setResult(Activity.RESULT_OK);
        finish();

    }
}
