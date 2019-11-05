package com.brianfakeurltwitter.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    public static final int sCapacity = 3;

    private List<Crime> mCrimes;
    private HashMap<UUID, Crime> mCrimeMap = new HashMap<>(sCapacity + 100);
    private HashMap<UUID, Integer> mCrimeIndex = new HashMap<>(sCapacity + 100);
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < sCapacity; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            addCrime(crime);
        }
    }

    public void addCrime(Crime crime) {
        mCrimeMap.put(crime.getId(), crime);
        mCrimeIndex.put(crime.getId(), mCrimes.size());
        mCrimes.add(crime);
    }

    public void deleteCrime(UUID crime) {
        int target = mCrimeIndex.get(crime);
        mCrimes.remove(target);
        mCrimeMap.remove(crime);
        remapCrimes();
    }

    public void remapCrimes() {
        mCrimeIndex = new HashMap<>(sCapacity + 100);
        for (int i = 0; i < mCrimes.size(); i++) {
            mCrimeIndex.put(mCrimes.get(i).getId(), i);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        return mCrimeMap.get(id);
    }

    public int getCrimePosition(UUID id) {
        return mCrimeIndex.get(id);
    }
}
