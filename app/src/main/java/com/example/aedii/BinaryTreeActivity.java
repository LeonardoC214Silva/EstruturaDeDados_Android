package com.example.aedii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;

public class BinaryTreeActivity extends AppCompatActivity {
    private enum stateUser {insert,remove,search,none}

    ScreenBinTree node;
    SurfaceView surfaceView;
    LinearLayout linearLayout;
    Button btnRemove,btnSearch,btnInsert;
    Button btnMin,btnMax,btnOK;
    stateUser clickButtons;
    private static final String TREESTATE = "chavearvore";

    TextInputEditText txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //elementos da interface
        setContentView(R.layout.activity_binary_tree);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //garante que apareça o botao de retorno na barra da atividade
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle("Arvore binaria");
        }
        linearLayout = findViewById(R.id.linLayoutInput);
        surfaceView= findViewById(R.id.surfaceView);

        //
        if(savedInstanceState==null) {
            node = new ScreenBinTree(surfaceView.getHolder());
        }else{
            ScreenBinTree naux;
            node =(ScreenBinTree) savedInstanceState.getSerializable(TREESTATE);
            naux = new ScreenBinTree(surfaceView.getHolder(),node.getArvore());
            node = naux;
        }

        btnInsert= findViewById(R.id.btnInsert);
        btnRemove = findViewById(R.id.btnRemove);
        btnSearch = findViewById(R.id.btnSearch);
        btnMin = findViewById(R.id.buttonMin);
        btnMax = findViewById(R.id.buttonMax);
        btnOK = findViewById(R.id.buttonOK);

        txt=findViewById(R.id.entrada);
        clickButtons=stateUser.insert;

        ///configurando cor inicial dos botoes
        atualizaBotoes();

        //tratamento do botao de layout inserir
        btnInsert.setOnClickListener(view -> {

            if(clickButtons==stateUser.insert){
                clickButtons=stateUser.none;
                linearLayout.setVisibility(View.GONE);
                node.desenhar();

            }else{
                if(clickButtons==stateUser.none){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                if(btnMin.getVisibility()==View.VISIBLE){
                    btnMin.setVisibility(View.GONE);
                    btnMax.setVisibility(View.GONE);
                }
                clickButtons= stateUser.insert;
            }

            atualizaBotoes();
        });
        //tratamento do botao de layoutremover
        btnRemove.setOnClickListener(view -> {

            if(clickButtons==stateUser.remove){
                clickButtons=stateUser.none;
                linearLayout.setVisibility(View.GONE);
                node.desenhar();

            }else{
                if(clickButtons==stateUser.none){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                if(btnMin.getVisibility()==View.VISIBLE){
                    btnMin.setVisibility(View.GONE);
                    btnMax.setVisibility(View.GONE);
                }
                clickButtons= stateUser.remove;
            }
            atualizaBotoes();
        });
        //tratamento do botao de layout de pesquisa
        btnSearch.setOnClickListener(view -> {

            if(clickButtons==stateUser.search){
                clickButtons=stateUser.none;
                btnSearch.setBackgroundColor(Color.CYAN);
                linearLayout.setVisibility(View.GONE);
                node.desenhar();

            }else{
                if(clickButtons==stateUser.none){
                    btnSearch.setBackgroundColor(Color.BLUE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
                if(btnMin.getVisibility()==View.GONE){
                    btnMin.setVisibility(View.VISIBLE);
                    btnMax.setVisibility(View.VISIBLE);
                }
                clickButtons= stateUser.search;
            }
            atualizaBotoes();
        });
        //parte com o tratamento das interações com a arvore referente a manipulação dela
        btnOK.setOnClickListener(view -> {
            switch (clickButtons){
                case insert:
                    if(!txt.getText().toString().equals("")){
                        node.insert(Integer.parseInt(txt.getText().toString()));
                    }
                    break;
                case remove:
                    if(!txt.getText().toString().equals("")){
                        node.remove(Integer.parseInt(txt.getText().toString()));
                    }
                    break;
                case search:
                    if(!txt.getText().toString().equals("")){
                        node.search(Integer.parseInt(txt.getText().toString()));
                    }
                    break;
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x,y;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                node.posx=event.getX();
                node.posy=event.getY();
                break;
           // case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
                x=event.getX()-node.posx;
                y=event.getY()-node.posy;
                if(y!=0){
                    node.posy= event.getY();
                    node.updatePosyArvore((int)y);
                }

                if(x!=0){
                    node.posx=event.getX();
                    node.updatePosxArvore((int)x);
                }
                node.desenhar();
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(TREESTATE, node);

        super.onSaveInstanceState(outState);

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("novamente");
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void atualizaBotoes(){
        btnInsert.setBackgroundColor(Color.BLUE);
        btnSearch.setBackgroundColor(Color.BLUE);
        btnRemove.setBackgroundColor(Color.BLUE);
        switch (clickButtons){
            case search:
                btnSearch.setBackgroundColor(Color.GRAY);
                break;
            case remove:
                btnRemove.setBackgroundColor(Color.GRAY);
                break;
            case insert:
                btnInsert.setBackgroundColor(Color.GRAY);
                break;
        }
    }
}