package com.semicolons.masco.pk.uiActivities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.Utils.AppClass;
import com.semicolons.masco.pk.Utils.Constants;
import com.semicolons.masco.pk.Utils.Utilities;
import com.semicolons.masco.pk.adapters.ExpandableListAdapter;
import com.semicolons.masco.pk.adapters.LatestSellingProductAdapter;
import com.semicolons.masco.pk.adapters.MainCategoriesAdapter;
import com.semicolons.masco.pk.adapters.SliderAdapterExample;
import com.semicolons.masco.pk.adapters.TopSellingAdapter;
import com.semicolons.masco.pk.dataModels.CartDataTable;
import com.semicolons.masco.pk.dataModels.CartResponse;
import com.semicolons.masco.pk.dataModels.CategoryDM;
import com.semicolons.masco.pk.dataModels.DataItem;
import com.semicolons.masco.pk.dataModels.ImagesListDM;
import com.semicolons.masco.pk.dataModels.Product;
import com.semicolons.masco.pk.dataModels.SliderImagesResponse;
import com.semicolons.masco.pk.dataModels.TopSellingResponse;
import com.semicolons.masco.pk.dataModels.UpdateCartResponse;
import com.semicolons.masco.pk.fragments.AccountFragment;
import com.semicolons.masco.pk.fragments.CategoryFragment;
import com.semicolons.masco.pk.fragments.HomeFragment;
import com.semicolons.masco.pk.viewModels.CartViewModel;
import com.semicolons.masco.pk.viewModels.HomeFragmentViewModel;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean isLogin;
    private boolean isFirstTimeActivityStarted = false;
    private int cartCounter = 0;

    RelativeLayout btn_login_or_signUp;

    private HomeFragmentViewModel homeFragmentViewModel;
    private CartViewModel cartViewModel;

    private TextView tv_appBar_title;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tv_address;

    private Boolean exit = false;
    private int userId;
    ImageView img_search;
    RelativeLayout rlSearch;

    private BottomNavigationView bottomNavigationView;


    boolean topSellingComplete = false;

    ProgressDialog progressDialog;
    private ExpandableListView expListView;
    private ExpandableListAdapter expandableListAdapter;
    RecyclerView recyclerFeaturedProducts;
    RecyclerView recyclerLatestProducts;
    RecyclerView recy_main_categories;
    TopSellingAdapter timeDealAdapter;
    LatestSellingProductAdapter latestSellingProductAdapter;

    //List student ERP OBJECT
    ArrayList<ImagesListDM> loungeList = new ArrayList<>();


    private List<CartDataTable> cartDataTableList = new ArrayList<>();
    // private List<CartProduct> cartProductList = new ArrayList<>();

    private int pos;
    private int pQun;
    private int pId;
    private ImageView imgSuperStore;
    MainCategoriesAdapter mainCategoriesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        boolean isCartFragment = intent.getBooleanExtra("IS_CART_FRAGMENT", false);

        HomeFragment fragment=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.navFragmentContainer,fragment).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment;

                switch (item.getItemId()){
                    case R.id.bottom_nav_home:
                        fragment=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.navFragmentContainer,fragment).commit();
                        break;
                    case R.id.bottom_nav_categories:
                        fragment=new CategoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.navFragmentContainer,fragment).commit();
                        break;
                    case R.id.bottom_nav_account:
                        fragment=new AccountFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.navFragmentContainer,fragment).commit();
                        break;
                    case R.id.bottom_nav_search:
                        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;

            }
        });

        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0); // 0-index header
        btn_login_or_signUp = headerLayout.findViewById(R.id.rel);
        tvName = headerLayout.findViewById(R.id.tvName);
        tvEmail = headerLayout.findViewById(R.id.tvEmail);
        tv_address = headerLayout.findViewById(R.id.tv_address);
        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);

            tvName.setText(sharedPreferences.getString(Constants.USER_NAME, "Hey"));
            tvEmail.setText(sharedPreferences.getString(Constants.USER_EMAIL, ""));
            tv_address.setText(sharedPreferences.getString(Constants.USER_ADDRESS, "Na"));
            btn_login_or_signUp.setClickable(false);

        } else {
            btn_login_or_signUp.setClickable(true);

        }

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE};
        String rationale = "Please provide location permission so that you can Continue ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                Toast.makeText(context, "Please provide permission to continue", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        init();
        initListeners();

        getCartItemDb();


        setTitle("Home");
    }

    private void init() {

        editor = sharedPreferences.edit();
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);

        img_search = findViewById(R.id.img_search);
        rlSearch = findViewById(R.id.rlSearch);


        expListView = (ExpandableListView) findViewById(R.id.lvExp);


        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);

        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);
        }

    }

    private void initListeners() {

        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait for amazing offers...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        rlSearch.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        btn_login_or_signUp.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btn_login_or_signUp.setOnClickListener(view -> {

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            //intent.putExtra("ACTIVITY_NAME",HomeActivity.class.getSimpleName());
            startActivity(intent);

        });
    }

    private void getCartItemDb() {

        cartViewModel.getCartDataTableLiveData(userId).observe(this, new Observer<List<CartDataTable>>() {
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

                    // timeDealAdapter = new TopSellingAdapter(Constants.topSellingProduct, cartDataTableList, HomeActivity.this);
                    //recyclerFeaturedProducts.setAdapter(timeDealAdapter);
                    //timeDealAdapter.notifyDataSetChanged();

                } else {
                    Log.d("CartData", "NULL");
                }
            }
        });

    }


    private void clickListner() {

        if (timeDealAdapter != null) {

            timeDealAdapter.setOnItemClickListener(new TopSellingAdapter.OnItemClickListener() {
                @Override
                public void onAddtoCartClick(View view, Product product) {


                    int id = product.getProduct_id();

                    Log.d("id", String.valueOf(id));

                    CartDataTable cartDataTable = new CartDataTable();
                    cartDataTable.setProductId(id);
                    cartDataTable.setProductQuantity(1);
                    cartDataTable.setUserId(userId);

                    cartViewModel.insertCart(cartDataTable);
//                Toast.makeText(HomeActivity.this, String.valueOf(id), Toast.LENGTH_SHORT).show();

                    int quantity = 1;
                    cartViewModel.addProductToCart(userId, id, quantity).observe(HomeActivity.this, new Observer<CartResponse>() {
                        @Override
                        public void onChanged(CartResponse cartResponse) {

                            progressDialog.show();
                            if (cartResponse != null) {
                                progressDialog.dismiss();
                                if (cartResponse.getMessage().equals("Product added successfully")) {

                                    Log.d("id2", String.valueOf(id));

//                                    Toast.makeText(HomeActivity.this, "Data added in API", Toast.LENGTH_SHORT).show();

                                    getCartItemDb();
                                    if (cartDataTableList.size() > 0) {
                                        timeDealAdapter.setList(cartDataTableList);
                                    } else {
                                        Toast.makeText(HomeActivity.this, "cartDataTableList empty", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                } else {

                                    CartDataTable cartDataTable1 = cartViewModel.getCart2(id, userId);

                                    if (cartDataTable1 != null) {
                                        cartViewModel.deleteCart(cartDataTable1);
                                    }
                                    progressDialog.dismiss();

                                    Toast.makeText(HomeActivity.this, cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                    progressDialog.dismiss();

                }

                @Override
                public void updateCartData(int productId, int quantity, int position, View view) {

                    Log.d("cartPos", String.valueOf(position));
                    pId = productId;
                    pQun = quantity;
                    pos = position;

                    progressDialog.show();

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
                            // cartViewModel.CartforUpdate(pQun,userId,pId);

                            Toast.makeText(HomeActivity.this, "updated!", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        progressDialog.show();
                        Toast.makeText(HomeActivity.this, "cartDataTable1 is NULL", Toast.LENGTH_SHORT).show();
                    }


                    cartViewModel.updateCart(pId, pQun, userId).observe(HomeActivity.this, new Observer<UpdateCartResponse>() {
                        @Override
                        public void onChanged(UpdateCartResponse updateCartResponse) {
                            progressDialog.dismiss();
                            if (updateCartResponse != null) {
                                progressDialog.dismiss();
                                if (updateCartResponse.getData() != null) {
                                    progressDialog.dismiss();

                                    if (updateCartResponse.getData().size() > 0) {
                                        Toast.makeText(HomeActivity.this, "API updated", Toast.LENGTH_SHORT).show();
                                    } else {

                                        progressDialog.dismiss();
                                        Toast.makeText(HomeActivity.this, "getData().size() empty", Toast.LENGTH_SHORT).show();

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
                                    Toast.makeText(HomeActivity.this, "getData().size() null", Toast.LENGTH_SHORT).show();

                                    CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

                                    if (cartDataTable1 != null) {

                                        CartDataTable cartDataTable = new CartDataTable();
                                        cartDataTable.setProductQuantity(pQun);
                                        cartDataTable.setProductId(pId);
                                        cartDataTable.setCartId(cartDataTable1.getCartId());
                                        cartDataTable.setUserId(userId);
                                        cartViewModel.deleteCart(cartDataTable);

                                    } else {
                                        Toast.makeText(HomeActivity.this, "cartdata Null", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(HomeActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            });
        }
        if (latestSellingProductAdapter != null) {
            latestSellingProductAdapter.setOnItemClickListener(new TopSellingAdapter.OnItemClickListener() {
                @Override
                public void onAddtoCartClick(View view, Product product) {


                    int id = product.getProduct_id();

                    Log.d("id", String.valueOf(id));

                    CartDataTable cartDataTable = new CartDataTable();
                    cartDataTable.setProductId(id);
                    cartDataTable.setProductQuantity(1);
                    cartDataTable.setUserId(userId);

                    cartViewModel.insertCart(cartDataTable);
//                Toast.makeText(HomeActivity.this, String.valueOf(id), Toast.LENGTH_SHORT).show();

                    int quantity = 1;
                    cartViewModel.addProductToCart(userId, id, quantity).observe(HomeActivity.this, new Observer<CartResponse>() {
                        @Override
                        public void onChanged(CartResponse cartResponse) {

                            progressDialog.show();
                            if (cartResponse != null) {
                                progressDialog.dismiss();
                                if (cartResponse.getMessage().equals("Product added successfully")) {

                                    Log.d("id2", String.valueOf(id));

//                                    Toast.makeText(HomeActivity.this, "Data added in API", Toast.LENGTH_SHORT).show();

                                    getCartItemDb();
                                    if (cartDataTableList.size() > 0) {
                                        timeDealAdapter.setList(cartDataTableList);
                                    } else {
                                        Toast.makeText(HomeActivity.this, "cartDataTableList empty", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                } else {

                                    CartDataTable cartDataTable1 = cartViewModel.getCart2(id, userId);

                                    if (cartDataTable1 != null) {
                                        cartViewModel.deleteCart(cartDataTable1);
                                    }
                                    progressDialog.dismiss();

                                    Toast.makeText(HomeActivity.this, cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                    progressDialog.dismiss();

                }

                @Override
                public void updateCartData(int productId, int quantity, int position, View view) {

                    Log.d("cartPos", String.valueOf(position));
                    pId = productId;
                    pQun = quantity;
                    pos = position;

                    progressDialog.show();

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
                            // cartViewModel.CartforUpdate(pQun,userId,pId);

                            Toast.makeText(HomeActivity.this, "updated!", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        progressDialog.show();
                        Toast.makeText(HomeActivity.this, "cartDataTable1 is NULL", Toast.LENGTH_SHORT).show();
                    }


                    cartViewModel.updateCart(pId, pQun, userId).observe(HomeActivity.this, new Observer<UpdateCartResponse>() {
                        @Override
                        public void onChanged(UpdateCartResponse updateCartResponse) {
                            progressDialog.dismiss();
                            if (updateCartResponse != null) {
                                progressDialog.dismiss();
                                if (updateCartResponse.getData() != null) {
                                    progressDialog.dismiss();

                                    if (updateCartResponse.getData().size() > 0) {
                                        Toast.makeText(HomeActivity.this, "API updated", Toast.LENGTH_SHORT).show();
                                    } else {

                                        progressDialog.dismiss();
                                        Toast.makeText(HomeActivity.this, "getData().size() empty", Toast.LENGTH_SHORT).show();

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
                                    Toast.makeText(HomeActivity.this, "getData().size() null", Toast.LENGTH_SHORT).show();

                                    CartDataTable cartDataTable1 = cartViewModel.getCart2(pId, userId);

                                    if (cartDataTable1 != null) {

                                        CartDataTable cartDataTable = new CartDataTable();
                                        cartDataTable.setProductQuantity(pQun);
                                        cartDataTable.setProductId(pId);
                                        cartDataTable.setCartId(cartDataTable1.getCartId());
                                        cartDataTable.setUserId(userId);
                                        cartViewModel.deleteCart(cartDataTable);

                                    } else {
                                        Toast.makeText(HomeActivity.this, "cartdata Null", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(HomeActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            });
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        prepareMenu(menu);
        return true;
    }

    @Override
    protected void onResume() {

        isLogin = sharedPreferences.getBoolean(Constants.USER_IS_LOGIN, false);
        if (isLogin) {
            userId = sharedPreferences.getInt(Constants.USER_ID, 0);
            cartViewModel.getCartCount(userId).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != null) {
                        cartCounter = integer;
                        Log.d("cartCounter", String.valueOf(cartCounter));
                    } else {
                        cartCounter = 0;
                    }
                    invalidateOptionsMenu();
                }
            });
        }
        super.onResume();
    }

    public void prepareMenu(Menu menu) {
        menu.clear();

        getMenuInflater().inflate(R.menu.home, menu);

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        if (isLogin) {

            if (cartCounter != 0) {
                menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(cartCounter, R.drawable.ic_baseline_shopping_cart_24));
            } else {
                menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(0, R.drawable.ic_baseline_shopping_cart_24));
            }
        } else {
            menuItem.setIcon(homeFragmentViewModel.getDrawableForCounter(0, R.drawable.ic_baseline_shopping_cart_24));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        prepareMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.simpleFrameLayout,new CartFragment()).commit();

            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_categories) {
            startActivity(new Intent(this, AllSubCategoriesActivities.class));
        } else if (id == R.id.nav_myOrders) {
            startActivity(new Intent(this, OrderActivity.class));
        } else if (id == R.id.nav_wishList) {
            startActivity(new Intent(this, WishListActivity.class));
        } else if (id == R.id.nav_shareEarn) {
            startActivity(new Intent(this, ShareEarnActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_wallet) {
            startActivity(new Intent(this, PointsActivity.class));
        } else if (id == R.id.nav_faqs) {
            startActivity(new Intent(this, FAQsActivity.class));
        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(this, AboutUsActivity.class));
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);

            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    "Masco.pk");
            String shareMessage = "Visit this app on play store  https://play.google.com/store/apps/details?id=com.semicolons.masco.pk";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    shareMessage);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } else if (id == R.id.nav_call) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + Constants.CALL_CENTER_NUMBER));
            startActivity(callIntent);
        } else if (id == R.id.nav_logout) {
            if (isLogin) {
                logOutDialog("Logout", "Are you sure to logout?");
            }else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void logOutDialog(String title, String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        sharedPreferences = getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        editor.commit();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); //
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

}