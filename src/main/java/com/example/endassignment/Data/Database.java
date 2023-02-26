package com.example.endassignment.Data;

import com.example.endassignment.Model.Availability;
import com.example.endassignment.Model.Item;
import com.example.endassignment.Model.Member;
import com.example.endassignment.Model.TypeOfUser;
import com.example.endassignment.Model.User;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {
    public Database() {
        initiateDatabase();
    }

    private void initiateDatabase() {
//Ignore Sonalint
       /*createMembers();
        createItems();
        createUsers();
       writeAllFiles();*/
        readItemsFromFile();
        readMemberFromFile();
        readUserFromFile();
    }

    private List<User> users = new ArrayList<User>();
    private final List<Member> members = new ArrayList<Member>();

    public List<User> getUsers() {
        return users;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Item> getListOfUnavailableItems(){
        items = getItems();
        List<Item> unavailableItems = new ArrayList<Item>();
        for (Item item : items) {
            if(item.availability == Availability.no){
                unavailableItems.add(item);
            }
        }
        return unavailableItems;
    }

    private List<Item> items = new ArrayList<Item>();

    public List<Item> getItems() {
        return items;
    }

    private void createUsers() {
        //Ignore Sonalint
        User user1 = new User("Alessandra", "Ribeiro", "ale", "123", LocalDate.of(2000, Month.JUNE, 9), TypeOfUser.Admin);
        User user2 = new User("Corey", "Waple", "corey", "123", LocalDate.of(1995, 06, 13), TypeOfUser.regularUser);
        users.add(user1);
        users.add(user2);
    }

    private void createMembers() {
        //Ignore Sonalint
        Member member1 = new Member(1, "Alessandra", "Ribeiro", "10/05/2020");
        Member member2 = new Member(2, "Corey", "Waple", "13/05/1995");
        members.add(member2);
        members.add(member1);
    }

    public void createItems() {
        //Ignore Sonalint
        Item item1 = new Item(1, Availability.yes, "Java for Dummies, 13th edition", "Vries, E. D");
        Item item2 = new Item(2, Availability.yes, "The Hunger Games, 2nd edition", "Suzanne Collins");
        Item item3 = new Item(3, Availability.yes, "Harry Potter and the Order of the Phoenix, 5th edition", "J.K. Rowling");
        Item item4 = new Item(4, Availability.yes, "Pride and Prejudice, 10th edition", "Jane Austen");

        Item item5 = new Item(5, Availability.yes, "To Kill a Mockingbird, 7th edition", "Harper Lee");
        Item item6 = new Item(6, Availability.yes, "The Book Thief, 4nd edition", "Markus Zusak ");
        Item item7 = new Item(7, Availability.yes, "Twilight, 5th edition", " Stephenie Meyer");
        Item item8 = new Item(8, Availability.yes, "Animal Farm, 10th edition", "George Orwell");
        items.addAll(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8));
    }

    private void streamItemsToFile() {
        try (
                FileOutputStream fos = new FileOutputStream(new File("Files/items.dat"));
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {

            for (Item item : items) {
                oos.writeObject(item);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void streamMembersToFile() {
        try (
                FileOutputStream fos = new FileOutputStream(new File("Files/members.dat"));
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {

            for (Member member : members) {
                oos.writeObject(member);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void streamUserToFile() {
        try (
                FileOutputStream fos = new FileOutputStream(new File("Files/users.dat"));
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {

            for (User user : users) {
                oos.writeObject(user);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void writeAllFiles() {
        streamUserToFile();
        streamMembersToFile();
        streamItemsToFile();
    }

    private void readItemsFromFile() {
        boolean endOfStream = false;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(new File("Files/items.dat")))) {
            while (!endOfStream) {
                try {
                    Item item = (Item) ois.readObject();
                    items.add(item);
                } catch (EOFException eofe) {
                    endOfStream = true; // break out of the loop
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readUserFromFile() {
        boolean endOfStream = false;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(new File("Files/users.dat")))) {
            while (!endOfStream) {
                try {
                    User user = (User) ois.readObject();
                    users.add(user);
                } catch (EOFException eofe) {
                    endOfStream = true; // break out of the loop
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readMemberFromFile() {
        boolean endOfStream = false;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(new File("Files/members.dat")))) {
            while (!endOfStream) {
                try {
                    Member member = (Member) ois.readObject();
                    members.add(member);
                } catch (EOFException eofe) {
                    endOfStream = true; // break out of the loop
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Item getItemByID(int id) {
        Item item1 = null;
        for (Item item : items) {
            if (item.getItemCode() == id) {
                item1 = item;
                break;
            }
        }
        return item1;
    }

    public Member getMemberByID(int id) {
        Member member1 = null;
        for (Member member : members) {
            if (member.getId() == id) {
                member1 = member;
                break;
            }
        }
        return member1;
    }
}

