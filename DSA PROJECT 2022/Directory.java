package PhoneDirectory;

import java.io.*;
import java.util.*;

public class Directory {

  ArrayList<String> list = new ArrayList<String>();
  ArrayList<String> list1 = new ArrayList<String>();
  ArrayList<String> groups = new ArrayList<String>();
  int k = 0;
  Scanner sc = new Scanner(System.in);

  public void displayMenu() {
    System.out.println("-----------Phone Directory------------");
    System.out.println("1-Insert");
    System.out.println("2-Delete");
    System.out.println("3-Show Contacts");
    System.out.println("4-Update");
    System.out.println("5-SearchNumber");
    System.out.println("6-Create Groups");
    System.out.println("7-Display Groups");
    System.out.println("8-Exit");
  }
  
  /**
   * @throws Exception
   */
  public void DisplayGroups(){
    groups.removeAll(groups);
    try{  
    File myObj = new File("Groups.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        groups.add(myReader.nextLine());
      }
    }
      catch (Exception e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      for(int i=0;i<groups.size();i++){
      System.out.println("        Group Name: "+groups.get(i));
      System.out.println("MEMBERS:");
      try{  
        File myObj1 = new File(groups.get(i)+".txt");
          Scanner myReader = new Scanner(myObj1);
          while (myReader.hasNextLine()) {
            System.out.println(myReader.nextLine());
          }
        }
        catch (Exception e) {
          System.out.println("Group is Empty.");
        }

    }


  }

  public String getdetails() {
    System.out.print("Enter new firstname : ");
    String fname = sc.nextLine();
    System.out.print("Enter new lastname : ");
    String lname = sc.nextLine();
    return (fname + "" + lname);
  }

  public void Menu() {
    char choose = 'y';
      while(choose!='n'){
    displayMenu();
    readfile();
    System.out.print("Enter your choice : ");
    int choice = sc.nextInt();
    if (choice == 1) {
      System.out.println("-----------Inserting-----------"); 
      sc.nextLine();
      insertInput();
    } else if (choice == 2) {
      System.out.println("-----------Deletion------------");
      sc.nextLine();
      String name = getdetails();
      delete(name);
      } 
    else if (choice == 3){ 
      showContacts(); }
    else if (choice == 4) {
      System.out.println("-----------Updating------------");
      sc.nextLine();
      Update();
      
    } 
    else if (choice == 5) {
      System.out.println("-----------Search Numbers------------");
      sc.nextLine();
      search();
    }
    else if(choice == 6){
      System.out.println("-----------Create Groups------------");
      sc.nextLine();
      creategroups();
    }
    if(choice==7){
      System.out.println("--------------Groups--------------------");
      DisplayGroups();
    }
     if (choice == 8) {
      System.out.println("-----------Program End------------");
      break;
    }
    System.out.print("Do you want to Continue(y/n): ");
    choose = sc.next().charAt(0);
  }

}

  public void updateNum(String name) {
    int count = 0, count1 = 0;
    for (int i = 0; i < list.size(); i++) {
      String temp = list.get(i);
      count = 0;
      for (int j = 0; j < temp.length(); j++) {
        if (j < name.length()) {
          if (temp.charAt(j) == name.charAt(j)) {
            count++;
          }
          if (count == name.length()) break;
        }
      }
      if (count == name.length()) {
        count1 = i;
        System.out.println("Contact Found!!");
        break;
      }
    }
    if (count == name.length()) {
      System.out.print("Enter New Number : ");
      String number = sc.nextLine();
      name = name + "\t" + number;
      list.set(count1, name);
      System.out.println("Sucessfully Updated");
    }
    else
    System.out.println("Not Contact find so not Updated");
  }

  public void updateName(String name) {
    int count = 0, count1 = 0;
    String save = "";
    for (int i = 0; i < list.size(); i++) {
      String temp = list.get(i);
      count = 0;
      for (int j = 0; j < temp.length(); j++) {
        if (j < name.length()) {
          if (temp.charAt(j) == name.charAt(j)) {
            count++;
          }
        } else if (count == name.length() && temp.charAt(j) != '\t') {
          save += temp.charAt(j);
        }
      }
      if (count == name.length()) {
        count1 = i;
        System.out.println("Contact Found!!");
        break;
      }
    }
    if (count == name.length()) {
      System.out.print("Enter new firstname : ");
      String fname = sc.nextLine();
      System.out.print("Enter new lastname : ");
      String lname = sc.nextLine();
      name = fname + lname + "\t" + save;
      list.set(count1, name);
      System.out.println("Sucessfully Updated");
    }
    else{
      System.out.println("Contact Found so not Updated");
    }
  }

  public void Update() {
    System.out.println("1-Update in Name : ");
    System.out.println("2-Update in PhoneNumber : ");
    System.out.print("Enter the choice : ");
    int n = sc.nextInt();
    if (n == 1) {
      sc.nextLine();
      System.out.println("Enter previous firstname : ");
      String fname = sc.nextLine();
      System.out.println("Enter previous lastname : ");
      String lname = sc.nextLine();
      fname = fname + lname;
      updateName(fname);
    }
    if (n == 2) {
      sc.nextLine();
      System.out.print("Enter Name : ");
      String num = sc.nextLine();
      updateNum(num);
    }
    sortList();
    rewritetext();
  }

  public void showContacts() {
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
  }
  public void addmembers(String file){
   char opt='y';
   try{
    while(opt=='y'){
    
    FileWriter fos = new FileWriter(file+".txt",true);
   String name = getdetails();
   System.out.print("Enter the number : ");
   String num=sc.nextLine();
   name=name+"\t"+num;
   fos.write(name+"\n");
   fos.close();
   System.out.print("do you add new member(y/n)");
   opt = sc.next().charAt(0);
   sc.nextLine();  
    }
  }
    catch(Exception e){
      System.out.println("Error");
    }  
  }
  public void creategroupsfile(String file){
    try {
      File myObj = new File(file+".txt");
      if (myObj.createNewFile()){ 
        System.out.println("File created: " + myObj.getName());
        FileWriter fos = new FileWriter("Groups.txt",true);
        fos.write(file+"\n");
        fos.close();}      
        else 
        System.out.println("File already exists.");     
        addmembers(file);
    } 
    catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    } 
   }

