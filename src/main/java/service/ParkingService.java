package service;

import entity.ParkingLot;
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
        repo.init(parkingLots);
    }
}
