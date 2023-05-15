package com.example.demo.ui;

import com.example.demo.domain.Prietenie;
import com.example.demo.domain.Utilizator;
import com.example.demo.service.Service;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Console extends AbstractUI {

    private Scanner cin;
    public Console(Service<UUID> srv) {
        super(srv);
        cin = new Scanner(System.in);
    }

    /**
     * starts the console user interface
     * where all the functionalities are represented by an integer
     */
    @Override
    public void start() {
        cin = new Scanner(System.in);

        System.out.println("Meniu : ");
        System.out.println("0 - exit");
        System.out.println("1 - show all functionalities");
        System.out.println("2 - Add a user");
        System.out.println("3 - Remove a user by its email");
        System.out.println("4 - Add a friendship");
        System.out.println("5 - Remove a friendship");
        System.out.println("6 - Show all users");
        System.out.println("7 - Show all friendships");
        System.out.println("8 - Number of communities");
        System.out.println("9 - Show all communities");
        System.out.println("10 - add predefined values");
        System.out.println("11-get most sociable comunity");
        System.out.println("12-update utilizator");


        String email1, email2, email;

        while(true)
        {
            System.out.print("Give a command: ");
            int command = cin.nextInt();

            switch (command)
            {
                case 0:
                    cin.close();
                    return;
                case 1:
                    System.out.println("The functionalities are : ");
                    System.out.println("1 - show all functionalities");
                    System.out.println("2 - Add a user");
                    System.out.println("3 - Remove a user by its email");
                    System.out.println("4 - Add a friendship");
                    System.out.println("5 - Remove a friendship");
                    System.out.println("6 - Show all users");
                    System.out.println("7 - Show all friendships");
                    System.out.println("8 - Number of communities");
                    System.out.println("9 - Show all communities");
                    System.out.println("10 - add predefined values");
                    System.out.println("11-Get most sociable comunity");

                    break;

                case 2://2 - Add a user
                    Utilizator user = readUser();
                    srv.addUser(user);
                    break;

                case 3://3 - Remove a user by its email
                    email = readEmail();
                    srv.deleteUser(email);
                    break;

                case 4://4 - Add a friendship
                    email1 = readEmail();
                    email2 = readEmail();
                    srv.adaugaPrietenie(email1, email2);
                    break;

                case 5://5 - Remove a friendship
                    email1 = readEmail();
                    email2 = readEmail();
                    srv.stergePrietenie(email1, email2);
                    break;

                case 6://6 - Show all users
                    Iterable<Utilizator> it = srv.getAllUsers();
                    it.forEach(System.out::println);
                    break;

                case 7://7 - Show all friendships
                    Iterable<Prietenie> itf = srv.getAllFriendships();
                    itf.forEach(System.out::println);
                    break;

                case 8://8 - Number of communities
                    System.out.println("The number of communities is: " + srv.numberOfCommunities());
                    break;

                case 9://9 - Show all communities
                    System.out.print("Communities:");
                    List<List<Utilizator>> l = srv.getAllCommunities();
                    l.stream().forEach(System.out::println);
                    break;

                case 10://10 - add predefined values
                    srv.add_Predefined_Values();
                    break;

                case 11://11-most sociable comunity
                    System.out.print("Most sociable comunity:");
                    Iterable<Utilizator> list = srv.getMostSociableCommunity();
                    for(Utilizator u:list){System.out.println(u.toString());}
                    break;
               case 12://update utilizator
                    updateUtilizator();
                    break;
                default:
                    System.out.println("Wrong Command!");
            }
        }
    }

    /**
     * Reads a User from the console and returns it.
     *
     * @return  The User that was read.
     *
     */
    @Override
    public Utilizator readUser() {
        System.out.print("Username: ");
        String username = cin.next();
        System.out.print("email: ");
        String email = cin.next();

        Utilizator user = new Utilizator(username, email);
        return user;
    }


    /**
     * Reads an email from the console and returns it.
     *
     * @return  The email that was read.
     *
     * @throws
     *
     */

    @Override
    public String readEmail() {
        System.out.print("Give the email: ");
        String email = cin.next();

        return email;
    }
    public void updateUtilizator(){
        System.out.print("Email: ");
        String email=cin.next();
        System.out.println("Modificare email/username: ");
        String caz=cin.next();

        if(caz.equals("email")){
            System.out.println(caz);
            System.out.println("Email nou: ");
            String newemail=cin.next();
            srv.updateUtilizator(email,newemail,2);
        }
        if(caz.equals("username")){
            System.out.println(caz);
            System.out.println("Username nou: ");
            String newu=cin.next();
            srv.updateUtilizator(email,newu,1);
        }
    }



}