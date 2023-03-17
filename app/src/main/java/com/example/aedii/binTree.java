package com.example.aedii;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.SurfaceHolder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

public class binTree implements Runnable  {
    int tam;
    boolean running;
    private Node root;
    private ShapeDrawable circlue,bounds;
    private Paint paint;
    private int keyaux;
    private HashMap<colors,Integer> estates = new HashMap<colors,Integer>();

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    private enum colors {white,blue,yellow,red,black,green}
    private int widht;
    private int option;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int posx;
    private int posy;
    public void setWidht(int widht) {
        this.widht = widht;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void setRoot(Node n){
        root=n;
    }
    public Node getRoot(){
        return root;
    }
    public class Node{
        private int key;
        private colors state;
        private Node left,right;
        boolean isLeft,isRight;
        boolean inLeft,inRight;
        Node(int k){
            state=colors.white;
            isLeft=false;
            isRight=false;
            inLeft=false;
            inRight=false;
            key=k;
            left=null;
            right=null;
        }


    }

    public void setOption(int option) {
        this.option = option;
    }

    public void setKeyaux(int keyaux) {
        this.keyaux = keyaux;
    }


    @Override
    public void run() {
        if(!running) {
            running=true;
            switch (option) {
                case 1:
                    try {
                        searchKey();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    draw();
                    break;
                case 2:
                    try {
                        removeKey();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    draw();
                    break;
                case 3:
                    try {
                        insertKey();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                        draw();
                    break;
                case 0:
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    draw();
            }

        running=false;
        }
    }
    public void insertKey() throws InterruptedException {
        Node n=root;
        if(root==null){
            root= new Node(keyaux);
            root.state=colors.yellow;
            draw();
            Thread.sleep(200);
            root.state=colors.white;
            draw();

        }else{
            recInset(n);
        }
    }
    private boolean recInset(Node n) throws InterruptedException {
        int time=400;
        if(n==null){
            return true;
        }
        if(keyaux==n.key){
            n.state=colors.red;
            draw();
            Thread.sleep(time);
            n.state=colors.white;
            draw();
            return false;
        }
        n.state=colors.yellow;
        draw();
        Thread.sleep(time);
        if(keyaux<n.key){

            if(recInset( n.left)){
                n.left = new Node(keyaux);
                n.left.state = colors.green;
                draw();
                Thread.sleep(time);
                n.left.state=colors.white;

            }
            n.state=colors.white;
            return false;
        }else{
            if(recInset( n.right)){
                n.right = new Node(keyaux);
                n.right.state=colors.green;
                draw();
                Thread.sleep(time);
                n.right.state=colors.white;
            }
            n.state=colors.white;
            return false;
        }
    }
    public void removeKey() throws InterruptedException {
        Node n = root;
        if(recRemove(n)){
            n.state=colors.green;
            draw();
            Thread.sleep(400);
            if(root.left==null) {
                root = root.right;
            }else {
                if (root.right == null) {
                    root = root.left;
                }else{
                    if(root.right.left==null){
                        root.right.left=root.left;
                        root=root.right;
                    }else{
                        Node aux,auxDad;
                        aux = root.right;
                        do {
                            auxDad=aux;
                            aux=aux.left;
                        }while (aux.left!=null);
                        auxDad.left=aux.right;
                        aux.left = root.left;
                        aux.right = root.right;
                        root = aux;

                    }
                }

            }
        }
    }
    public boolean recRemove(Node n) throws InterruptedException {
        int time = 400;
        if(n==null){
            return false;
        }
        if(n.key==keyaux){
            return true;
        }
        n.state=colors.yellow;
        draw();
        Thread.sleep(time);
        if(n.key<keyaux){
            if(recRemove(n.right)){
              if(n.right.right==null){
                  n.right=n.right.left;
              }else{
                  if(n.right.left==null){
                      n.right=n.right.right;
                  }else{
                      if(n.right.right.left==null){
                          n.right.right.left=n.right.left;
                          n.right=n.right.right;
                      }else{
                          Node aux,auxDad;
                          aux=n.right.right;
                          do {
                              auxDad=aux;
                              aux=aux.left;
                          }while (aux.left!=null);
                          auxDad.left=aux.right;
                          aux.left=n.right.left;
                          aux.right=n.right.right;
                          n.right=aux;
                      }
                  }
              }
            }
            n.state=colors.white;
            return false;
        }else{
            if(recRemove(n.left)){
                if(n.left.left==null){
                    n.left=n.left.right;
                }else{
                    if(n.left.right==null){
                        n.left=n.left.left;
                    }else{
                        if(n.left.right.left==null){
                            n.left.right.left=n.left.left;
                            n.left=n.left.right;
                        }else{
                            Node aux,auxDad;
                            aux=n.left.right;
                            do {
                                auxDad=aux;
                                aux=aux.left;
                            }while (aux.left!=null);
                            auxDad.left=aux.right;
                            aux.left=n.left.left;
                            aux.right=n.left.right;
                            n.left=aux;
                        }
                    }
                }
            }
            n.state=colors.white;
            return false;
        }
    }

    public void searchKey() throws InterruptedException {
        //faz a busca caso exista a arvore na raiz
        if(root!=null) recSearch( root);


    }
    public void recSearch(Node n) throws InterruptedException {
        if(n==null){
            return ;
        }
        if(keyaux==n.key){
            n.state=colors.green;
            draw();
            Thread.sleep(400);
            n.state=colors.white;
            return ;
        }
        n.state=colors.yellow;
        draw();
        Thread.sleep(400);
        if(keyaux<n.key){
            recSearch( n.left);
        }else{
            recSearch(n.right);
        }
        n.state=colors.white;
    }
    public binTree(int widht,SurfaceHolder sh) {
        tam = 100;
        running=false;
        option=0;
        root=null;
        posx=widht/2-tam/2;
        posy=20;
        this.widht=widht;
        circlue = new ShapeDrawable(new OvalShape());
        estates.put(colors.white,0xffffffff);
        estates.put(colors.yellow,0xffFFEC13);
        estates.put(colors.black, 0xff000000);
        estates.put(colors.red,0xffff5027);
        estates.put(colors.green,0xff52F548);
        paint = new Paint();
        paint.setColor(0xff000000);
        paint.setTextSize(80);
        paint.setStyle(Paint.Style.FILL);
        surfaceHolder=sh;
    }
    public void generateTree(int nNodes){
        Node n=root;
        for (int p = 0;p<nNodes;p++){
            if(root==null){
                root= new Node(new Random().nextInt(100));
                n = root;
            }else{
                recInsetNoDraw(new Random().nextInt(100),n);
            }

        }
    }

    private boolean recInsetNoDraw(int k, Node n) {

        if(n==null){
            return true;
        }
        if(k==n.key){
            return false;
        }
        if(k<n.key){
            if(recInsetNoDraw(k, n.left)){
                n.left = new Node(k);
            }
            return false;
        }else{
            if(recInsetNoDraw(k, n.right)){
                n.right = new Node(k);
            }
            return false;
        }
    }

    public void draw(){
        Node n = root;
        canvas=surfaceHolder.lockCanvas();
        //set background
        canvas.drawRGB( 219,173 , 34);
        recDraw(n, posx, posy,widht/4);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
    private void recDraw(Node n,int posX,int posY,int dist){
        if(n == null){//condição de parada da recurssão
            return;
        }
        int y=200;

        paint.setColor(estates.get(colors.black));
        //desenhando linhas para os filhos
        if(n.left!=null) {
            //desenhando linha para o filho esquerdo

            paint.setStrokeWidth(15);//grossura da linha
            canvas.drawLine(posX + tam /2 , posY + tam,posX-dist+tam/2,posY+y ,paint );


        }if(n.right!=null) {
            //desenhando a linha para o filho direito
            paint.setStrokeWidth(15);//grossura da linha
            canvas.drawLine(posX + tam / 2, posY + tam, posX +dist+tam/2, posY + y, paint);

        }
        //desenha o nó da arvore
        circlue.setBounds(posX, posY, posX+tam, posY+tam);
        circlue.getPaint().setColor(estates.get(n.state));
        circlue.draw(canvas);
        canvas.drawText(""+n.key, posX,posY+tam-10 , paint);//desenha numero no nó da arvore

        //chamada de recurssão para os filhos
        recDraw(n.left,posX-dist,posY+y,dist/2);
        recDraw(n.right,posX+dist,posY+y,dist/2);
    }

}
