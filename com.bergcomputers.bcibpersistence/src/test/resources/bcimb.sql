drop database bcib;
drop user bcib;
create database bcib;
CREATE USER 'bcib'@'localhost' IDENTIFIED BY 'bcib';
GRANT ALL ON bcib.* TO bcib@'localhost' IDENTIFIED BY 'bcib';

USE bcib;

DROP TABLE IF EXISTS TransactionTbl;
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS Device;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS Currency;
DROP TABLE IF EXISTS Beneficiary;
DROP TABLE IF EXISTS BaseEntity;

CREATE TABLE BaseEntity(id BIGINT NOT NULL AUTO_INCREMENT, version INTEGER DEFAULT 0, deleted BIT DEFAULT 0, creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
dtype VARCHAR(31) DEFAULT 'BaseEntity',
PRIMARY KEY (id));

CREATE TABLE Beneficiary(id BIGINT,iban VARCHAR(255),name VARCHAR(255),details VARCHAR(255),accountholder VARCHAR(255),
FOREIGN KEY (id)
	REFERENCES BaseEntity(id)
	ON DELETE CASCADE);

CREATE TABLE Currency(id BIGINT,symbol VARCHAR(10),exchangerate DOUBLE,
FOREIGN KEY (id)
	REFERENCES BaseEntity(id)
	ON DELETE CASCADE);

CREATE TABLE Role (id BIGINT NOT NULL, name VARCHAR(255),
FOREIGN KEY (id)
REFERENCES BaseEntity(id)
ON DELETE CASCADE
);



CREATE TABLE Customer(id BIGINT NOT NULL, roleid BIGINT NOT NULL, firstName VARCHAR(255), lastName VARCHAR(255), login VARCHAR(255), password VARCHAR(255),
FOREIGN KEY (id)
REFERENCES BaseEntity(id)
ON DELETE CASCADE,
FOREIGN KEY (roleid)
REFERENCES Role(id)
ON DELETE CASCADE
);

CREATE TABLE Device(id BIGINT NOT NULL, deviceid BIGINT NOT NULL, name VARCHAR(255), customerid BIGINT NOT NULL,
FOREIGN KEY (id)
REFERENCES BaseEntity(id)
ON DELETE CASCADE,
FOREIGN KEY (customerid)
REFERENCES Customer(id)
ON DELETE CASCADE
);



CREATE TABLE Account (id BIGINT NOT NULL, customerid BIGINT NOT NULL, currencyid BIGINT NOT NULL, iban VARCHAR(255), amount DOUBLE,
FOREIGN KEY (id)
REFERENCES BaseEntity(id)
ON DELETE CASCADE,
FOREIGN KEY (customerid)
REFERENCES Customer(id)
ON DELETE CASCADE,
FOREIGN KEY (currencyid)
REFERENCES Currency(id)
);

CREATE TABLE TransactionTbl (id BIGINT NOT NULL, accountid BIGINT NOT NULL, date TIMESTAMP, type VARCHAR(255), amount DOUBLE, sender VARCHAR(255), details VARCHAR(255), status VARCHAR(255),
FOREIGN KEY (id)
        REFERENCES BaseEntity(id)
        ON DELETE CASCADE,
FOREIGN KEY (accountid)
        REFERENCES Account(id)
        ON DELETE CASCADE
);

insert into BaseEntity (dtype) values('Currency');
insert into Currency values(LAST_INSERT_ID(), 'EUR', 1.0);
insert into BaseEntity (dtype) values('Role');
insert into Role values(LAST_INSERT_ID(), 'customer');
insert into BaseEntity (dtype) values('Role');
insert into Role values(LAST_INSERT_ID(), 'employee');
insert into BaseEntity(dtype) values('Customer');
insert into Customer values(LAST_INSERT_ID(), 3, 'Ion', 'Popescu','ionp','ionp');
insert into BaseEntity(dtype) values('Customer');
insert into Customer values(LAST_INSERT_ID(), 2, 'Alex', 'Ionescu','alexi','alexi');

insert into BaseEntity (dtype) values('Account');
insert into Account values(LAST_INSERT_ID(), 4, 1, 'ROBUC012345', 100.0);
insert into BaseEntity (dtype) values('Account');
insert into Account values(LAST_INSERT_ID(), 4, 1, 'ROBUC012346', 500.0);
insert into BaseEntity (dtype) values('Account');
insert into Account values(LAST_INSERT_ID(), 4, 1, 'ROBUC012347', 800.0);
insert into BaseEntity (dtype) values('Account');
insert into Account values(LAST_INSERT_ID(), 4, 1, 'ROBUC012348', 900.0);
insert into BaseEntity (dtype) values('Account');
insert into Account values(LAST_INSERT_ID(), 4, 1, 'ROTIM012345', 960.0);
insert into BaseEntity (dtype) values('Account');
insert into Account values(LAST_INSERT_ID(), 4, 1, 'ROTIM012349', 100.0);
insert into BaseEntity (dtype) values('Account');
insert into Account values(LAST_INSERT_ID(), 4, 1, 'ROTIM012346', 300.0);

insert into BaseEntity (dtype) values('Transaction');
insert into TransactionTbl values(LAST_INSERT_ID(),7, '2014-10-21T10:30:00', 'PAYMENT', 200.0, 'Alex Ion', 'Plata factura 23434', 'NEW');
insert into BaseEntity (dtype) values('Transaction');
insert into TransactionTbl values(LAST_INSERT_ID(),7, '2014-10-22T11:00:00', 'CREDIT', 1000.0, 'bCOMPUTERS', 'Plata salar', 'NEW');
insert into BaseEntity (dtype) values('Beneficiary');
insert into Beneficiary values(LAST_INSERT_ID(), 'ROBUC012345', 'plata curent', 'detalii ordin de plata pt factura lunara de curent', 'ENEL Energie SA');
insert into BaseEntity (dtype) values('Beneficiary');
insert into Beneficiary values(LAST_INSERT_ID(), 'ROBUC012346', 'plata rata casa', 'detalii ordin de plata pt rata la casa', 'BCR');


/*
insert into BaseEntity values();
select @currencyid=LAST_INSERT_ID();
insert into Currency values(@currencyid, 'EUR', 1.0);
insert into BaseEntity values();
insert into Role values(LAST_INSERT_ID(), 'customer');
insert into BaseEntity values();
select LAST_INSERT_ID();
select @roleid=LAST_INSERT_ID();
insert into Role values(@roleid, 'employee');

insert into BaseEntity values();
select @custid=LAST_INSERT_ID();
insert into Customer values(@custid, @roleid, 'CustomerFirst', 'CustomerLast','login','password');
insert into BaseEntity values();
select @accountid=LAST_INSERT_ID();
insert into Account values(@accountid, @custid, currencyid, 'ROBUC012345', 100.0);
insert into BaseEntity values();
select @transid=LAST_INSERT_ID();
insert into Transaction values(@transid,@accountid, '2014-10-21', 'PAYMENT', 200.0, 'Customer Name', 'Plata factura 23434', 'NEW');

*/
