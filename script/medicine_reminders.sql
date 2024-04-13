CREATE TABLE IF NOT EXISTS public.medicine_reminders
(
    reminder_id integer NOT NULL DEFAULT nextval('medicine_reminders_reminder_id_seq'::regclass),
    user_id integer NOT NULL,
    medicine_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    dosage character varying(50) COLLATE pg_catalog."default" NOT NULL,
    schedule character varying(100) COLLATE pg_catalog."default" NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    CONSTRAINT medicine_reminders_pkey PRIMARY KEY (reminder_id),
    CONSTRAINT "medicine_reminders_user_id_FK" FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)
ALTER TABLE IF EXISTS public.medicine_reminders
    OWNER to postgres;
