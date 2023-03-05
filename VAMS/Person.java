package VAMS;
import org.jetbrains.annotations.NotNull;

public class Person {
    private static int counter = 0;
    private int clgId;
    private String firstName;
    private String lastName;
    private String accommodationType;
    private String idnum;
    private String address;
    private int height;
    Person leftChild;
    Person rightChild;
    public Person() {
        this.clgId = 0;
        this.firstName = null;
        this.lastName = null;
        this.accommodationType = null;
        this.idnum = null;
        this.address = null;
        this.leftChild= null;
        this.rightChild= null;
    }
    public Person(@NotNull Person N) {
        this.clgId = N.clgId;
        this.firstName = N.firstName;
        this.lastName = N.lastName;
        this.accommodationType = N.accommodationType;
        this.idnum = N.idnum;
        this.address = N.address;
        this.leftChild= null;
        this.rightChild= null;
    }
    public Person(String firstName, String lastName, String accommodationType, String idnum){
        this.clgId = ++counter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accommodationType = accommodationType;
        this.idnum = idnum;
        this.address = null;
        this.leftChild= null;
        this.rightChild= null;
    }
    public Person(String firstName, String lastName, String accommodationType, String idnum, String address){
        this.clgId = ++counter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accommodationType = accommodationType;
        this.idnum = idnum;
        this.address = address;
        this.leftChild= null;
        this.rightChild= null;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAccommodationType() {
        return accommodationType;
    }
    public String getIdnum() {
        return idnum;
    }
    public String getAddress() {
        return address;
    }
    public int getClgId() {
        return clgId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }
    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setClgId(int clgId) {
        this.clgId = clgId;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}
