--ALTER TABLE TO ADD FORIEGNKEY TO PAGE_TEMPLATE_ASSOCIATION

ALTER TABLE CMS.PAGE_CATEGORY_ASSOCIATION
ADD CONSTRAINT FK_CATEGORY_ID 
FOREIGN KEY (CATEGORY_ID) 
REFERENCES CATEGORY(CATEGORY_ID),
ADD CONSTRAINT FK_PAGE_ID 
FOREIGN KEY (PAGE_ID) 
REFERENCES PAGE(PAGE_ID);