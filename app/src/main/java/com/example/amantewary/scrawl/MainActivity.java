package com.example.amantewary.scrawl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.amantewary.scrawl.API.ILabelAPI;
import com.example.amantewary.scrawl.Adapters.NotesList;
import com.example.amantewary.scrawl.Handlers.LabelHandler;

import com.example.amantewary.scrawl.Handlers.NoteHandler;
import com.l4digital.fastscroll.FastScrollRecyclerView;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    FastScrollRecyclerView notesListView;
    NotesList notesAdapter;
    ArrayList<String> labelOptions;
    NavigationView navigationView;
    FileOutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        notesListView = findViewById(R.id.viewNoteList);

        //Loading Labels from database
        //TODO: Need to move this in splash screen
        ILabelAPI labelAPI = RetroFitInstance.getRetrofit().create(ILabelAPI.class);

        Call<List<LabelHandler>> call = labelAPI.getLabels();
        call.enqueue(new Callback<List<LabelHandler>>() {
            @Override
            public void onResponse(Call<List<LabelHandler>> call, Response<List<LabelHandler>> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
                Log.d(TAG, "onResponse: Received Information: " + response.body().toString());
                List<LabelHandler> labels = response.body();
                labelOptions = new ArrayList<String>();
                for (LabelHandler label : labels) {
                    Log.e("label", label.getName());
                    labelOptions.add(label.getName());
                }

//                writeToFile(labelOptions);


                LabelLoader.getInstance().saveLabel(MainActivity.this, labelOptions);

            }

            @Override
            public void onFailure(Call<List<LabelHandler>> call, Throwable t) {
                Log.e(TAG, "onFailure: Something Went Wrong: " + t.getMessage());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Factory Design Pattern
                Intent intent = new Intent(MainActivity.this, AddNotesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestHandler request = new RequestHandler();
        request.getNoteList(MainActivity.this, notesListView);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_add) {
            final Menu menu = navigationView.getMenu();

                menu.add("Runtime item " + 1);


        } else if (id == R.id.nav_logout) {
            showDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");

        String positiveText = getString(android.R.string.yes);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                });

        String negativeText = getString(android.R.string.no);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing here
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    public void signOut() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.logoutUser();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

//    public void writeToFile(ArrayList<String> labels){
//        String filename = "labels.txt";
//        try {
//            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//            for (String fileLabels: labels){
//                outputStream.write(fileLabels.getBytes());
//                outputStream.write("\n".getBytes());
//            }
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
