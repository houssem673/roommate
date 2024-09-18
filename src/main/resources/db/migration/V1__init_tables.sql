create table workspace
(
    id   serial primary key,
    name varchar(500),
    roomname varchar(500)
);

create table equipment
(
    id          serial primary key,
    workspace   integer references workspace (id),
    description text
);

CREATE TABLE booking
(
    id         SERIAL PRIMARY KEY,
    username   text,
    start_time time,
    end_time   time,
    booking_day date,
    workspace_id int
);

