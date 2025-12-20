-- =========================================
-- 001-create-specializations
-- =========================================
create table if not exists specializations (
                                               spec_id      uuid primary key not null,
                                               name         varchar(120) not null unique,
    description  text,
    created_at   timestamp not null,
    updated_at   timestamp
    );

-- =========================================
-- 002-create-users
-- =========================================
create table if not exists users (
                                     user_id            uuid primary key not null,
                                     login              varchar(100) not null unique,
    password_hash      varchar(255) not null,
    role               varchar(50) not null,
    first_name         varchar(100) not null,
    last_name          varchar(100) not null,
    email              varchar(200) not null,
    phone              varchar(30),
    birth_date         date,
    sex                varchar(50),
    specialization_id  uuid,
    office             varchar(50),
    work_hours         varchar(100),
    created_at         timestamp not null,
    updated_at         timestamp,
    is_active          boolean not null
    );

alter table users
    add constraint fk_users_specialization
        foreign key (specialization_id)
            references specializations (spec_id);

create index if not exists idx_users_specialization_id
    on users (specialization_id);

-- =========================================
-- 003-create-timeslots
-- =========================================
create table if not exists timeslots (
                                         slot_id     uuid primary key not null,
                                         doctor_id   uuid not null,
                                         start_time  timestamp not null,
                                         end_time    timestamp not null,
                                         state       varchar(50) not null,
    is_blocked  boolean not null,
    created_at  timestamp not null,
    updated_at  timestamp
    );

alter table timeslots
    add constraint fk_timeslots_doctor
        foreign key (doctor_id)
            references users (user_id);

create index if not exists idx_timeslots_doctor_id
    on timeslots (doctor_id);

create index if not exists idx_timeslots_start_time
    on timeslots (start_time);

-- =========================================
-- 004-create-appointments
-- =========================================
create table if not exists appointments (
                                            appointment_id       uuid primary key not null,
                                            patient_id           uuid not null,
                                            doctor_id            uuid not null,
                                            slot_id              uuid,
                                            start_at             timestamp not null,
                                            duration_min         int not null,
                                            status               varchar(50) not null,
    description          text,
    cancellation_reason  varchar(250),
    notification_sent    boolean not null,
    created_at           timestamp not null,
    updated_at           timestamp,
    canceled_at          timestamp
    );

alter table appointments
    add constraint fk_appointments_patient
        foreign key (patient_id)
            references users (user_id);

alter table appointments
    add constraint fk_appointments_doctor
        foreign key (doctor_id)
            references users (user_id);

alter table appointments
    add constraint fk_appointments_slot
        foreign key (slot_id)
            references timeslots (slot_id);

create index if not exists idx_appointments_patient_id
    on appointments (patient_id);

create index if not exists idx_appointments_doctor_id
    on appointments (doctor_id);

create index if not exists idx_appointments_slot_id
    on appointments (slot_id);

create index if not exists idx_appointments_start_at
    on appointments (start_at);

-- =========================================
-- 005-create-diagnosis-attachments
-- =========================================
create table if not exists diagnosis_attachments (
                                                     attachment_id   uuid primary key not null,
                                                     appointment_id  uuid not null,
                                                     record_id       uuid,
                                                     text            text not null,
                                                     created_at      timestamp not null,
                                                     updated_at      timestamp
);

alter table diagnosis_attachments
    add constraint fk_diag_attach_appointment
        foreign key (appointment_id)
            references appointments (appointment_id);

alter table diagnosis_attachments
    add constraint uq_diag_attach_appointment_id
        unique (appointment_id);

alter table diagnosis_attachments
    add constraint fk_diag_attach_parent
        foreign key (record_id)
            references diagnosis_attachments (attachment_id);

create index if not exists idx_diag_attach_record_id
    on diagnosis_attachments (record_id);
