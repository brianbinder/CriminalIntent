package com.brianfakeurltwitter.criminalintent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

import static android.widget.CompoundButton.*;

public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private ImageButton mFirstCrimeButton;
    private ImageButton mLastCrimeButton;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getContext()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDatePretty());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        int currentPosition = CrimeLab.get(getContext()).getCrimePosition(mCrime.getId());
        Toast.makeText(getContext(), "Position: " + currentPosition, Toast.LENGTH_SHORT).show();

        mFirstCrimeButton = v.findViewById(R.id.first_crime_button);
        mFirstCrimeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CrimePagerActivity pager = (CrimePagerActivity) getContext();
                pager.setCurrentCrime(0);
            }
        });
        mFirstCrimeButton.setEnabled(CrimeLab.get(getContext()).getCrimePosition(mCrime.getId()) != 0);
        mLastCrimeButton = v.findViewById(R.id.last_crime_button);
        mLastCrimeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CrimePagerActivity pager = (CrimePagerActivity) getContext();
                pager.setCurrentCrime(CrimeLab.sCapacity - 1);
            }
        });
        mLastCrimeButton.setEnabled(CrimeLab.get(getContext()).getCrimePosition(mCrime.getId()) != CrimeLab.sCapacity - 1);

        return v;
    }
}
