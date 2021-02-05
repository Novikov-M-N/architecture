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
    charcode            varchar(3),
    symbol              varchar(1),
    primary key (charcode)
);
insert into currencies
    (charcode, symbol) values
    ('RUR', '₽');

drop table if exists financial_entries cascade;
create table financial_entries (
    id          bigserial,
    date        date,
    amount      int,
    category    int,
    note        varchar(255),
    primary key(id),
    foreign key (category) references categories(id)
);