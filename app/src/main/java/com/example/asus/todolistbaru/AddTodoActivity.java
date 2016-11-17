package com.example.asus.todolistbaru;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by ASUS on 11/12/2016.
 */

public class AddTodoActivity extends AppCompatActivity {

    private TodoStorage storage = new TodoStorage();
    private TextInputEditText tietTodoDesc;
    private Button btnKirim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tietTodoDesc = (TextInputEditText) findViewById(R.id.tietTodoDesc);
        btnKirim = (Button) findViewById(R.id.btnKirim);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        if (tietTodoDesc.getText().toString().isEmpty() || tietTodoDesc.getText().toString().equals("")) {
            Toast.makeText(this, "Tuliskan todo..", Toast.LENGTH_LONG).show();
        } else {
            String[] oldTodoList = storage.loadTodo(this);
            String[] newTodoList = new String[oldTodoList.length + 1];

            if (oldTodoList.length > 0) {
                for(int i=0; i<oldTodoList.length; i++) {
                    newTodoList[i] = oldTodoList[i];
                }
            }
            newTodoList[oldTodoList.length] = tietTodoDesc.getText().toString();

            storage.saveTodo(newTodoList, this);

            toMainActivity();
        }
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
