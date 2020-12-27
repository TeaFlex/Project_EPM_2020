
CREATE TABLE IF NOT EXISTS Employees(
    empID SERIAL,
    name VARCHAR(45) NOT NULL,
    address VARCHAR(45) NOT NULL,
    paymentClassification VARCHAR(45) NOT NULL,
    paymentSchedule VARCHAR(45) NOT NULL,
    paymentMethod VARCHAR(45) NOT NULL,
    CHECK (empID>=0),

    CONSTRAINT pk_EmpID PRIMARY KEY(EmpID)
);


CREATE TABLE IF NOT EXISTS SalariedClassification(
    empID int,
    salary decimal,

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS CommissionClassification(
    empID int,
    salary decimal,
    rate decimal,

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS HourlyClassification(
    empID int,
    salary decimal,

    PRIMARY KEY(EmpID),
    FOREIGN KEY(EmpID) REFERENCES Employees(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TimeCards(
    empID int,
    tDate DATE ,
    hours decimal,

    PRIMARY KEY(EmpID, tDate),
    FOREIGN KEY(EmpID) REFERENCES HourlyClassification(EmpID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Receipts(
    empID int,
    tDate DATE ,
    price decimal,

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

