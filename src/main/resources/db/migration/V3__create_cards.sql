create TABLE IF NOT EXISTS cards
(
    id UUID default gen_random_uuid(),
    account_id UUID not NULL,
	card_type CHARACTER VARYING(20) NULL,
	max_debet DECIMAL(1000,2) NOT NULL,
	max_limit DECIMAL(1000,2) NOT NULL,
	name CHARACTER VARYING(100) NOT NULL,
	surname CHARACTER VARYING(100) NULL,
	CONSTRAINT cards_pkey PRIMARY KEY (id),
    CONSTRAINT cards_account_fkey FOREIGN KEY (account_id) REFERENCES accounts(id)
);