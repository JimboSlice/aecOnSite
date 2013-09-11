SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `onsite` ;
CREATE SCHEMA IF NOT EXISTS `onsite` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
SHOW WARNINGS;
USE `onsite` ;

-- -----------------------------------------------------
-- Table `onsite`.`Company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`Company` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`Company` (
  `companyId` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `address` VARCHAR(45) NULL ,
  `city` VARCHAR(45) NULL ,
  `state` VARCHAR(2) NULL ,
  `zipcode` VARCHAR(5) NULL ,
  `email` VARCHAR(45) NULL ,
  `phone` VARCHAR(10) NULL ,
  `timeStamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`companyId`) ,
  UNIQUE INDEX `companyId_UNIQUE` (`companyId` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `onsite`.`Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`Project` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`Project` (
  `projectId` BIGINT NOT NULL AUTO_INCREMENT ,
  `address` VARCHAR(30) NULL ,
  `city` VARCHAR(45) NULL ,
  `country` VARCHAR(45) NULL ,
  `countryCode` VARCHAR(2) NULL ,
  `county` VARCHAR(45) NULL ,
  `subAddress` VARCHAR(45) NULL ,
  `state` VARCHAR(2) NULL ,
  `zipcode` VARCHAR(5) NULL ,
  `displayPic` VARCHAR(45) NULL ,
  `latcoord` FLOAT NULL ,
  `longcoord` FLOAT NULL ,
  `neighborhood` VARCHAR(45) NULL ,
  `projectName` VARCHAR(45) NULL ,
  `projectNumber` VARCHAR(45) NULL ,
  `uniqueRoomName` VARCHAR(45) NULL ,
  `timeStamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  `Company_companyId` BIGINT NOT NULL ,
  PRIMARY KEY (`projectId`, `Company_companyId`) ,
  UNIQUE INDEX `id_UNIQUE` (`projectId` ASC) ,
  INDEX `fk_Project_Company1` (`Company_companyId` ASC) ,
  CONSTRAINT `fk_Project_Company1`
    FOREIGN KEY (`Company_companyId` )
    REFERENCES `onsite`.`Company` (`companyId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `onsite`.`Report`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`Report` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`Report` (
  `reportId` BIGINT NOT NULL AUTO_INCREMENT ,
  `constructionphase` VARCHAR(45) NULL ,
  `peopleOnSite` INT NULL ,
  `rname` VARCHAR(45) NULL ,
  `rtype` VARCHAR(45) NULL ,
  `voiceData` BINARY NULL ,
  `weatherData` BINARY NULL ,
  `timeStamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  `Project_projectId` BIGINT NOT NULL ,
  INDEX `fk_Report_Project` (`Project_projectId` ASC) ,
  PRIMARY KEY (`reportId`) ,
  UNIQUE INDEX `reportId_UNIQUE` (`reportId` ASC) ,
  CONSTRAINT `fk_Report_Project`
    FOREIGN KEY (`Project_projectId` )
    REFERENCES `onsite`.`Project` (`projectId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `onsite`.`Area`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`Area` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`Area` (
  `areaId` BIGINT NOT NULL AUTO_INCREMENT ,
  `comment` VARCHAR(45) NULL ,
  `name` VARCHAR(45) NULL ,
  `number` VARCHAR(45) NULL ,
  `timeStamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  `Report_reportId` BIGINT NOT NULL ,
  PRIMARY KEY (`areaId`) ,
  INDEX `fk_Area_Report1` (`Report_reportId` ASC) ,
  UNIQUE INDEX `areaId_UNIQUE` (`areaId` ASC) ,
  CONSTRAINT `fk_Area_Report1`
    FOREIGN KEY (`Report_reportId` )
    REFERENCES `onsite`.`Report` (`reportId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `onsite`.`Asset`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`Asset` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`Asset` (
  `assetId` BIGINT NOT NULL AUTO_INCREMENT ,
  `appraisalFlag` TINYINT NULL ,
  `description` VARCHAR(45) NULL ,
  `initvalue` DOUBLE NULL ,
  `name` VARCHAR(45) NULL ,
  `purchaseDate` DATE NULL ,
  `type` VARCHAR(45) NULL ,
  `timeStamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  `Area_areaId` BIGINT NOT NULL ,
  PRIMARY KEY (`assetId`) ,
  UNIQUE INDEX `assetId_UNIQUE` (`assetId` ASC) ,
  INDEX `fk_Asset_Area1` (`Area_areaId` ASC) ,
  CONSTRAINT `fk_Asset_Area1`
    FOREIGN KEY (`Area_areaId` )
    REFERENCES `onsite`.`Area` (`areaId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `onsite`.`Note`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`Note` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`Note` (
  `noteId` BIGINT NOT NULL AUTO_INCREMENT ,
  `displayPic` VARCHAR(45) NULL ,
  `itemResolved` TINYINT NULL ,
  `latitude` DOUBLE NULL ,
  `longitude` DOUBLE NULL ,
  `note` VARCHAR(45) NULL ,
  `picCounter` INT(11) NULL ,
  `picOrientation` INT(11) NULL ,
  `picScale` FLOAT NULL ,
  `thePic` VARCHAR(45) NULL ,
  `trackableActionItem` TINYINT NULL ,
  `timeStamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  `Area_areaId` BIGINT NOT NULL ,
  PRIMARY KEY (`noteId`) ,
  UNIQUE INDEX `noteId_UNIQUE` (`noteId` ASC) ,
  INDEX `fk_Note_Area1` (`Area_areaId` ASC) ,
  CONSTRAINT `fk_Note_Area1`
    FOREIGN KEY (`Area_areaId` )
    REFERENCES `onsite`.`Area` (`areaId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `onsite`.`UserPreference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`UserPreference` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`UserPreference` (
  `userPreferenceId` BIGINT NOT NULL AUTO_INCREMENT ,
  `companyLogo` BINARY NULL ,
  `companyName` VARCHAR(45) NULL ,
  `consumerKey` VARCHAR(45) NULL ,
  `consumerSecret` VARCHAR(45) NULL ,
  `deletedb` TINYINT NULL ,
  `dropboxdirectory` VARCHAR(45) NULL ,
  `dropboxid` VARCHAR(45) NULL ,
  `dropboxpasswd` VARCHAR(45) NULL ,
  `emaildefault` VARCHAR(45) NULL ,
  `firstName` VARCHAR(20) NULL ,
  `lastName` VARCHAR(30) NULL ,
  `recordLimit` VARCHAR(45) NULL ,
  `titleOfPosition` VARCHAR(45) NULL ,
  `trailVersion` TINYINT NULL ,
  `timeStamp` DATE NULL ,
  PRIMARY KEY (`userPreferenceId`) ,
  UNIQUE INDEX `userPreferenceId_UNIQUE` (`userPreferenceId` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `onsite`.`Person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`Person` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`Person` (
  `personId` BIGINT NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(45) NOT NULL ,
  `subscriptionType` VARCHAR(45) NULL ,
  `password` VARCHAR(45) NULL ,
  `role` VARCHAR(1) NULL ,
  `timeStamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`personId`) ,
  UNIQUE INDEX `inspectorId_UNIQUE` (`personId` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `onsite`.`Person_HAS_Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`Person_HAS_Project` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`Person_HAS_Project` (
  `personId` BIGINT NOT NULL ,
  `projectId` BIGINT NOT NULL ,
  `timeStamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`personId`, `projectId`) ,
  INDEX `fk_Inspector_has_Project_Project1` (`projectId` ASC) ,
  INDEX `fk_Inspector_has_Project_Inspector1` (`personId` ASC) ,
  CONSTRAINT `fk_Inspector_has_Project_Inspector1`
    FOREIGN KEY (`personId` )
    REFERENCES `onsite`.`Person` (`personId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inspector_has_Project_Project1`
    FOREIGN KEY (`projectId` )
    REFERENCES `onsite`.`Project` (`projectId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `onsite`.`Person_HAS_Company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onsite`.`Person_HAS_Company` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `onsite`.`Person_HAS_Company` (
  `personId` BIGINT NOT NULL ,
  `companyId` BIGINT NOT NULL ,
  PRIMARY KEY (`personId`, `companyId`) ,
  INDEX `fk_Person_has_Company_Company1` (`companyId` ASC) ,
  INDEX `fk_Person_has_Company_Person1` (`personId` ASC) ,
  CONSTRAINT `fk_Person_has_Company_Person1`
    FOREIGN KEY (`personId` )
    REFERENCES `onsite`.`Person` (`personId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_has_Company_Company1`
    FOREIGN KEY (`companyId` )
    REFERENCES `onsite`.`Company` (`companyId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
