package entity;

public abstract class ParkingLocation {

    protected char parkingLotId;
    protected int spaceNo;

    public ParkingLocation() {
    }

    public ParkingLocation(char parkingLotId, int spaceNo) {
        this.parkingLotId = parkingLotId;
        this.spaceNo = spaceNo;
    }

    public int getSpaceNo() {
        return spaceNo;
    }

    public char getParkingLotId() {
        return parkingLotId;
    }
}
