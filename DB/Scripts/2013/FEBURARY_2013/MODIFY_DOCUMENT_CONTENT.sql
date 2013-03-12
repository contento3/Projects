-- Changed the type of document_content from BLOG to MEDIUM BLOB
-- to allow files of size upto 16MB to be stored in the database.

ALTER TABLE DOCUMENT MODIFY COLUMN DOCUMENT_CONTENT MEDIUMBLOB;
