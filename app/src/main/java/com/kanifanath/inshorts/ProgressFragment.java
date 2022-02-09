package com.kanifanath.inshorts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class ProgressFragment extends Fragment {

    public static Fragment newInstance() {
        return new ProgressFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_bar,parent, false);
        return view;
    }
}
