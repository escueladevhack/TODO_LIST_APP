package co.devhack.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoListActivity extends AppCompatActivity implements ChildEventListener {

    private FirebaseDatabase database;
    private DatabaseReference referenceDB;

    @Bind(R.id.lstTODO)
    ListView lstTODO;

    @Bind(R.id.txtItemTarea)
    EditText txtItemTarea;

    ArrayAdapter<String> adapter;

    List<String> lstItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        ButterKnife.bind(this);

        lstItems = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lstItems);

        lstTODO.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();

        referenceDB = database.getReference("Tareas");

        referenceDB.addChildEventListener(this);
    }

    @OnClick(R.id.btnAgregar)
    public void clickBtnAgregar() {
        referenceDB.push().setValue(txtItemTarea.getText().toString());
        txtItemTarea.setText("");
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String itemTarea = String.valueOf(dataSnapshot.getValue());
        lstItems.add(itemTarea);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
