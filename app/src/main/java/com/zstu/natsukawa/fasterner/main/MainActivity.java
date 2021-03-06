package com.zstu.natsukawa.fasterner.main;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.zstu.natsukawa.fastener.R;
import com.zstu.natsukawa.fasterner.abilitycheck.AbilityCheckFragment;
import com.zstu.natsukawa.fasterner.base.Datas;
import com.zstu.natsukawa.fasterner.fragments.TestFragment;
import com.zstu.natsukawa.fasterner.fragments.askforleave.AskForLeaveFragment;
import com.zstu.natsukawa.fasterner.msgmangement.MessageFragment;
import com.zstu.natsukawa.fasterner.personalinfo.PersonalInfoBean;
import com.zstu.natsukawa.fasterner.personalinfo.PersonalInfoFragment;
import com.zstu.natsukawa.fasterner.progress.ProgressFragment;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.MainView, MessageFragment.OnFragmentInteractionListener
        , PersonalInfoFragment.OnFragmentInteractionListener, AbilityCheckFragment.OnFragmentInteractionListener, ProgressFragment.OnFragmentInteractionListener
        , MainFragment.OnFragmentInteractionListener, TestFragment.OnFragmentInteractionListener, AskForLeaveFragment.OnFragmentInteractionListener {

    private static int counter = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    LocationManager lm;
    private String studentNumber;
    private MainPresenter mainPresenter;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        initialiseViews();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (counter == 1) {
            navigationView.setCheckedItem(R.id.main_page);
            mainPresenter.getStudentInfo(studentNumber);
            counter--;
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        int id = item.getItemId();
        switch (id) {
            case R.id.main_page:
                mainPresenter.getStudentInfo(studentNumber);
                break;
            case R.id.previous_projects:
                counter = 1;
                toolbar.setTitle("往届项目");
                toolbar.setSubtitle(null);
                Toast.makeText(this, "previousProjects", Toast.LENGTH_SHORT).show();
                break;
            case R.id.project_discussion:
                counter = 1;
                toolbar.setTitle("项目讨论");
                toolbar.setSubtitle(null);
                Toast.makeText(this, "projectDiscussion", Toast.LENGTH_SHORT).show();
                break;
            case R.id.project_progress:
                counter = 1;
                toolbar.setTitle("项目进度");
                toolbar.setSubtitle(null);
                fragmentTransaction.replace(R.id.frame_layout, ProgressFragment.newInstance("111", "!!!"));
                break;
            case R.id.team_member:
                counter = 1;
                toolbar.setTitle("团队成员");
                toolbar.setSubtitle(null);
                Toast.makeText(this, "teamMember", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mission_allocate:
                counter = 1;
                toolbar.setTitle("任务分配");
                toolbar.setSubtitle(null);
                Toast.makeText(this, "missionAllocate", Toast.LENGTH_SHORT).show();
                break;
            default:
                fragmentTransaction.commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initialiseViews() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        studentNumber = sp.getString("studentNumber", "null");
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ImageView studentAvatar = navigationView.getHeaderView(0).findViewById(R.id.student_avatar);
        mainPresenter = new MainPresenter(MainActivity.this, Datas.BASE_URL2);
        mainPresenter.getStudentInfo(studentNumber);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        navigationView.inflateMenu(R.menu.menu_for_team_leader);
        navigationView.setCheckedItem(R.id.main_page);
        navigationView.setNavigationItemSelectedListener(this);
        Glide
                .with(this)
                .load("http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg")
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(studentAvatar);
        toggle.syncState();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private void getLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        mainPresenter.attendance(studentNumber, location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else
                    Toast.makeText(this, "你已拒绝开启定位功能，签到功能可能暂时不可用，请务必手动打开定位功能", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void showSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getPersonalInfoBean(PersonalInfoBean personalInfoBean) {
        toolbar.setTitle("主页");
        toolbar.setSubtitle(null);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        mainFragment = MainFragment.newInstance(personalInfoBean);
        fragmentTransaction.replace(R.id.frame_layout, mainFragment).commit();
    }


    @Override
    public void checkClickedButton(int buttonId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        switch (buttonId) {
            case R.id.main_person_info:
                counter = 1;
                fragmentTransaction.replace(R.id.frame_layout, PersonalInfoFragment.newInstance(studentNumber));
                break;
            case R.id.main_attendance_button:
                counter = 1;
                new AlertDialog.Builder(this)
                        .setTitle("即将获取你的位置")
                        .setMessage("如果你距离实验室很远的话还请到实验室附近进行签到> _ <")
                        .setPositiveButton("确定！", (dialog, which) -> {
                            lm = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
                            getLocation();
                            dialog.dismiss();
                        })
                        .setNegativeButton("再等等！", (dialog, which) -> dialog.dismiss())
                        .show();
                break;
            case R.id.main_ask_for_leave_button:
                counter = 1;
                fragmentTransaction.replace(R.id.frame_layout, AskForLeaveFragment.newInstance(studentNumber));
                break;
            case R.id.main_message:
                counter = 1;
                fragmentTransaction.replace(R.id.frame_layout, MessageFragment.newInstance("111", "!!!!"));
                break;
            case R.id.main_test:
                counter = 1;
                fragmentTransaction.replace(R.id.frame_layout, TestFragment.newInstance("1111", "12314"));
                break;
            case R.id.main_tech_choice:
                counter = 1;
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

}
