insert into role(roleid,rolename) values(nextval('hibernate_sequence'),'ROLE_USER');
insert into role(roleid,rolename) values(nextval('hibernate_sequence'),'ROLE_MANAGER');
insert into users(userid,emailid,enabled,password,username,role_roleid) values(nextval('hibernate_sequence'),'admin@expense.com',true,'admin','admin',2);