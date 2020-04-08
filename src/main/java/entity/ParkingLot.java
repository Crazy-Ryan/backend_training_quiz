package entity;

public class ParkingLot {
    private char id;
    private int capacity;

    public ParkingLot(char id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public char getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }
}
