package com.viktor.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioGroup radioButtonGroup;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonHigh;
    private Button buttonSave;

    private AddNoteViewModel addNoteViewModel;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    finish();
                }

            }
        });
        initViews();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void initViews() {
        editTextNote = findViewById(R.id.editTextNote);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonHigh = findViewById(R.id.radioButtonHigh);
        buttonSave = findViewById(R.id.buttonSave);
    }

    private void saveNote() {

        String text = editTextNote.getText().toString().trim();
        int priority = getPriority();
        Note note = new Note(text, priority);
        if (text.isEmpty()){
            Toast.makeText(AddNoteActivity.this, "Поле текст должно быть заполнено", Toast.LENGTH_SHORT).show();
        } else { addNoteViewModel.saveNote(note);}


//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (text.isEmpty()){
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(AddNoteActivity.this, "Поле текст должно быть заполнено", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    noteDatabase.notesDao().add(note);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            finish();
//                        }
//                    });
//                }
//
//            }
//        });
//        thread.start();

    }

    private int getPriority() {
        int priority;
        if (radioButtonLow.isChecked()) {
            priority = 0;
        } else if (radioButtonMedium.isChecked()) {
            priority = 1;
        } else  {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}