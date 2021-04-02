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
import com.semicolons.masco.pk.adapters.CategoriesAdapter;
import com.semicolons.masco.pk.adapters.MainCategoryAdapter;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.viewModels.CategoryFragmentViewModel;
import com.semicolons.masco.pk.viewModels.SubCategoryFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    int grocery_id = 60, personal_care_id = 87, stationery_id = 90, snacks_id = 94, sports_id = 87, clean_id = 165, cosmetics_id = 91, baby_care_id = 88, food_id = 93,
            fragrances_id = 98, garments_id = 92, undergarments_id = 96, tailoring_material_id = 97, jewellery_id = 95, uncategorized_id = 15;
    private RecyclerView recy_grocery, recy_personal_care,
            recy_Stationery, recy_Snacks,
            recy_Sports, recy_baby_care, recy_cosmetics,
            recy_Food, recy_cleans, recy_fragrances, recy_garments, recy_undergarments, recy_tailoring_material, recy_jewellery, recy_uncategorized;
    private SubCategoryFragmentViewModel subCategoryFragmentViewModel;
    private ProgressDialog progressDialog;
    // private RecyclerView.LayoutManager layoutManager;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deal, container, false);

        initViews(view);

     /*   subCategoryGrocery(grocery_id);
        subCategoryPersonalCare(personal_care_id);
        subCategoryStaionary(stationery_id);
        subCategorySnacks(snacks_id);
        subCategorySports(sports_id);

        subCategoryClean(clean_id);
        subCategoryCosmetic(cosmetics_id);
        subCategoryBabyCare(baby_care_id);
        subCategoryFood(food_id);
        subCategoryFragrances(fragrances_id);
        subCategoryGarments(garments_id);
        subCategoryUnderGarments(undergarments_id);
        subCategorytailoring(tailoring_material_id);
        subCategoryJewelery(jewellery_id);
        subCategoryNA(uncategorized_id);*/

        return view;
    }

    private void initViews(View view) {
        // categoryFragmentViewModel = new ViewModelProvider(this).get(CategoryFragmentViewModel.class);
        subCategoryFragmentViewModel = new ViewModelProvider(this).get(SubCategoryFragmentViewModel.class);
        recy_grocery = view.findViewById(R.id.recy_grocery);
        recy_personal_care = view.findViewById(R.id.recy_personal_care);
        recy_Stationery = view.findViewById(R.id.recy_Stationery);
        recy_Snacks = view.findViewById(R.id.recy_Snacks);
        recy_Sports = view.findViewById(R.id.recy_Sports);
        recy_cleans = view.findViewById(R.id.recy_cleans);
        recy_cosmetics = view.findViewById(R.id.recy_cosmetics);
        recy_baby_care = view.findViewById(R.id.recy_baby_care);
        recy_Food = view.findViewById(R.id.recy_Food);
        recy_fragrances = view.findViewById(R.id.recy_fragrances);
        recy_garments = view.findViewById(R.id.recy_garments);
        recy_undergarments = view.findViewById(R.id.recy_undergarments);
        recy_tailoring_material = view.findViewById(R.id.recy_tailoring_material);
        recy_jewellery = view.findViewById(R.id.recy_jewellery);
        recy_uncategorized = view.findViewById(R.id.recy_uncategorized);


    }

   /* private void subCategoryGrocery(int id) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        subCategoryFragmentViewModel.getAllSubCategories(id).observe(getActivity(), new Observer<CategoryDM>() {
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

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_grocery.setLayoutManager(layoutManager);
                            recy_grocery.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter    taskListAdap = new CategoriesAdapter(getActivity(), resultItems);
                            recy_grocery.setAdapter(taskListAdap);
                            taskListAdap.notifyDataSetChanged();

                        }
                    }
                } else {
                    progressDialog.dismiss();
                }

            }
        });

    }

    private void subCategoryPersonalCare(int id) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        subCategoryFragmentViewModel.getAllSubCategories(id).observe(getActivity(), new Observer<CategoryDM>() {
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

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_personal_care.setLayoutManager(layoutManager);
                            recy_personal_care.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter   taskListAdap = new CategoriesAdapter(getActivity(), resultItems);
                            recy_personal_care.setAdapter(taskListAdap);
                            taskListAdap.notifyDataSetChanged();

                        }
                    }
                } else {
                    progressDialog.dismiss();
                }

            }
        });

    }

    private void subCategoryStaionary(int id) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        subCategoryFragmentViewModel.getAllSubCategories(id).observe(getActivity(), new Observer<CategoryDM>() {
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

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_Stationery.setLayoutManager(layoutManager);
                            recy_Stationery.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter   taskListAdap = new CategoriesAdapter(getActivity(), resultItems);
                            recy_Stationery.setAdapter(taskListAdap);

                        }
                    }
                } else {
                    progressDialog.dismiss();
                }

            }
        });

    }

    private void subCategorySnacks(int id) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        subCategoryFragmentViewModel.getAllSubCategories(id).observe(getActivity(), new Observer<CategoryDM>() {
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

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_Snacks.setLayoutManager(layoutManager);
                            recy_Snacks.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter  taskListAdap = new CategoriesAdapter(getActivity(), resultItems);
                            recy_Snacks.setAdapter(taskListAdap);

                        }
                    }
                } else {
                    progressDialog.dismiss();
                }

            }
        });

    }

    private void subCategorySports(int id) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        subCategoryFragmentViewModel.getAllSubCategories(id).observe(getActivity(), new Observer<CategoryDM>() {
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

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_Sports.setLayoutManager(layoutManager);
                            recy_Sports.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter taskListAdap = new CategoriesAdapter(getActivity(), resultItems);
                            recy_Sports.setAdapter(taskListAdap);

                        }
                    }
                } else {
                    progressDialog.dismiss();
                }

            }
        });

    }

    private void subCategoryClean(int id) {

        subCategoryFragmentViewModel.getAllSubCategories1(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {

                if (allBookingListResponseDM != null) {

                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();

                        if (resultItems.size() > 0) {

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_cleans.setLayoutManager(layoutManager);
                            recy_cleans.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter  taskListAdap1 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_cleans.setAdapter(taskListAdap1);
                            taskListAdap1.notifyDataSetChanged();


                        }
                    }
                }
            }
        });

    }

    private void subCategoryCosmetic(int id) {

        subCategoryFragmentViewModel.getAllSubCategories2(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {

                if (allBookingListResponseDM != null) {

                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();

                        if (resultItems.size() > 0) {


                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_cosmetics.setLayoutManager(layoutManager);
                            recy_cosmetics.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter    taskListAdap2 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_cosmetics.setAdapter(taskListAdap2);
                            taskListAdap2.notifyDataSetChanged();


                        }
                    }
                }

            }
        });

    }

    private void subCategoryBabyCare(int id) {

        subCategoryFragmentViewModel.getAllSubCategories3(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {

                if (allBookingListResponseDM != null) {

                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();

                        if (resultItems.size() > 0) {

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_baby_care.setLayoutManager(layoutManager);
                            recy_baby_care.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter      taskListAdap2 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_baby_care.setAdapter(taskListAdap2);
                            taskListAdap2.notifyDataSetChanged();


                        }
                    }
                }

            }
        });

    }

    private void subCategoryFood(int id) {

        subCategoryFragmentViewModel.getAllSubCategories4(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {

                if (allBookingListResponseDM != null) {

                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();

                        if (resultItems.size() > 0) {

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_Food.setLayoutManager(layoutManager);
                            recy_Food.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter    taskListAdap2 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_Food.setAdapter(taskListAdap2);
                            taskListAdap2.notifyDataSetChanged();


                        }
                    }
                }

            }
        });

    }

    private void subCategoryFragrances(int id) {

        subCategoryFragmentViewModel.getAllSubCategories5(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {
                // progressDialog.dismiss();
                if (allBookingListResponseDM != null) {
                    //   progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();
                        //     progressDialog.dismiss();
                        if (resultItems.size() > 0) {
                            //       progressDialog.dismiss();

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_fragrances.setLayoutManager(layoutManager);
                            recy_fragrances.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter   taskListAdap2 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_fragrances.setAdapter(taskListAdap2);
                            taskListAdap2.notifyDataSetChanged();


                        }
                    }
                }

            }
        });

    }

    private void subCategoryGarments(int id) {

        subCategoryFragmentViewModel.getAllSubCategories7(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {

                if (allBookingListResponseDM != null) {
                    //   progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();

                        if (resultItems.size() > 0) {


                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_garments.setLayoutManager(layoutManager);
                            recy_garments.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter taskListAdap2 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_garments.setAdapter(taskListAdap2);
                            taskListAdap2.notifyDataSetChanged();


                        }
                    }
                }

            }
        });

    }

    private void subCategoryUnderGarments(int id) {

        subCategoryFragmentViewModel.getAllSubCategories7(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {

                if (allBookingListResponseDM != null) {
                    //   progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();

                        if (resultItems.size() > 0) {


                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_undergarments.setLayoutManager(layoutManager);
                            recy_garments.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter  taskListAdap2 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_garments.setAdapter(taskListAdap2);
                            taskListAdap2.notifyDataSetChanged();


                        }
                    }
                }

            }
        });

    }

    private void subCategorytailoring(int id) {

        subCategoryFragmentViewModel.getAllSubCategories7(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {

                if (allBookingListResponseDM != null) {
                    //   progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();

                        if (resultItems.size() > 0) {


                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_tailoring_material.setLayoutManager(layoutManager);
                            recy_tailoring_material.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter  taskListAdap2 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_tailoring_material.setAdapter(taskListAdap2);
                            taskListAdap2.notifyDataSetChanged();


                        }
                    }
                }

            }
        });

    }


    private void subCategoryNA(int id) {

        subCategoryFragmentViewModel.getAllSubCategories6(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {
                // progressDialog.dismiss();
                if (allBookingListResponseDM != null) {
                    //   progressDialog.dismiss();
                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();
                        //     progressDialog.dismiss();
                        if (resultItems.size() > 0) {
                            //       progressDialog.dismiss();

                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_uncategorized.setLayoutManager(layoutManager);
                            recy_uncategorized.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter    taskListAdap2 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_uncategorized.setAdapter(taskListAdap2);
                            taskListAdap2.notifyDataSetChanged();


                        }
                    }
                }

            }
        });

    }


    private void subCategoryJewelery(int id) {

        subCategoryFragmentViewModel.getAllSubCategories8(id).observe(getActivity(), new Observer<CategoryDM>() {
            @Override
            public void onChanged(CategoryDM allBookingListResponseDM) {

                if (allBookingListResponseDM != null) {

                    if (allBookingListResponseDM.getData() != null) {
                        List<DataItem> resultItems = allBookingListResponseDM.getData();

                        if (resultItems.size() > 0) {


                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                            recy_jewellery.setLayoutManager(layoutManager);
                            recy_jewellery.addItemDecoration(new GridSpacingItemDecoration(4, 3, false));
                            CategoriesAdapter      taskListAdap2 = new CategoriesAdapter(getActivity(), resultItems);
                            recy_jewellery.setAdapter(taskListAdap2);
                            taskListAdap2.notifyDataSetChanged();


                        }
                    }
                }

            }
        });

    }*/


}
