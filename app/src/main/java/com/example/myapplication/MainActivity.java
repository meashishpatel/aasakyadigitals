package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextAge;
    private Button buttonSubmit, buttonMap, buttonStats; // Added buttonStats
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private List<User> userList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAge = findViewById(R.id.editTextAge);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonMap = findViewById(R.id.buttonMap); // Button to open map page
        buttonStats = findViewById(R.id.buttonStats); // Button to open stats page
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);

        // Initialize Database
        databaseHelper = new DatabaseHelper(this);

        // Load Data from Database
        userList = databaseHelper.getAllUsers();
        userAdapter = new UserAdapter(userList);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsers.setAdapter(userAdapter);

        // Handle Submit Button Click
        buttonSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String ageText = editTextAge.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(ageText)) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageText);
            if (age <= 0 || age > 100) {
                Toast.makeText(this, "Age must be between 1 and 100", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(name, email, age);
            databaseHelper.addUser(user);
            userList.add(user);
            userAdapter.notifyDataSetChanged();

            editTextName.setText("");
            editTextEmail.setText("");
            editTextAge.setText("");
            Toast.makeText(this, "User saved successfully", Toast.LENGTH_SHORT).show();
        });

        // Handle Map Button Click
        buttonMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });

        // Handle Stats Button Click
        buttonStats.setOnClickListener(v -> {
            // Navigate to StatsActivity
            Intent intent = new Intent(MainActivity.this, StatsActivity.class);
            startActivity(intent);
        });
    }
}
