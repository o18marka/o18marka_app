package com.example.projekt;

public class Minklass {

    private String name;
    private String location;
    private int length;
    private String category;
    private int year;
    private String cost;
    private String fate;

    public Minklass(){
        name="NONE";
        location="NONE";
        length=-1;
        category="NONE";
        year=-1;
        cost="UNKNOWN";
        fate="NONE";
    }

    public Minklass(String n, String l, int h, String c, int y, String o, String f){
        name=n;
        location=l;
        length=h;
        category=c;
        year=y;
        cost=o;
        fate=f;
    }

    public String info(){
        String tmp=new String();
        tmp+="Name: "+name+"\n";
        tmp+="Fought for: "+location+"\n";
        tmp+="Type: "+category+"\n";
        tmp+="Built: "+year+"\n";
        tmp+="Cost to build: "+cost+"\n";
        tmp+="Length: "+length+"m\n\n";
        tmp+="Fate:\n"+fate+".\n";
        return tmp;
    }

    public void setName(String n){
        name=n;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() { //Översätter JSON datan till en string som appen kan hantera
        return name;
    }
}
