package entity;

public class ParkingTicket {
    private char parkingLotId;
    private int spaceNo;
    private String licensePlateNo;

    public ParkingTicket() {
    }

    public ParkingTicket(char parkingLotId, int spaceNo, String licensePlateNo) {
        this.parkingLotId = parkingLotId;
        this.spaceNo = spaceNo;
        this.licensePlateNo = licensePlateNo;
    }

    public char getParkingLotId() {
        return parkingLotId;
    }

    public int getSpaceNo() {
        return spaceNo;
    }

    public String getLicensePlateNo() {
        return licensePlateNo;
    }
}
