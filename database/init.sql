-- table partner --
create table if not exists partners (
    id serial primary key,
    trading_name varchar(255) not null,
    owner_name varchar(100) not null,
    document varchar(100) not null unique
);

-- table geography --
create table if not exists geo_data (
    id serial primary key,
    partner_id bigint not null references partners(id),
    type varchar(15),
    coordinates varchar(255)
);