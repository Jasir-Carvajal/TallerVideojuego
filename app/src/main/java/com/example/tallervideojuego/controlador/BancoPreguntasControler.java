package com.example.tallervideojuego.controlador;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallervideojuego.R;
import com.example.tallervideojuego.controlador.bace.Controlador;
import com.example.tallervideojuego.modelo.base.Entidad;
import com.example.tallervideojuego.modelo.base.Registro;
import com.example.tallervideojuego.modelo.entidades.Carta;
import com.example.tallervideojuego.modelo.entidades.Categoria;
import com.example.tallervideojuego.modelo.registro.RegistroCat_Car;
import com.example.tallervideojuego.vista.EditarCarta_act;
import com.example.tallervideojuego.vista.EditarCategoria_act;

import java.util.ArrayList;

public class BancoPreguntasControler extends Controlador{

    private AppCompatActivity act;

    private Button btnAplicar, btnAgregar;

    private Spinner spinnerCat;
    private AdapterBancoPreguntas adapterBancoPreguntas;

    private Registro registroCategorias;
    private Registro registroCartas;
    private RegistroCat_Car registroRelacion;





    private ArrayAdapter<CharSequence> adapter;
    private ArrayList<String> catItems = new ArrayList<>();

    private ListView listaE;
    private String itemSelected;



    public BancoPreguntasControler(AppCompatActivity act) {
        super(act);
        this.act = act;

        btnAplicar = this.act.findViewById(R.id.btnAplicar);
        btnAgregar = this.act.findViewById(R.id.btnAgregar);
        listaE = this.act.findViewById(R.id.lista_categorias);


        registroCategorias = new Registro(Categoria.class);
        registroCartas = new Registro(Carta.class);
        registroRelacion = new RegistroCat_Car();



        //  = this.act.findViewById(R.id.cartas_container);


        catItems = getStrings();

        catItems.add(0,"TODAS");

        //////////SPINNER MANEJO/////////////
        spinnerCat = act.findViewById(R.id.spinnerCat);

        adapter = new ArrayAdapter(this.act, R.layout.style_spinner,catItems);
        adapter.setDropDownViewResource(R.layout.style_dropdown_spinner);
        spinnerCat.setAdapter(adapter);

        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelected = adapterView.getItemAtPosition(i).toString();
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //////////SPINNER MANEJO/////////////
        setFunctions();


        update(registroCartas.getListaEntidades());
    }



    public void setFunctions(){
        btnAgregar.setOnClickListener(add());
        btnAplicar.setOnClickListener(apply());
    }

    public View.OnClickListener edit(Entidad entidad){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = entidad.getId();
                Intent intent = new Intent(act, EditarCarta_act.class);
                intent.putExtra("id",id);
                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener delete(Entidad entidad){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registroCartas.delete(entidad);
                update(registroCartas.getListaEntidades());
            }
        };
    }

    private void update(ArrayList<Entidad> lista_usable) {
        adapterBancoPreguntas = new AdapterBancoPreguntas(lista_usable,act,this);
        listaE.setAdapter(adapterBancoPreguntas);
    }

    public View.OnClickListener add(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, EditarCarta_act.class);
                act.startActivity(intent);
            }
        };
    }

    public View.OnClickListener apply(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(itemSelected.equalsIgnoreCase("TODAS")) {
                   update(registroCartas.getEntidades());
               } else {
                   Categoria categoria = (Categoria) registroCategorias.search("titulo", itemSelected);
                   categoria.setRegistroCat_car(registroRelacion);
                   ArrayList<Entidad> cartas = categoria.getCartasDeCategoria();
                   update(cartas);
               }
            }
        };
    }

    public ArrayList<String> getStrings(){
        ArrayList<String> listArray = new ArrayList<>();

        for (int i = 0; i< registroCategorias.getListaEntidades().size(); i++){

            Categoria categoria = (Categoria) registroCategorias.getEntidades().get(i);

            listArray.add(categoria.getTitulo());
        }

        return  listArray;
    }



}
