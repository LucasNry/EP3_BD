CREATE TABLE chefemilitar 
(
	codigo_chef text PRIMARY KEY,
	faixa text,
	nroDivisao integer,
	codigog text,
	nomel text NOT NULL 
)

CREATE TABLE liderpolitico 
(
	nomel text,
	codigog text,
	apoios text,
	PRIMARY KEY(nomel, codigog)
)

CREATE TABLE grupoarmado 
(
	codigog text PRIMARY KEY,
	nomegrupo text,
	numbaixasg integer
)

CREATE TABLE divisao 
(
	nrodivisao integer,
	codigog text,
	numbaixasd integer,
	barcos integer,
	tanques integer,
	avioes integer,
	homens integer,
	PRIMARY KEY(nrodivisao, codigog)
)

CREATE TABLE organizacaoM 
(
	codigoorg text PRIMARY KEY,
	nomeorg text,
	tipo text,
	tipoajuda text,
	orglider text,
	numpessoas integer
)

CREATE TABLE conflito 
(
	codconflito text PRIMARY KEY,
	nome text,
	tipoconf text,
	numferidos integer,
	nummortos integer
)

CREATE TABLE tipoarma 
(
	nomearma text PRIMARY KEY,
	indicador integer
)

CREATE TABLE fornece 
(
	codigog text,
	nomearma text,
	nometraf text,
	numarmas integer,
	PRIMARY KEY (codigog, nomearma, nometraf)
)

CREATE TABLE confrelig 
(
	codconflito text,
	religiao text,
	PRIMARY KEY (codconflito, religiao)
)

CREATE TABLE dialoga 
(
	nomel text,
	codigog text,
	codigoorg text,
	PRIMARY KEY (nomel, codigog, codigoorg)
)

CREATE TABLE entpart 
(
	codigog text,
	codconflito text,
	degrupo date,
	PRIMARY KEY (codigog, codconflito, degrupo)
)

CREATE TABLE saidapart 
(
	codigog text,
	codconflito text,
	dsgrupo date,
	PRIMARY KEY (codigog, codconflito, dsgrupo)
)

CREATE TABLE saidamed
(
	codigog text,
	codconflito text,
	dsmedia date,
	PRIMARY KEY (codigog, codconflito, dsmedia)
)

CREATE TABLE entradamed
(
	codigog text,
	codconflito text,
	demedia date,
	PRIMARY KEY (codigog, codconflito, demedia)
)

CREATE TABLE territorial
(
	codconflito text,
	regiao text,
	PRIMARY KEY (codconflito, regiao)
)

CREATE TABLE conflitopais
(
	codconflito text,
	pais text,
	PRIMARY KEY (codconflito, pais)
)

CREATE TABLE confecon
(
	codconflito text,
	matprima text,
	PRIMARY KEY (codconflito, matprima)
)

CREATE TABLE confetnia
(
	codconflito text,
	etnia text,
	PRIMARY KEY (codconflito, etnia)
)

CREATE TABLE podefornecer
(
	nomearma text,
	nometraf text,
	quantidade integer,
	PRIMARY KEY (nomearma, nometraf)
)
