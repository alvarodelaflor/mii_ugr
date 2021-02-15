iris2=iris
iris2$Species=NULL
#normalizo iris2, Notese el uso y sintaxis del bucle
for (j in 1:4) {x=iris2[,j] ; v=(x-mean(x))/sqrt(var(x)); iris2[,j]=v}
#Pruebo con k-medoides optimo numero de grupos
pamk.result=pamk(iris2)
pamk.result
pamk.result$pamobject$nc
table(pamk.result$pambject$clustering,iris$Species)
plot(pamk.result$pamobject)
#(Ver descripcion de la funcion)
group=pamk.result$pamobject$clustering
cluster.stats(dist(iris2),group)
