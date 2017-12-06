-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema puntoventa
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema puntoventa
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `puntoventa` DEFAULT CHARACTER SET utf8 ;
USE `puntoventa` ;

-- -----------------------------------------------------
-- Table `puntoventa`.`Inventario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puntoventa`.`Inventario` (
  `idInventario` INT NOT NULL AUTO_INCREMENT,
  `codigobarra` VARCHAR(50) NOT NULL,
  `descripcion` TEXT NOT NULL,
  `cantidad` DOUBLE NOT NULL,
  `preciocompra` DOUBLE NOT NULL,
  `precioventa` DOUBLE NOT NULL,
  PRIMARY KEY (`idInventario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `puntoventa`.`Movimiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puntoventa`.`Movimiento` (
  `idMovimiento` INT NOT NULL AUTO_INCREMENT,
  `codigoproducto` VARCHAR(50) NOT NULL,
  `tipomov` VARCHAR(45) NOT NULL,
  `cantidadvendida` DOUBLE NOT NULL,
  `preciototal` DOUBLE NOT NULL,
  `usuario` VARCHAR(100) NOT NULL,
  `fecha` DATETIME NOT NULL,
  PRIMARY KEY (`idMovimiento`, `fecha`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `puntoventa`.`Corte`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puntoventa`.`Corte` (
  `idCorte` INT NOT NULL AUTO_INCREMENT,
  `inicioperiodo` DATETIME NOT NULL,
  `finperiodo` DATETIME NOT NULL,
  `totalcaja` DOUBLE NOT NULL,
  `cantidadTot` DOUBLE NOT NULL,
  `usuario` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idCorte`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
