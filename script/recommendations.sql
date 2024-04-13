CREATE TABLE IF NOT EXISTS public.recommendations
(
    recommendation_id integer NOT NULL DEFAULT nextval('recommendations_recommendation_id_seq'::regclass),
    user_id integer NOT NULL,
    recommendation_text text COLLATE pg_catalog."default" NOT NULL,
    date date NOT NULL,
    CONSTRAINT recommendations_pkey PRIMARY KEY (recommendation_id),
    CONSTRAINT "recommendations_user_id_FK" FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)

ALTER TABLE IF EXISTS public.recommendations
    OWNER to postgres;