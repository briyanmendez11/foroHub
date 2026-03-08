CREATE TABLE perfiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuarios_perfiles (
   usuario_id BIGINT NOT NULL,
   perfil_id BIGINT NOT NULL,

   PRIMARY KEY (usuario_id, perfil_id),

   CONSTRAINT fk_usuario
       FOREIGN KEY (usuario_id)
           REFERENCES usuarios(id),

   CONSTRAINT fk_perfil
       FOREIGN KEY (perfil_id)
           REFERENCES perfiles(id)
);