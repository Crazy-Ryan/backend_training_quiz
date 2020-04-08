package service;

import entity.ParkingLot;
import entity.ParkingSpaceInfo;
import entity.ParkingTicket;
import exception.ParkingLotFullException;
import repo.Repo;

import java.util.ArrayList;
import java.util.List;

public class ParkingService {
    Repo repo = new Repo();

    public void init(String initInfo) {
        String[] parkingLotsInfo = initInfo.split(",");
        List<ParkingLot> parkingLots = new ArrayList<>();
        for (String parkingLotInfo : parkingLotsInfo) {
            String[] parkingLotDetails = parkingLotInfo.split(":");
            parkingLots.add(new ParkingLot(parkingLotDetails[0].charAt(0), Integer.parseInt(parkingLotDetails[1])));
        }
        repo.emptyDb();
        for (ParkingLot parkingLot : parkingLots) {
            repo.initDbData(parkingLot);
        }
    }

    public ParkingTicket park(String carNumber) {
        ParkingSpaceInfo availableParkingSpace = repo.findAvailableParkingSpace();
        int availableParkingSpaceId = availableParkingSpace.getSpaceId();
        if (-1 == availableParkingSpaceId) {
            throw new ParkingLotFullException();
        } else {
            repo.setParkingCar(availableParkingSpaceId, carNumber);
            return new ParkingTicket(
                    availableParkingSpace.getParkingLotId(),
                    availableParkingSpace.getSpaceNo(),
                    carNumber);
        }
    }
}
