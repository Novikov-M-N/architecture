drop table if exists category_types cascade;
create table category_types (
    id                  int auto_increment,
    title               varchar(10),
    primary key(id)
);
insert into category_types
    (title) values
    ('INCOME'),
    ('EXPENSE');

drop table if exists categories cascade;
create table categories (
    id                  int auto_increment,
    title               varchar(255),
    type                int,
    primary key(id),
    foreign key(type) references category_types(id)
);
insert into categories
    (title, type) values
    ('Еда', 2),
    ('Бензин', 2),
    ('Домашние животные', 2),
    ('Зарплата основная', 1),
    ('Колымы', 1),
    ('Вклад', 1);

drop table if exists currencies cascade;
create table currencies (
    id                  int auto_increment,
    char_code            varchar(3),
    symbol              varchar(1),
    rate                numeric,
    primary key (id)
);
insert into currencies
    (char_code, symbol, rate) values
    ('RUR', '₽', 1),
    ('USD', '$', 74.65),
    ('EUR', '€', 89.95);

drop table if exists moneys cascade;
create table moneys (
    id                  bigserial,
    amount              numeric(8,2),
    currency            int,
    primary key(id),
    foreign key (currency) references currencies(id)
);

insert into moneys
    (amount, currency) values
    (100.25, 1),
    (120.11, 2),
    (61.23, 3);

drop table if exists financial_entries cascade;
create table financial_entries (
    id          bigserial,
    date        date,
    money       bigint,
    category    int,
    note        varchar(255),
    primary key(id),
    foreign key (category) references categories(id),
    foreign key (money) references moneys(id)
);