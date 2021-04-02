package com.semicolons.masco.pk.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.adapters.AllDealsAdapter;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.viewModels.SubCategoryFragmentViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllDealsFragment extends Fragment {

    ProgressDialog progressDialog;
    private RecyclerView recy_deal;
    private AllDealsAdapter allDealsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SubCategoryFragmentViewModel categoryFragmentViewModel;


    public AllDealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_deals, container, false);

        initViews(view);

        dealList(8);


        return view;

    }

    private void dealList(int catId) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching Deals...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        categoryFragmentViewModel.getAllSubCategories(catId).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {
                progressDialog.dismiss();
                if (allBookingListResponseDM != null) {
                    progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();
                        progressDialog.dismiss();
                        if (resultItems.size() > 0) {
                            progressDialog.dismiss();

                            prepareListData(resultItems);
                        } else {
//                            Intent intent = new Intent(SubCategoryActivity.this, ProductListActivity.class);
//                            intent.putExtra(Constants.SUB_CATEGORY_OBJECT, dataItem);
//                            startActivity(intent);
//                            finish();

                            progressDialog.dismiss();

                        }
                    }
                } else {
                    progressDialog.dismiss();
                }

            }
        });

    }

    private void prepareListData(List<DataItem> resultItems) {

        layoutManager = new GridLayoutManager(getActivity(), 2);
        recy_deal.setLayoutManager(layoutManager);
        recy_deal.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        allDealsAdapter = new AllDealsAdapter(getActivity(), resultItems);
        recy_deal.setAdapter(allDealsAdapter);
        allDealsAdapter.notifyDataSetChanged();
    }

    public void initViews(View view) {

        categoryFragmentViewModel = new ViewModelProvider(this).get(SubCategoryFragmentViewModel.class);
        recy_deal = view.findViewById(R.id.recy_Deal);

    }
}
