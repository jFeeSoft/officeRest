SET search_path = adm, public, pg_catalog;

DROP TABLE IF EXISTS AD_INFORMACJE_DODATKOWE CASCADE
;
DROP TABLE IF EXISTS AD_UZYTKOWNIK CASCADE
;
DROP TABLE IF EXISTS AD_ROLA CASCADE
;
DROP TABLE IF EXISTS AD_ROLA_UZYTKOWNIKA CASCADE
;
DROP TABLE IF EXISTS AD_UPRAWNIENIA CASCADE
;
DROP TABLE IF EXISTS AD_UPRAWNIENIA_ROLI CASCADE
;

CREATE TABLE AD_INFORMACJE_DODATKOWE ( 
	ID_INF_DOD bigint NOT NULL,    /* klucz g��wny tabeli */
	RELACJA varchar(50) NOT NULL,    /* wskazanie tabeli, kt�rej tycz� si� informacje */
	ID_POWIAZANIA integer NOT NULL,    /* identyfikator tabeli, do kt�rej utworzono informacje dodatkowe */
	NOTATKI text,    /* notatki u�ytkownika, dot. innych danych itp */
	ETYKIETY text,    /* etykiety nadane powi�zanym danym */
	ID_ZALACZNIKA integer    /* id za��cznika - sposob przechowywania plikow do ustalenia */
)
;
COMMENT ON TABLE AD_INFORMACJE_DODATKOWE
    IS 'dodatkowe informacja odno�nie u�ytkownika'
;
COMMENT ON COLUMN AD_INFORMACJE_DODATKOWE.ID_INF_DOD
    IS 'klucz g��wny tabeli'
;
COMMENT ON COLUMN AD_INFORMACJE_DODATKOWE.RELACJA
    IS 'wskazanie tabeli, kt�rej tycz� si� informacje'
;
COMMENT ON COLUMN AD_INFORMACJE_DODATKOWE.ID_POWIAZANIA
    IS 'identyfikator tabeli, do kt�rej utworzono informacje dodatkowe'
;
COMMENT ON COLUMN AD_INFORMACJE_DODATKOWE.NOTATKI
    IS 'notatki u�ytkownika, dot. innych danych itp'
;
COMMENT ON COLUMN AD_INFORMACJE_DODATKOWE.ETYKIETY
    IS 'etykiety nadane powi�zanym danym'
;
COMMENT ON COLUMN AD_INFORMACJE_DODATKOWE.ID_ZALACZNIKA
    IS 'id za��cznika - sposob przechowywania plikow do ustalenia'
;

CREATE TABLE AD_UZYTKOWNIK ( 
	ID_UZYTKOWNIKA integer NOT NULL,    /* identyfikator u�ytkownika */
	NAZWISKO varchar(100),    /* nazwisko u�ytkownika */
	IMIE varchar(30),    /* imi� u�ytkownika */
	PESEL varchar(11),    /* PESEL u�ytkownika */
	EMAIL varchar(80),    /* adres e-mail u�ytkownika */
	ID_STANOWISKA integer,    /* identyfikator stanowiska zajmowanego przez u�ytkownika */
	ID_KOMORKI integer,    /* Idetyfikator kom�rki organizacyjnej - sl_komorka_organizacyjna */
	ZABLOKOWANY char(1) DEFAULT 'N',    /* informacja czy dany u�ytkownik zosta� zablokowany */
	HASLO varchar(64)    /* Has�o zaszyfrowane */
)
;
COMMENT ON TABLE AD_UZYTKOWNIK
    IS 'Tabela zawieraj�ca dane u�ytkownik�w loguj�cych si� do systemu'
;
COMMENT ON COLUMN AD_UZYTKOWNIK.ID_UZYTKOWNIKA
    IS 'identyfikator u�ytkownika'
;
COMMENT ON COLUMN AD_UZYTKOWNIK.NAZWISKO
    IS 'nazwisko u�ytkownika'
