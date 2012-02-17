ALTER TABLE site_domain ADD CONSTRAINT site_site_domain FOREIGN KEY (site_id) REFERENCES site(site_id);
INSERT INTO site_domain (domain_name,site_id) VALUES ("www.market.org",3);
INSERT INTO site_domain (domain_name,site_id) VALUES ("www.contento.com",4);
INSERT INTO site_domain (domain_name,site_id) VALUES ("www.baazaar.com",3);
INSERT INTO site_domain (domain_name,site_id) VALUES ("www.baazaar.pk",3);