package com.user;

import utils.db.JukeboxDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {

    private boolean userLoginStatus;
    private static String phone;
    private final JukeboxDB db;

    public static String getPhone() {
        return phone;
    }

    public User(){
        this.db = new JukeboxDB();
        userLoginStatus = false;
    }

    public boolean userRegistration(){
        System.out.print("******************************************************************** ");
        System.out.print(" SIGN UP PANEL! ");
        System.out.println("********************************************************************");
        Scanner sc = new Scanner(System.in);
        String name, phone, password;
        while(true){

            System.out.println("Enter Your Name (Please Enter a single word i.e No Spaces )");
            name = sc.next().trim();
            System.out.println("Enter Your Mobile Number (Please Enter a single word i.e No Spaces )");
            phone = sc.next().trim();
            System.out.println("Enter a minimum of 6 Digit Password (Please Enter a single word i.e No Spaces)");
            password = sc.next().trim();
            if(!(phone.matches("[0-9]+")) || password.length() < 6 || phone.length() < 10 || phone.length() >13 ){
                System.out.println("Wrong Input Format! Please Try Again");
                continue;
            }
            break;
        }

        try{
            ResultSet rs = this.db.selectFromTable("select * from users where phone = "+phone);
            if(rs.next()){
                System.out.println("User Already Exists. Please Login to Continue");
                userLogin();
            }
            else{
                String query = "Insert into users(userName, phone, password) values('"+name+"','"+phone+"','"+password+"')";
                int row = this.db.insertIntoTable(query);
                if(row > 0){
                    this.userLoginStatus = true;
                    User.phone = phone;
                    System.out.println("USER REGISTERED SUCCESSFULLY");

                }else{
                    System.out.println("USER REGISTRATION FAILED");
                }
            }
        }catch(RuntimeException | SQLException e){
            System.out.println(e.getMessage());
        }


        return userLoginStatus;
    }


    public boolean userLogin() {
        System.out.print("******************************************************************** ");
        System.out.print(" SIGN IN PANEL! ");
        System.out.println("********************************************************************");
        Scanner sc = new Scanner(System.in);
        String phone, password;
        while(true){

            String phonePattern = "[0-9]+";
            System.out.println("Enter Registered Mobile Number (Please Enter a single word i.e No Spaces )");
            phone = sc.next().trim();
            System.out.println("Enter Password (Please Enter a single word i.e No Spaces)");
            password = sc.next().trim();
            if(phone.length() < 10 || !(phone.matches(phonePattern)) || password.length() < 6){
                System.out.println("Wrong Input Format! Please Try Again");
                continue;
            }
            break;
        }

        try{
            String query = "Select password from users where phone = "+phone;
            ResultSet rs = this.db.selectFromTable(query);
            if(rs.next()){
                String originalPassword = rs.getString(1);
                if(originalPassword.equals(password.trim())){
                    System.out.println("LOGIN SUCCESSFUL");
                    User.phone = phone;
                    userLoginStatus = true;
                }
                else{
                    System.out.println("WRONG CREDENTIALS");
                    System.out.println("********************************************************************");
                }
            }else{
                System.out.println("No User Exists with the phone number " + phone + " Please Signup First!");
                userRegistration();
            }
        }catch (RuntimeException | SQLException e){
            System.out.println(e.getMessage());
        }
        return userLoginStatus;
    }

}
