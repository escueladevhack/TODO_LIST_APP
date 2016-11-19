package co.devhack.todolist;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {


    private static final String TAG = "RegistroFragment";

    private FirebaseAuth mAuth;
    private ProgressDialog progress;

    @Bind(R.id.txtEmailRegistro)
    EditText txtEmailRegistro;

    @Bind(R.id.txtPasswordRegistro)
    EditText txtPasswordRegistro;

    @Bind(R.id.coordinator_registro)
    CoordinatorLayout coordinator;

    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        ButterKnife.bind(this, view);

        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    @OnClick(R.id.btnRegistrar)
    public void clickRegistrar() {

        String email = txtEmailRegistro.getText().toString();
        String password = txtPasswordRegistro.getText().toString();

        progress = ProgressDialog.show(getActivity(), "", "Cargando...");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.dismiss();

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getActivity(), TodoListActivity.class);
                            getActivity().startActivity(intent);
                        } else {
                            Log.e(TAG, task.getException().getMessage());
                            Snackbar.make(coordinator, "Error creando la cuenta", Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });


    }


}
