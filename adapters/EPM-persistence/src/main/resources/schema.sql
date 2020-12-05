
CREATE TABLE IF NOT EXISTS Employees(
    empID INTEGER(5),
    name VARCHAR(45) NOT NULL,
    address VARCHAR(45) NOT NULL,
    type VARCHAR(45) NOT NULL,
    paymentMethod VARCHAR(45) NOT NULL,

    CONSTRAINT pk_EmpID PRIMARY KEY(EmpID)
);


CREATE TABLE IF NOT EXISTS SalariedClassification(
    empID int,
    salary DOUBLE,

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS CommissionClassification(
    empID int,
    salary DOUBLE,
    rate DOUBLE,

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS HourlyClassification(
    empID int,
    salary DOUBLE,

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TimeCards(
    empID int,
    tDate DATE ,
    hours DOUBLE,

    PRIMARY KEY(EmpID, tDate),
    FOREIGN KEY(EmpID) REFERENCES HourlyClassification(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Receipts(
    empID int,
    tDate DATE ,
    price DOUBLE,

    PRIMARY KEY(EmpID, tDate),
    FOREIGN KEY(EmpID) REFERENCES CommissionClassification(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS DirectDepositMethod(
    empID int,
    iban VARCHAR(45),
    bank VARCHAR(45),

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS MailMethod(
    empID int,
    email VARCHAR(45),

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID) ON DELETE CASCADE
);

