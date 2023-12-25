package com.example.lb02.Models;
public class User {
    private String login;
    private String pass;
    private int id;

    private String firstName;

    User(int id, String name, String login, String pass){
        this.id=id;
        this.firstName = name;
        this.login=login;
        this.pass=pass;
    }
    public User(String name,String login, String pass){
        this.firstName = name;
        this.login = login;
        this.pass = pass;
    }

    public int getID(){
        return this.id;
    }
    public void setID(int id){
        this.id = id;
    }

    public String getLogin(){
        return this.login;
    }
    public void setLogin(String login){
        this.login = login;
    }

    public String getPass(){
        return this.pass;
    }
    public void setPass(String pass){
        this.pass = pass;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String name){
        this.firstName = name;
    }
}
