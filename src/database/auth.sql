/*check username exist*/
create or replace procedure check_ex_username(user_in varchar(50), out is_true boolean)
    language plpgsql
as
$$
begin
    select exists(select 1
                  from "user"
                  where username = user_in)
    into is_true;
end;
$$;

/*get password by username*/
create or replace function get_pass_by_username(user_in varchar(50))
    returns varchar(255)
    language plpgsql
as
$$
declare
    pass_out varchar(255);
begin
    select password
    into pass_out
    from "user"
    where username = user_in;
    return pass_out;
end;
$$;

/*get role by username*/
create or replace function get_role_by_username(user_in varchar(50))
    returns varchar(20)
    language plpgsql
as
$$
declare
    role_out varchar(20);
begin
    select role
    into role_out
    from "user"
    where username = user_in;
    return role_out;
end;
$$;

