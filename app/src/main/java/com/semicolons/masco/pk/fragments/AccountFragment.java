package com.semicolons.masco.pk.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.uiActivities.OrderActivity;
import com.semicolons.masco.pk.uiActivities.PointsActivity;
import com.semicolons.masco.pk.uiActivities.ProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private CardView cardViewProfile;
    private CardView cardViewMyClub;
    private CardView cardViewOrder;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_account, container, false);
        initViews(view);

        return view;
    }

    private void initViews(View view) {


        cardViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        cardViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });

        cardViewMyClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), PointsActivity.class));
            }
        });
    }
}
