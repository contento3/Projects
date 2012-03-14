ALTER TABLE template_directory ADD account_id INT(11) NOT NULL;
ALTER TABLE template_directory ADD FOREIGN KEY (account_id) REFERENCES account(account_id);