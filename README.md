## Mock de web empresarial

Proyecto de web de gestión interna empresarial que muestra información diferentes vistas: Una principal, una tabla con información de las oficinas y una última tabla con información de los empleados que permite agregar, editar o eliminar empleados.

## Desarrollo
El back está desarrollado en Java y SpringBoot siguiendo una arquitectura hexagonal para mejorar la escalabilidad y el mantenimiento. La base de datos usada es PostgreSQL en Neon.tech.
Para la autenticación he implementado el login mediante Firebase Authenticator para que las peticiones POST, PUT y DELETE solo puedan ser realizadas por usuarios logueados.
El front está desarrollado en Vue.js para crear un interfaz funcional que permite una visualización clara de los datos
El sistema se encuentre desplegado en Railway

Link a la web: [web](mock-webempresarial-production.up.railway.app)


