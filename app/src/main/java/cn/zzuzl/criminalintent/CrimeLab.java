package cn.zzuzl.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhanglei53 on 2018/1/24.
 */

public class CrimeLab {
    private Context mContext;
    private static CrimeLab sCrimeLab = null;
    private SQLiteDatabase mDatabase = null;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        // 过去数据库，如果没有则新建一个
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    /**
     * get
     * @param uuid
     * @return
     */
    public Crime getCrime(UUID uuid) {
        List<Crime> crimes = queryCrimes(CrimeDbSchema.CrimeTable.Cols.UUID + "=?", new String[]{uuid.toString()});
        for (Crime crime : crimes) {
            if (crime.getUUID().toString().equals(uuid.toString())) {
                return crime;
            }
        }
        return null;
    }

    /**
     * insert
     *
     * @param crime
     */
    public void addCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME, null, values);
    }

    /**
     * update
     *
     * @param crime
     */
    public void updateCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeDbSchema.CrimeTable.NAME, values,
                CrimeDbSchema.CrimeTable.Cols.UUID + "=?",
                new String[]{crime.getUUID().toString()});
    }

    /**
     * query
     *
     * @param where
     * @param whereArgs
     * @return
     */
    public List<Crime> queryCrimes(String where, String[] whereArgs) {
        List<Crime> crimes = new ArrayList<Crime>();
        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,  // table
                null,  // cols
                where,  // where
                whereArgs,  // where args
                null,  // group by
                null,  // having
                null  // orderBy
        );

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CursorWrapper cursorWrapper = new CursorWrapper(cursor);
                String uuid = cursorWrapper.getString(cursorWrapper.getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
                String title = cursorWrapper.getString(cursorWrapper.getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE));
                long date = cursorWrapper.getLong(cursorWrapper.getColumnIndex(CrimeDbSchema.CrimeTable.Cols.DATE));
                int solved = cursorWrapper.getInt(cursorWrapper.getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED));

                Crime crime = new Crime();
                crime.setUUID(UUID.fromString(uuid));
                crime.setTitle(title);
                crime.setDate(new Date(date));
                crime.setSolved(solved != 0);
                crimes.add(crime);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return crimes;
    }

    /**
     * 包装crime为contentValues
     *
     * @param crime
     * @return
     */
    private static ContentValues getContentValues(Crime crime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeDbSchema.CrimeTable.Cols.UUID, crime.getUUID().toString());
        contentValues.put(CrimeDbSchema.CrimeTable.Cols.TITLE, crime.getTitle());
        contentValues.put(CrimeDbSchema.CrimeTable.Cols.DATE, crime.getDate().getTime());
        contentValues.put(CrimeDbSchema.CrimeTable.Cols.SOLVED, crime.isSolved());

        return contentValues;
    }

    public List<Crime> getCrimeList() {
        return queryCrimes(null, null);
    }
}
