step1:
	docker run hello-world
	docker stop $$(docker ps -a -q)
	docker rm $$(docker ps -a -q)
	docker rmi $$(docker images -q)

step2:
	airflow scheduler

step3:
	airflow webserver -p 8080