;
COMMENT ON COLUMN AD_UZYTKOWNIK.IMIE
    IS 'imi� u�ytkownika'
;
COMMENT ON COLUMN AD_UZYTKOWNIK.PESEL
    IS 'PESEL u�ytkownika'
;
COMMENT ON COLUMN AD_UZYTKOWNIK.EMAIL
    IS 'adres e-mail u�ytkownika'
;
COMMENT ON COLUMN AD_UZYTKOWNIK.ID_STANOWISKA
    IS 'identyfikator stanowiska zajmowanego przez u�ytkownika'
;
COMMENT ON COLUMN AD_UZYTKOWNIK.ID_KOMORKI
    IS 'Idetyfikator kom�rki organizacyjnej - sl_komorka_organizacyjna'
;
COMMENT ON COLUMN AD_UZYTKOWNIK.ZABLOKOWANY
    IS 'informacja czy dany u�ytkownik zosta� zablokowany'
;
COMMENT ON COLUMN AD_UZYTKOWNIK.HASLO
    IS 'Has�o zaszyfrowane'
;

CREATE TABLE AD_ROLA ( 
	ID_ROLI integer NOT NULL,    /* idektyfikator roli */
	NAZWA_ROLI varchar(200)    /* nazwa roli */
)
;
COMMENT ON TABLE AD_ROLA
    IS 'tabela zawiera zdefiniowane role'
;
COMMENT ON COLUMN AD_ROLA.ID_ROLI
    IS 'idektyfikator roli'
;
COMMENT ON COLUMN AD_ROLA.NAZWA_ROLI
    IS 'nazwa roli'
;

CREATE TABLE AD_ROLA_UZYTKOWNIKA ( 
	ID_ROLI_UZYTKOWNIKA integer NOT NULL,    /* idektyfikator roli u�ytkownika */
	ID_UZYTKOWNIKA integer NOT NULL,    /* identyfikator u�ytkownika */
	ID_ROLI integer    /* idektyfikator roli */
)
;
COMMENT ON TABLE AD_ROLA_UZYTKOWNIKA
    IS 'tabela przechowuje informacje o rolach  u�ytkownika'
;
COMMENT ON COLUMN AD_ROLA_UZYTKOWNIKA.ID_ROLI_UZYTKOWNIKA
    IS 'idektyfikator roli u�ytkownika'
;
COMMENT ON COLUMN AD_ROLA_UZYTKOWNIKA.ID_UZYTKOWNIKA
    IS 'identyfikator u�ytkownika'
;
COMMENT ON COLUMN AD_ROLA_UZYTKOWNIKA.ID_ROLI
    IS 'idektyfikator roli'
;

CREATE TABLE AD_UPRAWNIENIA ( 
	ID_UPRAWNIENIA integer NOT NULL,    /* uprawnienia dla danej roli */
	NAZWA_UPRAWNIENIA varchar(150)
)
;
COMMENT ON TABLE AD_UPRAWNIENIA
    IS 's�ownik uprawnie�'
;
COMMENT ON COLUMN AD_UPRAWNIENIA.ID_UPRAWNIENIA
    IS 'uprawnienia dla danej roli'
;

CREATE TABLE AD_UPRAWNIENIA_ROLI ( 
	ID_UPR_ROLI integer NOT NULL,    /* uprawnienia dla danej roli */
	ID_ROLI integer,    /* idektyfikator roli */
	ID_UPRAWNIENIA integer,
	CZY_DOMYSLNE char(1),    /* informacja czy dane uprawnienie jest domy�lnie w��czone */
	CZY_WLACZONE char(1)    /* Informacja czy yzytkownik w��czy� dane uprawnienie dla zadanej roli */
)
;
COMMENT ON TABLE AD_UPRAWNIENIA_ROLI
    IS 'uprawnienia przypisane do roli'
