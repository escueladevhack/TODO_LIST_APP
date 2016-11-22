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

    // Se recupera los controles con Butterknife
    @Bind(R.id.lstTODO)
    ListView lstTODO;

    @Bind(R.id.txtItemTarea)
    EditText txtItemTarea;

    // Se crea adaptador => es el puente entre los datos y los views
    ArrayAdapter<String> adapter;

    List<String> lstItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        ButterKnife.bind(this);

        // Se inicializa la lista de datos y el adaptador
        lstItems = new ArrayList<>();
        // Se asigna un layout al adaptador dado por android
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lstItems);
        // Se asigna el adaptador al listview
        lstTODO.setAdapter(adapter);

        // Se crea una instancia a la BD de firebase
        database = FirebaseDatabase.getInstance();
        // Se referencia al node de tareas del JSON creado en firebase
        referenceDB = database.getReference("Tareas");
        // Se adiciona un listeners a al nodo de tareas para saber sus cambios
        referenceDB.addChildEventListener(this);
    }

    @OnClick(R.id.btnAgregar)
    public void clickBtnAgregar() {
        // Se adiciona un nodo a tareas en firebase - Push produce un key
        referenceDB.push().setValue(txtItemTarea.getText().toString());
        txtItemTarea.setText("");
    }

    // Este metodo recupera el node agregado en tareas
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String itemTarea = String.valueOf(dataSnapshot.getValue());
        lstItems.add(itemTarea);
        // se llama esta metodo para que el list se pinte con los valores nuevos
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
