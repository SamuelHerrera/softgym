-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema softgym
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema softgym
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `softgym` DEFAULT CHARACTER SET utf8 ;
USE `softgym` ;

-- -----------------------------------------------------
-- Table `softgym`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`Cliente` (
  `idcliente` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `telefono` VARCHAR(15) NOT NULL,
  `email` VARCHAR(40) NOT NULL,
  `fecharegistro` DATE NOT NULL,
  `notas` TEXT NULL,
  PRIMARY KEY (`idcliente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`FotoCliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`FotoCliente` (
  `idcliente` INT NOT NULL,
  `foto` MEDIUMBLOB NOT NULL,
  CONSTRAINT `fk_FotoCliente_Cliente`
    FOREIGN KEY (`idcliente`)
    REFERENCES `softgym`.`Cliente` (`idcliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`HuellaCliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`HuellaCliente` (
  `idcliente` INT NOT NULL,
  `huelladigital` LONGBLOB NOT NULL,
  INDEX `fk_table1_Cliente1_idx` (`idcliente` ASC),
  CONSTRAINT `fk_table1_Cliente1`
    FOREIGN KEY (`idcliente`)
    REFERENCES `softgym`.`Cliente` (`idcliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`Usuario` (
  `idusuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `contrasena` VARCHAR(20) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idusuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`FotoUsuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`FotoUsuario` (
  `idusuario` INT NOT NULL,
  `foto` MEDIUMBLOB NOT NULL,
  INDEX `fk_table1_Usuario1_idx` (`idusuario` ASC),
  CONSTRAINT `fk_table1_Usuario1`
    FOREIGN KEY (`idusuario`)
    REFERENCES `softgym`.`Usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`HuellaUsuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`HuellaUsuario` (
  `idusuario` INT NOT NULL,
  `huellausuario` LONGBLOB NOT NULL,
  INDEX `fk_table1_Usuario2_idx` (`idusuario` ASC),
  CONSTRAINT `fk_table1_Usuario2`
    FOREIGN KEY (`idusuario`)
    REFERENCES `softgym`.`Usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`Ingresos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`Ingresos` (
  `idIngresos` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `motivo` VARCHAR(15) NOT NULL,
  `descripcion` VARCHAR(255) NOT NULL,
  `cantidad` DOUBLE NOT NULL,
  `usuario` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idIngresos`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`BarrasCliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`BarrasCliente` (
  `idcliente` INT NOT NULL,
  `codigobarras` VARCHAR(45) NOT NULL,
  INDEX `fk_BarrasCliente_Cliente1_idx` (`idcliente` ASC),
  CONSTRAINT `fk_BarrasCliente_Cliente1`
    FOREIGN KEY (`idcliente`)
    REFERENCES `softgym`.`Cliente` (`idcliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`BarrasUsuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`BarrasUsuario` (
  `idusuario` INT NOT NULL,
  `codigobarras` VARCHAR(45) NOT NULL,
  INDEX `fk_table1_Usuario3_idx` (`idusuario` ASC),
  CONSTRAINT `fk_table1_Usuario3`
    FOREIGN KEY (`idusuario`)
    REFERENCES `softgym`.`Usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`Egresos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`Egresos` (
  `idEgresos` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `motivo` VARCHAR(65) NOT NULL,
  `descripcion` VARCHAR(255) NOT NULL,
  `cantidad` DOUBLE NOT NULL,
  `usuario` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idEgresos`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`FlujoDiarioClientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`FlujoDiarioClientes` (
  `idFlujoClientes` INT NOT NULL AUTO_INCREMENT,
  `fechahora` DATETIME NOT NULL,
  `idcliente` INT NOT NULL,
  PRIMARY KEY (`idFlujoClientes`),
  INDEX `fk_FlujoDiarioClientes_Cliente1_idx` (`idcliente` ASC),
  CONSTRAINT `fk_FlujoDiarioClientes_Cliente1`
    FOREIGN KEY (`idcliente`)
    REFERENCES `softgym`.`Cliente` (`idcliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`HistorialPagoClientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`HistorialPagoClientes` (
  `idHistorialPagoClientes` INT NOT NULL AUTO_INCREMENT,
  `idIngresos` INT NOT NULL,
  `idcliente` INT NOT NULL,
  PRIMARY KEY (`idHistorialPagoClientes`),
  INDEX `fk_HistorialPagoClientes_Ingresos1_idx` (`idIngresos` ASC),
  INDEX `fk_HistorialPagoClientes_Cliente1_idx` (`idcliente` ASC),
  CONSTRAINT `fk_HistorialPagoClientes_Ingresos1`
    FOREIGN KEY (`idIngresos`)
    REFERENCES `softgym`.`Ingresos` (`idIngresos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HistorialPagoClientes_Cliente1`
    FOREIGN KEY (`idcliente`)
    REFERENCES `softgym`.`Cliente` (`idcliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`FechaCorte_Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`FechaCorte_Clientes` (
  `idcliente` INT NOT NULL,
  `fechacorte` DATE NOT NULL,
  CONSTRAINT `idcliente`
    FOREIGN KEY (`idcliente`)
    REFERENCES `softgym`.`Cliente` (`idcliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `softgym`.`CorteEfectivo_periodo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `softgym`.`CorteEfectivo_periodo` (
  `idCorteEfectivo_periodo` INT NOT NULL AUTO_INCREMENT,
  `TotalIngresos` DOUBLE NOT NULL,
  `TotalEgresos` DOUBLE NOT NULL,
  `EfectivoEnCaja` DOUBLE NOT NULL,
  `Usuario` VARCHAR(50) NOT NULL,
  `FInicioPeriodo` DATE NOT NULL,
  `FTerminoPeriodo` DATE NOT NULL,
  PRIMARY KEY (`idCorteEfectivo_periodo`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
