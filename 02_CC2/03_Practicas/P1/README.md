<div class="stackedit__html"><p><strong>Master profesional Ingeniería Informática – Universidad de Granada</strong><br>
<em>Curso 2020-2021</em></p>
<p>Sesión 1 - Práctica -&gt;  <strong>Cloud Computing: servicios y aplicaciones</strong></p>
<p>Profesor de prácticas: Manuel J. Parra Royón<br>
E-mail: <a href="mailto:manuelparra@decsai.ugr.es">manuelparra@decsai.ugr.es</a><br>
Twitter: <a href="http://twitter.com/manugrapevine">twitter.com/manugrapevine</a><br>
LinkedIn: <a href="https://www.linkedin.com/in/manuelparraroyon/">https://www.linkedin.com/in/manuelparraroyon/</a></p>
<h1 id="servicio--de-almacenamiento--con-nextcloud">Servicio  de almacenamiento  con NextCloud</h1>
<p>Está sesión práctica consiste en realizar un despligue de NextCloud completo utilizando diferentes microservicios encargados de tareas específicas, tales como la administración de las Bases de Datos, Autenticación, Balanceado (proxy) de carga o gestión del almacenamiento.</p>
<p>Para ello partiremos de un  servicio de almacenamiento en Cloud como NextCloud, que  convertiremos en un microservicio en contenedores y al cual le añadiremos todos los demás servicios adionales para darle soporte. Estos servicios se añadirán de forma incremental, y finalmente se procederá a crear un fichero de composición de contenedores con <code>docker-compose</code>.</p>
<h3 id="objetivos">Objetivos</h3>
<ul>
<li>Comprender el modelo de Cloud Computing con un servicio de almacenamiento de ficheros.</li>
<li>Conocer el despliegue de software como servicio en Cloud Computing de forma incremental.</li>
<li>Componer microservicios de forma básica con docker y docker-compose.</li>
<li>Cononer herramientas básica de balanceado y distribución de carga.</li>
<li>Conocer los modos de ejecución de órdenes dentro de los contenedores.</li>
</ul>
<h3 id="aquitectura">Aquitectura</h3>
<p>La composición de microservicios será la siguiente:</p>
<ul>
<li><strong>NGINX</strong> : trabajará como Proxy y Balanceador de carga para el microservicio que albergará NextCloud.</li>
<li><strong>NextCloud</strong>: contendrá el microservicio de almacenamiento en Cloud. Puede ser uno o varios.</li>
<li><strong>MySQL/MariaDB</strong>: microservicio que dotará a NextCloud de acceso y gestión de los datos.</li>
<li><strong>LDAP</strong>: microservicio para conectar la autenticación de NextCloud para los usuarios y el acceso.</li>
</ul>
<p>De forma opcional, la arquitectura debe permitir balancear la carga desde el servicio NGINX hasta los contenedores (al menos 2), de modo que el tráfico se encauce hacía los contenedores de NextCloud. Los demás microservicios sólo es necesario que tengan una única instancia de ejecución.</p>
<h3 id="dónde-vamos-a-trabajar">Dónde vamos a trabajar</h3>
<p>Para el trabajo con contenedores se usarán dos entornos:</p>
<ul>
<li><strong>Entorno local</strong>: Este entorno corresponde al PC del alumno. Para ello debes tener instalado <code>docker</code> y <code>docker-compose</code>. Se recomienda utilizar Linux para la sesión.</li>
<li><strong>Entorno cloud</strong>: Para este entorno le proporcionaremos al alumno unas credenciales para que pueda desplegar sus contendores en un servidor en la nube llamado <code>hadoop.ugr.es</code>, al que accederá por <code>ssh</code> y tendrá la opción de subir el fichero de composición <code>docker-compose</code>.</li>
</ul>
<h2 id="tareas-a-desarrollar">Tareas a desarrollar</h2>
<h3 id="creación-de-contenedor-con-nginx">Creación de contenedor con NGINX</h3>
<p>Este contenedor contendrá el servicio que el usuario conecta desde Internet, y es el responsable de pasar todas las peticiones a NextCloud que se encuentra en el BackEnd.<br>
Además debe incluir las directivas para realizar un balanceado de carga entre al menos dos contenedores de NextCloud (o solo uno si no se hace esta opción).</p>
<p>Para ello hay que tener en cuenta:</p>
<ul>
<li>Como se despliega NGINX en contendores.</li>
<li>Como se utiliza la configuración de <code>nginx.conf</code> para adaptarla a nuestras necesidades:
<ul>
<li>Balanceado de carga</li>
<li>Reenvio / Proxy para los contenedores de NextCloud.</li>
</ul>
</li>
</ul>
<p><strong>Ejecución del contenedor:</strong></p>
<pre><code>&lt;containersystem&gt; run  --name nginx -v $HOME/practica1/nginx.conf:/etc/nginx/nginx.conf:ro -p 8080:80 -d nginx
</code></pre>
<p><strong>Plantilla de configuración de nginx.conf:</strong></p>
<pre><code>http {
    upstream nextcloudservice {
        server nextcloud01;
        server nextcloud02;
        server nextcloud03;
    }

    server {
        listen 80;

        location / {
            proxy_pass http://nextcloudservice;
        }
    }
}
</code></pre>
<h3 id="creación-de-contenedor-con-nextcloud">Creación de contenedor con NextCloud</h3>
<p>Existen varias formas de crear el contenedor de forma más o menos automatizada, en nuestro caso la configuración inicial debe permitir conectar NextCloud con <strong>MySQL/MariaDB</strong>, para poder utilizado de forma distribuida, ya que si se usa <strong>SQLite</strong> no tenemos la opción de hacer un sistema más robusto. De este modo la opción por defecto que debeis usar es la de Base de Datos con <strong>MySQL/MariaDB</strong>.</p>
<p>Para ello, utilizaremos la siguiente descripción de servicios (en este caso de ejemplo, sólo se monta un servicio de NextCloud y una BBDD MaríaSQL), pero debes tener en cuenta que debe admitir al menos 2 servicios idénticos (opcionales) de NextCloud que comparten el espacio de almacenamiento (no de BBDD, que es sólo una instancia).</p>

    # Creamos un nuevo directorio
    mkdir NextCloud-docker-server

    cd NextCloud-docker-server
    
    #Copiamos este codigo en un fichero llamado docker-compose.yml

    version: '3'
    services:
    nextcloud:
        image: "nextcloud:21.0.0-apache"
        ports:
        - 8080:80
        restart: always
        volumes:
        - nextcloud:/var/www/html
        environment:
        - MYSQL_DATABASE=nextcloud
        - MYSQL_USER=nextcloud
        - MYSQL_PASSWORD=&lt;MYSQL_PASSWORD&gt;
        - MYSQL_HOST=mariadb
        - NEXTCLOUD_ADMIN_USER=&lt;NEXTCLOUD_ADMIN_USER&gt;
        - NEXTCLOUD_ADMIN_PASSWORD=&lt;NEXTCLOUD_ADMIN_PASSWORD&gt;
    mariadb:
        image: "mariadb:10.4.8-bionic"
        command: "--transaction-isolation=READ-COMMITTED --binlog-format=ROW"
        restart: always
        volumes:
        - db:/var/lib/mysql
        environment:
        - MYSQL_ROOT_PASSWORD=&lt;MYSQL_ROOT_PASSWORD&gt;
        - MYSQL_PASSWORD=&lt;MYSQL_PASSWORD&gt;
        - MYSQL_DATABASE=nextcloud
        - MYSQL_USER=nextcloud
    volumes:
    nextcloud:
    db:

    # (Opcional) Creamos en entorno necesario para la inicialización del sistema, para ello dentro de este mismo directorio añadimos un fichero oculto llamado .env: Añadimos las variables que queramos, por ejemplo las de las claves que son más sensibles:

    MYSQL_PASSWORD=&lt;MYSQL_PASSWORD&gt;
    MYSQL_ROOT_PASSWORD=&lt;MYSQL_ROOT_PASSWORD&gt;
    MYSQL_PASSWORD=&lt;MYSQL_PASSWORD&gt;
    NEXTCLOUD_ADMIN_PASSWORD=&lt;NEXTCLOUD_ADMIN_PASSWORD&gt;


    # Construimos el contenedor 
    docker-compose up -d
    # o bien, si queremos ver el proceso de carga y los logs en tiempo real, usaremos:
    docker-compose up

