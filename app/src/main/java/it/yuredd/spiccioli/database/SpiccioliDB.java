package it.yuredd.spiccioli.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Elia on 03/05/2015.
 */
public class SpiccioliDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "spiccioliDB";
    private static final String TABLE_TRANSACTIONS = "transactions";

    private static final String TRANSACTIONS_COLUMN_ID = "_id";
    private static final String TRANSACTIONS_COLUMN_DESCRIPTION = "_description";
    private static final String TRANSACTIONS_COLUMN_AMOUNT = "_amount";

    public SpiccioliDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " +
                TABLE_TRANSACTIONS + "("
                + TRANSACTIONS_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + TRANSACTIONS_COLUMN_DESCRIPTION + " TEXT,"
                + TRANSACTIONS_COLUMN_AMOUNT + " TEXT"
                + ")";
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    public void addTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(TRANSACTIONS_COLUMN_DESCRIPTION, transaction.getDescription());
        values.put(TRANSACTIONS_COLUMN_AMOUNT, transaction.getAmount().toPlainString());

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.insertOrThrow(TABLE_TRANSACTIONS, null, values);
            Log.i("addTransaction", "Insertion ok.");
        } catch (Exception ex) {
            Log.e("addTransaction", ex.getMessage());
        }

        db.close();
    }


    public ArrayList<Transaction> findTransactions() {
        return findTransactions(null);
    }

    public ArrayList<Transaction> findTransactions(String transactionDescription) {

        String query = "SELECT * FROM " + TABLE_TRANSACTIONS;

        if(transactionDescription != null) {
            query = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANSACTIONS_COLUMN_DESCRIPTION + " = \"" + transactionDescription + "\"";
        }


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        if(cursor.moveToFirst()) {
            Log.d("SpiccioliDB", "Found #" + cursor.getCount() + " transactions.");
            do {
                Transaction transaction = new Transaction();
                transaction.setID(Integer.parseInt(cursor.getString(0)));
                transaction.setDescription(cursor.getString(1));
                transaction.setAmount(new BigDecimal(cursor.getString(2)));
                transactions.add(transaction);
            } while(cursor.moveToNext());
            cursor.close();
        } else {
            transactions = null;
        }

        db.close();
        return transactions;
    }

    public boolean deleteTransaction(String transactionDescription) {
        boolean result = false;

        String query = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANSACTIONS_COLUMN_DESCRIPTION + " = \"" + transactionDescription + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Transaction transaction = new Transaction();

        if(cursor.moveToFirst()) {
            transaction.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_TRANSACTIONS, TRANSACTIONS_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(transaction.getID())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
