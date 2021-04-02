package com.semicolons.masco.pk.uiActivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.Utilities;
import com.semicolons.masco.pk.adapters.ProductsListAdapter;
import com.semicolons.masco.pk.adapters.RelatedProductsListAdapter;
import com.semicolons.masco.pk.dataModels.AddFavResponse;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.DeleteFavResponse;
import com.semicolons.masco.pk.dataModels.FavDataTable;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import com.semicolons.masco.pk.itemDecorator.GridSpacingItemDecoration;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.semicolons.masco.pk.viewModels.ProductListFragmentViewModel;
import com.semicolons.masco.pk.viewModels.WishViewModel;

public class ProductDetailActivity extends AppCompatActivity implements RelatedProductsListAdapter.onItemClickListenerAdd {

    private Product product;
    private ImageView expandedImage;
    private TextView tv_product_title;
    private TextView tv_price;
    private TextView tv_product_description;
    private TextView tv_write_your_review;
    private Button addToCartButton;
    private CartViewModel cartViewModel;
    private Context context;
    private HomeFragmentViewModel homeFragmentViewModel;
    private WishViewModel wishViewModel;
    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private int userId;
    private boolean isLogin;
    private boolean isCarted;
    private List<CartDataTable> cartDataTableList;
    private CartDataTable cartDataTable;
    private int cartCounter = 0;
    private LinearLayout addToFavLayout;
    private FavDataTable favDataTableGlobal;
    private ProductListFragmentViewModel productListFragmentViewModel;

    private RecyclerView recy_categories;
    private RelatedProductsListAdapter taskListAdap;
    private RecyclerView.LayoutManager layoutManager;
    private ProductListFragmentViewModel categoryFragmentViewModel;
    private ArrayList<FavDataTable> favDataTableArrayList;


    EditText et_search4;
    private TabLayout tabLayout;
    private ImageView img_addToFavLayout;

    public Button less_info_btn;
    /* access modifiers changed from: private */
    public Button other_btn;
    /* access modifiers changed from: private */
    public Button price_btn;
    /* access modifiers changed from: private */
    public Button img_btn;

    Boolean isOnePressed = false;
    Boolean isSecondPlace = false;

    ImageView img_search;
    private String catId;

