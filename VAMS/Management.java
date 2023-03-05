package VAMS;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static java.lang.Math.max;

public class Management {
    private Person head;
    public Management(){
        this.head = null;
    }
    public Person getHead() {
        return head;
    }
    private int height(Person node) {
        return node != null ? node.getHeight() : -1;
    }
    private void updateHeight(@NotNull Person node) {
        if(node.leftChild != null)
            updateHeight(node.leftChild);
        if(node.rightChild != null)
            updateHeight(node.rightChild);
        int leftChildHeight = height(node.leftChild);
        int rightChildHeight = height(node.rightChild);
        node.setHeight(max(leftChildHeight, rightChildHeight) + 1);
    }
    private int balanceFactor(@NotNull Person node) {
        return height(node.rightChild) - height(node.leftChild);
    }
    private @NotNull Person rotateRight(@NotNull Person node) {
        Person leftChild = node.leftChild;

        node.leftChild = leftChild.rightChild;
        leftChild.rightChild = node;

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;
    }
    private @NotNull Person rotateLeft(@NotNull Person node) {
        Person rightChild = node.rightChild;

        node.rightChild = rightChild.leftChild;
        rightChild.leftChild = node;

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;
    }
    private Person rebalance(Person node) {
        int bf = balanceFactor(node);

        if (bf < -1) {
            if (balanceFactor(node.leftChild) > 0)
                node.leftChild = rotateLeft(node.leftChild);
            node = rotateRight(node);
        }

        if (bf > 1) {
            if (balanceFactor(node.rightChild) < 0)
                node.rightChild = rotateRight(node.rightChild);
            node = rotateLeft(node);
        }

        return node;
    }
    private @NotNull Person insertNode(Person newNode, Person node) {
        if (node == null) {
            node = new Person(newNode);
        }
        else if (newNode.getClgId() < node.getClgId())
            node.leftChild = insertNode(newNode, node.leftChild);
        else if (newNode.getClgId() > node.getClgId())
            node.rightChild = insertNode(newNode, node.rightChild);
        else
            throw new IllegalArgumentException("BST already contains a node with key " + newNode.getClgId());

        return node;
    }
    public void insertRecord(Person newNode) {
        this.head = insertNode(newNode, this.head);
        updateHeight(this.head);
        this.head = rebalance(this.head);
    }
    private Person deleteNode(Person node, int clgId) {
        if (node != null){
            if (clgId < node.getClgId())
                node.leftChild = deleteNode(node.leftChild, clgId);
            else if (clgId > node.getClgId())
                node.rightChild = deleteNode(node.rightChild, clgId);
            else if (node.leftChild == null && node.rightChild == null)
                node = null;
            else if (node.leftChild == null)
                node = node.rightChild;
            else if (node.rightChild == null)
                node = node.leftChild;
            else
                deleteNodeWithTwoChildren(node);
        }
        return node;
    }
    private void deleteNodeWithTwoChildren(@NotNull Person node) {
        Person inOrderSuccessor = findMinimum(node.rightChild);

        Person left = node.leftChild;
        Person right = node.rightChild;
        node.setClgId(inOrderSuccessor.getClgId());
        node.setFirstName(inOrderSuccessor.getFirstName());
        node.setLastName(inOrderSuccessor.getLastName());
        node.setIdnum(inOrderSuccessor.getIdnum());
        node.setAddress(inOrderSuccessor.getAddress());
        node.setAccommodationType(inOrderSuccessor.getAccommodationType());
        node.leftChild = left;
        node.rightChild = right;

        node.rightChild = deleteNode(node.rightChild, inOrderSuccessor.getClgId());
    }
    private Person findMinimum(@NotNull Person node) {
        while (node.leftChild != null)
            node = node.leftChild;
        return node;
    }
    public void deleteRecord(int clgId, ArrayList<Accommodation> accommodations){
        Person p = this.searchByClgId(clgId);
        String[] str = p.getAddress().split("-");

        for (Accommodation a : accommodations){
            if(Objects.equals(a.getAccommodationType(), p.getAccommodationType()) &&
            a.getBlock() == str[1].charAt(0) && a.getAccommodationNo() == parseInt(str[2]))
                a.setAvailability(parseInt(str[3]), true);
        }
        this.head = deleteNode(this.head, clgId);
        updateHeight(this.head);
    }
    public void deleteDuplicate(String idnum, ArrayList<Accommodation> accommodations){
        ArrayList<Person> records = this.searchByIdnum(idnum);
        for(int i = 1; i < records.size(); i++)
            deleteRecord(records.get(i).getClgId(), accommodations);
    }
    private void printRecord(){
        System.out.println("College ID" + "\t\t" +
                "First Name" + "\t\t" +
                "Last Name" + "\t\t" +
                "Accommodation Type" + "\t\t" +
                "ID number" + "\t\t" +
                "Address");
    }
    private void printRecord(@NotNull Person node){
        System.out.println("\t" + node.getClgId() + "\t\t\t" +
                node.getFirstName() + "\t\t\t" +
                node.getLastName() + "\t\t\t" +
                node.getAccommodationType() + "\t\t\t\t\t" +
                node.getIdnum() + "\t\t\t" +
                node.getAddress());
    }
    private void printRecords(Person head){
        if(head == null)
            return;
        printRecords(head.leftChild);
        printRecord(head);
        printRecords(head.rightChild);
    }
    public void printRecords(){
        if (this.head == null){
            System.out.println("No records to print!!!");
            return;
        }
        printRecord();
        printRecords(this.head);
    }
    public void printRecords(ArrayList<Person> records){
        if (records == null){
            System.out.println("No records to print!!!");
            return;
        }
        printRecord();
        for (Person record: records)
            printRecord(record);
    }
    private ArrayList<Person> searchByName(Person head, String firstName){
        if(head == null)
            return null;
        ArrayList<Person> res = new ArrayList<>();
        if(head.getFirstName().equalsIgnoreCase(firstName))
            res.add(head);

        if(searchByName(head.leftChild, firstName) != null)
            res.addAll(searchByName(head.leftChild, firstName));
        if(searchByName(head.rightChild, firstName) != null)
            res.addAll(searchByName(head.rightChild, firstName));
        return res;
    }
    private ArrayList<Person> searchByName(Person head, String firstName, String lastName){
        if(head == null)
            return null;
        ArrayList<Person> res = new ArrayList<>();
        if(head.getFirstName().equalsIgnoreCase(firstName) && head.getLastName().equalsIgnoreCase(lastName))
            res.add(head);
        if(searchByName(head.leftChild, firstName, lastName) != null)
            res.addAll(searchByName(head.leftChild, firstName, lastName));
        if(searchByName(head.rightChild, firstName, lastName) != null)
            res.addAll(searchByName(head.rightChild, firstName, lastName));
        return res;
    }
    public ArrayList<Person> searchByName(String @NotNull ...name){
        ArrayList<Person> res = new ArrayList<>();
        if(this.head == null)
                return null;
        if(name.length == 1)
            res.addAll(searchByName(this.head, name[0]));
        else if(name.length == 2)
            res.addAll(searchByName(this.head, name[0], name[1]));
        else
            System.out.println("Enter valid name!!!");
        return res;
    }
    private ArrayList<Person> searchByIdnum(Person head, String idnum){
        if(head == null)
            return null;
        ArrayList<Person> res = new ArrayList<>();
        if(head.getIdnum().equalsIgnoreCase(idnum))
            res.add(head);
        if(searchByIdnum(head.leftChild, idnum) != null)
            res.addAll(searchByIdnum(head.leftChild, idnum));
        if(searchByIdnum(head.rightChild, idnum) != null)
            res.addAll(searchByIdnum(head.rightChild, idnum));
        return res;
    }
    public ArrayList<Person> searchByIdnum(String idnum){
        return searchByIdnum(this.head, idnum);
    }
    private Person searchByClgId(Person head, int clgId){
        if(head == null)
            return null;
        else{
            if(head.getClgId() == clgId)
                return head;
            Person right = searchByClgId(head.rightChild, clgId);
            Person left = searchByClgId(head.leftChild, clgId);
            return (right == null) ? left : right;
        }
    }
    public Person searchByClgId(int clgId){
        return searchByClgId(this.head, clgId);
    }
    public boolean updateRecord(@NotNull Person updatedNode){
        Person currNode = searchByClgId(this.head, updatedNode.getClgId());
        if (currNode == null)
            return false;
        currNode.setFirstName(updatedNode.getFirstName());
        currNode.setLastName(updatedNode.getLastName());
        currNode.setIdnum(updatedNode.getIdnum());
        currNode.setAccommodationType(updatedNode.getAccommodationType());
        currNode.setAddress(updatedNode.getAddress());
        return true;
    }
    public boolean updateAddress(int clgId, String Address){
        Person currNode = searchByClgId(clgId);
        if (currNode == null)
            return false;
        currNode.setAddress(Address);
        return true;
    }
    public boolean updateName(int clgId, String ...arr){
        Person currNode = searchByClgId(clgId);
        if (currNode == null)
            return false;
        if(arr.length == 2){
            currNode.setFirstName(arr[0]);
            currNode.setLastName(arr[1]);
            return true;
        }
        else if(arr.length == 1){
            currNode.setFirstName(arr[0]);
            return true;
        }
        else return false;
    }
    public boolean updateIdnum(int clgId, String idnum) {
        Person currNode = searchByClgId(clgId);
        if (currNode == null)
            return false;
        currNode.setIdnum(idnum);
        return true;
    }
    public boolean updateAccommodationType(int clgId, String accommodationType){
        Person currNode = searchByClgId(clgId);
        if (currNode == null)
            return false;
        currNode.setAccommodationType(accommodationType);
        return true;
    }


