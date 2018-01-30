package cn.zzuzl.criminalintent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.Serializable;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeFragment extends Fragment {
    private static final String TAG = CrimeFragment.class.getName();
    private static final String CRIME_ID = "crime_id";

    private Crime mCrime;
    private EditText mEditText;
    private Button mButton;
    private CheckBox mCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID uuid = (UUID) getArguments().getSerializable(CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(uuid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mEditText = view.findViewById(R.id.crime_title);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditText.setText(mCrime.getTitle());

        mButton = view.findViewById(R.id.crime_date);
        mButton.setText(mCrime.getDate().toString());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerFragment().show(getFragmentManager(), "Date Picker");
            }
        });

        mCheckBox = view.findViewById(R.id.crime_solved);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        mCheckBox.setChecked(mCrime.isSolved());

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        // 把修改保存到数据库
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    public static CrimeFragment newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(CRIME_ID, uuid);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

}
