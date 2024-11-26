-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ssafyhome
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafyhome
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafyhome` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafyhome` ;

-- -----------------------------------------------------
-- Table `ssafyhome`.`aptinfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`aptinfo` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`aptinfo` (
  `apt_id` VARCHAR(15) NOT NULL,
  `apt_name` VARCHAR(30) NULL DEFAULT NULL,
  `building` VARCHAR(30) NULL DEFAULT NULL,
  `construction_year` INT NULL DEFAULT NULL,
  `road_address` VARCHAR(50) NULL DEFAULT NULL,
  `legal_address` VARCHAR(50) NULL DEFAULT NULL,
  `lat` DOUBLE NULL DEFAULT NULL,
  `lon` DOUBLE NULL DEFAULT NULL,
  `region_code` VARCHAR(10) NULL DEFAULT NULL,
  `road_name` VARCHAR(20) NULL DEFAULT NULL,
  `road_building_main_code` VARCHAR(4) NULL DEFAULT NULL,
  `road_building_sub_code` VARCHAR(4) NULL DEFAULT NULL,
  `road_id` VARCHAR(4) NULL DEFAULT NULL,
  `road_ground_code` VARCHAR(4) NULL DEFAULT NULL,
  `road_code` VARCHAR(8) NULL DEFAULT NULL,
  `legal_dong` VARCHAR(10) NULL DEFAULT NULL,
  `legal_dong_main_code` VARCHAR(10) NULL DEFAULT NULL,
  `legal_dong_sub_code` VARCHAR(10) NULL DEFAULT NULL,
  `legal_dong_sigungu_code` VARCHAR(10) NULL DEFAULT NULL,
  `legal_dong_eupmyeondong_code` VARCHAR(10) NULL DEFAULT NULL,
  `legal_dong_lot_code` VARCHAR(4) NULL DEFAULT NULL,
  `geom_point` POINT NOT NULL,
  `legal_dong_full_code` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`apt_id`),
  SPATIAL INDEX `idx_geom_point` (`geom_point`) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`aptdeal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`aptdeal` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`aptdeal` (
  `deal_id` INT NOT NULL AUTO_INCREMENT,
  `apt_id` VARCHAR(15) NULL DEFAULT NULL,
  `deal_amount` BIGINT NULL DEFAULT NULL,
  `deal_type` VARCHAR(10) NULL DEFAULT NULL,
  `deal_year` INT NULL DEFAULT NULL,
  `deal_month` INT NULL DEFAULT NULL,
  `deal_day` INT NULL DEFAULT NULL,
  `area` DOUBLE NULL DEFAULT NULL,
  `floor` INT NULL DEFAULT NULL,
  `seller` VARCHAR(10) NULL DEFAULT NULL,
  `buyer` VARCHAR(10) NULL DEFAULT NULL,
  `registration_date` DATE NULL DEFAULT NULL,
  `broker_location` VARCHAR(20) NULL DEFAULT NULL,
  `cancel_date` DATE NULL DEFAULT NULL,
  `is_cancelled` VARCHAR(4) NULL DEFAULT NULL,
  PRIMARY KEY (`deal_id`),
  INDEX `apt_id` (`apt_id` ASC) VISIBLE,
  CONSTRAINT `aptdeal_ibfk_1`
    FOREIGN KEY (`apt_id`)
    REFERENCES `ssafyhome`.`aptinfo` (`apt_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3831303
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`users` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`users` (
  `user_id` VARCHAR(20) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `role` ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`boards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`boards` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`boards` (
  `board_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` TEXT NOT NULL,
  `post_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hit_point` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`board_id`),
  INDEX `id` (`board_id` ASC) VISIBLE,
  INDEX `board_ibfk_1` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 185
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`checklist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`checklist` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`checklist` (
  `checklist_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`checklist_id`),
  INDEX `fk_checklist_users` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_checklist_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`checklist_apt`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`checklist_apt` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`checklist_apt` (
  `checklist_apt_id` INT NOT NULL AUTO_INCREMENT,
  `apt_id` VARCHAR(15) NULL DEFAULT NULL,
  `checklist_id` INT NULL DEFAULT NULL,
  `user_id` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`checklist_apt_id`),
  INDEX `checklist_apt_ibfk_1` (`apt_id` ASC) VISIBLE,
  INDEX `checklist_apt_ibfk_3` (`user_id` ASC) VISIBLE,
  INDEX `checklist_apt_ibfk_2` (`checklist_id` ASC) VISIBLE,
  CONSTRAINT `checklist_apt_ibfk_1`
    FOREIGN KEY (`apt_id`)
    REFERENCES `ssafyhome`.`aptinfo` (`apt_id`),
  CONSTRAINT `checklist_apt_ibfk_2`
    FOREIGN KEY (`checklist_id`)
    REFERENCES `ssafyhome`.`checklist` (`checklist_id`),
  CONSTRAINT `checklist_apt_ibfk_3`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 116
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`checklist_apt_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`checklist_apt_items` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`checklist_apt_items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `checklist_apt_id` INT NULL DEFAULT NULL,
  `item` VARCHAR(255) NULL DEFAULT NULL,
  `checked` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`item_id`),
  INDEX `fk_checklist_apt_items_checklist_apt` (`checklist_apt_id` ASC) VISIBLE,
  CONSTRAINT `fk_checklist_apt_items_checklist_apt`
    FOREIGN KEY (`checklist_apt_id`)
    REFERENCES `ssafyhome`.`checklist_apt` (`checklist_apt_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 365
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`checklist_items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`checklist_items` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`checklist_items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `checklist_id` INT NULL DEFAULT NULL,
  `item` VARCHAR(1000) NULL DEFAULT NULL,
  `checked` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`item_id`),
  INDEX `checklist_id` (`checklist_id` ASC) VISIBLE,
  CONSTRAINT `checklist_items_ibfk_1`
    FOREIGN KEY (`checklist_id`)
    REFERENCES `ssafyhome`.`checklist` (`checklist_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 71
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`comments` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`comments` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  `comment_content` VARCHAR(2000) NOT NULL,
  `comment_create_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  INDEX `fk_comments_boards` (`board_id` ASC) VISIBLE,
  CONSTRAINT `fk_comments_boards`
    FOREIGN KEY (`board_id`)
    REFERENCES `ssafyhome`.`boards` (`board_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 30
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`dong_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`dong_code` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`dong_code` (
  `legal_dong_full_code` VARCHAR(10) NULL DEFAULT NULL,
  `legal_address` VARCHAR(70) NULL DEFAULT NULL,
  FULLTEXT INDEX `address_index` (`legal_address`) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`facilities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`facilities` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`facilities` (
  `facility_id` TEXT NULL DEFAULT NULL,
  `facility_name` TEXT NULL DEFAULT NULL,
  `category` ENUM('병원', '세탁', '편의점', '지하철', '학교') NULL DEFAULT NULL,
  `road_address` TEXT NULL DEFAULT NULL,
  `lat` TEXT NULL DEFAULT NULL,
  `lon` TEXT NULL DEFAULT NULL,
  `geom` POINT NOT NULL,
  SPATIAL INDEX `sp_index` (`geom`) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`file_board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`file_board` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`file_board` (
  `file_board_id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NOT NULL,
  `file_url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`file_board_id`),
  INDEX `fk_file_board_boards` (`board_id` ASC) VISIBLE,
  CONSTRAINT `fk_file_board_boards`
    FOREIGN KEY (`board_id`)
    REFERENCES `ssafyhome`.`boards` (`board_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 157
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`refresh_token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`refresh_token` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`refresh_token` (
  `refresh_token_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(255) NOT NULL,
  `token` VARCHAR(1000) NOT NULL,
  `expiration_time` DATETIME NOT NULL DEFAULT (now() + interval 30 day),
  PRIMARY KEY (`refresh_token_id`),
  INDEX `fk_refresh_token_users` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_refresh_token_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 316
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`user_house_like`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`user_house_like` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`user_house_like` (
  `user_house_like_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NULL DEFAULT NULL,
  `apt_id` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`user_house_like_id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `apt_id` (`apt_id` ASC) VISIBLE,
  CONSTRAINT `user_house_like_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`users` (`user_id`),
  CONSTRAINT `user_house_like_ibfk_2`
    FOREIGN KEY (`apt_id`)
    REFERENCES `ssafyhome`.`aptinfo` (`apt_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 76
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
