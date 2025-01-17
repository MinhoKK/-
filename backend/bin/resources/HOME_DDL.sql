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
-- Table `ssafyhome`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`users` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`users` (
  `user_id` VARCHAR(20) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `role` ENUM('admin', 'user') NOT NULL,
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
  `subject` VARCHAR(20) NOT NULL,
  `content` VARCHAR(20) NOT NULL,
  `post_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hit_point` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`board_id`),
  INDEX `id` (`board_id` ASC) VISIBLE,
  INDEX `board_ibfk_1` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`dongcode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`dongcode` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`dongcode` (
  `dongCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`dongCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`houseinfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`houseinfo` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`houseinfo` (
  `aptCode` BIGINT NOT NULL,
  `buildYear` INT NULL DEFAULT NULL,
  `roadName` VARCHAR(40) NULL DEFAULT NULL,
  `roadNameBonbun` VARCHAR(5) NULL DEFAULT NULL,
  `roadNameBubun` VARCHAR(5) NULL DEFAULT NULL,
  `roadNameSeq` VARCHAR(2) NULL DEFAULT NULL,
  `roadNameBasementCode` VARCHAR(1) NULL DEFAULT NULL,
  `roadNameCode` VARCHAR(7) NULL DEFAULT NULL,
  `dong` VARCHAR(40) NULL DEFAULT NULL,
  `bonbun` VARCHAR(4) NULL DEFAULT NULL,
  `bubun` VARCHAR(4) NULL DEFAULT NULL,
  `sigunguCode` VARCHAR(5) NULL DEFAULT NULL,
  `eubmyundongCode` VARCHAR(5) NULL DEFAULT NULL,
  `dongCode` VARCHAR(10) NULL DEFAULT NULL,
  `landCode` VARCHAR(1) NULL DEFAULT NULL,
  `apartmentName` VARCHAR(40) NULL DEFAULT NULL,
  `jibun` VARCHAR(10) NULL DEFAULT NULL,
  `lng` VARCHAR(30) NULL DEFAULT NULL,
  `lat` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`aptCode`),
  UNIQUE INDEX `UNIQUE` (`buildYear` ASC, `apartmentName` ASC, `jibun` ASC, `sigunguCode` ASC, `eubmyundongCode` ASC) INVISIBLE,
  INDEX `houseinfo_dongCode_dongcode_dongCode_fk_idx` (`dongCode` ASC) INVISIBLE,
  CONSTRAINT `houseinfo_dongCode_dongcode_dongCode_fk`
    FOREIGN KEY (`dongCode`)
    REFERENCES `ssafyhome`.`dongcode` (`dongCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`housedeal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`housedeal` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`housedeal` (
  `no` BIGINT NOT NULL,
  `dealAmount` VARCHAR(20) NULL DEFAULT NULL,
  `dealYear` INT NULL DEFAULT NULL,
  `dealMonth` INT NULL DEFAULT NULL,
  `dealDay` INT NULL DEFAULT NULL,
  `area` VARCHAR(20) NULL DEFAULT NULL,
  `floor` VARCHAR(4) NULL DEFAULT NULL,
  `cancelDealType` VARCHAR(1) NULL DEFAULT NULL,
  `aptCode` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `housedeal_aptCode_houseinfo_aptCode_fk_idx` (`aptCode` ASC) VISIBLE,
  CONSTRAINT `housedeal_aptCode_houseinfo_aptCode_fk`
    FOREIGN KEY (`aptCode`)
    REFERENCES `ssafyhome`.`houseinfo` (`aptCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`region`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`region` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`region` (
  `region_id` INT NOT NULL,
  `user_id` VARCHAR(20) NOT NULL,
  `sidoName` VARCHAR(45) NULL DEFAULT NULL,
  `gugunName` VARCHAR(45) NULL DEFAULT NULL,
  `dongName` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`region_id`),
  INDEX `fk_likeregion_userinfo1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_likeregion_userinfo1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`stores`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`stores` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`stores` (
  `store_id` INT NOT NULL,
  `store_name` VARCHAR(45) NOT NULL,
  `store_address` VARCHAR(45) NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  `langititude` DOUBLE NOT NULL,
  `latitude` DOUBLE NOT NULL,
  PRIMARY KEY (`store_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafyhome`.`user_house_like`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ssafyhome`.`user_house_like` ;

CREATE TABLE IF NOT EXISTS `ssafyhome`.`user_house_like` (
  `user_house_like_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NOT NULL,
  `aptCode` BIGINT NOT NULL,
  PRIMARY KEY (`user_house_like_id`),
  INDEX `user_house_like_id` (`user_house_like_id` ASC) VISIBLE,
  INDEX `aptCode` (`aptCode` ASC) VISIBLE,
  INDEX `uattention_ibfk_1` (`user_id` ASC) VISIBLE,
  CONSTRAINT `uattention_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ssafyhome`.`users` (`user_id`),
  CONSTRAINT `uattention_ibfk_2`
    FOREIGN KEY (`aptCode`)
    REFERENCES `ssafyhome`.`houseinfo` (`aptCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
