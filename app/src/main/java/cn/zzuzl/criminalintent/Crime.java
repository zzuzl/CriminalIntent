package cn.zzuzl.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhanglei53 on 2018/1/24.
 */

public class Crime {
    private UUID mUUID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mUUID = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
