package com.example.aedii;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class ScreenBinTree implements SurfaceHolder.Callback, Serializable {

    private binTree arvore,aux;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    float posx,posy;

    public binTree getArvore() {
        return arvore;
    }

    public ScreenBinTree(SurfaceHolder sh) {
        aux=null;
        surfaceHolder = sh;
        surfaceHolder.addCallback(this);

    }
    public ScreenBinTree(SurfaceHolder sh, binTree b) {
        arvore = b;
        surfaceHolder = sh;
        surfaceHolder.addCallback(this);

    }
    public binTree saveTree(){
        return arvore;
    }

    public void setTree(binTree tree){
        this.arvore.setRoot(tree.getRoot());
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {


        canvas= this.surfaceHolder.lockCanvas();
        if(arvore==null){
            arvore = new binTree(canvas.getWidth(),this.surfaceHolder);
            arvore.generateTree(9);
        }else{
            arvore.setSurfaceHolder(this.surfaceHolder);
            arvore.setWidht(canvas.getWidth());
        }
        /*
        arvore = new binTree(canvas.getWidth(),canvas.getHeight(),this.surfaceHolder);
        if(aux!=null){
            arvore.setRoot(aux.getRoot());
        }

         */
        this.surfaceHolder.unlockCanvasAndPost(canvas);
        arvore.draw();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
    public void updatePosxArvore(int valor){
        arvore.setPosx(arvore.getPosx()+valor);
       // arvore.draw();
    }
    public void updatePosyArvore(int valor){
        arvore.setPosy(arvore.getPosy()+valor);
        //arvore.draw();
    }
    public  void desenhar(){
        arvore.setOption(0);
        new Thread(arvore).start();
    }
    public void insert(int key){
        arvore.setKeyaux(key);
        arvore.setOption(3);
        new Thread(arvore).start();
    }
    public void remove(int key){
        arvore.setKeyaux(key);
        arvore.setOption(2);
        new Thread(arvore).start();
    }
    public void search(int key){
        arvore.setKeyaux(key);
        arvore.setOption(1);
        new Thread(arvore).start();
    }

}
