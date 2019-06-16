package com.example.caojunsheng.hfutnews;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by caojunsheng on 2017/4/10.
 */

public class AboutusFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View AboutusLayout = inflater.inflate(R.layout.aboutus_layout, container, false);
        return AboutusLayout;
    }
}
