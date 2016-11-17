package com.example.asus.todolistbaru;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


// ini adalah MainActivity
// Activity merupakan bagian yang mengontrol view...
// satu activity hanya memiliki satu view
public class MainActivity extends AppCompatActivity {

    // MainActivity ini akan berusulan langsung dengan listvie yang ada di layout, jadi deklarasikan ListView tsb di sini
    private ListView lvTodo;

    private String[] todoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ini adalah method onCreate
        // dipanggil saat aplikasi pertama kali dijalankan
        // artinya saat Activity ini dibuat, maka eksekusi baris kode di bawah

        super.onCreate(savedInstanceState);     // baris yang ini bisa dipelajari lebih lanjut nanti
        setContentView(R.layout.activity_main);     // tempelkan view "activity_main" ke Class ini, supaya semua komponen yang ada di view tsb bisa dikenali oleh class ini

        // bagian ini nanti tahap berikutnya
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // bagian ini juga nanti seberes listview
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddTodoActivity();
            }
        });

        // definisikan lvTodo di atas dengan ListView yang ada di layout
        lvTodo = (ListView) findViewById(R.id.lvTodo);
        populateTodoList();
    }

    private void populateTodoList() {
        TodoStorage storage = new TodoStorage();
        todoItems = storage.loadTodo(this);
        if (todoItems.length > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
            lvTodo.setAdapter(adapter);

            // long click event
            lvTodo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    // opening dialog,
                    // karena kita akan mengubah salah satu item, maka kita harus tau item mana yang kita ubah
                    // jadi kita parsingkan parameter position (posisi item di list
                    openDialog(position);
                    return false;
                }
            });

        } else {
            Toast.makeText(this, "Belum ada todo", Toast.LENGTH_LONG).show();
        }

    }

    private void openDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kelola Todo");
        builder.setMessage("Isi pesan dialog")
                .setCancelable(true)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toEditTodoActivity(position);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete
                        deleteTodo(position);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void toEditTodoActivity(int position) {

        String todoItem = todoItems[position];     // ambil item yang mau kita edit

        Intent intent = new Intent(this, EditTodoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("position", position);    // supaya tau item mana yang akan diedit
        intent.putExtra("todoItem", todoItem);    // cara mengirmkan data ke intent tujuan
        startActivity(intent);
    }

    private void deleteTodo(int position) {
        String[] newTodoList = new String[todoItems.length - 1];

        int j = 0;
        for (int i=0; i<newTodoList.length; i++) {
            if (i == position) {  // kalo i == nomor urut item yang akan di hapus
                j++;    // skip list lama yang nomor urutnya tsb
            }
            newTodoList[i] = todoItems[j++];     // pindahkan list yang lama ke list baru
        }

        // simpan list baru
        TodoStorage storage = new TodoStorage();
        storage.saveTodo(newTodoList, this);


        //todoItems = null;     // kosongkan list untuk listview
        populateTodoList();   // isi ulang listview
    }

    private void toAddTodoActivity() {
        Intent intent = new Intent(this, AddTodoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
