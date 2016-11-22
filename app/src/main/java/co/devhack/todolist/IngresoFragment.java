package co.devhack.todolist;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
public class IngresoFragment extends Fragment {

    private FirebaseAuth mAuth;

    private static final String TAG = "IngresoFragment";
    private ProgressDialog progress;

    // Se recupera los controles con Butterknife
    @Bind(R.id.txtemail)
    EditText txtEmail;

    @Bind(R.id.txtPassword)
    EditText txtPassword;

    @Bind(R.id.coordinator_ingreso)
    CoordinatorLayout coordinator;


    public IngresoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingreso, container, false);

        ButterKnife.bind(this, view);

        // Se crea instancia para la auth de firebase
        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    @OnClick(R.id.btnCrearCuenta)
    public void clickCrearCuenta() {
        // Permite asignar un fragment a un activity
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameUsuario, new RegistroFragment());
        transaction.commit();
    }

    @OnClick(R.id.btnIngresar)
    public void clickIngresar() {

        progress = ProgressDialog.show(getActivity(), "", "Cargando...");

        // Se autentica con Firebase Auth por medio de un e-mail y password
        mAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.dismiss();

                        if (task.isSuccessful()) {
                            // Si la autenticaccion fue correcta se redirecciona a la actividad de tareas
                            Intent intent = new Intent(getActivity(), TodoListActivity.class);
                            getActivity().startActivity(intent);

                        } else {
                            Log.e(TAG, task.getException().getMessage());
                            Snackbar.make(coordinator, "Error ingresando a la cuenta", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
