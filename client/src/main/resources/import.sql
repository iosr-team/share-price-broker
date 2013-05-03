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
insert into STOCK_INDEX (id, name, description) values (nextval('hibernate_sequence'), 'wig', 'WIG')
insert into STOCK_INDEX (id, name, description) values (nextval('hibernate_sequence'), 'wig20', 'WIG20')
insert into STOCK_INDEX (id, name, description) values (nextval('hibernate_sequence'), 'dax', 'DAX')
insert into STOCK_INDEX (id, name, description) values (nextval('hibernate_sequence'), 'dow.jones', 'Dow Jones')

-- init users ---------------------------------------------------------------
insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'bartek.unknown@gmail.com',true, 'superuser', 'superuser', 'Jan', 'Kowalski',(select id from role where name = 'ROLE_SUPERUSER'), (select id from tenant where name = 'SUPERUSER_TENANT'));

insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'ala@example.com',true, 'ala', '123', 'Ala', 'Nowak',(select id from role where name = 'ROLE_ADMIN'), (select id from tenant where name = 'AGH'));
insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'ola@example.com',true, 'ola', '123', 'Ola', 'Kowalska',(select id from role where name = 'ROLE_USER'), (select id from tenant where name = 'AGH'));

insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'marcin@example.com',true, 'marcin', '123', 'Marcin', 'Wrog',(select id from role where name = 'ROLE_ADMIN'), (select id from tenant where name = 'UJ'));

-- sample stock quotes ---------------------------------------------------------------
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'PKO', 1200, 5, now(), (select id from stock_index where name = 'wig'), (select id from tenant where name = 'AGH'))
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'KGHM', 1150, 17, now(), (select id from stock_index where name = 'wig'), (select id from tenant where name = 'AGH'))
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'PZU', 1134, 12, now(), (select id from stock_index where name = 'wig20'), (select id from tenant where name = 'AGH'))
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'ASSECO', 110, 7, now(), (select id from stock_index where name = 'dow.jones'), (select id from tenant where name = 'AGH'))

insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'IBM', 3200, 5, now(), (select id from stock_index where name = 'dax'), (select id from tenant where name = 'UJ'))
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'Google', 3150, 17, now(), (select id from stock_index where name = 'dax'), (select id from tenant where name = 'UJ'))
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'Microsoft', 3134, 12, now(), (select id from stock_index where name = 'dow.jones'), (select id from tenant where name = 'UJ'))
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'PKO', 310, 7, now(), (select id from stock_index where name = 'dow.jones'), (select id from tenant where name = 'UJ'))

insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'NBP', 2200, 5, now(), (select id from stock_index where name = 'wig20'), (select id from tenant where name = 'PK'))
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'Mostostal', 2150, 17, now(), (select id from stock_index where name = 'wig20'), (select id from tenant where name = 'PK'))
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'Tymbark S.A.', 2134, 12, now(), (select id from stock_index where name = 'dow.jones'), (select id from tenant where name = 'PK'))
insert into STOCK_QUOTE (id, companyName, value, change, date, STOCK_INDEX_ID, TENANT_ID) values (nextval('hibernate_sequence'), 'KGHM', 210, 7, now(), (select id from stock_index where name = 'dow.jones'), (select id from tenant where name = 'PK'))