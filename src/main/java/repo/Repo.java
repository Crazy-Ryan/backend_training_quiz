package repo;

import entity.ParkingLot;
import entity.ParkingSpaceInfo;
import entity.ParkingTicket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class Repo {
    public void emptyDb() {
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

    public void initDbData(ParkingLot parkingLot) {
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

    public ParkingSpaceInfo findAvailableParkingSpace() {
        Connection connection = DatabaseUtil.connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String setStartIdCmd = "SELECT id,parking_lot_id,space_no FROM parking_space_info WHERE isnull(license_plate_no) " +
                    "ORDER BY parking_lot_id,space_no LIMIT 1";
            preparedStatement = connection.prepareStatement(setStartIdCmd);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new ParkingSpaceInfo(
                        resultSet.getInt("id"),
                        resultSet.getString("parking_lot_id").charAt(0),
                        resultSet.getInt("space_no"));
            } else {
                return new ParkingSpaceInfo(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ParkingSpaceInfo(-1);
        } finally {
            DatabaseUtil.releaseSource(connection, preparedStatement, resultSet);
        }
    }

    public ParkingSpaceInfo findAvailableParkingSpaceIntelligent() {
        Connection connection = DatabaseUtil.connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String setStartIdCmd = "SELECT id, parking_lot_id, space_no\n" +
                    "FROM parking_space_info\n" +
                    "WHERE isnull(license_plate_no)\n" +
                    "  AND parking_lot_id = (SELECT parking_lot_id\n" +
                    "                         FROM (SELECT id, parking_lot_id\n" +
                    "                               FROM parking_space_info\n" +
                    "                               WHERE isnull(license_plate_no)) AS emptySpace\n" +
                    "                         GROUP BY parking_lot_id\n" +
                    "                         ORDER BY count(id) DESC\n" +
                    "                         LIMIT 1)\n" +
                    "ORDER BY parking_lot_id, space_no\n" +
                    "LIMIT 1";
            preparedStatement = connection.prepareStatement(setStartIdCmd);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new ParkingSpaceInfo(
                        resultSet.getInt("id"),
                        resultSet.getString("parking_lot_id").charAt(0),
                        resultSet.getInt("space_no"));
            } else {
                return new ParkingSpaceInfo(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ParkingSpaceInfo(-1);
        } finally {
            DatabaseUtil.releaseSource(connection, preparedStatement, resultSet);
        }
    }

    public void setParkingCar(int id, String licensePlateNo) {
        Connection connection = DatabaseUtil.connectToDB();
        PreparedStatement preparedStatement = null;
        try {
            String updateCmd = "UPDATE parking_space_info SET license_plate_no = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(updateCmd);
            if ((null == licensePlateNo) || (licensePlateNo.isEmpty())) {
                preparedStatement.setNull(1, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, licensePlateNo);
            }
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.releaseSource(connection, preparedStatement);
        }
    }

    public int fetchCar(ParkingTicket parkingTicket) {
        Connection connection = DatabaseUtil.connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String queryCmd = "SELECT id FROM parking_space_info WHERE " +
                    "parking_lot_id = ? AND " +
                    "space_no = ? AND " +
                    "license_plate_no = ?";
            preparedStatement = connection.prepareStatement(queryCmd);
            preparedStatement.setString(1, parkingTicket.getParkingLotId() + "");
            preparedStatement.setInt(2, parkingTicket.getSpaceNo());
            preparedStatement.setString(3, parkingTicket.getLicensePlateNo());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            DatabaseUtil.releaseSource(connection, preparedStatement, resultSet);
        }
    }
}
