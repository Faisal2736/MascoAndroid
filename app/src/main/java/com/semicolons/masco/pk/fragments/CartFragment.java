package com.semicolons.masco.pk.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.adapters.CartProductsAdapter;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartProduct;
import com.semicolons.masco.pk.dataModels.CartProductResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import com.semicolons.masco.pk.uiActivities.CheckoutActivity;
import com.semicolons.masco.pk.viewModels.CartViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements CartProductsAdapter.onCartItemClickListener {

    private RecyclerView recyclerView;
    private CartViewModel cartViewModel;
    private TextView cartPageTotalAmountTextView;
    private TextView cartPageDiscountTextView;
    private TextView cartPageGrandTotaTextView;
    private ProgressDialog progressDialog;
    private CartProductsAdapter cartProductsAdapter;
    private Button proceedButton;
    private Context context;
    private SharedPreferences sharedPreferences;
    private List<CartProduct> cartProductList;
    private int userId;
    private int productQuantity;
    private int ProductId;
    private int pos;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        initViews(view);

        return view;
    }


    private void initViews(View view) {
        context=getActivity();
        cartPageTotalAmountTextView=view.findViewById(R.id.cartPageTotalAmountTextView);
        cartPageDiscountTextView=view.findViewById(R.id.cartPageDiscountTextView);
        cartPageGrandTotaTextView=view.findViewById(R.id.cartPageGrandTotalTextView);
        proceedButton=view.findViewById(R.id.cartProceedButton);
        recyclerView=view.findViewById(R.id.rvCartProducts);

        sharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        boolean isLogin=sharedPreferences.getBoolean(Constants.USER_IS_LOGIN,false);

        cartViewModel=new ViewModelProvider(this).get(CartViewModel.class);

        initListener();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(isLogin){
            userId=sharedPreferences.getInt(Constants.USER_ID,0);
            getCartList();
        }else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(),"Please Login",Toast.LENGTH_SHORT).show();
        }
    }

    public void getCartList(){
        cartViewModel.getCartList(userId).observe(getActivity(), new Observer<CartProductResponse>() {
            @Override
            public void onChanged(CartProductResponse cartProductResponse) {
                progressDialog.dismiss();
                if(cartProductResponse!=null){
                    progressDialog.dismiss();
                    if(cartProductResponse.getData()!=null){
                        progressDialog.dismiss();

                        cartProductList=cartProductResponse.getData();
                        cartPageTotalAmountTextView.setText(cartProductResponse.getSubPrice());
                        cartPageDiscountTextView.setText(cartProductResponse.getDiscount10());
                        cartPageGrandTotaTextView.setText(cartProductResponse.getTotal());

                        prepareCartProductList(cartProductResponse.getData());


                    }else {
                        progressDialog.dismiss();
                        cartPageTotalAmountTextView.setText(cartProductResponse.getSubPrice());
                        cartPageDiscountTextView.setText(cartProductResponse.getDiscount10());
                        cartPageGrandTotaTextView.setText(cartProductResponse.getTotal());
                        ArrayList<CartProduct> list=new ArrayList<>();
                        prepareCartProductList(list);
                        Toast.makeText(context,"No Data Found",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Server not responding",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void prepareCartProductList(List<CartProduct> cartProductList) {
        cartProductsAdapter=new CartProductsAdapter(getActivity(),cartProductList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(cartProductsAdapter);
        cartProductsAdapter.notifyDataSetChanged();
    }

    public void initListener(){

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onUpdateCart(int productId, int quantity,int position,boolean isCartAdded) {

        productQuantity=quantity;
        ProductId=productId;
        pos=position;
        progressDialog.show();
        cartViewModel.updateCart(productId,quantity,userId).observe(this, new Observer<UpdateCartResponse>() {
            @Override
            public void onChanged(UpdateCartResponse updateCartResponse) {
                progressDialog.dismiss();
                if(updateCartResponse!=null){
                    progressDialog.dismiss();
                    if(updateCartResponse.getData()!=null){
                        progressDialog.dismiss();
                        if(updateCartResponse.getData().size()>0){
                            CartDataTable cartDataTable1=cartViewModel.getCart2(ProductId,userId);

                            CartDataTable cartDataTable=new CartDataTable();
                            cartDataTable.setProductQuantity(productQuantity);
                            cartDataTable.setProductId(ProductId);
                            cartDataTable.setCartId(cartDataTable1.getCartId());
                            cartDataTable.setUserId(userId);
                            cartViewModel.updateCart(cartDataTable);
                            if(productQuantity==0){
                                cartViewModel.deleteCart(cartDataTable);
                                cartProductList.remove(pos);
                                cartProductsAdapter.notifyItemRemoved(pos);
                            }else {
                                CartProduct cartProduct=cartProductList.get(pos);
                                cartProduct.setQuantity(productQuantity);
                                cartProductList.set(pos,cartProduct);
                                cartProductsAdapter.notifyItemChanged(pos);
                            }

                            //getCartList();
                        }else {

                            progressDialog.dismiss();
                            CartDataTable cartDataTable1=cartViewModel.getCart2(ProductId,userId);

                            CartDataTable cartDataTable=new CartDataTable();
                            cartDataTable.setProductQuantity(productQuantity);
                            cartDataTable.setProductId(ProductId);
                            cartDataTable.setCartId(cartDataTable1.getCartId());
                            cartDataTable.setUserId(userId);
                            cartViewModel.deleteCart(cartDataTable);
                            cartProductList.remove(pos);
                            cartProductsAdapter.notifyDataSetChanged();
                            //cartViewModel.deleteCartTable();
                            //getCartList();

                        }
                    }else {
                        progressDialog.dismiss();
                        CartDataTable cartDataTable1=cartViewModel.getCart2(ProductId,userId);

                        if(cartDataTable1!=null){
                            CartDataTable cartDataTable=new CartDataTable();
                            cartDataTable.setProductQuantity(productQuantity);
                            cartDataTable.setProductId(ProductId);
                            cartDataTable.setCartId(cartDataTable1.getCartId());
                            cartDataTable.setUserId(userId);
                            cartViewModel.deleteCart(cartDataTable);
                            cartProductList.clear();
                            cartProductsAdapter.notifyDataSetChanged();
                            //cartViewModel.deleteCartTable();
                            //getCartList();
                        }

                        //Toast.makeText(context,"Something went wrong. Please try again",Toast.LENGTH_SHORT);
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Something went wrong. Please try again",Toast.LENGTH_SHORT);
                }
            }
        });

    }

}
