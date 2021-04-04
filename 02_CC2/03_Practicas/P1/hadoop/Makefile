up:
	cd NextCloud-docker-server && docker-compose up -d --remove-orphans

up_log:
	cd NextCloud-docker-server && docker-compose up --remove-orphans

down:
	cd NextCloud-docker-server && docker-compose down --remove-orphans

rebuild:
	cd NextCloud-docker-server && docker-compose build --no-cache

stop-nc01:
	cd NextCloud-docker-server && docker-compose stop nextcloud01

stop-nc02:
	cd NextCloud-docker-server && docker-compose stop nextcloud02

stop-nc03:
	cd NextCloud-docker-server && docker-compose stop nextcloud03

start-nc01:
	cd NextCloud-docker-server && docker-compose start nextcloud01

start-nc02:
	cd NextCloud-docker-server && docker-compose start nextcloud02

start-nc03:
	cd NextCloud-docker-server && docker-compose start nextcloud03

disable-plugins:
	cd NextCloud-docker-server && bash disable1.sh && bash disable2.sh