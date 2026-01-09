/*check email customer*/
create or replace procedure check_email_customer(email_in varchar(255), out is_exist boolean)
    language plpgsql
as
$$
begin
    select exists(select 1
                  from customer c
                  where c.email = email_in)
    into is_exist;
end;
$$;

/* add customer */
create or replace procedure add_customer(name_in varchar(100), phone_in varchar(20), email_in varchar(100),
                                         address_in varchar(255))
    language plpgsql
as
$$
begin
    insert into customer (name, phone, email, address) values (name_in, phone_in, email_in, address_in);
end;
$$;

/* display customer */
create or replace function get_all_customer()
    returns table
            (
                id      int,
                name    varchar(100),
                phone   varchar(20),
                email   varchar(100),
                address varchar(255)
            )
as
$$
begin
    return query select c.id, c.name, c.phone, c.email, c.address from customer c;
end;
$$ language plpgsql;