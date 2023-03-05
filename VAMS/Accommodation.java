package VAMS;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class Accommodation {
    private final String accommodationType;
    private char block;
    private int accommodationNo;
    private int capacity;
    private int rent;
    private boolean [] availability;
    public Accommodation() {
        this.accommodationType = null;
        this.block = '\0';
        this.accommodationNo = 0;
        this.rent = 0;
        this.capacity = 0;
    }
    public Accommodation(@NotNull String accommodationType, char block, int accommodationNo, int capacity){
        this.accommodationType = accommodationType;
        this.block = block;
        this.accommodationNo = accommodationNo;
        this.capacity = capacity;
        this.availability = new boolean[capacity];
        if(accommodationType.equalsIgnoreCase("Type-I"))
            this.rent = 45000;
        else if(accommodationType.equalsIgnoreCase("Type-II"))
            this.rent = 60000;
        else if(accommodationType.equalsIgnoreCase("Type-III"))
            this.rent = 75000;
        Arrays.fill(this.availability, true);
    }
    public Accommodation(char block, int accommodationNo){
        this.accommodationType = "Type-III";
        this.block = block;
        this.accommodationNo = accommodationNo;
        this.capacity = 1;
        this.availability = new boolean[capacity];
        this.rent = 75000;
        Arrays.fill(this.availability, true);
    }
    public String getAccommodationType() {
        return accommodationType;
    }
    public char getBlock() {
        return block;
    }
    public int getAccommodationNo() {
        return accommodationNo;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getRent() {
        return rent;
    }
    public void setBlock(char block) {
        this.block = block;
    }
    public void setAccommodationNo(int accommodationNo) {
        this.accommodationNo = accommodationNo;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setRent(int rent) {
        this.rent = rent;
    }
    public boolean getAvailability(int roomNo) {
        return availability[roomNo - 1];
    }
    public void setAvailability(int roomNo, boolean status) {
        this.availability[roomNo - 1] = status;
    }
}
