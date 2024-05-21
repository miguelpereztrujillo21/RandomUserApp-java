package com.mpt.randomuserapp_java.modules.main;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.mpt.randomuserapp_java.adapters.UserAdapter;
import com.mpt.randomuserapp_java.databinding.ActivityMainBinding;
import com.mpt.randomuserapp_java.models.User;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel ;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityMainBinding.inflate(getLayoutInflater());
       viewModel = new ViewModelProvider(this).get(MainViewModel.class);
       setContentView(binding.getRoot());
       setUpAdapter();
       initObservers();
       viewModel.getUsersFromRepository();

    }

    private void initObservers() {
        viewModel.getUsers().observe(this, users -> {
            adapter.submitList(users);
        });
    }

    private void setUpAdapter() {
        adapter = new UserAdapter(this, user -> {
            // Handle click event
        });
        binding.recyclerMain.setAdapter(adapter);
    }



}