  public void creategroups(){
   System.out.print("Enter the GroupName : ");
   String group = sc.nextLine(); 
   creategroupsfile(group);
  }

  public void insertInput() {
    
    System.out.print("Enter the first name : ");
    String fname = sc.nextLine();
    System.out.print("Enter the last name : ");
    String lname = sc.nextLine();
    System.out.print("Enter the Phone number : ");
    String num = sc.nextLine();

    insert(fname, lname, num);
   

  }

  public void insert(String fname, String lname, String num) {
    String name = fname + "" + lname + "\t" + num;
    name=name.toLowerCase();
    WriteFile(name);
    sortList();
    rewritetext();
    System.out.println("Inserting Sucessfully");
  }

  public void readfile() {
    try {
      list.removeAll(list);
      File myObj = new File("PhoneDirectory.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        list.add(myReader.nextLine());
      }
      list1 = list;
      myReader.close();
    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void rewritetext() {
    try {
      String save = "";
      String file = "PhoneDirectory.txt";
      FileWriter fos = new FileWriter(file);
      for (int i = 0; i < list.size(); i++) {
        save += list.get(i) + "\n";
      }
      for (int j = list.size() - 1; j >= 0; j--) {
        save = save.replaceAll(list1.get(j), list.get(j));
      }
      fos.append(save);
      fos.flush();
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void sortList() {
     // it is pre defined sorting algorithm for LinkedList
    list.sort(Comparator.naturalOrder());
  }

  public void WriteFile(String name) {
    try {
      list.add(name);
      FileWriter fw = new FileWriter("PhoneDirectory.txt", true);
      fw.write(name);
      fw.close();
      System.out.println("The content is successfully appended to the file.");
    } catch (Exception ioe) {
      System.out.print("\nSomething went wrong!");
    }
  }

  public void print() {
    System.out.println(list.get(3));
  }

  public void delete(String name) {
    int count = 0,count1=0;;
   
    for (int i = 0; i < list.size(); i++) {
      String temp = list.get(i);
      count = 0;
      for (int j = 0; j < temp.length(); j++) {
        if (j < name.length()) {
          if (temp.charAt(j) == name.charAt(j)) {
            count++;
          }
          if (count == name.length()) break;
        }
      }
      if (count == name.length()) {
       count1 = i;
        System.out.println("Contact Found and Deleted!!");
        break;
      }
    }
    if(count!=name.length()){
      System.out.println("Contact not Found and so not Deleted!!");
    }
    else{
    list.remove(count1);
    sortList();
    rewritetext();
    }
  }

  public void search() {
    String name = getdetails();
    int count = 0, count1 = 0;
    String save = "";
    for (int i = 0; i < list.size(); i++) {
      String temp = list.get(i);
      count = 0;
      for (int j = 0; j < temp.length(); j++) {
        if (j < name.length()) {
          if (temp.charAt(j) == name.charAt(j)) {
            count++;
          }
        } else if (count == name.length() && temp.charAt(j) != '\t') {
          save += temp.charAt(j);
        }
      }
      if (count == name.length()) {
        count1 = i;
        break;
      }
    }
    System.out.println("Contact Found: \n" + name + " " + save);
  }

  public static void main(String args[]) {
    Directory Obj = new Directory();
    Obj.Menu();
  }
}
