
create table if not exists client(
    id bigint not null auto_increment,
    name varchar(255) not null,
    phone varchar(13) not null,
    primary key(id)
);

create table if not exists account(
    id bigint not null auto_increment,
    client_id bigint not null,
    name varchar(255) not null,
    balance numeric(19,2),
    primary key(id),
    constraint fk_client_account foreign key(client_id) references client(id)
        on update no action on delete cascade
);


create table if not exists operation(
    id bigint not null auto_increment,
    ddate date,
    src_account_id bigint not null,
    dst_account_id bigint not null,
    amount numeric(19,2),
    comment varchar(255) default '',
    primary key(id),
    constraint fk_src_account_operation foreign key(src_account_id) references account(id)
        on update no action on delete cascade,
    constraint fk_dst_account_operation foreign key(dst_account_id) references account(id)
        on update no action on delete cascade
);
