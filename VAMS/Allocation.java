package VAMS;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Allocation {
    private Person p;
    private String accommodationType;
    private char preferredBlock;
    private int preferredAccommodationNo;
    private int preferredRoomNo;
    public Allocation(){
        this.p = null;
        this.accommodationType = null;
        this.preferredBlock = '\0';
        this.preferredAccommodationNo = 0;
        this.preferredRoomNo = 1;
    }
    public Allocation(@NotNull Person p, char preferredBlock, int preferredAccommodationNo, int preferredRoomNo){
        this.p = p;
        this.accommodationType = p.getAccommodationType();
        this.preferredBlock = preferredBlock;
        this.preferredAccommodationNo = preferredAccommodationNo;
        this.preferredRoomNo = preferredRoomNo;
    }
    public Allocation(@NotNull Person p, char preferredBlock, int preferredAccommodationNo){
        this.p = p;
        this.accommodationType = p.getAccommodationType();
        this.preferredBlock = preferredBlock;
        this.preferredAccommodationNo = preferredAccommodationNo;
        this.preferredRoomNo = 1;
    }
    public Allocation(@NotNull Person p, char preferredBlock){
        this.p = p;
        this.accommodationType = p.getAccommodationType();
        this.preferredBlock = preferredBlock;
        this.preferredAccommodationNo = 0;
        this.preferredRoomNo = 1;
    }
    public Allocation(@NotNull Person p){
        this.p = p;
        this.accommodationType = p.getAccommodationType();
        this.preferredBlock = '\0';
        this.preferredAccommodationNo = 0;
        this.preferredRoomNo = 1;
    }
    public Person getP() {
        return p;
    }
    public void setP(Person p) {
        this.p = p;
    }
    public String getAccommodationType() {
        return accommodationType;
    }
    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }
    public char getPreferredBlock() {
        return preferredBlock;
    }
    public void setPreferredBlock(char preferredBlock) {
        this.preferredBlock = preferredBlock;
    }
    public int getPreferredAccommodationNo() {
        return preferredAccommodationNo;
    }
    public void setPreferredAccommodationNo(int preferredAccommodationNo) {
        this.preferredAccommodationNo = preferredAccommodationNo;
    }
    public int getPreferredRoomNo() {
        return preferredRoomNo;
    }
    public void setPreferredRoomNo(int preferredRoomNo) {
        this.preferredRoomNo = preferredRoomNo;
    }
    private int getRoom(@NotNull Accommodation a){
        int i = 1;
        while(i <= a.getCapacity()){
            if(a.getAvailability(i))
                return i;
            i++;
        }
        return 0;
    }
    private int getClosestAvailable(@NotNull Accommodation a, int roomNo){
        if(a.getAvailability(roomNo))
            return roomNo;
        int i = 1;
        while(roomNo + i <= a.getCapacity() && roomNo - i > 0){
            if(a.getAvailability(roomNo + i))
                return roomNo + i;
            else if (a.getAvailability(roomNo - i))
                return roomNo - i;
            i++;
        }
        while(roomNo + i <= a.getCapacity()){
            if(a.getAvailability(roomNo + i))
                return roomNo + i;
            i++;
        }
        while(roomNo - i > 0){
            if (a.getAvailability(roomNo - i))
                return roomNo - i;
            i++;
        }
        return 0;
    }
    public String allocate(ArrayList<Accommodation> accommodations){
        String address = "";
        if (this.accommodationType.equalsIgnoreCase("Type-I"))
            address = "HB";
        else if (this.accommodationType.equalsIgnoreCase("Type-II"))
            address = "WB";
        else if (this.accommodationType.equalsIgnoreCase("Type-III"))
            address = "PB";
        for(Accommodation a : accommodations){
            if(Objects.equals(a.getAccommodationType(), this.accommodationType) &&
                    (this.preferredBlock == '\0' || a.getBlock() == this.getPreferredBlock()) &&
                    (this.getPreferredAccommodationNo() == 0 || a.getAccommodationNo() == this.getPreferredAccommodationNo())){
                int roomNo = this.getClosestAvailable(a, preferredRoomNo);
                if(roomNo != 0) {
                    address = address + "-" + a.getBlock() + "-" + a.getAccommodationNo() + "-" + roomNo;
                    a.setAvailability(roomNo, false);
                    return address;
                }
                break;
            }
        }
        for(Accommodation a : accommodations){
            if(Objects.equals(a.getAccommodationType(), this.accommodationType)  &&
                    (this.preferredBlock == '\0' || a.getBlock() == this.getPreferredBlock())){
                int roomNo = this.getRoom(a);
                if(roomNo != 0) {
                    address = address + "-" + a.getBlock() + "-" + a.getAccommodationNo() + "-" + roomNo;
                    a.setAvailability(roomNo, false);
                    return address;
                }
            }
        }
        for(Accommodation a : accommodations){
            if(Objects.equals(a.getAccommodationType(), this.accommodationType)){
                int roomNo = this.getRoom(a);
                if(roomNo != 0) {
                    address = address + "-" + a.getBlock() + "-" + a.getAccommodationNo() + "-" + roomNo;
                    a.setAvailability(roomNo, false);
                    return address;
                }
            }
        }
        return null;
    }
}

/*
Aditya
Gupta
1
23950
1
A
1
5
Tushar
Khorwal
1
23947
1
A
1
6
Abhishek
Namdev
2
12345
2
Abhyuday
Parashar
3
45612
2
Pramod
Pawar
1
45687
2
 */
