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
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.adapters.SubCategoriesAdapter;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.viewModels.SubCategoryFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragment extends Fragment {

    DataItem dataItem;
    private RecyclerView recy_categories;
    private SubCategoriesAdapter taskListAdap;
    private RecyclerView.LayoutManager layoutManager;
    private SubCategoryFragmentViewModel categoryFragmentViewModel;
    private ProgressDialog progressDialog;


    public SubCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deal, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        categoryFragmentViewModel = new ViewModelProvider(this).get(SubCategoryFragmentViewModel.class);
        recy_categories = view.findViewById(R.id.recy_categories);

        Bundle b = getArguments();
        dataItem = (DataItem) b.getSerializable(Constants.CATEGORY_OBJECT);
        categoryList(dataItem.getCategoryID());


    }

    private void categoryList(int catId) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching sub...");
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

//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable(Constants.SUB_CATEGORY_OBJECT, dataItem);
//                            Fragment fragment = new ProductListFragment();
//                            FragmentTransaction categoryFragmentTrans = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
//                            categoryFragmentTrans.replace(R.id.simpleFrameLayout, fragment);
//                       /*     categoryFragmentTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                            categoryFragmentTrans.addToBackStack("Sub Category");*/
//                            fragment.setArguments(bundle);
//                            categoryFragmentTrans.commit();

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
        recy_categories.setLayoutManager(layoutManager);
        recy_categories.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        taskListAdap = new SubCategoriesAdapter(getActivity(), resultItems);
        recy_categories.setAdapter(taskListAdap);
        taskListAdap.notifyDataSetChanged();
    }
}
