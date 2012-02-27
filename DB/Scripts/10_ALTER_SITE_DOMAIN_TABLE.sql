ALTER TABLE site_domain ADD CONSTRAINT site_site_domain FOREIGN KEY (site_id) REFERENCES site(site_id);
