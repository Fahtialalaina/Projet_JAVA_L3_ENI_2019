-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 02 sep. 2019 à 21:55
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `gpb`
--

-- --------------------------------------------------------

--
-- Structure de la table `banque`
--

DROP TABLE IF EXISTS `banque`;
CREATE TABLE IF NOT EXISTS `banque` (
  `NumBanque` int(11) NOT NULL,
  `NomBanque` varchar(50) DEFAULT NULL,
  `ContactBanque` varchar(50) DEFAULT NULL,
  `PretBanque` int(11) DEFAULT NULL,
  PRIMARY KEY (`NumBanque`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `banque`
--

INSERT INTO `banque` (`NumBanque`, `NomBanque`, `ContactBanque`, `PretBanque`) VALUES
(12, 'BOA', '0345261458', 7),
(13, 'BMOI', '0346521478', 8),
(14, 'BNI', '0326695412', 10),
(15, 'BFV-SG', '0324574114', 9);

-- --------------------------------------------------------

--
-- Structure de la table `client_table`
--

DROP TABLE IF EXISTS `client_table`;
CREATE TABLE IF NOT EXISTS `client_table` (
  `cin` varchar(12) NOT NULL,
  `nomc` varchar(50) DEFAULT NULL,
  `prenomc` varchar(50) DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sexe` varchar(50) DEFAULT NULL,
  `gsm` varchar(50) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `date_inscription` date DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`cin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `client_table`
--

INSERT INTO `client_table` (`cin`, `nomc`, `prenomc`, `date_naissance`, `age`, `sexe`, `gsm`, `adresse`, `date_inscription`, `image`) VALUES
('101251197029', 'TIALALAINA', 'Fandresena', '1995-09-19', 24, 'Masculin', '0345176541', 'Ambalafary', '2019-09-02', ''),
('101251487541', 'ANDRIANJATOVO', 'Alfred Nico', '1979-11-22', 40, 'Masculin', '0342658435', 'Ankofafa', '2019-09-02', ''),
('101315248652', 'RASOANAIVO', 'Joseph', '1965-09-23', 54, 'Masculin', '0346821457', 'Manaotsara', '2019-09-02', ''),
('123654987485', 'TOLOJANAHARY', 'Nomena', '1985-09-11', 34, 'Féminin', '0332564851', 'Anjoma', '2019-09-02', ''),
('301251462579', 'ZONOMENA', 'Fifaliana', '1972-09-27', 47, 'Féminin', '0326787714', 'Ampasambazaha', '2019-09-02', ''),
('301256412251', 'RAZANAMARO', 'Tahiana', '1958-09-11', 61, 'Masculin', '0342564531', 'Tambohobe', '2019-09-02', '');

-- --------------------------------------------------------

--
-- Structure de la table `effectif`
--

DROP TABLE IF EXISTS `effectif`;
CREATE TABLE IF NOT EXISTS `effectif` (
  `NumBanque` int(20) NOT NULL,
  `NomBanque` varchar(50) NOT NULL,
  `Taux` int(3) NOT NULL,
  `Effectif` bigint(50) NOT NULL,
  `TotalPret` bigint(255) NOT NULL,
  `TotalAPayer` bigint(255) NOT NULL,
  PRIMARY KEY (`NumBanque`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `effectif`
--

INSERT INTO `effectif` (`NumBanque`, `NomBanque`, `Taux`, `Effectif`, `TotalPret`, `TotalAPayer`) VALUES
(12, 'BOA', 7, 1, 24000000, 25680000),
(13, 'BMOI', 8, 2, 53500000, 57780000),
(14, 'BNI', 10, 1, 18000000, 19800000),
(15, 'BFV-SG', 9, 2, 13000000, 14170000);

-- --------------------------------------------------------

--
-- Structure de la table `login_table`
--

DROP TABLE IF EXISTS `login_table`;
CREATE TABLE IF NOT EXISTS `login_table` (
  `login` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `sexe` varchar(50) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  `cin` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `login_table`
--

INSERT INTO `login_table` (`login`, `password`, `nom`, `prenom`, `sexe`, `tel`, `image`, `cin`) VALUES
('fahtia', 'fahtia', 'TIALALAINA', 'Fandresena', 'Masculin', '0345176541', 'C:\\Users\\FANDRESENA\\Desktop\\Capture1.PNG', '101251197029');

-- --------------------------------------------------------

--
-- Structure de la table `pret`
--

DROP TABLE IF EXISTS `pret`;
CREATE TABLE IF NOT EXISTS `pret` (
  `idPret` int(20) NOT NULL AUTO_INCREMENT,
  `cincand` varchar(12) NOT NULL,
  `NumBanque` int(12) NOT NULL,
  `Montant` bigint(255) NOT NULL,
  `MontantAPayer` bigint(255) NOT NULL,
  `datePret` date NOT NULL,
  PRIMARY KEY (`idPret`),
  KEY `NumBanquePret` (`NumBanque`),
  KEY `CinPret` (`cincand`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `pret`
--

INSERT INTO `pret` (`idPret`, `cincand`, `NumBanque`, `Montant`, `MontantAPayer`, `datePret`) VALUES
(15, '101251197029', 13, 50000000, 54000000, '2019-09-02'),
(16, '101251487541', 13, 3500000, 3780000, '2019-09-02'),
(17, '101315248652', 14, 18000000, 19800000, '2019-09-02'),
(18, '123654987485', 15, 5000000, 5450000, '2018-09-21'),
(19, '301251462579', 12, 24000000, 25680000, '2019-09-02'),
(20, '301256412251', 15, 8000000, 8720000, '2019-09-02');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `effectif`
--
ALTER TABLE `effectif`
  ADD CONSTRAINT `NumBanqueEffectif` FOREIGN KEY (`NumBanque`) REFERENCES `banque` (`NumBanque`);

--
-- Contraintes pour la table `pret`
--
ALTER TABLE `pret`
  ADD CONSTRAINT `CinPret` FOREIGN KEY (`cincand`) REFERENCES `client_table` (`cin`),
  ADD CONSTRAINT `NumBanquePret` FOREIGN KEY (`NumBanque`) REFERENCES `banque` (`NumBanque`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
