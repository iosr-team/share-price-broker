-- init roles ---------------------------------------------------------------
insert into role(id,name) values(nextval('hibernate_sequence'),'ROLE_USER');
insert into role(id,name) values(nextval('hibernate_sequence'),'ROLE_ADMIN');
insert into role(id,name) values(nextval('hibernate_sequence'),'ROLE_SUPERUSER');

-- init tenants ---------------------------------------------------------------
insert into tenant(id,name,enabled,description) values(nextval('hibernate_sequence'),'SUPERUSER_TENANT',true, 'Superuser'); --special tenant for superuser - TODO: is this a good solution or simply leave NULL for superuser?

insert into tenant(id,name,enabled,description) values(nextval('hibernate_sequence'),'AGH',true,'Akademia Gorniczo-Hutnicza');
insert into tenant(id,name,enabled,description) values(nextval('hibernate_sequence'),'UJ',true,'Uniwersytet Jagiellonski');
insert into tenant(id,name,enabled,description) values(nextval('hibernate_sequence'),'PK',true,'Politechnika Krakowska');

-- init stock indices ---------------------------------------------------------------
insert into STOCK_COMPANY (symbol, name) values ('GOOG', 'Google Inc.')
insert into STOCK_COMPANY (symbol, name) values ('NVDA', 'NVIDIA Corporation')


-- init users ---------------------------------------------------------------
insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'bartek.unknown@gmail.com',true, 'superuser', 'superuser', 'Jan', 'Kowalski',(select id from role where name = 'ROLE_SUPERUSER'), (select id from tenant where name = 'SUPERUSER_TENANT'));

insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'ala@example.com',true, 'ala', '123', 'Ala', 'Nowak',(select id from role where name = 'ROLE_ADMIN'), (select id from tenant where name = 'AGH'));
insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'ola@example.com',true, 'ola', '123', 'Ola', 'Kowalska',(select id from role where name = 'ROLE_USER'), (select id from tenant where name = 'AGH'));

insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'marcin@example.com',true, 'marcin', '123', 'Marcin', 'Wrog',(select id from role where name = 'ROLE_ADMIN'), (select id from tenant where name = 'UJ'));

-- init bindings ---------------------------------------------------------------------
insert into TENANT_STOCK_COMPANIES (STOCK_COMPANY_SYMBOL, TENANT_ID) values('GOOG',	(select id from tenant where name = 'AGH'))
insert into TENANT_STOCK_COMPANIES (STOCK_COMPANY_SYMBOL, TENANT_ID) values('NVDA',	(select id from tenant where name = 'AGH'))
insert into TENANT_STOCK_COMPANIES (STOCK_COMPANY_SYMBOL, TENANT_ID) values('GOOG',	(select id from tenant where name = 'UJ'))

-- sample stock quotes ---------------------------------------------------------------
insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL, value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'GOOG', 1200,	(timestamp 'now' - interval '180 minutes'), (select id from tenant where name = 'AGH'))
insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL, value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'GOOG', 1150, (timestamp 'now' - interval '135 minutes'), (select id from tenant where name = 'AGH'))
insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL, value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'GOOG', 1134, (timestamp 'now' - interval '90 minutes'), (select id from tenant where name = 'AGH'))
insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL, value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'GOOG', 110,	(timestamp 'now' - interval '45 minutes'), (select id from tenant where name = 'AGH'))
                                                 
insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL, value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'NVDA', 3200,	(timestamp 'now' - interval '180 minutes'), (select id from tenant where name = 'UJ'))
insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL, value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'NVDA', 3150,	(timestamp 'now' - interval '135 minutes'), (select id from tenant where name = 'UJ'))
insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL, value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'NVDA', 3134, (timestamp 'now' - interval '90 minutes'), (select id from tenant where name = 'UJ'))
insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL, value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'NVDA', 310, 	(timestamp 'now' - interval '45 minutes'), (select id from tenant where name = 'UJ'))
                                                 
--insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL, value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'NBP', 2200, 5, now(), (select id from stock_index where name = 'wig20'), (select id from tenant where name = 'PK'))
--insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'Mostostal', 2150, 17, now(), (select id from stock_index where name = 'wig20'), (select id from tenant where name = 'PK'))
--insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'Tymbark S.A.', 2134, 12, now(), (select id from stock_index where name = 'dow.jones'), (select id from tenant where name = 'PK'))
--insert into STOCK_QUOTE (id, STOCK_COMPANY_SYMBOL value, date, TENANT_ID) values (nextval('hibernate_sequence'), 'KGHM', 210, 7, now(), (select id from stock_index where name = 'dow.jones'), (select id from tenant where name = 'PK'))