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
 * Created by ASUS on 11/13/2016.
 */

public class EditTodoActivity extends AppCompatActivity {

    private String todoItem;
    private int position;
    private TextInputEditText tietTodoDesc;
    private Button btnKirim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // cara menerima data dari intent/activity sebelumnya
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            position = intent.getIntExtra("position", 0);
            todoItem = intent.getStringExtra("todoItem");
        }

        // definisikan komponen yang ada di layout
        tietTodoDesc = (TextInputEditText) findViewById(R.id.tietTodoDesc);
        tietTodoDesc.setText(todoItem);    // supaya item yang mau diedit muncul di input box
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
            TodoStorage storage = new TodoStorage();

            // ambil list yang udah disimpen
            String[] oldList = storage.loadTodo(this);

            //buat list baru
            String[] newList = new String[oldList.length];

            for (int i=0; i<oldList.length; i++) {
                if (i == position) {   // jika posisi list sama dg urutan item yang lagi kita edit
                    newList[i] = tietTodoDesc.getText().toString();    // isi item tsb dg inputan yang baru
                } else {
                    newList[i] = oldList[i];
                }
            }

            // simpan list yang baru
            storage.saveTodo(newList, this);

            // kembali ke todolist
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
