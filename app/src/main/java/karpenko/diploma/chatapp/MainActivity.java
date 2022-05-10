package karpenko.diploma.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView messageListView;
    private MessageAdapter adapter;
    private ProgressBar progressBar;
    private ImageButton sendImageButton;
    private Button sendMessageButton;
    private EditText messageEditText;
    private String userName;

    FirebaseDatabase database;
    DatabaseReference messagesDatabaseReference;
    ChildEventListener messagesChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        messagesDatabaseReference = database.getReference().child("messages");


        userName = "DefaultUser";
        progressBar = findViewById(R.id.progressBar);
        sendImageButton = findViewById(R.id.sendPhotoButton);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        messageEditText = findViewById(R.id.messageEditText);

        messageListView = findViewById(R.id.massageListView);
        List<AwesomeMessage> awesomeMessages= new ArrayList<>();
        adapter = new MessageAdapter(this, R.layout.message_item, awesomeMessages);
        messageListView.setAdapter(adapter);

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence,
                                          int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence,
                                      int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0){
                    sendMessageButton.setEnabled(true);
                }else sendMessageButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        messageEditText.setFilters(new InputFilter[]
                {new InputFilter.LengthFilter(255) {
        }});

        sendMessageButton.setOnClickListener(view -> {
            AwesomeMessage message = new AwesomeMessage();
            message.setText(messageEditText.getText().toString());
            message.setName(userName);
            message.setImageUri(null);

            messagesDatabaseReference.push().setValue(message);

            messageEditText.setText("");
        });

        sendImageButton.setOnClickListener(view -> {

        });

        messagesChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AwesomeMessage message = snapshot.getValue(AwesomeMessage.class);
                adapter.add(message);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        messagesDatabaseReference.addChildEventListener(messagesChildEventListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}