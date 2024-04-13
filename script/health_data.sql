CREATE TABLE IF NOT EXISTS public.health_data
(
    health_data_id integer NOT NULL DEFAULT nextval('health_data_id_seq'::regclass),
    user_id integer NOT NULL,
    weight numeric NOT NULL,
    height numeric NOT NULL,
    steps integer NOT NULL,
    heart_rate integer NOT NULL,
    water_intake numeric NOT NULL,
    hours_of_sleep numeric NOT NULL,
    date date NOT NULL,
    CONSTRAINT health_data_pkey PRIMARY KEY (health_data_id),
    CONSTRAINT "health_data_user_id_FK" FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)

ALTER TABLE IF EXISTS public.health_data
    OWNER to postgres;