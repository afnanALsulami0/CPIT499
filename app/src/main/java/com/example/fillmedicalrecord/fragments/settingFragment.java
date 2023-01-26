package com.example.fillmedicalrecord.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.activites.SplashActivity;
import com.example.fillmedicalrecord.activites.complete_user_dataActivity;

public class settingFragment extends Fragment {

    Button btn,logout;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        sharedPreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        getActivity().setTitle("Setting");

        btn = rootView.findViewById(R.id.next_btn);
        logout = rootView.findViewById(R.id.logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), complete_user_dataActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                startActivity(new Intent(requireContext(), SplashActivity.class));
                requireActivity().finish();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}