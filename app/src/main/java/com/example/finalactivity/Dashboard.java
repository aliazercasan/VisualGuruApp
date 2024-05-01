package com.example.finalactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{


    public CardView Wedding, Portrait, Pet, Fashion,Fineart,Passport,JobId,Graduation;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);





        Wedding = findViewById(R.id.wedding);
        Portrait = findViewById(R.id.portrait);
        Pet = findViewById(R.id.pet);
        Fashion = findViewById(R.id.fashion);
        Fineart = findViewById(R.id.fineart);
        Passport = findViewById(R.id.passport);
        JobId = findViewById(R.id.jobId);
        Graduation = findViewById(R.id.graduation);

        Wedding.setOnClickListener(this);
        Portrait.setOnClickListener(this);
        Pet.setOnClickListener(this);
        Fashion.setOnClickListener(this);
        Fineart.setOnClickListener(this);
        Passport.setOnClickListener(this);
        JobId.setOnClickListener(this);
        Graduation.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

    }

    @Override
    public void onClick(View v) {
        Intent intent;

        if (v.getId() == R.id.wedding) {
            intent = new Intent(this, Wedding.class);
            startActivity(intent);
        } else if (v.getId() == R.id.portrait) {
            intent = new Intent(this, Portrait.class);
            startActivity(intent);
        } else if (v.getId() == R.id.pet) {
            intent = new Intent(this, Pet.class);
            startActivity(intent);
        } else if (v.getId() == R.id.fashion) {
            intent = new Intent(this, Fashion.class);
            startActivity(intent);
        }else if (v.getId() == R.id.fineart) {
            intent = new Intent(this, Fashion.class);
            startActivity(intent);
        }else if (v.getId() == R.id.passport) {
            intent = new Intent(this, Fashion.class);
            startActivity(intent);
        }else if (v.getId() == R.id.jobId) {
            intent = new Intent(this, Fashion.class);
            startActivity(intent);
        }else if (v.getId() == R.id.graduation) {
            intent = new Intent(this, Fashion.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.nav_account) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
        } else if (item.getItemId() == R.id.nav_history) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new History()).commit();
        } else if (item.getItemId() == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new About()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void repFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
