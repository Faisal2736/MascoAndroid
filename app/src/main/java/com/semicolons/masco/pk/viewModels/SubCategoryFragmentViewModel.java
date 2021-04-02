package com.semicolons.masco.pk.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.repository.DealMallRepository;


public class SubCategoryFragmentViewModel extends AndroidViewModel {
    DealMallRepository dealMallRepositoryItem;


    public SubCategoryFragmentViewModel(@NonNull Application application) {
        super(application);
        dealMallRepositoryItem = new DealMallRepository(application);

    }
    public LiveData<CategoryDM> getAllSubCategories(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories(cat_id);
    }


    public LiveData<CategoryDM> getAllSubCategoriesGrocery(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories(cat_id);
    }

    public LiveData<CategoryDM> subCategoryPersonalCare(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories1(cat_id);
    }

    public LiveData<CategoryDM> subCategoryStaionary(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories2(cat_id);
    }

    public LiveData<CategoryDM> subCategorySnacks(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories3(cat_id);
    }

    public LiveData<CategoryDM> subCategorySports(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories4(cat_id);
    }

    public LiveData<CategoryDM> subCategoryClean(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories5(cat_id);
    }

    public LiveData<CategoryDM> subCategoryCosmetic(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories6(cat_id);
    }

    public LiveData<CategoryDM> subCategoryBabyCare(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories7(cat_id);
    }

    public LiveData<CategoryDM> subCategoryFragrances(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories8(cat_id);
    }
    public LiveData<CategoryDM> subCategoryFood(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories9(cat_id);
    }

    public LiveData<CategoryDM> subCategoryGarments(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories10(cat_id);
    }
    public LiveData<CategoryDM> subCategoryUnderGarments(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories11(cat_id);
    }
    public LiveData<CategoryDM> subCategorytailoring(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories12(cat_id);
    }
    public LiveData<CategoryDM> subCategoryNA(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories13(cat_id);
    }

    public LiveData<CategoryDM> subCategoryJewelery(int cat_id) {

        return dealMallRepositoryItem.getAllSubCategories14(cat_id);
    }
}
