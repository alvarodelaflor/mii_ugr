FROM nextcloud:21.0.0-apache

ENV PORT 9001
EXPOSE 9001
ENTRYPOINT []
CMD sed -i "s/80/$PORT/g" /etc/apache2/sites-available/000-default.conf /etc/apache2/ports.conf && docker-php-entrypoint apache2-foreground