    private int pQun;
    private int pId;
    private int pos2;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        this.price_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ProductDetailActivity.this.isOnePressed = true;
                if (ProductDetailActivity.this.isOnePressed.booleanValue() || ProductDetailActivity.this.isSecondPlace.booleanValue()) {
                    ProductDetailActivity.this.less_info_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.img_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.other_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.price_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_click_design));
                    ProductDetailActivity.this.isOnePressed = false;
                    ProductDetailActivity.this.isSecondPlace = true;
                    return;
                }
                ProductDetailActivity.this.price_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_click_design));
                ProductDetailActivity.this.isSecondPlace = true;
            }
        });
        this.less_info_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (ProductDetailActivity.this.isOnePressed.booleanValue() || ProductDetailActivity.this.isSecondPlace.booleanValue()) {
                    ProductDetailActivity.this.price_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.img_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.other_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.less_info_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_click_design));
                    ProductDetailActivity.this.isOnePressed = false;
                    ProductDetailActivity.this.isSecondPlace = true;
                    return;
                }
                ProductDetailActivity.this.less_info_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_click_design));
                ProductDetailActivity.this.isSecondPlace = true;
            }
        });
        this.img_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (ProductDetailActivity.this.isOnePressed.booleanValue() || ProductDetailActivity.this.isSecondPlace.booleanValue()) {
                    ProductDetailActivity.this.price_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.less_info_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.other_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.img_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_click_design));
                    ProductDetailActivity.this.isOnePressed = false;
                    ProductDetailActivity.this.isSecondPlace = true;
                    return;
                }
                ProductDetailActivity.this.img_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_click_design));
                ProductDetailActivity.this.isSecondPlace = true;
            }
        });
        this.other_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (ProductDetailActivity.this.isOnePressed.booleanValue() || ProductDetailActivity.this.isSecondPlace.booleanValue()) {
                    ProductDetailActivity.this.price_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.less_info_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.img_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_design));
                    ProductDetailActivity.this.other_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_click_design));
                    ProductDetailActivity.this.isOnePressed = false;
                    ProductDetailActivity.this.isSecondPlace = true;
                    return;
                }
                ProductDetailActivity.this.other_btn.setBackgroundDrawable(ProductDetailActivity.this.getResources().getDrawable(R.drawable.button_click_design));
                ProductDetailActivity.this.isSecondPlace = true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        prepareMenu(menu);
        return true;
    }

    @Override
    protected void onResume() {

        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        if (isLogin) {
            cartViewModel.getCartCount(userId).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != null) {
                        cartCounter = integer;
                    } else {
                        cartCounter = 0;
                    }
                    invalidateOptionsMenu();
                }
            });

            getCartItemDb();


            if (product != null) {
                cartViewModel.getCartTable(product.getProduct_id(), userId).observe(this, new Observer<CartDataTable>() {
                    @Override
                    public void onChanged(CartDataTable cartDataTable) {
                        if (cartDataTable != null) {
                            if (cartDataTable.getProductId() == product.getProduct_id()) {
                                isCarted = true;
                                addToCartButton.setText("Remove from cart");
                                ProductDetailActivity.this.cartDataTable = cartDataTable;
                            } else {
                                isCarted = false;
                                addToCartButton.setText("Add to Cart");
                            }
                        }
                    }
                });
            }
        }

        categoryFragmentViewModel.getAllFavourites(userId).observe(this, new Observer<List<FavDataTable>>() {
            @Override
            public void onChanged(List<FavDataTable> favDataTableList) {
                if (favDataTableList != null) {
                    favDataTableArrayList = (ArrayList<FavDataTable>) favDataTableList;
                }
            }
        });

        super.onResume();
    }

    public void prepareMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        menu.findItem(R.id.action_search).setVisible(false);
        MenuItem menuItem = menu.findItem(R.id.action_cart);

        if (cartCounter != 0) {
            menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(Integer.valueOf(cartCounter), R.drawable.ic_baseline_shopping_cart_24));
        } else {
            menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(0, R.drawable.ic_baseline_shopping_cart_24));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();

        prepareMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                onBackPressed();
                return true;
            case R.id.action_cart:
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initViews() {
        context = this;
        img_search = findViewById(R.id.img_search);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        categoryFragmentViewModel = new ViewModelProvider(this).get(ProductListFragmentViewModel.class);
        recy_categories = findViewById(R.id.recy_categories);
        addToFavLayout = (LinearLayout) findViewById(R.id.addToFavLayout);
        img_addToFavLayout = findViewById(R.id.img_addToFavLayout);
        addToCartButton = findViewById(R.id.btnPost);
        expandedImage = findViewById(R.id.expandedImage);
        tv_product_title = findViewById(R.id.tv_product_title);
        tv_price = findViewById(R.id.tv_price);
        tv_product_description = findViewById(R.id.tv_product_description);
        tv_write_your_review = findViewById(R.id.tv_write_your_review);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        wishViewModel = new ViewModelProvider(this).get(WishViewModel.class);
        productListFragmentViewModel = new ViewModelProvider(this).get(ProductListFragmentViewModel.class);

        price_btn = (Button) findViewById(R.id.prodDet_price_btn);
        less_info_btn = (Button) findViewById(R.id.prodDet_lessInfo_btn);
        img_btn = (Button) findViewById(R.id.prodDet_img_btn);
        other_btn = (Button) findViewById(R.id.prodDet_other_btn);


        //  tabLayout = findViewById(R.id.tabLayout);

        Intent intent = getIntent();
        assert intent != null;
        product = (Product) intent.getSerializableExtra(Constants.PRODUCT_OBJECT);
        catId = intent.getStringExtra(Constants.SUB_CATEGORY_ID);

        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);
            favDataTableGlobal = wishViewModel.getFavourite(product.getProduct_id(), userId);
            if (favDataTableGlobal != null) {

                img_addToFavLayout.setImageResource(R.drawable.ic_baseline_star_filled_24);
              /*  TextView textView = (TextView) addToFavLayout.getChildAt(0);
                textView.setText("Remove from Favourite");*/
            }
        }


        if (catId==null) {
            catId = "60";
            categoryList(Integer.parseInt(catId));

        }
        else {
            categoryList(Integer.parseInt(catId));
        }


        img_addToFavLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLogin) {

                    if (favDataTableGlobal != null) {
                        deleteDialog();
                    } else {
                        initProgresDialog();
                        //int resourceId=getResources().getIdentifier("com.budget.budgetmanager:drawable/ic_baseline_star_filled_24","drawable",getPackageName());
                        img_addToFavLayout.setImageResource(R.drawable.ic_baseline_star_filled_24);
                        productListFragmentViewModel.addToFavourite(String.valueOf(product.getProduct_id()), userId).observe((LifecycleOwner) context, new Observer<AddFavResponse>() {
                            @Override
                            public void onChanged(AddFavResponse addFavResponse) {

                                progressDialog.dismiss();
                                if (addFavResponse != null) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, addFavResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    img_addToFavLayout.setImageResource(R.drawable.ic_baseline_star_filled_24);

                                  /*  TextView textView=(TextView) addToFavLayout.getChildAt(0);
                                    textView.setText("Remove from Favourite");*/
                                    FavDataTable favDataTable = new FavDataTable();
                                    favDataTable.setProductId(product.getProduct_id());
                                    favDataTable.setUserId(userId);
                                    productListFragmentViewModel.insertFavToDB(favDataTable);

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }


                } else {
                    Toast.makeText(context, "Please Login", Toast.LENGTH_SHORT).show();
                }


            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLogin) {
                    //int quantityToRemove=getCartList().get(index).getQuantity();
                    initProgresDialog();

                    if (isCarted) {
                        cartViewModel.updateCart(product.getProduct_id(), 0, userId).observe((LifecycleOwner) context, new Observer<UpdateCartResponse>() {
                            @Override
                            public void onChanged(UpdateCartResponse updateCartResponse) {
                                progressDialog.dismiss();
                                if (updateCartResponse != null) {
                                    progressDialog.dismiss();

                                    cartViewModel.deleteCart(cartDataTable);
                                    isCarted = false;
                                    addToCartButton.setText("Add to Cart");
                                    invalidateOptionsMenu();

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Something went wrong. Please try again", Toast.LENGTH_SHORT);
                                }
                            }
                        });
                    } else {
                        cartViewModel.addProductToCart(userId, product.getProduct_id(), 1).observe((LifecycleOwner) context, new Observer<CartResponse>() {
                            @Override
                            public void onChanged(CartResponse cartResponse) {
                                progressDialog.dismiss();
                                if (cartResponse != null) {
                                    progressDialog.dismiss();
                                    if (cartResponse.getMessage().equals("Product added successfully")) {
                                        progressDialog.dismiss();
                                        addToCartButton.setText("Remove From Cart");

                                        CartDataTable cartDataTable = new CartDataTable();
                                        cartDataTable.setProductId(product.getProduct_id());
                                        cartDataTable.setProductQuantity(1);
                                        cartDataTable.setUserId(userId);
                                        cartViewModel.insertCart(cartDataTable);

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    intent1.putExtra("ACTIVITY_NAME", ProductDetailActivity.class.getSimpleName());
                    intent1.putExtra(Constants.PRODUCT_OBJECT, product);
                    startActivity(intent1);
                    finish();
                }
            }
        });


        RequestOptions options = new RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .priority(Priority.HIGH);
        Glide
                .with(this)
                .load(product.getImage_name())
                .apply(options)
                .into(expandedImage);

        tv_product_title.setText(product.getProduct_title());
        tv_product_description.setText(product.getProduct_description());
        tv_price.setText(product.getPrice());


    }

    public void initProgresDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void getCartItemDb() {

        cartViewModel.getCartDataTableLiveData(userId).observe(ProductDetailActivity.this, new Observer<List<CartDataTable>>() {
            @Override
            public void onChanged(List<CartDataTable> cartDataTables) {
                if (cartDataTables != null) {
                    cartDataTableList = cartDataTables;


                    for (int i = 0; i < cartDataTableList.size(); i++) {
                        Utilities.p[i] = cartDataTables.get(i).getProductId();
                        Utilities.q[i] = cartDataTables.get(i).getProductQuantity();
                    }

                    for (int i = 0; i < cartDataTableList.size(); i++) {

                        Log.d("CartData", cartDataTableList.get(i).getCartId() + ": " +
                                cartDataTableList.get(i).getProductId() + ": " + cartDataTableList.get(i).getProductQuantity() + ": " +
                                cartDataTableList.get(i).getUserId());
                    }


                } else {
                    Log.d("CartData", "NULL");
                    Toast.makeText(getApplicationContext(), "CartData Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void categoryList(int catId) {


        categoryFragmentViewModel.getProductsList(1, catId).observe(this, new Observer<TopSellingResponse>() {
            @Override
            public void onChanged(TopSellingResponse allBookingListResponseDM) {
                if (allBookingListResponseDM != null) {
                    if (allBookingListResponseDM.getData() != null) {
                        List<Product> resultItems = allBookingListResponseDM.getData();
                        if (resultItems.size() > 0) {
                            Log.d("getCategoryID", String.valueOf(catId));
                            resultItems.remove(0);
                            prepareListData(resultItems, String.valueOf(catId));
                        } else {

                        }
                    }
                } else {
                }

            }
        });

    }


    private void prepareListData(List<Product> resultItems, String catid) {

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recy_categories.setLayoutManager(layoutManager);
        taskListAdap = new RelatedProductsListAdapter(this, resultItems, this, cartDataTableList, favDataTableArrayList, catid);
        recy_categories.setAdapter(taskListAdap);
        taskListAdap.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view, Product product1) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        product = product1;

        int id = product.getProduct_id();

        Log.d("id", String.valueOf(id));

        CartDataTable cartDataTable = new CartDataTable();
        cartDataTable.setProductId(id);
        cartDataTable.setProductQuantity(1);
        cartDataTable.setUserId(userId);

        cartViewModel.insertCart(cartDataTable);


        int quantity = 1;
        cartViewModel.addProductToCart(userId, product.getProduct_id(), quantity).observe(this, new Observer<CartResponse>() {
            @Override
            public void onChanged(CartResponse cartResponse) {
                progressDialog.dismiss();
                if (cartResponse != null) {
                    progressDialog.dismiss();
                    if (cartResponse.getMessage().equals("Product added successfully")) {
                        progressDialog.dismiss();
                        Log.d("id2", String.valueOf(id));


                        getCartItemDb();

                        if (cartDataTableList.size() > 0) {
                            taskListAdap.setList(cartDataTableList);
                        } else {
                        }
                        progressDialog.dismiss();
                        invalidateOptionsMenu();

                    } else {
                        Toast.makeText(context, cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onFavClick(View view, Product productFav, int position) {

        if (isLogin) {
            int userId = sharedPreferences.getInt(Constants.USER_ID, 0);

            FavDataTable favDataTable = wishViewModel.getFavourite(productFav.getProduct_id(), userId);

            if (favDataTable != null) {
                deleteDialog(view, productFav, position);
            } else {
                progressDialog = new ProgressDialog(this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                product = productFav;
                ImageView imageView = (ImageView) view;
                //int resourceId=getResources().getIdentifier("com.budget.budgetmanager:drawable/ic_baseline_star_filled_24","drawable",getPackageName());
                imageView.setImageResource(R.drawable.ic_baseline_star_filled_24);

                categoryFragmentViewModel.addToFavourite(String.valueOf(product.getProduct_id()), userId).observe(this, new Observer<AddFavResponse>() {
                    @Override
                    public void onChanged(AddFavResponse addFavResponse) {
                        progressDialog.dismiss();
                        if (addFavResponse != null) {
                            progressDialog.dismiss();
                            showResponseDialog("Added", addFavResponse.getMessage());
                            Toast.makeText(context, addFavResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            FavDataTable favDataTable = new FavDataTable();
                            favDataTable.setProductId(product.getProduct_id());
                            favDataTable.setUserId(userId);
                            categoryFragmentViewModel.insertFavToDB(favDataTable);

                        } else {
                            progressDialog.dismiss();
                            showResponseDialogFailed("Error", "Try Again");

                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }


        }

    }

    @Override
    public void updateCart(int productId, int quantity, int position, View view) {

        pId = productId;
        pQun = quantity;
        pos2 = position;


        CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

        if (cartDataTable1 != null) {

            if (pQun == 0) {
                cartViewModel.deleteCart(cartDataTable1);
            } else {

                CartDataTable cartDataTable = new CartDataTable();
                cartDataTable.setProductQuantity(pQun);
                cartDataTable.setProductId(pId);
                cartDataTable.setCartId(cartDataTable1.getCartId());
                cartDataTable.setUserId(userId);
                cartViewModel.updateCart(cartDataTable);

                progressDialog.show();

                Toast.makeText(getApplicationContext(), "updated!", Toast.LENGTH_SHORT).show();

            }
        } else {
            progressDialog.show();
            Toast.makeText(getApplicationContext(), "cartDataTable1 is NULL", Toast.LENGTH_SHORT).show();
        }

        cartViewModel.updateCart(pId, pQun, userId).observe(ProductDetailActivity.this, new Observer<UpdateCartResponse>() {
            @Override
            public void onChanged(UpdateCartResponse updateCartResponse) {
                progressDialog.dismiss();
                if (updateCartResponse != null) {
                    progressDialog.dismiss();
                    if (updateCartResponse.getData() != null) {
                        progressDialog.dismiss();

                        if (updateCartResponse.getData().size() > 0) {
                            Toast.makeText(getApplicationContext(), "API updated", Toast.LENGTH_SHORT).show();
                        } else {

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "getData().size() empty", Toast.LENGTH_SHORT).show();

                            CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

                            CartDataTable cartDataTable = new CartDataTable();
                            cartDataTable.setProductQuantity(pQun);
                            cartDataTable.setProductId(pId);
                            cartDataTable.setCartId(cartDataTable1.getCartId());
                            cartDataTable.setUserId(userId);
                            cartViewModel.deleteCart(cartDataTable);

                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "getData().size() null", Toast.LENGTH_SHORT).show();

                        CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

                        if (cartDataTable1 != null) {

                            CartDataTable cartDataTable = new CartDataTable();
                            cartDataTable.setProductQuantity(pQun);
                            cartDataTable.setProductId(pId);
                            cartDataTable.setCartId(cartDataTable1.getCartId());
                            cartDataTable.setUserId(userId);
                            cartViewModel.deleteCart(cartDataTable);

                        } else {
                            Toast.makeText(getApplicationContext(), "cartdata Null", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void deleteDialog(View view, Product productFav, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete")
                .setMessage("Are you sure you want to remove item from favourite?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int userId = sharedPreferences.getInt(Constants.USER_ID, 0);
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        product = productFav;
                        pos = position;
                        ImageView imageView = (ImageView) view;
                        //int resourceId=getResources().getIdentifier("com.budget.budgetmanager:drawable/ic_baseline_star_filled_24","drawable",getPackageName());
                        imageView.setImageResource(R.drawable.ic_star_border_black);

                        wishViewModel.deleteFavourite(product.getProduct_id(), userId).observe((LifecycleOwner) context, new Observer<DeleteFavResponse>() {
                            @Override
                            public void onChanged(DeleteFavResponse deleteFavResponse) {
                                progressDialog.dismiss();
                                if (deleteFavResponse != null) {
                                    progressDialog.dismiss();
                                    if (deleteFavResponse.getData() != null) {
                                        progressDialog.dismiss();
                                        if (deleteFavResponse.getData().equals("Remove from favorite successfully")) {
                                            progressDialog.dismiss();
                                            Toast.makeText(context, deleteFavResponse.getData(), Toast.LENGTH_SHORT).show();

                                            FavDataTable favDataTable = wishViewModel.getFavourite(product.getProduct_id(), userId);

                                            wishViewModel.deleteFavouriteFromDB(favDataTable);

                                        } else {
                                            progressDialog.dismiss();
                                            showResponseDialog("Deleted", deleteFavResponse.getData());
                                            Toast.makeText(context, deleteFavResponse.getData(), Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        showResponseDialogFailed("Error", "Try Again");

                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    showResponseDialogFailed("Error", "Try Again");

                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showResponseDialog(String title, String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showResponseDialogFailed(String title, String msg) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void deleteDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete")
                .setMessage("Are you sure you want to remove item from favourite?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int userId = sharedPreferences.getInt(Constants.USER_ID, 0);
                        initProgresDialog();
                        img_addToFavLayout.setImageResource(R.drawable.ic_star_border_black);

                        wishViewModel.deleteFavourite(product.getProduct_id(), userId).observe((LifecycleOwner) context, new Observer<DeleteFavResponse>() {
                            @Override
                            public void onChanged(DeleteFavResponse deleteFavResponse) {
                                progressDialog.dismiss();
                                if (deleteFavResponse != null) {
                                    progressDialog.dismiss();
                                    if (deleteFavResponse.getData() != null) {
                                        progressDialog.dismiss();
                                        if (deleteFavResponse.getData().equals("Remove from favorite successfully")) {
                                            progressDialog.dismiss();
                                            Toast.makeText(context, deleteFavResponse.getData(), Toast.LENGTH_SHORT).show();

                                            FavDataTable favDataTable = wishViewModel.getFavourite(product.getProduct_id(), userId);

                                            wishViewModel.deleteFavouriteFromDB(favDataTable);
                                            img_addToFavLayout.setImageResource(R.drawable.ic_star_border_black);

                                    /*        TextView textView= (TextView) addToFavLayout.getChildAt(0);
                                            textView.setText("Add to List");*/
                                            favDataTableGlobal = null;

                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(context, deleteFavResponse.getData(), Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