;
COMMENT ON COLUMN AD_UPRAWNIENIA_ROLI.ID_UPR_ROLI
    IS 'uprawnienia dla danej roli'
;
COMMENT ON COLUMN AD_UPRAWNIENIA_ROLI.ID_ROLI
    IS 'idektyfikator roli'
;
COMMENT ON COLUMN AD_UPRAWNIENIA_ROLI.CZY_DOMYSLNE
    IS 'informacja czy dane uprawnienie jest domy�lnie w��czone'
;
COMMENT ON COLUMN AD_UPRAWNIENIA_ROLI.CZY_WLACZONE
    IS 'Informacja czy yzytkownik w��czy� dane uprawnienie dla zadanej roli'
;


ALTER TABLE AD_UZYTKOWNIK
	ADD CONSTRAINT UQ_AD_UZYTKOWNIK_ID_UZYTKOWNIKA UNIQUE (ID_UZYTKOWNIKA)
;
ALTER TABLE AD_ROLA
	ADD CONSTRAINT UQ_AD_ROLA_ID_ROLI UNIQUE (ID_ROLI)
;
ALTER TABLE AD_ROLA_UZYTKOWNIKA
	ADD CONSTRAINT UQ_AD_ROLA_UZYTKOWNIKA_ID_ROLI_UZYTKOWNIKA UNIQUE (ID_ROLI_UZYTKOWNIKA)
;
ALTER TABLE AD_UPRAWNIENIA
	ADD CONSTRAINT UQ_AD_UPRAWNIENIA_ID_UPRAWNIENIA UNIQUE (ID_UPRAWNIENIA)
;
ALTER TABLE AD_INFORMACJE_DODATKOWE ADD CONSTRAINT PK_AD_INFORMACJE_DODATKOWE 
	PRIMARY KEY (ID_INF_DOD)
;


ALTER TABLE AD_UZYTKOWNIK ADD CONSTRAINT PK_AD_UZYTKOWNIK 
	PRIMARY KEY (ID_UZYTKOWNIKA)
;


ALTER TABLE AD_ROLA ADD CONSTRAINT PK_AD_ROLA 
	PRIMARY KEY (ID_ROLI)
;


ALTER TABLE AD_ROLA_UZYTKOWNIKA ADD CONSTRAINT PK_AD_ROLA_UZYTKOWNIKA 
	PRIMARY KEY (ID_ROLI_UZYTKOWNIKA)
;


ALTER TABLE AD_UPRAWNIENIA ADD CONSTRAINT PK_AD_UPRAWNIENIA 
	PRIMARY KEY (ID_UPRAWNIENIA)
;




ALTER TABLE AD_ROLA_UZYTKOWNIKA ADD CONSTRAINT FK_AD_ROLA_UZYTKOWNIKA_AD_ROLA 
	FOREIGN KEY (ID_ROLI) REFERENCES AD_ROLA (ID_ROLI)
;

ALTER TABLE AD_ROLA_UZYTKOWNIKA ADD CONSTRAINT FK_AD_ROLA_UZYTKOWNIKA_AD_UZYTKOWNIK 
	FOREIGN KEY (ID_UZYTKOWNIKA) REFERENCES AD_UZYTKOWNIK (ID_UZYTKOWNIKA)
;

ALTER TABLE AD_UPRAWNIENIA_ROLI ADD CONSTRAINT FK_AD_UPRAWNIENIA_AD_ROLA 
	FOREIGN KEY (ID_ROLI) REFERENCES AD_ROLA (ID_ROLI)
;

ALTER TABLE AD_UPRAWNIENIA_ROLI ADD CONSTRAINT FK_AD_UPRAWNIENIA_ROLI_AD_UPRAWNIENIA 
	FOREIGN KEY (ID_UPRAWNIENIA) REFERENCES AD_UPRAWNIENIA (ID_UPRAWNIENIA)
;
