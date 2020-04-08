package repo;

import entity.ParkingLot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class Repo {
    public void init(List<ParkingLot> parkingLots) {
        emptyDb();
        for (ParkingLot parkingLot : parkingLots) {
            initDbData(parkingLot);
        }
    }

    private void emptyDb() {
        Connection connection = DatabaseUtil.connectToDB();
        PreparedStatement preparedStatement = null;
        try {
            String sqlCmd = "DELETE FROM parking_space_info";
            preparedStatement = connection.prepareStatement(sqlCmd);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.releaseSource(connection, preparedStatement);
        }
    }

    private void initDbData(ParkingLot parkingLot) {
        Connection connection = DatabaseUtil.connectToDB();
        PreparedStatement preparedStatement = null;
        char parkingLotId = parkingLot.getId();
        int parkingCapacity = parkingLot.getCapacity();
        try {
            String setStartIdCmd = "ALTER TABLE parking_space_info AUTO_INCREMENT = 1";
            preparedStatement = connection.prepareStatement(setStartIdCmd);
            preparedStatement.execute();
            for (int index = 1; index <= parkingCapacity; index++) {
                String insertCmd = "INSERT INTO parking_space_info(parking_lot_id, space_no, license_plate_no) " +
                        "VALUES (?,?,NULL)";
                preparedStatement = connection.prepareStatement(insertCmd);
                preparedStatement.setString(1, parkingLotId + "");
                preparedStatement.setInt(2, index);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.releaseSource(connection, preparedStatement);
        }

    }
}
