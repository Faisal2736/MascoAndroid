package com.semicolons.masco.pk.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.adapters.ProductsListAdapter;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.viewModels.ProductListFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    private RecyclerView recy_categories;
    private ProductsListAdapter taskListAdap;
    private RecyclerView.LayoutManager layoutManager;
    private ProductListFragmentViewModel categoryFragmentViewModel;
    private ProgressDialog progressDialog;
    private TextView tv_no_booking;

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        categoryFragmentViewModel = new ViewModelProvider(this).get(ProductListFragmentViewModel.class);
        recy_categories = view.findViewById(R.id.recy_categories);
        tv_no_booking = view.findViewById(R.id.tv_no_booking);


        Bundle b = getArguments();
        DataItem dataItem = (DataItem) b.getSerializable(Constants.SUB_CATEGORY_OBJECT);
        categoryList(dataItem.getCategoryID());


    }

    private void categoryList(int catId) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching products...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        categoryFragmentViewModel.getProductsList(catId,1).observe(getActivity(), new Observer<TopSellingResponse>() {
            @Override
            public void onChanged(TopSellingResponse allBookingListResponseDM) {
                progressDialog.dismiss();
                if (allBookingListResponseDM != null) {
                    progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<Product> resultItems = allBookingListResponseDM.getData();
                        progressDialog.dismiss();
                        if (resultItems.size() > 0) {
                            progressDialog.dismiss();

                            prepareListData(resultItems);
                        } else {
                            tv_no_booking.setText("Not available");
                            progressDialog.dismiss();
                            tv_no_booking.setVisibility(View.VISIBLE);

                        }
                    }
                } else {
                    progressDialog.dismiss();
                }

            }
        });

    }

    private void prepareListData(List<Product> resultItems) {

        layoutManager = new GridLayoutManager(getActivity(), 2);
        recy_categories.setLayoutManager(layoutManager);
        recy_categories.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        //taskListAdap = new ProductsListAdapter(getActivity(), resultItems);
        recy_categories.setAdapter(taskListAdap);
        taskListAdap.notifyDataSetChanged();
    }
}
