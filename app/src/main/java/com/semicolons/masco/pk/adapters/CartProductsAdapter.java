package com.semicolons.masco.pk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.dataModels.CartProduct;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.Holder> {

    private Context context;
    private List<CartProduct> cartProducts;
    private onCartItemClickListener onCartItemClickListener;
    private int cartCounter=0;

    public CartProductsAdapter(Context context, List<CartProduct> cartProducts,onCartItemClickListener onCartItemClickListener) {
        this.context = context;
        this.cartProducts = cartProducts;
        this.onCartItemClickListener=onCartItemClickListener;
    }

    @NonNull
    @Override
    public CartProductsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_list_item,parent,false);
        return  new CartProductsAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductsAdapter.Holder holder, int position) {
        CartProduct cartProduct=cartProducts.get(position);
        holder.cartProductPricetextView.setText(cartProduct.getUnitPrice());
        holder.cartBrandTextView.setText("Str Brand");
        holder.cartProductTitleTextView.setText(cartProduct.getProductTitle());
        holder.cartProductQuantityTextView.setText(String.valueOf(cartProduct.getQuantity()));

        //cartCounter=cartCounter
        //SharedHelper.putKey(context, Constants.CART_COUNTER,cartCounter);

        RequestOptions options = new RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .priority(Priority.HIGH);
        Glide
                .with(context)
                .load(cartProducts.get(position).getImageName())
                .apply(options)
                .into(holder.cartProductImageView);

    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cartProductTitleTextView;
        TextView cartBrandTextView;
        TextView cartProductPricetextView;
        TextView cartProductQuantityTextView;
        ImageView cartProductImageView;
        Button addCartButton;
        Button minusCartButton;

        public Holder(@NonNull View itemView) {
            super(itemView);

            cartProductTitleTextView=itemView.findViewById(R.id.cartProductNameTextView);
            cartBrandTextView=itemView.findViewById(R.id.cartProductBrandTextView);
            cartProductPricetextView=itemView.findViewById(R.id.cartProductPriceTextView);
            cartProductQuantityTextView=itemView.findViewById(R.id.cartProductQuantityTextView);
            cartProductImageView=itemView.findViewById(R.id.cartProductImageView);
            addCartButton=itemView.findViewById(R.id.cartAddButton);
            minusCartButton=itemView.findViewById(R.id.cartMinusButton);
            addCartButton.setOnClickListener(this);
            minusCartButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int quantity=cartProducts.get(this.getAdapterPosition()).getQuantity();
            int prodId=cartProducts.get(this.getAdapterPosition()).getProductId();
            boolean isCartAdded=false;

            if(v.findViewById(R.id.cartAddButton)==v){
                quantity++;
                isCartAdded=true;
            }else {
                quantity--;
                isCartAdded=false;
            }

            onCartItemClickListener.onUpdateCart(prodId,quantity,this.getAdapterPosition(),isCartAdded);
        }
    }

    public interface onCartItemClickListener{
        void onUpdateCart(int productId,int quantity,int position,boolean checkCart);

    }

}
