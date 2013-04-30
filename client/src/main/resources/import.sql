-- init roles ---------------------------------------------------------------
insert into role(id,name) values(nextval('hibernate_sequence'),'ROLE_USER');
insert into role(id,name) values(nextval('hibernate_sequence'),'ROLE_ADMIN');
insert into role(id,name) values(nextval('hibernate_sequence'),'ROLE_SUPERUSER');

-- init tenants ---------------------------------------------------------------
insert into tenant(id,name,enabled,description) values(nextval('hibernate_sequence'),'SUPERUSER_TENANT',true, 'Superuser'); --special tenant for superuser - TODO: is this a good solution or simply leave NULL for superuser?

insert into tenant(id,name,enabled,description) values(nextval('hibernate_sequence'),'AGH',true,'Akademia Gorniczo-Hutnicza');
insert into tenant(id,name,enabled,description) values(nextval('hibernate_sequence'),'UJ',true,'Uniwersytet Jagiellonski');
insert into tenant(id,name,enabled,description) values(nextval('hibernate_sequence'),'PK',true,'Politechnika Krakowska');

-- init users ---------------------------------------------------------------
insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'bartek.unknown@gmail.com',true, 'superuser', 'superuser', 'Jan', 'Kowalski',(select id from role where name = 'ROLE_SUPERUSER'), (select id from tenant where name = 'SUPERUSER_TENANT'));
insert into users(id,email,enabled,login, password, name, surname, role_id, tenant_id) values(nextval('hibernate_sequence'),'ala@example.com',true, 'ala', '123', 'Ala', 'Nowak',(select id from role where name = 'ROLE_ADMIN'), (select id from tenant where name = 'AGH'));