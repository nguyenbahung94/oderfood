package com.example.nbhung.appfood.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.nbhung.appfood.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbhung on 9/14/2017.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "EditTable";
    private static final int DB_VER = 1;
    private Context context;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
    }

    public List<Order> getCards() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                {
                    result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                            c.getString(c.getColumnIndex("ProductName")),
                            c.getString(c.getColumnIndex("Quantity")),
                            c.getString(c.getColumnIndex("Price")),
                            c.getString(c.getColumnIndex("Discount"))));
                }
            } while (c.moveToNext());
        }
        return result;
    }

    public void copyDatabase() throws IOException {
        //  SQLiteDatabase checkDB = SQLiteDatabase.openDatabase(context.getAssets().+(DATABASE_NAME), null, SQLiteDatabase.OPEN_READONLY);
        //  checkDB.close();
        InputStream myInput = context.getAssets().open(DB_NAME);
        String outFileName = "/data/data/" + context.getPackageName() + "/databases/" + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void addToCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s,'%s');",
                order.getProductId(), order.getProductName(), order.getQuantity(), order.getPrice(), order.getDiscount());
        db.execSQL(query);
    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
}
