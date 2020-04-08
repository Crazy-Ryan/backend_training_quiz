package entity;

public class ParkingTicket extends ParkingLocation {
    private String licensePlateNo;

    public ParkingTicket(char parkingLotId, int spaceNo, String licensePlateNo) {
        super(parkingLotId, spaceNo);
        this.licensePlateNo = licensePlateNo;
    }

    public String getLicensePlateNo() {
        return licensePlateNo;
    }

    @Override
    public String toString() {
        return parkingLotId + "," + spaceNo + "," + licensePlateNo;
    }
}
