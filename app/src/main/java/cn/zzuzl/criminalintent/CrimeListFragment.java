package cn.zzuzl.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeListFragment extends Fragment {
    private static final String TAG = CrimeListFragment.class.getName();
    private RecyclerView mRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 有菜单按钮
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        List<Crime> crimeList = CrimeLab.get(getActivity()).getCrimeList();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimeList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(crimeList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);

                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getUUID());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ViewHolder
     */
    private class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private TextView mDate;
        private ImageView mImageView;
        private Crime mCrime;

        public CrimeViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.crime_item, parent, false));
            mTitle = itemView.findViewById(R.id.crime_title);
            mDate = itemView.findViewById(R.id.crime_date);
            mImageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitle.setText(crime.getTitle());
            mDate.setText(crime.getDate().toString());
            mImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            // Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getUUID());
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getUUID());
            startActivity(intent);
        }
    }

    /**
     * Adapter
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeViewHolder> {
        private List<Crime> mCrimes = null;

        public CrimeAdapter(List<Crime> crimes) {
            if (crimes == null) {
                crimes = new ArrayList<Crime>();
            }
            mCrimes = crimes;
        }

        @Override
        public CrimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            Log.d(TAG, "onCreateViewHolder");

            return new CrimeViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder");
            holder.bind(mCrimes.get(position));
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        /**
         * 更新列表
         * @param crimes
         */
        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }
    }

}
