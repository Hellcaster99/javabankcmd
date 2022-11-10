import java.util.*;
import java.io.*;

class Account{
    int AccNum=0;
    String name;
    double money;
    private char[] password;

    int tranctr=0;

    List history = new ArrayList<String>();

    /*Account(){
        //do nothing
    }*/
    Account(String n, char[] p){
        this.name = n;
        this.setPass(p);
        this.AccNum++;
    }

    public void setPass(char[] p){
        this.password = p;
    }
    public char[] getPass(){
        return password;
    }
    public void ShowDetails(){
        System.out.println("Account holder : "+this.name);
        System.out.println("Balance : "+this.money);
    }
    public void deposit(double m){
        if(this.money+m>100000000){
            System.out.println("Transaction failed , maximum balance reached !");
            return;
        }
        this.money+=m;
        this.history.add((++tranctr)+". You deposited Rs "+m);
    }

    public void withdraw(double m){
        if(this.money < m) {
            System.out.println("\nTransaction failed , Insufficient balance");
        }
        else if(this.money-m<5000){
            System.out.println("\nTransaction failed , minimum balance 5000 required !");
        }
        else{
            this.money-=m;
            System.out.println("Withdrawal Successful !");
            this.history.add((++tranctr)+". You withdrew Rs "+m);
        }
    }

    public void sendmoneyto(Account obj2, double amt) {
        if (this.money < amt) {
            System.out.println("\nTransaction failed , Insufficient balance");
        } else if (this.money - amt < 5000) {
            System.out.println("\nTransaction failed , minimum balance 5000 required !");
        } else {
            this.money -= amt;
            if (obj2.money + amt > 100000000) {
                System.out.println("Transaction failed , maximum balance reached !");
                return;
            }
            obj2.money += amt;
            System.out.println("Transfer Successful !");
            this.history.add((++tranctr)+". You sent Rs " + amt + " to " + obj2.name);
            obj2.history.add((++obj2.tranctr)+". You received Rs " + amt + " from " + this.name);
        }
    }
}

public class Main {

    static void createAccount(String n, char[] p, List l){
        Account a = new Account(n,p);
        l.add(a);
    }

    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Console console = System.console();
            List al = new ArrayList<Account>();
            int a = 1;
            int accn = 0;
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("These are the following guidelines:\n1. Press 1 to Create an Account\n2. Press 2 to login to an existing account\n3. Press 3 to exit.");
            while (a == 1) {
                int b;
                int c = 1;
                System.out.print("\nPress a number : ");
                b = sc.nextInt();
                System.out.print("\n");
                String yourname;
                char[] pw;
                switch (b) {
                    case 1:
                        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
                        System.out.print("Enter your name : ");
                        yourname = sc.next();
                        pw = console.readPassword("Enter a password for your account : ");
                        createAccount(yourname, pw, al);
                        System.out.println("Account created successfully !");
                        System.out.println("Your account number is " + (++accn));
                        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
                        break;
                    case 2:
                        boolean r = false;
                        int tries = 0;
                        System.out.print("Enter your account number : ");
                        int acnum = sc.nextInt();
                        try {
                            Account obj1 = (Account) al.get(acnum - 1);
                            while (!r) {
                                char pwd[] = console.readPassword("Enter the password : ");
                                if (Arrays.equals(pwd, obj1.getPass())) {
                                    r = true;
                                    System.out.println("Logged in successfully !");
                                    System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
                                    while (c == 1) {
                                        int z;
                                        System.out.println("\nPress :\n  1 to check balance and details\n  2 for depositing money.\n  3 for withdrawing money\n  4 to send money\n  5 to see transaction history\n  6 to logout\n\n");
                                        z = sc.nextInt();
                                        switch (z) {
                                            case 1:
                                                obj1.ShowDetails();
                                                break;
                                            case 2:
                                                System.out.print("Enter amount to deposit : ");
                                                double m = sc.nextDouble();
                                                obj1.deposit(m);
                                                break;
                                            case 3:
                                                System.out.print("Enter amount to withdraw : ");
                                                double mo = sc.nextDouble();
                                                obj1.withdraw(mo);
                                                break;
                                            case 4:
                                                int num;
                                                double amt;
                                                System.out.print("Enter the account number you want to send money to : ");
                                                num = sc.nextInt();
                                                if (num == acnum) {
                                                    System.out.println("Cannot send money to yourself this way.");
                                                } else if (al.size() < num) {
                                                    System.out.println("Account does not exist.");
                                                } else {
                                                    System.out.print("Enter the amount you want to send : ");
                                                    amt = sc.nextDouble();
                                                    Account obj2 = (Account) al.get(num - 1);
                                                    obj1.sendmoneyto(obj2, amt);
                                                }
                                                break;
                                            case 5:
                                                if ((obj1.history.size() < 1)) {
                                                    System.out.println("No transactions done yet.");
                                                } else {
                                                    for (int i = 0; i < (obj1.history.size()); i++) {
                                                        System.out.println((obj1.history.get(i)));
                                                    }
                                                }
                                                break;
                                            case 6:
                                                System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
                                                c = 0;
                                                break;
                                        }
                                    }
                                } else {
                                    if (tries < 4) {
                                        System.out.println("Wrong password !");
                                        tries++;
                                        System.out.println("Tries remaining : " + (5 - tries));
                                        r = false;
                                    } else {
                                        System.out.println("Tries remaining : 0");
                                        System.out.println("Maximum tries reached ! ");
                                        r = true;
                                    }
                                }
                            }
                        }catch (Exception e) {
                            System.out.println("Bank Account with account number " + acnum + " does not exist, please create it first");
                        }
                        break;
                    case 3:
                        System.out.println("--------------------------------------------------------------------------------------------");
                        a = 0;
                        break;
                }
            }
    }
}