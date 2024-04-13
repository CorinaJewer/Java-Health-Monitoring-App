CREATE TABLE IF NOT EXISTS public.doctors
(
    user_id integer NOT NULL,
    medical_license_number character varying(100) COLLATE pg_catalog."default" NOT NULL,
    specialization character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT doctors_pkey PRIMARY KEY (user_id),
    CONSTRAINT "doctors_users_id_FK" FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

ALTER TABLE IF EXISTS public.doctors
    OWNER to postgres;