package todolist.yassine.com.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import todolist.yassine.com.todolist.data.DatabaseHandler;
import todolist.yassine.com.todolist.model.MyList;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;
    private DatabaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new DatabaseHandler(getApplicationContext());

        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.buttonId);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveToDB();
            }
        });
    }

    public void saveToDB(){

        MyList list = new MyList();

        list.setTitle(mEditText.getText().toString().trim());

        dbh.addToDo(list);
        dbh.close();

        mEditText.setText("");

        Intent intent = new Intent(MainActivity.this, ShowToDoActivity.class);
        startActivity(intent);


    }
}
