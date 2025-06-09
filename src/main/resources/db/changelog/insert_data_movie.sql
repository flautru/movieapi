--liquibase formatted sql

--changeset fabien:insert-movies
INSERT INTO movie (title, director, genre, rating, release_year) VALUES
('Inception', 'Christopher Nolan', 'Sci-Fi', 8.8, 2010),
('SUPER MARIO BROS. LE FILM', 'Nitend', 'Enfant', 7.2, 2023),
('BARBIE', 'Jean Emanuelle', 'Comédie', 5.8, 2023),
('AVATAR : LA VOIE DE L''EAU', 'Sophie Cordier', 'Sci-Fi', 5.2, 2023),
('ASTERIX ET OBELIX ET L''EMPIRE DU MILIEU', 'Christophe Dechavagne', 'Comédie', 4.6, 2023),
('OPPENHEIMER', 'Margot Depoulas', 'Histoire', 8, 2023),
('LES GARDIENS DE LA GALAXIE : VOLUME 3', 'Gearge Lucas', 'Super-Hero', 7.6, 2023),
('LES TROIS MOUSQUETAIRES - D''ARTAGNAN', 'Michel Dépres', 'Comédie', 8.3, 2023),
('ELEMENTAIRE', 'Christopher Nolan', 'Action', 8.4, 2023),
('INDIANA JONES ET LE CADRAN DE LA DESTINEE', 'George Lucas', 'Aventure', 3.2, 2023),
('MISSION IMPOSSIBLE DEAD RECKONING PARTIE 1', 'Tom Cruse', 'Actien', 4.9, 2023),
('PAW PATROL : LA SUPER PATROUILLE - LE FILM', 'Super chien', 'Enfant', 10.0, 2023),
('CREED III', 'Rocky Balboa', 'Sport', 9.4, 2023);
