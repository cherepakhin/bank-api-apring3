--select * from client;
--insert into client(id,name,phone) values(0,'-','');
--insert into account(id,client_id,balance) values(0,0,0.00);
insert into client(id,name,phone) values(0,'NAME_0',''),(1,'NAME_1','');
insert into account(id,client_id,balance,name) values(1,0,100.00,'ACCOUNT_1'),(2,1,100.00,'ACCOUNT_2');
insert into operation(id,src_account_id,dst_account_id,amount,ddate) values (0,1,2,50.00,'2018-03-01');
insert into operation(id,src_account_id,dst_account_id,amount,ddate) values (1,2,1,150.00,'2018-03-10');
