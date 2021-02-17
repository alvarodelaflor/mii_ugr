create table "data" (
                          "id" BIGSERIAL PRIMARY KEY,
                          "name" varchar not null
);

# --- !Downs

drop table "data" if exists;
