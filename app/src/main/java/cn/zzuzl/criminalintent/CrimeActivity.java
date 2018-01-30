package cn.zzuzl.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String CRIME_ID = "crime_id";

    @Override
    protected Fragment createFragment() {
        return CrimeFragment.newInstance((UUID) getIntent().getSerializableExtra(CRIME_ID));
    }

    public static Intent newIntent(Context context, UUID uuid) {
        Intent intent = new Intent(context, CrimeActivity.class);
        intent.putExtra(CRIME_ID, uuid);
        return intent;
    }
}
