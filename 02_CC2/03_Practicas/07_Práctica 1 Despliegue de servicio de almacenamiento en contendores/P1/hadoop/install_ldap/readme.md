ldapadd -x -D "cn=admin,dc=openstack,dc=org" -w password -c -f NextCloud-docker-server/user/user1.ldif

ldappasswd -s password -W -D "cn=admin,dc=openstack,dc=org" -x "cn=alvarodelaflor,ou=Users,dc=openstack,dc=org"

Password: password

En nextcloud:

Apps>Busca>Ldap y le das a enable

Settings > ldap

host -> openldap

one base dn per line -> ou=Users,dc=openstack,dc=org