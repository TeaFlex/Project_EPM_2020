CREATE TABLE IF NOT EXISTS Employees(
    EmpID INTEGER(5),
    nameEmp VARCHAR(45) NOT NULL,
    addressEmp VARCHAR(45) NOT NULL,
    paymentClass VARCHAR(45) NOT NULL,
    paymentMethod VARCHAR(45) NOT NULL,
    paymentSchedule VARCHAR(45) NOT NULL,

    CONSTRAINT pk_EmpID PRIMARY KEY(EmpID)
);