    /////////////////////////////////////////////

//    public static void main(String[] args) {
//        Person p1 = new Person("Aditya", "Gupta", "Type-I", "23950", "HB-A-3-67");
//        Person p2 = new Person("Tushar", "Khorwal", "Type-I", "23947", "HB-A-2-65");
//        Person p3 = new Person("Pramod", "Pawar", "Type-I", "23566", "HB-A-2-61");
//        Person p4 = new Person("Abhishek", "Raj", "Type-I", "21950", "HB-A-3-67");
//        Person p5 = new Person("Himanshu", "Agarwal", "Type-I", "23110", "HB-A-2-11");
//        Person p6 = new Person("Sandesh", "Chajjed", "Type-I", "23150", "HB-A-3-22");
//        Person p7 = new Person("Aadersh", "Daheria", "Type-I", "21212", "HB-A-2-33");
//        Person p8 = new Person("Chinmay", "Rathod", "Type-I", "32322", "HB-A-2-44");
//        Person p9 = new Person("Aditya", "Agarwal", "Type-I", "23212", "HB-A-3-1");
//        Person p10 = new Person("Sandesh", "Khorwal", "Type-I", "23217", "HB-A-2-6");
//        Person p11 = new Person("Himanshu", "Pawar", "Type-I", "24336", "HB-A-2-13");
//        Person p12 = new Person("Pramod", "Raj", "Type-I", "21930", "HB-A-3-24");
//        Person p13 = new Person("Chinmay", "Agarwal", "Type-I", "23220", "HB-A-2-35");
//        Person p14 = new Person("Daheria", "Chajjed", "Type-I", "22110", "HB-A-3-56");
//        Person p15 = new Person("Agarwal", "Daheria", "Type-I", "21112", "HB-A-2-98");
//        Person p16 = new Person("Chinmay", "Gupta", "Type-I", "35622", "HB-A-2-8");
//        Person p17 = new Person("Dhananjay", "Gupta", "Type-II", "12322", "WB-A-2-8");
//        Person p18 = new Person("Shafi", "Gupta", "Type-II", "23422", "WB-B-2-8");
//        Person p19 = new Person("Poonam", "Gupta", "Type-II", "34522", "WB-A-1-8");
//        Person p20 = new Person("Sneha", "Gupta", "Type-III", "89622", "PB-A-2-8");
//        Person p21 = new Person("Sneha", "Gupta", "Type-III", "89622", "PB-A-2-8");
//        RecordTree records = new RecordTree();
//        records.insertRecord(p1);
//        records.insertRecord(p2);
//        records.insertRecord(p3);
//        records.insertRecord(p4);
//        records.insertRecord(p5);
//        records.insertRecord(p6);
//        records.insertRecord(p7);
//        records.insertRecord(p8);
//        records.insertRecord(p9);
//        records.insertRecord(p10);
//        records.insertRecord(p11);
//        records.insertRecord(p12);
//        records.insertRecord(p13);
//        records.insertRecord(p14);
//        records.insertRecord(p15);
//        records.insertRecord(p16);
//        records.insertRecord(p17);
//        records.insertRecord(p18);
//        records.insertRecord(p19);
//        records.insertRecord(p20);
//        records.insertRecord(p21);
//
//        records.printRecords();
////        records.deleteDuplicate("89622");
////        records.printRecords(records.searchByName("SNEHA"));
////        records.printRecord(records.searchByClgId(18));
////        records.updateName(18, "Shafi", "Mohommad");
////        records.printRecord(records.searchByClgId(18));
////        records.printRecords();
////        records.deleteRecord(records.head.getClgId());
////        records.printRecords();
//    }
}