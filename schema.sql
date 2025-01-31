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

ALTER TABLE mvt_retrait_fond
MODIFY COLUMN daty TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

INSERT INTO fond_user (solde, Id_utilisateur) 
VALUES ('100000', 1); 

INSERT INTO mvt_retrait_fond (solde, etat, Id_utilisateur)
VALUES (10000, 0, 1); 