package com.semicolons.masco.pk.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.dataModels.Product;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    private Product product;
    private ImageView expandedImage;
    private TextView tv_product_title;
    private TextView tv_price;
    private TextView tv_product_description;
    private TextView tv_write_your_review;

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        expandedImage = view.findViewById(R.id.expandedImage);
        tv_product_title = view.findViewById(R.id.tv_product_title);
        tv_price = view.findViewById(R.id.tv_price);
        tv_product_description = view.findViewById(R.id.tv_product_description);
        tv_write_your_review = view.findViewById(R.id.tv_write_your_review);

        Bundle b = getArguments();
        assert b != null;
        product = (Product) b.getSerializable(Constants.PRODUCT_OBJECT);

        RequestOptions options = new RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .priority(Priority.HIGH);
        Glide
                .with(getActivity())
                .load(product.getImage_name())
                .apply(options)
                .into(expandedImage);

        tv_product_title.setText(product.getProduct_title());
        tv_product_description.setText(product.getProduct_description());
        tv_price.setText("RS " + product.getPrice());

        tv_write_your_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
