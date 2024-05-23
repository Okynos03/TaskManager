package com.example.taskmanager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    TextView welcome;
    ListView tasksList;
    private FloatingActionButton fab;
    private User user;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        tasksList = findViewById(R.id.tasksList);

        //welcome = findViewById(R.id.welcome);

        //String full_name = getIntent().getExtras().getString("full_name");

        ArrayAdapter<String> tasks = new ArrayAdapter<>(this,
                android.R.layout.select_dialog_multichoice,
                new String[] {"lavar carro", "lavar trastes", "comer"});
        tasksList.setChoiceMode(2);
        tasksList.setAdapter(tasks);

        Bundle bundle = getIntent().getExtras();
        user =(User) bundle.getSerializable("user");

        String full_name = user.getFirts_name() + user.getLast_name();


        //welcome.setText("\nBy: " + full_name);
        /*ArrayAdapter<String> tasks = new ArrayAdapter<>(this,
                android.R.layout.select_dialog_multichoice,
                new String[] {"lavar carro", "lavar trastes", "comer"});
        tasksList.setChoiceMode(2);
        tasksList.setAdapter(tasks);

        Bundle bundle = getIntent().getExtras();
        User user =(User) bundle.getSerializable("user");
        String full_name = user.getFirts_name() + user.getLast_name();
        //txtName.setText("HELLO george laurence"  + full_name);*/

        //welcome.setText("Welcome " + full_name);
        initFab();
    }

    private void initFab(){
        fab = findViewById(R.id.fab);
        fab.setOnClickListener((v) ->{
            showAddTaskForm();
        });
    }
    private void showAddTaskForm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Add Task");
        //builder.setMessage("Hello");
        View view = LayoutInflater.from(this)
                .inflate(R.layout.task_add_form, null);
        builder.setView(view);

        EditText taskName = view.findViewById(R.id.taskName);
        EditText taskDescription = view.findViewById(R.id.taskDescription);
        EditText taskDate = view.findViewById(R.id.taskDate);
        Button btnSaveTask = view.findViewById(R.id.btnSaveTask);
        Button btnCancelTask = view.findViewById(R.id.btnCancelTask);

        Dialog dialog = builder.create();

        btnCancelTask.setOnClickListener((v) -> dialog.dismiss());
        String taskId = UUID.randomUUID().toString();

        btnSaveTask.setOnClickListener((v) -> {
            Task newTask = new Task(taskId, taskName.getText().toString(), taskDescription.getText().toString(),
                    taskDate.getText().toString(), false,user.getUserId());

            createFireBaseTask(newTask);
            dialog.dismiss();

            Toast.makeText(getApplicationContext(), "Task saved successfully...", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    private void createFireBaseTask(Task task){
        DatabaseReference ref = db.getReference("task/" + task.getId());
        ref.setValue(task);
    }
}