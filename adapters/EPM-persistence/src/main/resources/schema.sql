CREATE TABLE IF NOT EXISTS Employees(
    EmpID INTEGER(5),
    nameEmp VARCHAR(45) NOT NULL,
    addressEmp VARCHAR(45) NOT NULL,
    paymentClassification VARCHAR(45) NOT NULL,
    paymentMethod VARCHAR(45) NOT NULL,
    paymentSchedule VARCHAR(45) NOT NULL,

    CONSTRAINT pk_EmpID PRIMARY KEY(EmpID)
);


CREATE TABLE IF NOT EXISTS SalariedClassification(
    EmpID int,
    salary DOUBLE,

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID)
);

CREATE TABLE IF NOT EXISTS CommissionClassification(
    EmpID int,
    salary DOUBLE,
    rate DOUBLE,

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID)
);

CREATE TABLE IF NOT EXISTS HourlyClassification(
    EmpID int,
    salary DOUBLE,

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID)
);

CREATE TABLE IF NOT EXISTS TimeCards(
    EmpID int,
    tDate DATE ,
    hours DOUBLE,

    PRIMARY KEY(EmpID, tDate),
    FOREIGN KEY(EmpID) REFERENCES HourlyClassification(EmpID)
);

CREATE TABLE IF NOT EXISTS Receipts(
    EmpID int,
    tDate DATE ,
    price DOUBLE,

    PRIMARY KEY(EmpID, tDate),
    FOREIGN KEY(EmpID) REFERENCES CommissionClassification(EmpID)
);