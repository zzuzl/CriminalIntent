package cn.zzuzl.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhanglei53 on 2018/1/30.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = CrimeBaseHelper.class.getName();
    private static final int VERSION = 1;
    private static final String DB_NAME = "crime.db";

    public CrimeBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "CrimeBaseHelper.onCreate");
        db.execSQL("create table " + CrimeDbSchema.CrimeTable.NAME + "("
                + "_id integer primary key autoincrement," + CrimeDbSchema.CrimeTable.Cols.UUID
                + "," + CrimeDbSchema.CrimeTable.Cols.TITLE
                + "," + CrimeDbSchema.CrimeTable.Cols.DATE
                + "," + CrimeDbSchema.CrimeTable.Cols.SOLVED + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "CrimeBaseHelper.onUpgrade");
    }
}
