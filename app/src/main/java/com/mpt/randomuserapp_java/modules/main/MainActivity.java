package com.mpt.randomuserapp_java.modules.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mpt.randomuserapp_java.R;
import com.mpt.randomuserapp_java.adapters.UserAdapter;
import com.mpt.randomuserapp_java.databinding.ActivityMainBinding;
import com.mpt.randomuserapp_java.helpers.Constants;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.annotations.NonNull;

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
         initComponents();
       initObservers();
       initListeners();
       viewModel.getUsersFromDatabase();
    }

    private void initComponents() {
        setUpAdapter();
        initChips();
    }

    private void initObservers() {
        viewModel.getUsers().observe(this, users ->
                adapter.submitList(users));

        viewModel.getFilterEmail().observe(this, viewModel::getUsersStartingWith);
        viewModel.getFilterGender().observe(this, gender -> {

        });
    }

    private void initListeners() {
        binding.searchBarMain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setFilterEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
        binding.swipeMain.setOnRefreshListener(() -> {
            viewModel.syncData();
            binding.swipeMain.setRefreshing(false);
        });
    }

    private void setUpAdapter() {
        adapter = new UserAdapter(this, user -> {
            // Handle click event
        });
        binding.recyclerMain.setAdapter(adapter);
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        binding.recyclerMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@androidx.annotation.NonNull @NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
                    if (dy > 0 && totalItemCount >= lastVisible - 5) {
                        viewModel.loadNextPage();
                    }
                }
            }
        });
    }

    private void initChips() {
        initChip(binding.layoutFilters.chipGenderMaleMain, Constants.SIMPLE_MALE);
        initChip(binding.layoutFilters.chipGenderFemaleMain,Constants.SIMPLE_FEMALE);
    }

    private void initChip(Chip chip, String filter) {
        chip.setOnClickListener(v -> {
            Chip chip1 = (Chip) v;

                viewModel.onChipCheckedChanged(chip1.isChecked(), filter);

        });
    }

}