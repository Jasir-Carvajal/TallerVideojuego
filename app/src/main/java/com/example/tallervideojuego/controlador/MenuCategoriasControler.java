package com.example.tallervideojuego.controlador;


import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.vista.AddJugadores_act;
import com.example.tallervideojuego.vista.BancoPreguntas_act;
import com.example.tallervideojuego.vista.EditarCategoria_act;


import java.util.ArrayList;

public final class MenuCategoriasControler extends Controlador{
    /**
     * La clase controlador para el activity menu
     */

    private LinearLayout container;
    private LinearLayout[] personalizados;
    private Button add, bancoPreguntas, regresar, random;
    private Registro registroCategorias;

    /**
     * Constructor de la clase
     * @param act La referencia del activity donde se inicializa el controlador
     */
    public MenuCategoriasControler(AppCompatActivity act) {
        super(act);

        registroCategorias = new Registro(Categoria.class);

        add = this.act.findViewById(R.id.add);
        container = this.act.findViewById(R.id.cat_container);
        regresar = this.act.findViewById(R.id.regresar);
        //random = this.act.findViewById(R.id.random);
        bancoPreguntas = this.act.findViewById(R.id.bancoPreguntas);


        addButtons();
        setFunctions();
    }

    /**
     * Este método funciona para asignar los View.OnClickListener a los botones o elementos necesarios
     */
    private void setFunctions(){
        add.setOnClickListener(onAdd());
        // regresar.setOnClickListener(onRegresa());
        bancoPreguntas.setOnClickListener(onBancoPreguntas());
        //random.setOnClickListener(random());

    }
    /**
     * Este método funciona para asignar los valores al menu de categorias
     */
    private void addButtons() {

        ArrayList<Entidad> lista = registroCategorias.getEntidades();
        System.out.println(lista.size());

        personalizados = new LinearLayout[lista.size()];


        ContextThemeWrapper contextCat = new ContextThemeWrapper(act, R.style.btn_cat);
        ContextThemeWrapper contextEdit = new ContextThemeWrapper(act, R.style.edit_btn);
        ContextThemeWrapper contextLyt = new ContextThemeWrapper(act, R.style.lyt_personalizados);

        LinearLayout.LayoutParams params_lyt =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params_lyt.leftMargin = dp(40);//
        params_lyt.topMargin = dp(10);
        params_lyt.rightMargin = dp(40);//

        LinearLayout.LayoutParams params_btn_cat = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_btn_cat.width=0;
        params_btn_cat.weight= (float) 0.65;
        params_btn_cat.topMargin =dp(8);
        params_btn_cat.bottomMargin = dp(8);


        LinearLayout.LayoutParams params_btn_edit = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_btn_edit.width=0;
        params_btn_edit.weight= (float) 0.35;
        //params_btn_edit.height = 120;
        params_btn_edit.topMargin = dp(8);
        params_btn_edit.rightMargin = dp(8);
        params_btn_edit.bottomMargin = dp(8);



        Categoria random = (Categoria) registroCategorias.search("titulo","Random");
        int indexOfRandom = lista.indexOf(random);

        personalizados[indexOfRandom] = new LinearLayout(contextLyt);
        Button btn_random = new Button(contextCat);



        //aplica parametros de layaout anteriormente obtenidos
        btn_random.setLayoutParams(params_btn_cat);

        personalizados[indexOfRandom].setLayoutParams(params_lyt);

        //quita sombra
        btn_random.setStateListAnimator(null);

        //coloca titulos
        btn_random.setText(random.getTitulo());

        btn_random.setTypeface(ResourcesCompat.getFont(act.getApplicationContext(), R.font.fira_bold));
        btn_random.setOnClickListener(onPlay(random.getId()));

        //añade a la vista
        personalizados[indexOfRandom] .addView(btn_random,0);
        container.addView(personalizados[indexOfRandom] );

        for (Entidad entidad:lista) {
            if (lista.indexOf(entidad)!=indexOfRandom){
            //obtiene los parametros de layaout de los botones ya definodos
            Categoria cat = (Categoria)entidad;
            personalizados[lista.indexOf(entidad)] = new LinearLayout(contextLyt);
            Button btn_cat = new Button(contextCat);
            Button btn_editar = new Button(contextEdit);

            btn_editar.setBackgroundResource(R.drawable.btns);
            //aplica parametros de layaout anteriormente obtenidos
            btn_cat.setLayoutParams(params_btn_cat);
            btn_editar.setLayoutParams(params_btn_edit);
            personalizados[lista.indexOf(entidad)] .setLayoutParams(params_lyt);

            //quita sombra
            btn_cat.setStateListAnimator(null);
            btn_editar.setStateListAnimator(null);
            //coloca titulos
            btn_cat.setText(cat.getTitulo());
            btn_editar.setText("Editar");

            btn_cat.setTypeface(ResourcesCompat.getFont(act.getApplicationContext(), R.font.fira_bold));
            btn_editar.setTypeface(ResourcesCompat.getFont(act.getApplicationContext(), R.font.fira_bold));

            btn_cat.setOnClickListener(onPlay(cat.getId()));
            btn_editar.setOnClickListener(onEdit(cat.getId()));

            //añade a la vista
            personalizados[lista.indexOf(entidad)] .addView(btn_cat,0);
            personalizados[lista.indexOf(entidad)] .addView(btn_editar,1);
            container.addView(personalizados[lista.indexOf(entidad)] );
            }
        }

        //Toast.makeText(act, "Prueba btns", Toast.LENGTH_SHORT).show();
    }



    /**
     * MÉTODO para la funcion de editar categoria
     * @return Retorna el View.OnClickListener
     */
    private View.OnClickListener onEdit(int id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(act, EditarCategoria_act.class);

                intent.putExtra("id",id);

                act.startActivity(intent);

            }
        };
    }


    /**
     * MÉTODO para la funcion de jugar (dirige a la pantalla de añadir jugadores)
     * @return Retorna el View.OnClickListener
     */
    public View.OnClickListener onPlay(int id){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, AddJugadores_act.class);

                intent.putExtra("id",id);

                act.startActivity(intent);

            }
        };
    }

    /**
     * MÉTODO para la funcion de redirigir al banco de preguntas
     * @return Retorna el View.OnClickListener
     */
    private View.OnClickListener onBancoPreguntas() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(act, BancoPreguntas_act.class);

                act.startActivity(intent);
            }
        };
    }

    private View.OnClickListener onRegresa() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(act, "editar ", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * MÉTODO para la funcion de añadir categoria
     * @return Retorna el View.OnClickListener
     */
    private View.OnClickListener onAdd() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, EditarCategoria_act.class);
                act.startActivity(intent);
            }
        };
    }


    public View.OnClickListener random(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, AddJugadores_act.class);

                act.startActivity(intent);
            }
        };
    }
}