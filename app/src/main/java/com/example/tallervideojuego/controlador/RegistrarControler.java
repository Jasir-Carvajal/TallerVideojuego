package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.base.Controlador;
import com.example.tallervideojuego.modelo.Api.Api;
import com.example.tallervideojuego.modelo.LoadingDialog;
import com.example.tallervideojuego.vista.Login_act;
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.util.concurrent.FutureCallback;

public class RegistrarControler extends Controlador {

    /**
     * La clase controlador para el activity registrar
     */

    private AppCompatActivity act;
    private Button btnRegistrarse;
    private TextInputEditText txtNombre, txtCorreo, txtPassword;
    private Api api;
    private LoadingDialog loadingDialog;

    /**
     * Constructor de la clase
     *
     * @param act se refiere al Activity que corresponde este controlador
     */
    public RegistrarControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnRegistrarse = this.act.findViewById(R.id.btnRegistrarse);
        txtNombre = this.act.findViewById(R.id.txtNombre);
        txtCorreo = this.act.findViewById(R.id.txtCorreo);
        txtPassword = this.act.findViewById(R.id.txtPassword);

        api = new Api();

        loadingDialog = new LoadingDialog(this.act);

        setFunctions();
    }

    public void setFunctions(){
        btnRegistrarse.setOnClickListener(registrarse());
    }

    /**
     * MÉTODO para la funcion de registrarse
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener registrarse(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //loadingDialog.starLoadingDialog();
                String nombre = txtNombre.getText().toString().trim();
                String correo = txtCorreo.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String retorno;


                if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                    message("Debe de llenar todos los campos");
                }else if(!isValidEmail()){
                    txtCorreo.setError("Correo invalido");
                } else {
                    loadingDialog.starLoadingDialog();

                      api.register(nombre, correo, password, new FutureCallback<String>() {
                           @Override
                           public void onSuccess(String result) {
                               onRegSuccess(result);
                           }

                           @Override
                           public void onFailure(Throwable t) {

                           }
                       });


                }

            }
        };
    }

    @WorkerThread
    public void onRegSuccess(String retorno){
        act.runOnUiThread(() -> {

            if (retorno.equalsIgnoreCase("{\"status\":false,\"message\":\"validation error\",\"errors\":{\"email\":[\"The email has already been taken.\"]}}")){
                loadingDialog.dismissDialog();
                txtCorreo.setError("Ya existe una cuenta con este correo");
            } else{
//                           Intent intent = new Intent(act, MenuCategorias_act.class);
                loadingDialog.dismissDialog();
                Intent intent = new Intent(act, Login_act.class);
                act.startActivity(intent);
                regresar();
            }
        });
    }


    public boolean isValidEmail() {
        String correo = txtCorreo.getText().toString().trim();

        return (Patterns.EMAIL_ADDRESS.matcher(correo).matches());
    }
}
