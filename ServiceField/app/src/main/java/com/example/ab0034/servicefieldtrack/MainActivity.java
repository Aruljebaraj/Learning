package com.example.ab0034.servicefieldtrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ab0034.servicefieldtrack.Fragment.ClosedCase;
import com.example.ab0034.servicefieldtrack.Fragment.pendingCase;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener,
        pendingCase.OnFragmentInteractionListener, ClosedCase.OnChildFragmentToActivityInteractionListener, AdapterView.OnItemClickListener {
    ListView Nav_list;
    ArrayList<NavDto> NavList;
    DrawerLayout drawer_layout;
    FloatingActionButton FabAddNewLead;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView ImgNavOpen;
    String count1 = "", count2 = "";
    int NewReferralsCount;
    TextView TxtHeader;
    private boolean PressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImgNavOpen = (ImageView) findViewById(R.id.ImgNavOpen);
        TxtHeader = (TextView) findViewById(R.id.TxtHeader);
        Nav_list = (ListView) findViewById(R.id.Nav_list);
        Nav_list.setOnItemClickListener(this);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        FabAddNewLead = (FloatingActionButton) findViewById(R.id.FabAddNewLead);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.Tabs);

        Nav();
        clickListeners();
        Nav_list.setAdapter(new NavAdapter(this, NavList));
        setupViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void clickListeners() {
        ImgNavOpen.setOnClickListener(this);
//        TxtAddNewReferrals.setOnClickListener(this);
        FabAddNewLead.setOnClickListener(this);
        tabLayout.setOnTabSelectedListener(this);
    }

    private View Customized(String Name, int type) {
        String totalcount = "";
        int image = 0;
        switch (type) {
            case 0:
                totalcount = count1;
                image = R.drawable.newreferrals;
                break;
            case 1:
                totalcount = count2;
                image = R.drawable.pendingreports;
                break;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dashboard_tab, null);
//        view.setScaleY(-1);
        ImageView ImgCustomTab = (ImageView) view.findViewById(R.id.ImgCustomTab);
        TextView TxtCount = (TextView) view.findViewById(R.id.TxtCount);
        TextView TabName = (TextView) view.findViewById(R.id.TxtTabName);
        TabName.setText(Name);
        TxtCount.setText(totalcount);
        ImgCustomTab.setImageResource(image);
        return view;
    }


    public void Nav() {
        NavList = new ArrayList<>();
        NavDto dto;
        dto = new NavDto();
        dto.Nav_items = "Home";
        dto.Nav_img = R.drawable.ic_home;
        NavList.add(dto);
        dto = new NavDto();
        dto.Nav_items = "Setting";
        dto.Nav_img = R.drawable.settings_hollow;
        NavList.add(dto);
        dto = new NavDto();
        dto.Nav_items = "Logout";
        dto.Nav_img = R.drawable.logout_hollow;
        NavList.add(dto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_layout:
                drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
                    drawer_layout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer_layout.openDrawer(Gravity.LEFT);
                }
                break;

            case R.id.FabAddNewLead:
                Intent i = new Intent(MainActivity.this, AddNewReferrals.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.go_in, R.anim.go_out);
                break;

//            case R.id.TxtHeader:
//                anim();
//                break;

            case R.id.ImgNavOpen:
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.END);
                } else {
                    drawer_layout.openDrawer(GravityCompat.START);

                }
                break;

            default:
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new pendingCase(), "---");
        adapter.addFragment(new ClosedCase(), "---");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                TxtHeader.setText(getString(R.string.pending_cases));
                pendingCase pendingCasesFragment = new pendingCase();
                break;
            case 1:
                TxtHeader.setText(getString(R.string.closed_cases));
                ClosedCase closedCaseFragmentnew = new ClosedCase();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void messageFromParentFragmentToActivity(String NewReferralsCount) {
        count1 = NewReferralsCount;
//        if (!isRefreshed) {
        tabLayout.getTabAt(0).setCustomView(Customized(getString(R.string.pending_cases), 0));
//
//        } else {
//            try {
//
//                TextView v = (TextView) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.TxtCount);
//                v.setText(NewReferralsCount);
//            } catch (Exception e) {
//                Toast.makeText(this, "Crashed", Toast.LENGTH_SHORT).show();
//            }
//        }
//        isRefreshed = true;
        Log.i("TAG", NewReferralsCount);
    }

    @Override
    public void messageFromChildFragmentToActivity(String PendingReferralsCount) {
        count2 = PendingReferralsCount;
//        if (!isRefreshed2) {
        tabLayout.getTabAt(1).setCustomView(Customized(getString(R.string.closed_cases), 1));
//        } else {
//            try {
//
//                TextView v = (TextView) tabLayout.getTabAt(1).getCustomView().findViewById(R.id.TxtCount);
//                v.setText(PendingReferralsCount);
//            } catch (Exception e) {
//                Toast.makeText(this, "Crashed", Toast.LENGTH_SHORT).show();
//            }
//        }
//        isRefreshed2 = true;
        Log.i("TAG", PendingReferralsCount);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (PressedOnce) {
                PressedOnce = false;
//                TxtHeaderName.setText("Dashboard");
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
//                super.onBackPressed();
                PressedOnce = true;


            } else {
//                finish();

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("CONFIRM EXIT")
                        .setContentText("Do you want to exit the app?")
                        .setConfirmText("Exit")
                        .setCancelText("Cancel")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                System.exit(0);
                            }
                        })
                        .show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Logout();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
        }
    }

//    public void anim() {
//        SpringSystem springSystem = SpringSystem.create();
//
//// Add a spring to the system.
//        Spring spring = springSystem.createSpring();
//
//// Add a listener to observe the motion of the spring.
//        spring.addListener(new SimpleSpringListener() {
//
//            @Override
//            public void onSpringUpdate(Spring spring) {
//                // You can observe the updates in the spring
//                // state by asking its current value in onSpringUpdate.
//                float value = (float) spring.getCurrentValue();
//                float scale = 1f - (value * 0.5f);
//                TxtHeader.setScaleX(scale);
//                TxtHeader.setScaleY(scale);
//            }
//        });
//
//// Set the spring in motion; moving from 0 to 1
//        spring.setEndValue(1);
//    }

    public void Logout()

    {
        Intent i = new Intent(MainActivity.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    class NavAdapter extends ArrayAdapter<ArrayList<String>> {
        ArrayList<NavDto> NavArraylist;
        Context context;

        public NavAdapter(Context context, ArrayList<NavDto> NavArraylist) {
            super(context, R.layout.row_nav_items);
            this.context = context;
            this.NavArraylist = NavArraylist;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_nav_items, parent, false);
            NavDto Nav = NavArraylist.get(position);
            TextView TxtNavItem = (TextView) v.findViewById(R.id.TxtNavItem);
            ImageView ImgNavItem = (ImageView) v.findViewById(R.id.ImgNavItem);
            TxtNavItem.setText(Nav.Nav_items);
            ImgNavItem.setImageResource(Nav.Nav_img);
            return v;
        }

        @Override
        public int getCount() {
            return NavArraylist.size();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    class NavDto {
        String Nav_items;
        int Nav_img;
    }
}
