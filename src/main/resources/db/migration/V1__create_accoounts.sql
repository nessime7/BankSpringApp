create TABLE IF NOT EXISTS accounts
(
    id UUID default gen_random_uuid(),
    name CHARACTER VARYING(30) NOT NULL,
    balance DECIMAL(1000,2) NOT NULL,
    currency CHARACTER VARYING(4) NOT null,
    CONSTRAINT accounts_pkey PRIMARY KEY (id)
);