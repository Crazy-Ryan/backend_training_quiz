CREATE DATABASE tw_parking_lot DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
USE tw_parking_lot;
CREATE TABLE parking_space_info
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    parking_lot_id   VARCHAR(4) NOT NULL,
    space_no         INT        NOT NULL,
    license_plate_no VARCHAR(50)
) ENGINE = InnoDB,
  DEFAULT CHARSET utf8mb4;