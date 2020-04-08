package entity;

public class ParkingSpaceInfo extends ParkingLocation {
    private int spaceId;

    public ParkingSpaceInfo(int spaceId) {
        this.spaceId = spaceId;
    }

    public ParkingSpaceInfo(int spaceId, char parkingLotId, int spaceNo) {
        super(parkingLotId, spaceNo);
        this.spaceId = spaceId;
    }

    public int getSpaceId() {
        return spaceId;
    }
}
