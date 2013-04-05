--- Change made to fix the the bug of sites not being listed once a page is added to it
--- caused by the absense of 'account_id' attribute in the category table.

ALTER TABLE CMS.CATEGORY ADD COLUMN ACCOUNT_ID INT NOT NULL DEFAULT 1;
ALTER TABLE CMS.CATEGORY ADD CONSTRAINT FOREIGN KEY(ACCOUNT_ID) REFERENCES ACCOUNT(ACCOUNT_ID);
