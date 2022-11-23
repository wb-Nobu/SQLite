package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView title, content;
    EditText ed_title, ed_content;
    Button bt_new, bt_save, bt_update, bt_delete;
    ListView listView;
    NoteDatabaseHelper noteDatabaseHelper;
    NoteAdapter noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        try {
            loadData();
        } catch (Exception e)
        {}

        addEvents();
    }

    private void addEvents() {
        bt_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_title.setText("");
                ed_content.setText("");
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_title.getText().toString().equals("")||ed_content.getText().toString().equals("")){
                    return;
                } else {
                    String title = ed_title.getText().toString();
                    String content = ed_content.getText().toString();
                    Note note = new Note(title, content);

                    noteDatabaseHelper.addNote(note);
                    noteAdapter.notifyDataSetChanged();
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note notes = noteAdapter.getItem(position);

                ed_title.setText(notes.getTitle());
                ed_content.setText(notes.getContent());

                bt_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notes.setTitle(ed_title.getText().toString());
                        notes.setContent(ed_title.getText().toString());

                        noteDatabaseHelper.updateNote(notes);

                        loadData();
                    }
                });

                bt_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noteDatabaseHelper.deleteNote(notes);

                        loadData();
                    }
                });
            }
        });
    }

    //gán dl từ database vào listview
    private void loadData() {
        noteAdapter = new NoteAdapter(MainActivity.this, R.layout.layout_listview_custom);

        noteDatabaseHelper = new NoteDatabaseHelper(MainActivity.this);

        noteAdapter.addAll(noteDatabaseHelper.getAllNotes());
    }

    private void anhxa() {
        title = (TextView) findViewById(R.id.tv_title);
        content = (TextView) findViewById(R.id.tv_content);

        ed_title = (EditText) findViewById(R.id.ed_title);
        ed_content = (EditText) findViewById(R.id.ed_content);

        bt_new = (Button) findViewById(R.id.bt_new);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_save = (Button) findViewById(R.id.bt_save);
        bt_update = (Button) findViewById(R.id.bt_update);

        listView = (ListView) findViewById(R.id.lv_database);
    }
}