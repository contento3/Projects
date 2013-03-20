--Adds NOT NULL constraints to the head, body, date_created and account_id fields.

--Converts the account_id fields from VARCHAR(45) to INT, and added FK constraint

ALTER TABLE article MODIFY head VARCHAR(45) NOT NULL;
ALTER TABLE article MODIFY body LONGTEXT NOT NULL;
ALTER TABLE article MODIFY date_created DATE NOT NULL;
ALTER TABLE article MODIFY account_id INT NOT NULL;
ALTER TABLE article ADD CONSTRAINT FK_ARTICLE_ACCOUNT FOREIGN KEY (account_id) REFERENCES account(account_id);
