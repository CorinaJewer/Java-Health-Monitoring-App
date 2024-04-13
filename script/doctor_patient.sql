CREATE TABLE IF NOT EXISTS public.doctor_patient
(
    doctor_id integer NOT NULL,
    patient_id integer NOT NULL,
    CONSTRAINT doctor_patient_pkey PRIMARY KEY (doctor_id, patient_id),
    CONSTRAINT "doctor_patient_doctor_id_FK" FOREIGN KEY (doctor_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT "doctor_patient_patient_id_FK" FOREIGN KEY (patient_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)
ALTER TABLE IF EXISTS public.doctor_patient
    OWNER to postgres;