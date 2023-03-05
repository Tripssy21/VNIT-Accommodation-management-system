import VAMS.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static ArrayList<Accommodation> populate() {
        Accommodation h1 = new Accommodation("Type-I", 'A', 1, 20);
        Accommodation h2 = new Accommodation("Type-I", 'A', 2, 20);
        Accommodation h3 = new Accommodation("Type-I", 'B', 1, 20);
        Accommodation h4 = new Accommodation("Type-I", 'A', 3, 20);
        Accommodation h5 = new Accommodation("Type-I", 'B', 3, 20);
        Accommodation h6 = new Accommodation("Type-I", 'C', 1, 20);
        Accommodation w1 = new Accommodation("Type-II", 'A', 1, 20);
        Accommodation h7 = new Accommodation("Type-I", 'A', 2, 20);
        Accommodation p1 = new Accommodation('C', 1);
        Accommodation p2 = new Accommodation('A', 1);
        Accommodation p3 = new Accommodation('B', 1);
        Accommodation p4 = new Accommodation('C', 2);
        Accommodation p5 = new Accommodation('A', 2);
        ArrayList<Accommodation> a = new ArrayList<Accommodation>();
        a.add(h1);
        a.add(h2);
        a.add(h3);
        a.add(h4);
        a.add(h5);
        a.add(h6);
        a.add(w1);
        a.add(h7);
        a.add(p1);
        a.add(p2);
        a.add(p3);
        a.add(p4);
        a.add(p5);
        System.out.println();
        System.out.println();
        System.out.println("Hostels Available: ");
        System.out.println("HB"+"-"+h1.getBlock()+"-"+h1.getAccommodationNo()+"\tcapacity: "+ h1.getCapacity());
        System.out.println("HB"+"-"+h2.getBlock()+"-"+h2.getAccommodationNo()+"\tcapacity: "+ h2.getCapacity());
        System.out.println("HB"+"-"+h3.getBlock()+"-"+h3.getAccommodationNo()+"\tcapacity: "+ h3.getCapacity());
        System.out.println("HB"+"-"+h4.getBlock()+"-"+h4.getAccommodationNo()+"\tcapacity: "+ h4.getCapacity());
        System.out.println("HB"+"-"+h5.getBlock()+"-"+h5.getAccommodationNo()+"\tcapacity: "+ h5.getCapacity());
        System.out.println("HB"+"-"+h6.getBlock()+"-"+h6.getAccommodationNo()+"\tcapacity: "+ h6.getCapacity());
        System.out.println("HB"+"-"+h7.getBlock()+"-"+h7.getAccommodationNo()+"\tcapacity: "+ h7.getCapacity());
        System.out.println();
        System.out.println();

        System.out.println("Workers Accommodation Available: ");
        System.out.println("WB"+"-"+w1.getBlock()+"-"+w1.getAccommodationNo()+"\tcapacity: "+ w1.getCapacity());
        System.out.println();
        System.out.println();

        System.out.println("Faculty Accommodation Available: ");
        System.out.println("PB"+"-"+p1.getBlock()+"-"+p1.getAccommodationNo());
        System.out.println("PB"+"-"+p2.getBlock()+"-"+p2.getAccommodationNo());
        System.out.println("PB"+"-"+p3.getBlock()+"-"+p3.getAccommodationNo());
        System.out.println("PB"+"-"+p4.getBlock()+"-"+p4.getAccommodationNo());
        System.out.println("PB"+"-"+p5.getBlock()+"-"+p5.getAccommodationNo());
        System.out.println();
        System.out.println();
        return a;
    }
    public static String accommodationType(int choice){
        if(choice == 1)
            return "Type-I";
        else if(choice == 2)
            return "Type-II";
        else
            return "Type-III";
    }
    public static String blockType(String aType){
        if(Objects.equals(aType, "Type-I"))
            return "HB";
        else if(Objects.equals(aType, "Type-II"))
            return "WB";
        else
            return "PB";
    }
    public static Person inputPerson(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the first name: ");
        String fName = sc.next();
        System.out.print("Enter the last name: ");
        String lName = sc.next();
        System.out.println("Choose the profession: \n1. Student\n2. Employee\n3. Faculty");
        String aType = accommodationType(sc.nextInt());
        System.out.print("Enter valid gov. id: ");
        String idnum = sc.next();
        return new Person(fName, lName, aType, idnum);
    }
    public static String addressAllocation(Person p, ArrayList<Accommodation> accommodations){
        System.out.println("Press 1: For preferential allocation");
        System.out.println("Press 2: For random allocation");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        Allocation a;
        if(choice == 1){
            if(p.getAccommodationType() != "Type-III"){
                System.out.print("Enter preferred block: ");
                char pBlock = sc.next().charAt(0);
                System.out.print("Enter preferred allocation no: ");
                int aNo = sc.nextInt();
                System.out.print("Enter preferred room no(choose 1 if no preference): ");
                int rNo = sc.nextInt();
                rNo = rNo == 0 ? 1 : rNo;
                a = new Allocation(p, pBlock, aNo, rNo);
            }
            else{
                System.out.print("Enter preferred block: ");
                char pBlock = sc.next().charAt(0);
                System.out.print("Enter preferred allocation no: ");
                int aNo = sc.nextInt();
                a = new Allocation(p, pBlock, aNo);
            }
        }
        else
            a = new Allocation(p);
        return a.allocate(accommodations);
    }

    public static void main(String[] args) {
        ArrayList<Accommodation> accommodations = new ArrayList<>(populate());
        Management m = new Management();
        for (int i = 0; i < 5; i++){
            Person p = new Person(inputPerson());
            String address = addressAllocation(p, accommodations);
            if(address != null){
                p.setAddress(address);
                m.insertRecord(p);
            }
        }
        m.printRecords();
        m.deleteRecord(4, accommodations);
        Person p = new Person(inputPerson());
        System.out.println(m.getHead());
        String address = addressAllocation(p, accommodations);
        if(address != null){
            p.setAddress(address);
            m.insertRecord(p);
        }
        System.out.println(m.getHead().getFirstName());
        m.printRecords();
    }
}