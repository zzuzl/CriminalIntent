package cn.zzuzl.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by zhanglei53 on 2018/1/24.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
