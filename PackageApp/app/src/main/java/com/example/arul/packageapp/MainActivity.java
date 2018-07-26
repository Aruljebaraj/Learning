package com.example.arul.packageapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arul.packageapp.Fragment.PackedList;
import com.example.arul.packageapp.Fragment.OrderedList;
import com.example.arul.packageapp.Model.EmployeeModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ImageView ImgNavOpen, ImgNewOrder;
    NavigationView navigation_view;
    DrawerLayout drawer_layout;
    TabLayout tabLayout;
    ViewPager Viewpager;
    MenuView.ItemView home, settings;
    String DB_NAME = "Dailyfresh";
    String Token;
    EmployeeModel employeeModel = EmployeeModel.getInstance();
    boolean Permissiondenied = false;
    boolean CanClose = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkCallPermission();
        setData();
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        ImgNavOpen = (ImageView) findViewById(R.id.ImgNavOpen);
        ImgNewOrder = (ImageView) findViewById(R.id.ImgNewOrder);
        setupDrawerContent(navigation_view);
        ImgNavOpen.setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        Viewpager = (ViewPager) findViewById(R.id.Viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("Order List"));
        tabLayout.addTab(tabLayout.newTab().setText("Packed List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        Viewpager.setAdapter(adapter);
        Viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setData() {
        try {


            SQLiteDatabase mydatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            String sql = "SELECT Token FROM Employeedetails";
            Cursor dashData;
            dashData = mydatabase.rawQuery(sql, null);
            if (dashData.moveToFirst()) {
                Token = dashData.getString(0);
                employeeModel.setToken(Token);

            }
            dashData.close();
        } catch (Exception e) {

            Toast.makeText(this, "Please Login Again", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.home:
                break;
//            case R.id.AllOrders:
//                Intent i = new Intent(MainActivity.this, AllOrdersMapActivity.class);
//                startActivity(i);
//                break;
            case R.id.settings:
//                startActivity(new Intent(MainActivity.this, Settings.class));
                break;
            default:
        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawer_layout.closeDrawers();

    }

    public boolean checkCallPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        if (Permissiondenied) {
            finish();
        } else {
            try {
                if (CanClose) {
                    CloseApp();
                } else {
                    CanClose = true;
                    Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CanClose = false;
                        }
                    }, 2000);
                }
            } catch (Exception e) {
                Toast.makeText(this, "An error Occurred.", Toast.LENGTH_SHORT).show();
                CloseApp();
            }
        }
    }
    private void CloseApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.ImgNavOpen:
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.END);
                } else {
                    drawer_layout.openDrawer(GravityCompat.START);
                }

//                break;
//
//            default:
        }

    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    OrderedList tab2 = new OrderedList();
                    return tab2;
                case 1:
                    PackedList tab1 = new PackedList();
                    return tab1;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
