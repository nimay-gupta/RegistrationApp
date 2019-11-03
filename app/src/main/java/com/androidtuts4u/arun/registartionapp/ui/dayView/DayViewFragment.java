package com.androidtuts4u.arun.registartionapp.ui.dayView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.androidtuts4u.arun.registartionapp.R;
import com.androidtuts4u.arun.registartionapp.RegistrationActivity;
import com.androidtuts4u.arun.registartionapp.adapter.UserDetailsAdapter;
import com.androidtuts4u.arun.registartionapp.database.Entity;
import com.androidtuts4u.arun.registartionapp.database.MyAppDatabase;

import java.util.ArrayList;
import java.util.List;


public class DayViewFragment extends Fragment {

    private DayViewModel galleryViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button btnRegister;

    List<Entity> nodeList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(DayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_description, container, false);


        recyclerView = (RecyclerView) root.findViewById(R.id.rv_users);
        btnRegister = (Button) root.findViewById(R.id.bt_register);

        MyAppDatabase.getAppDatabase(getContext());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegistrationActivity.class);
                startActivity(intent);
//                finish();
            }
        });

        nodeList = new ArrayList<Entity>();
        nodeList = MyAppDatabase.getAppDatabase(getContext()).dao().getAllPlayers();

        layoutManager = new LinearLayoutManager(getActivity());
        UserDetailsAdapter.isDayView = true;
        userAdapter = new UserDetailsAdapter(nodeList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        return root;

    }
}