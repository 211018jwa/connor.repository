DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Account;

CREATE TABLE Client (

	client_id SERIAL PRIMARY KEY,
	client_first_name VARCHAR(255) NOT NULL,
	client_last_name VARCHAR(255) NOT NULL,
	client_age INTEGER NOT NULL,
	client_address VARCHAR(255) NOT NULL,
	client_phone_number VARCHAR(255) NOT NULL
);

INSERT INTO Client
	(client_first_name, client_last_name, client_age, client_address, client_phone_number)
VALUES 
	('Albert', 'Kindle', 23, '234 Rivers Ave', '(232) 449-5645'),
	('Taylor', 'Bridgewater', 27, '992 Park Sq', '(444) 121-0388');

SELECT *
FROM Client;

CREATE TABLE Account (

	account_id SERIAL PRIMARY KEY,
	account_number VARCHAR(255) NOT NULL,
	account_type VARCHAR(255) NOT NULL,
	account_amount NUMERIC NOT NULL,
	client_id INTEGER NOT NULL,
	
	CONSTRAINT fk_client FOREIGN KEY(client_id)
		REFERENCES Client(client_id)
);

INSERT INTO Account
	(account_number, account_type, account_amount, client_id)
VALUES
	('2399410065', 'Checking', 1245.87, 1),
	('2399410066', 'Savings', 10234.94, 1),
	('2343665322', 'Savings', 2319.21, 2);

SELECT *
FROM Account;

SELECT *
FROM client
WHERE client_id = 1;

UPDATE Client
	SET client_first_name = 'Courteny',
	    client_last_name = 'Rivers',
	    client_age = 24,
	    client_address = '3454 Lake Johns',
	    client_phone_number = '(123) 535-9929'
WHERE 
client_id = 2;

DELETE FROM Client 
WHERE client_id = 3;

SELECT *
FROM Account
WHERE account_id = 1 AND client_id = 1;

UPDATE Account
	SET account_number= '1990226612',
	    account_type = 'Checking',
	    account_amount = 3456.78
WHERE 
account_id = 2 AND client_id = 2;

DELETE FROM Account 
WHERE account_id = 2 AND client_id = 2;