create table clients
(
    client_id    serial primary key,
    username     varchar(20) not null,
    name         varchar(20) not null,
    last_name    varchar(20),
    created_date timestamp   not null default now(),
    is_active    bool        not null default true
);

create table accounts
(
    id             serial primary key,
    account_number varchar(19) unique not null,
    balance        numeric               default 0.00,
    client_id      INTEGER            not null,
    account_type varchar(15) not null,
    is_active      bool               not null default true,
    created_date   timestamp          not null default now()
);

alter table accounts
    add constraint fk_client
        foreign key (client_id) references clients (client_id);

create table transactions
(
    id  UUID primary key,
    from_account_number varchar(19) not null,
    receiver_account_number   varchar(19) not null,
    amount              numeric        default 0.00,
    client_id           INTEGER     not null,
    status varchar(15) not null,
    created_date        timestamp   not null default now()
);

alter table transactions
    add constraint fk_from_account
        foreign key (from_account_number) references accounts (account_number);

alter table transactions
    add constraint fk_to_account
        foreign key (receiver_account_number) references accounts (account_number);

create table entry
(
    entry_id   serial primary key,
    account_number varchar(19)   not null,
    type     varchar(20)   not null,
    amount    numeric not null default 0.00,
    transaction_id   UUID          not null,
    created_date   timestamp     not null default now()
);
alter table entry
    add constraint fk_entry_account
        foreign key (account_number) references accounts (account_number);

alter table entry
    add constraint fk_transaction_id
        foreign key (transaction_id) references transactions (id);

create table pending_funds
(
    id serial primary key,
    transaction_id   UUID          not null,
    account_number varchar(19) not null,
    amount numeric not null default 0.00,
    is_pending bool not null default true,
    created_date   timestamp     not null default now()
);

alter table pending_funds
    add constraint fk_pend_fund_account
        foreign key (account_number) references accounts (account_number);

alter table pending_funds
    add constraint fk_transaction_id
        foreign key (transaction_id) references transactions (id);