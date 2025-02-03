CREATE TABLE user(
   id BIGINT AUTO_INCREMENT,
   nom varchar(255),
    email varchar(255),
    password varchar(255), -- Déclaré comme UNSIGNED
    PRIMARY KEY(id)
   
);

INSERT into user (nom,email,password) VALUES ('marc','fandresenajoy@gmail.com','12345678');
INSERT into user (nom,email,password) VALUES ('hozalice','hozalice@gmail.com','12345678');
INSERT into user (nom,email,password) VALUES ('landy','landy@gmail.com','12345678');
INSERT into user (nom,email,password) VALUES ('jose','jose@gmail.com','12345678');
CREATE TABLE fond_user(
    Id_fond INT AUTO_INCREMENT,
   solde DECIMAL(15,2)  ,
    Id_utilisateur BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY(Id_fond),
    FOREIGN KEY(Id_utilisateur) REFERENCES user(id)
);


CREATE TABLE mvt_retrait_fond(
   Id_retrait INT AUTO_INCREMENT,
   solde DECIMAL(15,2)  ,
   daty DATETIME,
   etat BOOLEAN,
   Id_utilisateur BIGINT UNSIGNED NOT NULL,
   PRIMARY KEY(Id_retrait),
   FOREIGN KEY(Id_utilisateur) REFERENCES user(id)
);


CREATE TABLE mvt_depot_fond(
   Id_depot INT AUTO_INCREMENT,
   solde DECIMAL(15,2)  ,
   daty DATETIME,
   etat BOOLEAN,
   Id_utilisateur BIGINT UNSIGNED NOT NULL,
   PRIMARY KEY(Id_depot),
   FOREIGN KEY(Id_utilisateur) REFERENCES user(id)
);



ALTER TABLE mvt_retrait_fond
MODIFY COLUMN daty TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


ALTER TABLE mvt_depot_fond
MODIFY COLUMN daty TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

INSERT INTO fond_user (solde, Id_utilisateur) 
VALUES ('1000000', 1); 

INSERT INTO mvt_depot_fond (solde, etat, Id_utilisateur)
VALUES (10000, 0, 1); 
 

 //cryptomonaie

CREATE TABLE Cryptomonaie(
   Id_Cryptomonaie INT AUTO_INCREMENT,
   nom VARCHAR(50) ,
   cours DECIMAL(15,10)  ,
   PRIMARY KEY(Id_Cryptomonaie)
);


INSERT INTO Cryptomonaie (nom, cours) VALUES
('Bitcoin', 10000.002),
('Ethereum', 9000.987),
('Ripple', 9500.987),
('Litecoin', 9200.2222),
('Cardano', 10000.001),
('Polkadot', 9020.1),
('Chainlink', 9500.015),
('Bitcoin Cash', 9200.15),
('Dogecoin', 9900.1),
('Stellar', 9560);

CREATE TABLE CoursHistorique(
   Id_CoursHistorique INT AUTO_INCREMENT,
   idCrypto VARCHAR(50)  NOT NULL,
   datyUpdate DATETIME NOT NULL,
   cours DECIMAL(15,3)  ,
   PRIMARY KEY(Id_CoursHistorique)
);


CREATE TABLE crypto_user(
   Id_crypto INT AUTO_INCREMENT,
   nb_crypto DECIMAL(15,9)  ,
   Id_Cryptomonaie INT NOT NULL,
   Id_utilisateur BIGINT UNSIGNED NOT NULL,
   PRIMARY KEY(Id_crypto),
   FOREIGN KEY(Id_Cryptomonaie) REFERENCES Cryptomonaie(Id_Cryptomonaie),
   FOREIGN KEY(Id_utilisateur) REFERENCES user(id)
);



CREATE TABLE mvt_vendre_crypto(
   Id_vendre INT AUTO_INCREMENT,
   nb_crypto DECIMAL(15,7)  ,
   cours DECIMAL(15,2)  ,
      daty DATETIME,
   Id_Cryptomonaie INT NOT NULL,
   Id_utilisateur BIGINT UNSIGNED NOT NULL,
   PRIMARY KEY(Id_vendre),
   FOREIGN KEY(Id_Cryptomonaie) REFERENCES Cryptomonaie(Id_Cryptomonaie),
   FOREIGN KEY(Id_utilisateur) REFERENCES user(id)
);


CREATE TABLE mvt_achat_crypto(
   Id_achat INT AUTO_INCREMENT,
   nb_crypto DECIMAL(15,5)  ,
   cours DECIMAL(15,2)  ,
      daty DATETIME,
   Id_Cryptomonaie INT NOT NULL,
   Id_utilisateur BIGINT UNSIGNED NOT NULL,
   PRIMARY KEY(Id_achat),
   FOREIGN KEY(Id_Cryptomonaie) REFERENCES Cryptomonaie(Id_Cryptomonaie),
   FOREIGN KEY(Id_utilisateur) REFERENCES user(id)
);

ALTER TABLE mvt_vendre_crypto
MODIFY COLUMN daty TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


ALTER TABLE mvt_achat_crypto
MODIFY COLUMN daty TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
