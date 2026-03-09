INSERT INTO usuarios (nombre, correo_electronico, contrasena)
VALUES (
        'Admin',
        'admin@foro.com',
        '$2a$10$tH/QbcLVrM/TQM5H7yisgu2JAXCZnuBFk9zUNwh4UVqkET9WVBGRS'
       );

INSERT INTO usuarios_perfiles (usuario_id, perfil_id)
VALUES (1, 1);