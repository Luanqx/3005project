package com.company;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.InputStream;

public class Main {


        public static void main(String[] args) { // in our main function we can be (call) either user or owner
                try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Luan1234")) {
                        while(true) {
                            System.out.println("(0)I am an user");
                            System.out.println("(1)I am the owner");
                            Scanner whoami = new Scanner(System.in);  // Create a Scanner object
                            String role = whoami.nextLine();  // Read user input

                            if (Integer.parseInt(role) == 0) {
                                iamuser();


                            } else {
                                iamowner();
                            }
                        }
                }/*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
                        System.out.println("Connection failure.");
                        e.printStackTrace();
                }
        }
        public static void usermenu(boolean loggedin,String logged_in_name)//this is the menu for a user
        {

                if(logged_in_name==null) {
                        System.out.println("-----------------------------------------");
                }
                else if(loggedin) {
                        System.out.println("-----------Logged in as "+logged_in_name+"-------------");
                }
                System.out.println("(0)Exit");
                System.out.println("(1)Log in");
                System.out.println("(2)View available books ");
                System.out.println("(3)Track delivery");
                System.out.println("(5)Store information");
                System.out.println("-----------------------------------------");
                System.out.println("Please input your options [0 For Exit]: ");
        }
        public static void Search(boolean loggedin,ArrayList<Order> orders,Integer login_id,String logged_in_name) {// ths function allows users to view available books, they can search on ISBN, book title or author title, they can put the selected books into the cart
                ArrayList<String> cart= new ArrayList<String>();                                                    // then check out, after inputting addresses, the book is marked as sold in database, and this is used for generating reports and transfer money to publishers
                try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Luan1234")) {
                        while (true) {
                                if(logged_in_name==null) {
                                        System.out.println("-----------------------------------------");
                                }
                                else if(loggedin) {
                                        System.out.println("-----------Logged in as "+logged_in_name+"-------------");
                                }
                                System.out.println("(0)Quit searching");
                                System.out.println("(1)Search by ISBN");
                                System.out.println("(2)Search by Book_title ");
                                System.out.println("(3)Search by Author_name");
                                System.out.println("(4)Check Out");
                                System.out.println("-----------------------------------------");
                                System.out.println("[Waiting for checkout]: "+ cart);
                                System.out.println("-----------------------------------------");
                                System.out.println("Please input your options [0 For Exit]: ");
                                Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                                String query = myObj.nextLine();  // Read user input
                                int Intquery = Integer.parseInt(query);
                                switch (Intquery) {
                                        case 0:
                                                System.out.println("Quit searching");

                                                return;

                                        case 1:
                                                ArrayList<String> result= new ArrayList<String>(); // select a book by ISBN

                                                Scanner myObj2 = new Scanner(System.in);  // Create a Scanner object
                                                System.out.println("Enter a ISBN:");
                                                String ISBN = myObj2.nextLine();  // Read user input
                                                PreparedStatement ps = connection.prepareStatement("select *\n" +
                                                        "from book\n" +
                                                        "where book.ISBN=? and sold=0");
                                                ps.setString(1, ISBN);
                                                ResultSet resultSet = ps.executeQuery();
                                                //System.out.printf("%-30.30s  %-30.30s%n","course_id","prereq_id");

                                                        while (resultSet.next()) {
                                                                System.out.printf("%-20.20s%-30.90s  %-20.90s  %-30.30s%n", "ISBN: " + resultSet.getString("ISBN"), "book_name: " + resultSet.getString("book_title"), "  author_name: " + resultSet.getString("author_name")
                                                                        , "  price: " + resultSet.getString("price"));
                                                                result.add(resultSet.getString("ISBN"));
                                                        }
                                                if(result.size()!=0) {
                                                        Scanner myObj5 = new Scanner(System.in);  // Create a Scanner object
                                                        //System.out.println("Adding a book to cart by typing in its ISBN:");
                                                        System.out.println("(0)Add in to the cart");
                                                        System.out.println("(1)Cancel");
                                                        String addin = myObj5.nextLine();  // Read user input
                                                        System.out.println(addin.equals('0'));
                                                        System.out.println(addin);
                                                        if (0 == Integer.parseInt(addin)) {
                                                                cart.add(ISBN);
                                                                break;


                                                        } else {


                                                                break;
                                                        }
                                                }
                                                else{
                                                        System.out.println("Nothing found");
                                                        break;
                                                }


                                        case 2:
                                                ArrayList<String> result2= new ArrayList<String>(); // select books by title
                                                Scanner myObj3 = new Scanner(System.in);  // Create a Scanner object
                                                System.out.println("Enter a book_title:");
                                                String name = myObj3.nextLine();  // Read user input

                                                PreparedStatement ps2 = connection.prepareStatement("select *\n" +
                                                        "from book\n" +
                                                        "where book.book_title=? and sold=0");
                                                ps2.setString(1, name);
                                                ResultSet resultSet2 = ps2.executeQuery();
                                                //System.out.println("Enter a book_title:");
                                                //System.out.printf("%-30.30s  %-30.30s%n","course_id","prereq_id");

                                                        while (resultSet2.next()) {
                                                                System.out.printf("%-20.20s%-30.90s  %-20.90s  %-30.30s%n", "ISBN: " + resultSet2.getString("ISBN"), "book_name: " + resultSet2.getString("book_title"), "  author_name: " + resultSet2.getString("author_name")
                                                                        , "  price: " + resultSet2.getString("price"));
                                                                result2.add(resultSet2.getString("ISBN"));
                                                        }
                                                if(result2.size()!=0) {
                                                        Scanner myObj6 = new Scanner(System.in);  // Create a Scanner object
                                                        System.out.println("Adding ISBNs to cart:");
                                                        System.out.println("e.g. 00001 00002 ...");
                                                        String addin2 = myObj6.nextLine();  // Read user input
                                                        String[] parts = addin2.split(" ");
                                                        //System.out.println(Arrays.toString(parts));
                                                        for (int i = 0; i < parts.length; i++) {
                                                                if (!(parts[i].equals(" ")) && !(parts[i].contains(","))&&result2.contains(parts[i])) {
                                                                        cart.add(parts[i]);
                                                                }
                                                                else{
                                                                        System.out.println("Invalid input");
                                                                }
                                                        }
                                                        break;
                                                }
                                                else{
                                                        System.out.println("Nothing found");
                                                        break;
                                                }


                                        case 3:
                                                ArrayList<String> result3= new ArrayList<String>();// select books by author name
                                                Scanner myObj4 = new Scanner(System.in);  // Create a Scanner object
                                                System.out.println("Enter a author:");
                                                String author = myObj4.nextLine();  // Read user input
                                                PreparedStatement ps3 = connection.prepareStatement("select *\n" +
                                                        "from book\n" +
                                                        "where book.author_name=? and sold=0");
                                                ps3.setString(1, author);
                                                ResultSet resultSet3 = ps3.executeQuery();
                                                //System.out.printf("%-30.30s  %-30.30s%n","course_id","prereq_id");

                                                while (resultSet3.next()) {
                                                        System.out.printf("%-20.20s%-30.90s  %-20.90s  %-30.30s%n", "ISBN: " + resultSet3.getString("ISBN"), "book_name: " + resultSet3.getString("book_title"), "  author_name: " + resultSet3.getString("author_name")
                                                                , "  price: " + resultSet3.getString("price"));
                                                        result3.add(resultSet3.getString("ISBN"));
                                                }
                                                if(result3.size()!=0) {
                                                        Scanner myObj7 = new Scanner(System.in);  // Create a Scanner object
                                                        System.out.println("Adding ISBNs to cart:");
                                                        System.out.println("e.g. 00001 00002 ...");
                                                        String addin3 = myObj7.nextLine();  // Read user input
                                                        String[] parts2 = addin3.split(" ");
                                                        System.out.println(Arrays.toString(parts2));
                                                        for (int i = 0; i < parts2.length; i++) {
                                                                if (!(parts2[i].contains(" ")) && !(parts2[i].contains(","))&&result3.contains(parts2[i])) {
                                                                        cart.add(parts2[i]);
                                                                }
                                                                else{
                                                                        System.out.println("Invalid input");
                                                                }
                                                        }

                                                        break;
                                                }
                                                else{
                                                        System.out.println("Nothing found");
                                                        break;
                                                }

                                        case 4: // check out and , marked selected book as sold, create an order store it in db

                                                if(!loggedin){

                                                        System.out.println("You have to log in before check out");
                                                        break;
                                                }
                                                else if(cart.size()==0){
                                                        System.out.println("You have to select al least one book for check out");
                                                        break;
                                                }
                                                else{
                                                        for (int i = 0; i < cart.size() ; i++) {
                                                                PreparedStatement ps4 = connection.prepareStatement("SELECT count(*)\n" +
                                                                        "FROM book\n" +
                                                                        "WHERE ISBN = ? and sold=0\n");
                                                                ps4.setString(1, cart.get(i));
                                                                ResultSet resultSet4 = ps4.executeQuery();

                                                                while (resultSet4.next()) {
                                                                        if (resultSet4.getInt("count")!=0) {
                                                                                PreparedStatement sold = connection.prepareStatement("select *\n" +
                                                                                        "from book\n" +
                                                                                        "where ISBN=? and sold=0");
                                                                                sold.setString(1, cart.get(i));
                                                                                ResultSet resultsold = sold.executeQuery();
                                                                                //System.out.printf("%-30.30s  %-30.30s%n","course_id","prereq_id");

                                                                                while (resultsold.next()) {

                                                                                        PreparedStatement soldbook = connection.prepareStatement("update book\n" +
                                                                                                "set sold=1\n" +
                                                                                                "where ISBN=?\n" +
                                                                                                "\n");

                                                                                        soldbook.setString(1, resultsold.getString("ISBN"));

                                                                                        soldbook.executeUpdate();


                                                                                        //System.out.println(resultSet4.getString("count"));
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                        Scanner myObj8 = new Scanner(System.in);  // Create a Scanner object
                                                        System.out.println("Please input your billing address");
                                                        String billing = myObj8.nextLine();  // Read user input
                                                        Scanner myObj9 = new Scanner(System.in);  // Create a Scanner object
                                                        System.out.println("Please input your billing address");
                                                        String shipping = myObj9.nextLine();  // Read user input

                                                        Statement get_max_order_num=connection.createStatement();
                                                        ResultSet max = get_max_order_num.executeQuery("select max(orders.orders_number)\n" +
                                                                "from orders");
                                                        Integer maxnum=0;
                                                        //Integer counter=0;
                                                        while(max.next()){
                                                                //counter++;
                                                                maxnum= max.getInt("max");
                                                        }
                                                        System.out.println("Please remember your order number to track your packages");
                                                        System.out.println("Order number: "+(maxnum+1));

                                                        //System.out.println(Arrays.toString(parts2));
                                                        ArrayList<String> order= new ArrayList<String>();
                                                        for (int i = 0; i < cart.size() ; i++) {
                                                                order.add(cart.get(i));


                                                        }

                                                        Order aorder = new Order(shipping, billing,order,login_id,maxnum+1);
                                                        orders.add(aorder);
                                                        aorder.print();
                                                        PreparedStatement make_order = connection.prepareStatement("insert into orders values(?,?,?,?,'In ware house');");
                                                        make_order.setInt(1,maxnum+1);
                                                        make_order.setString(2,billing);
                                                        make_order.setString(3,shipping);
                                                        make_order.setInt(4,login_id);
                                                        make_order.executeUpdate();
                                                        cart.clear();


                                                }
                                                break;





                                }
                        }
                } catch (SQLException throwables) {
                        throwables.printStackTrace();
                }
        }
        public static void ownermenu(){
                System.out.println("-----------------------------------------");
                System.out.println("(0)Exit");
                System.out.println("(1)Add books");
                System.out.println("(2)Remove books");
                System.out.println("(3)View reports");
                System.out.println("(4)View sent emails ");
                System.out.println("(5)View transfer ");
                System.out.println("-----------------------------------------");
                System.out.println("Please input your options [0 For Exit]: ");

        }
        public static void iamowner() {
                try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Luan1234")) {
                        while (true) {
                                ownermenu();
                                Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                                System.out.println("Enter choice:");
                                String input = myObj.nextLine();  // Read user input
                                //System.out.println("choice is: " + input);  // Output user input
                                //System.out.println(myObj);
                                int choice = Integer.parseInt(input);
                                switch (choice) {
                                        case 0:
                                                System.out.println("Exiting the system");

                                                return;
                                        case 1: // when the owner choose 1, which is adding a book, system will first check if the ISBN exist regardless if it is sold, if there isnt, the owner can continue
                                                Scanner isbn = new Scanner(System.in);
                                                System.out.println("Enter an ISBN:");
                                                String isbnstr = isbn.nextLine();
                                                PreparedStatement check_isbn = connection.prepareStatement("select count(*)\n" +
                                                        "from book\n" +
                                                        "where isbn=?");

                                                check_isbn.setString(1, isbnstr);


                                                ResultSet isbnexsit = check_isbn.executeQuery();

                                                while (isbnexsit.next()) {
                                                        if (isbnexsit.getInt("count")!=0) {
                                                                System.out.println("ISBN already existed");
                                                                break;
                                                        } else {

                                                                Scanner book_title = new Scanner(System.in);
                                                                System.out.println("Enter a book_title:");
                                                                String book_titlestr = book_title.nextLine();

                                                                Scanner author_name = new Scanner(System.in);
                                                                System.out.println("Enter an author_name:");
                                                                String author_namestr = author_name.nextLine();

                                                                Scanner gernre = new Scanner(System.in);
                                                                System.out.println("Enter an gernre:");
                                                                String gernrestr = gernre.nextLine();

                                                                Scanner price = new Scanner(System.in);
                                                                System.out.println("Enter an price:");
                                                                String pricestr = price.nextLine();

                                                                Scanner publisher_id = new Scanner(System.in);
                                                                System.out.println("Enter an publisher_id:");
                                                                String publisher_idstr = publisher_id.nextLine();

                                                                Scanner store_id = new Scanner(System.in);
                                                                System.out.println("Enter an store_id:");
                                                                String store_idstr = store_id.nextLine();

                                                                Scanner banking_account = new Scanner(System.in);
                                                                System.out.println("Enter an banking_account:");
                                                                String banking_accountstr = banking_account.nextLine();

                                                                Scanner percentage = new Scanner(System.in);
                                                                System.out.println("Enter an percentage:");
                                                                String percentagestr = percentage.nextLine();

                                                                PreparedStatement check_valid = connection.prepareStatement("select count(*)\n" +
                                                                        "from publisher\n" +
                                                                        "where publisher_id=? and banking_account=?");

                                                                check_valid.setInt(1, Integer.parseInt(publisher_idstr));
                                                                check_valid.setInt(2, Integer.parseInt(banking_accountstr));

                                                                ResultSet checkresult = check_valid.executeQuery();

                                                                while (checkresult.next()) {
                                                                        if (checkresult.getInt("count")!=0) {


                                                                                PreparedStatement addbook = connection.prepareStatement("insert into book values (?,?,?,?,?,?,?,?,?,0);");
                                                                                addbook.setString(1, isbnstr);
                                                                                addbook.setString(2, book_titlestr);
                                                                                addbook.setString(3, author_namestr);
                                                                                addbook.setString(4, gernrestr);
                                                                                addbook.setInt(5, Integer.parseInt(pricestr));
                                                                                addbook.setInt(6, Integer.parseInt(publisher_idstr));
                                                                                addbook.setInt(7, Integer.parseInt(store_idstr));
                                                                                addbook.setInt(8, Integer.parseInt(banking_accountstr));
                                                                                addbook.setInt(9, Integer.parseInt(percentagestr));

                                                                                addbook.executeUpdate();
                                                                                System.out.println("Added successfully");

                                                                        } else {
                                                                                System.out.println("publisher_id or banking_account does not exist");
                                                                                break;
                                                                        }
                                                                }

                                                        }
                                                }
                                                break;
                                        case 2:// the owner choose 2 to drop a tuple in book relation, I think the owner can only drop the books that currently in stock.
                                                Scanner isbn2 = new Scanner(System.in);
                                                System.out.println("Enter an ISBN:");
                                                String isbnstr2 = isbn2.nextLine();
                                                PreparedStatement check_isbn2 = connection.prepareStatement("select count(*)\n" +
                                                        "from book\n" +
                                                        "where isbn=? and sold=0");

                                                check_isbn2.setString(1, isbnstr2);


                                                ResultSet isbnexsit2 = check_isbn2.executeQuery();

                                                while (isbnexsit2.next()) {
                                                        if (isbnexsit2.getInt("count")==0) {
                                                                System.out.println("ISBN does not exist or has been sold");
                                                                break;
                                                        }
                                                        else{
                                                                PreparedStatement delete = connection.prepareStatement("DELETE FROM Book\n" +
                                                                        "\n" +
                                                                        "WHERE ISBN=? and sold=0");
                                                                delete.setString(1,isbnstr2 );
                                                                delete.executeUpdate();
                                                                System.out.println("Delete successfully");
                                                                break;

                                                        }
                                                }
                                                break;

                                        case 3: // retrieve all the book that sold, using those data to generate reports
                                                report report= new report();
                                                Statement stmt = connection.createStatement();
                                                ResultSet rset = stmt.executeQuery("select *\n" +
                                                        "from book\n" +
                                                        "where sold=1");
                                                while (rset.next()) {
                                                        String gernre=rset.getString("gernre");
                                                        String author=rset.getString("author_name");
                                                        Integer price=rset.getInt("price");
                                                        Integer percentage=rset.getInt("percentage");


                                                        report.add_gernre_report(gernre);
                                                        report.add_author_report(author);
                                                        report.add_expenditure_report(price,percentage);
                                                }
                                                report.print();
                                                break;

                                        case 4:// to see if there is book that has less than in stock, notice the publisher the amount we need, the threshold I set for supply is 5
                                                Statement sendemail=connection.createStatement();
                                                ResultSet needtosend= sendemail.executeQuery("select book_title,count(*),publisher_id\n" +
                                                        "from book\n" +
                                                        "where sold=0\n" +
                                                        "group by book_title,publisher_id");


                                                while(needtosend.next()){
                                                        Integer count=needtosend.getInt("count");
                                                        String book_title=needtosend.getString("book_title");
                                                        Integer publisher_id=needtosend.getInt("publisher_id");
                                                        if(count<5){
                                                                PreparedStatement sendexe = connection.prepareStatement("select publisher_id,email_address, publisher_name\n" +
                                                                        "from publisher\n" +
                                                                        "where publisher_id=?");

                                                                sendexe.setInt(1, publisher_id);


                                                                ResultSet sendresult = sendexe.executeQuery();
                                                                while (sendresult.next()) {
                                                                        System.out.println("Emails already sent to ("+publisher_id+") "+ sendresult.getString("publisher_name")+" "
                                                                                +sendresult.getString("email_address")+" "+book_title+" requires "+(5-count));

                                                                }

                                                        }



                                                }
                                                break;
                                        case 5: // transfer money to the publisher, the amount of money is determinded by the percentage variable in the book relation.
                                                Statement sendtransfer=connection.createStatement();
                                                ResultSet trans= sendtransfer.executeQuery("select book_title,percentage,publisher_id,price,count(*)\n" +
                                                        "from book\n" +
                                                        "where sold=1\n" +
                                                        "group by book_title,percentage,publisher_id,price\n");


                                                while(trans.next()){

                                                        Integer publisher_id=trans.getInt("publisher_id");

                                                                PreparedStatement transfer_exe = connection.prepareStatement("select publisher_id,email_address, publisher_name\n" +
                                                                        "from publisher\n" +
                                                                        "where publisher_id=?");

                                                                transfer_exe.setInt(1, publisher_id);


                                                                ResultSet sendresult = transfer_exe.executeQuery();
                                                                while (sendresult.next()) {
                                                                        Integer price=trans.getInt("price");
                                                                        Integer percentage=trans.getInt("percentage");

                                                                        System.out.printf("%-20.20s  %-30.90s  %-20.90s  %-30.30s%n", "Transfer to: " , "publisher_id: " + trans.getInt("publisher_id"), "  Email: " + sendresult.getString("email_address")
                                                                                , "  amount: " + (price*(percentage/100.0)*trans.getInt("count")));

                                                                }





                                                }
                                                break;




                                }

                        }
                }
                catch (SQLException e) {
                        System.out.println("Connection failure.");
                        e.printStackTrace();
                }
        }
        public static void iamuser() {
                try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Luan1234")) {
                        boolean loggedin = false;
                        Integer login_id = 0;
                        String logged_in_name=null;
                        ArrayList<Order> orders = new ArrayList<Order>();

                        while (true) {
                                usermenu(loggedin,logged_in_name);

                                Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                                System.out.println("Enter choice:");
                                String input = myObj.nextLine();  // Read user input
                                //System.out.println("choice is: " + input);  // Output user input
                                //System.out.println(myObj);
                                int choice = Integer.parseInt(input);
                                switch (choice) {
                                        case 0:
                                                System.out.println("Exiting the system");

                                                return;
                                        case 1: // logging in

                                                Scanner myObj2 = new Scanner(System.in);  // Create a Scanner object
                                                System.out.println("Enter Account:");
                                                String Account = myObj2.nextLine();  // Read user input

                                                Scanner myObj3 = new Scanner(System.in);  // Create a Scanner object
                                                System.out.println("Enter Password:");
                                                String Password = myObj3.nextLine();  // Read user input
                                                Integer check=0;
                                                PreparedStatement ps = connection.prepareStatement("select *\n" +
                                                        "from Users\n" +
                                                        "where Users.Password=? and Users.Account=?");
                                                ps.setString(1, Password);
                                                ps.setString(2, Account);
                                                ResultSet resultSet = ps.executeQuery();
                                                //System.out.printf("%-30.30s  %-30.30s%n","course_id","prereq_id");
                                                while (resultSet.next()) {
                                                        check++;
                                                        login_id = resultSet.getInt("user_id");
                                                        loggedin = true;
                                                        System.out.println("Hello, welcome "+resultSet.getString("user_name")+"!");
                                                        logged_in_name=resultSet.getString("user_name");
                                                }
                                                if(check==0){
                                                        System.out.println("Incorrect account or password");
                                                }

                                                break;
                                        case 2: // to see the book that in stock which means sold=0
                                                Statement stmt = connection.createStatement();
                                                ResultSet rset = stmt.executeQuery("select isbn,book_title,author_name,price\n" +
                                                        "from book\n" +
                                                        "where sold=0\n");
                                                while (rset.next()) {
                                                        System.out.printf("%-20.20s%-30.90s  %-20.90s  %-30.30s%n", "ISBN: " + rset.getString("ISBN"), "book_name: " + rset.getString("book_title"), "  author_name: " + rset.getString("author_name")
                                                                , "  price: " + rset.getString("price"));
                                                }

                                                Search(loggedin, orders, login_id,logged_in_name);

                                                break;
                                        case 3: // check the order, by order number
                                                Scanner myObj4 = new Scanner(System.in);  // Create a Scanner object
                                                System.out.println("Enter Order number:");
                                                String ordernumber = myObj4.nextLine();  // Read user input
                                                PreparedStatement ps2 = connection.prepareStatement("select *\n" +
                                                        "from orders\n" +
                                                        "where orders.orders_number=?");
                                                ps2.setInt(1, Integer.parseInt(ordernumber));
                                                ResultSet targetorder = ps2.executeQuery();

                                                while (targetorder.next()) {

                                                        System.out.println("Order number: " + targetorder.getInt("orders_number"));
                                                        System.out.println("Shipping address: " + targetorder.getString("shipping_address"));
                                                        System.out.println("Billing address: " + targetorder.getString("billing_address"));
                                                        System.out.println("User_id: " + targetorder.getInt("users_id"));
                                                        System.out.println("Status: " + targetorder.getString("status"));
                                                }

                                                break;

                                }
                        }
                }
                catch (SQLException e) {
                        System.out.println("Connection failure.");
                        e.printStackTrace();
                }
                }
}

