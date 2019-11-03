package com.androidtuts4u.arun.registartionapp.ui.home;

import android.content.Context;
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


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    public static RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;
    String parent_name = "Zen";
    public static int parent_id = -1;
    Context context;
    Button btnRegister;

    public static List<Entity> nodeList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        context = getContext();
        parent_id = MyAppDatabase.getAppDatabase(context).dao().findByName(parent_name).get(0).getId();
        parent_name = "Zen";
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_users);
        btnRegister = (Button) root.findViewById(R.id.bt_register);

        MyAppDatabase.getAppDatabase(getContext());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegistrationActivity.class);
                intent.putExtra("PARENT_ID", parent_id);
                startActivity(intent);
            }
        });

        nodeList = new ArrayList<Entity>();
        nodeList = MyAppDatabase.getAppDatabase(getContext()).dao().findbyParentId(parent_id);

        layoutManager = new LinearLayoutManager(getActivity());
        userAdapter = new UserDetailsAdapter(nodeList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);


        return root;
    }
}