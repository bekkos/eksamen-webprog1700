CREATE TABLE Kunde (
    KId INTEGER(11) auto_increment,
    Fornavn VARCHAR(50),
    Etternavn VARCHAR(50),
    Adresse VARCHAR(50),
    Postnr VARCHAR(4),
    Telefonnr VARCHAR(8),
    Epost VARCHAR(50),
    PRIMARY KEY(Kid)
);

CREATE TABLE Poststed (
    Postnr VARCHAR(4),
    Poststed VARCHAR(50),
    PRIMARY KEY (PostNr)
);


CREATE TABLE Pakke (
    PId INTEGER(11) auto_increment,
    KId INTEGER(11),
    Volum DECIMAL(10,0),
    Vekt DECIMAL(10,0),
    PRIMARY KEY (PId),
    FOREIGN KEY (KId) REFERENCES Kunde(KId)
);