<h3 id="creación-de-contenedor-con-mariadbmysql">Creación de contenedor con MariaDB/MySQL</h3>
<p>Hay muchas formas de componer MySQL/MariaDB, una de ellas puede ser la siguiente:<br>
wget <a href="https://raw.githubusercontent.com/owncloud/docs/master/modules/admin_manual/examples/installation/docker/docker-compose.yml">https://raw.githubusercontent.com/owncloud/docs/master/modules/admin_manual/examples/installation/docker/docker-compose.yml</a></p>
<h3 id="creación-de-contenedor-con-ldap">Creación de contenedor con LDAP</h3>
<p>Está parte es más compleja integrarla directamente sobre NextCloud, por lo que se hace una vez que el sistema está funcionando y se tiene acceso al microservicio de LDAP que se despliega con todo el sistema.</p>
<p>Aquí está toda la documentación: <a href="https://github.com/manuparra/docker_ldap">https://github.com/manuparra/docker_ldap</a></p>
<h3 id="modos-de-ejecución-de-docker--customización-de-nextcloud">Modos de ejecución de Docker:  Customización de NextCloud</h3>
<p>Otro de los aspectos a considerar en esta práctica es que una vez que se ha desplegado la estructura de contenedores, y el sistema está funcionando, es necesario ejecutar unas órdenes dentro de los contenedores de NextCloud, de modo que podamos intereactuar con el contenedor sin tener que entrar explicitamente en cada contenedor.</p>
<p>NextCloud incluye en su instalación y por tanto dentro del contenedor una serie de órdenes para hacer modificaciones en la configuración sin tener que hacerlo manualmente desde el interfaz de administración web del servicio. Esta órden es <code>occ</code> y permite gestionar la configuración de NextCloud de forma directa desde la línea de órdenes. Para ello debes revisar la documentación de la aplicación <code>occ</code> aquí: <a href="https://docs.nextcloud.com/server/20/admin_manual/configuration_server/occ_command.html">https://docs.nextcloud.com/server/20/admin_manual/configuration_server/occ_command.html</a></p>
<p>Este comando debe llamarse usando docker del siguiente modo:</p>
<p><em>Por ejemplo para ver la lista de opciones de configuración:</em></p>
<pre><code> docker exec -u www-data -it &lt;nombre servicio NextCloud&gt; php occ config:list
</code></pre>
<p><em>Por ejemplo para ver la lista de opciones de configuración:</em></p>
<pre><code> docker exec -u www-data -it &lt;nombre servicio NextCloud&gt; php occ config:list
</code></pre>
<p><em>Para ver la lista de opciones modificables de la aplicación (plugins)</em>:</p>
<pre><code>sudo docker exec -u www-data -it &lt;nombre servicio NextCloud&gt; php occ app:list
</code></pre>
<p>En esta parte se pide deshabilitar los siguientes plugins de la aplicación utilizando docker para hacer los cambios en el interior del contenedor, pero usando docker:</p>
<p><code>accessibility</code>, <code>dashboard</code>, <code>accessibility</code>, <code>firstrunwizard</code>, <code>nextcloud_announcements</code>, <code>photos</code>,<code>weather_status</code>, <code>user_status</code>, <code>survey_client</code>, <code>support</code>, <code>recommendations</code> y <code>updatenotification</code>.</p>
<p>Estas operaciones pueden ir dentro de un fichero <code>.sh</code> que se puede ejecutar al terminar de desplegar todos los contenedores.</p>
<h2 id="resultados-de-la-sesión">Resultados de la sesión</h2>
<ul>
<li>El alumno debe poder conectar a un servicio NextCloud y utilizarlo sin problemas.</li>
<li>El alumno debe customizar el servicio NextCloud, ejecutando  órdenes del servicio con docker para hacer modificaciónes en la configuración.</li>
<li>El alumno debe crear un fichero <code>docker-compose.yml</code> que incluya:
<ul>
<li>a) Servicio NGINX</li>
<li>b) Servicio/s NextCloud</li>
<li>c) Servicio MySQL o María SQL</li>
<li>d) Servicio de Autenticación</li>
</ul>
</li>
<li>La arquitectura resultante debe ser la siguiente:</li>
</ul>
<pre><code>Usuario --&gt; NGINX --&gt; NextCloudCloud01 --&gt; MariaDB00
                      NextCloudCloud00 --&gt; MariaDB00
                      ----------
                          v
                      Auth LDAP
</code></pre>
<h2 id="preguntas---cuestionario">Preguntas - Cuestionario</h2>
<ul>
<li>¿Cuál sería el  cuello de botella del sistema?.</li>
<li>¿Qué ocurre si uno de los contenedores de NextCloud deja de funcionar?:
<ul>
<li>¿Caería todo el sistema?.</li>
</ul>
</li>
<li>¿Qué medidas tomarías para evitar sobrecargar el sistema por ejemplo lanzando varias decenas de contedores cuando el uso el sistema es mínimo?.
<ul>
<li>¿Qué directivas tomarías si la demanda de uso del sistema crece, qué microservicios 	deberías tener en cuenta?.</li>
<li>¿Y si se cae la instancia de MySQL, qué ocurriría?, ¿Cómo solucionarías el problema?.</li>
</ul>
</li>
</ul>
</div>