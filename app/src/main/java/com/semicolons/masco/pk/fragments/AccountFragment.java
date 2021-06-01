package com.semicolons.masco.pk.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.databinding.FragmentAccount2Binding;

public class AccountFragment extends Fragment {

    private FragmentAccount2Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccount2Binding.inflate(getLayoutInflater(), container, false);

        getActivity().findViewById(R.id.rlSearch).setVisibility(View.GONE);

        return binding.getRoot();
    }
}