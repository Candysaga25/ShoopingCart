package com.ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecommerceapp.model.Product;
import com.ecommerceapp.model.UpdateCartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    public Toolbar mToolbar;
    private Context mContext;
    private ImageView imageViewUser;
    private CustomProgressDialog customProgressDialog;
    private TextView mTxtToolbarTitle;
    private LayerDrawable mCartMenuIcon;
    private int mCartCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTxtToolbarTitle = (TextView) mToolbar.findViewById(R.id.lblTitle);
        mTxtToolbarTitle.setText("Products");
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initNavigationDrawer();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mCartMenuIcon = (LayerDrawable) menu.findItem(R.id.action_cart).getIcon();
        List<Product> productList = Helper.retrieveJsonTocache(mContext);
        setBadgeCount(this, mCartMenuIcon, String.valueOf(productList.size()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent intent = new Intent(mContext, ShoppingCartActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initNavigationDrawer() {

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                int id = menuItem.getItemId();

                if (id == R.id.nav_camera) {
                    ProductListingFragment productListingFragment = new ProductListingFragment();
                    fragmentTransaction(productListingFragment);
                    setToolbarTitle("Products");
                    selectNavMenu(0);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ProductListingFragment productListingFragment = new ProductListingFragment();
        fragmentTransaction(productListingFragment);
        setToolbarTitle("Products");
        selectNavMenu(0);

    }


    public void fragmentTransaction(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }
    public void setToolbarTitle(String title) {
        mTxtToolbarTitle.setText(title);
    }
    public void selectNavMenu(int navItemIndex) {
        mNavigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }
    public void onClickIncrementCartCount(int count) {
        setBadgeCount(this, mCartMenuIcon, String.valueOf(count));
    }

    @Subscribe
    public void onEvent(UpdateCartItem updateCartItem) {
        onClickIncrementCartCount(updateCartItem.count);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
