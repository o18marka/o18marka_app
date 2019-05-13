package com.example.projekt;

public class Minklass {

    private String objekt;
    private String info;
    private int info2;

    public Minklass(){
        objekt="NoObject";
        info="NoInfo";
        info2=-1;
    }

    public Minklass(String n, String l, int h){
        objekt=n;
        info=l;
        info2=h;
    }

    public String info(){
        String tmp=new String();
        tmp+=objekt+" : "+info+" och har en höjd på "+info2+"m.";
        return tmp;
    }

    public void setName(String n){
        objekt=n;
    }

    public String getName(){
        return objekt;
    }

    @Override
    public String toString() { //Översätter JSON datan till en string som appen kan hantera
        return objekt;
    }
}
