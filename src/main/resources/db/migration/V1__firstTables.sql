CREATE TABLE tEstado (
	sigEst varchar(2) NOT NULL,
	nomEst varchar(255) NULL,
	PRIMARY KEY (sigEst)
);

CREATE TABLE tCidade (
	codCid integer NOT NULL AUTO_INCREMENT,
	nomCid varchar(255) NULL,
	sigEst varchar(255) NULL,
	PRIMARY KEY (codCid)
);

CREATE TABLE  tCliente(
	codCli integer NOT NULL AUTO_INCREMENT,
	nomCli varchar(255) NULL,
	datNas date,
	idtSex varchar(50),
	codCid varchar(255) NULL,
	PRIMARY KEY (codCli)
);


