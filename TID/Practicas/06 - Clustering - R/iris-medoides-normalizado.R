iris2=iris
iris2$Species=NULL
#normalizo iris2, Notese el uso y sintaxis del bucle
for (j in 1:4) {x=iris2[,j] ; v=(x-mean(x))/sqrt(var(x)); iris2[,j]=v}
#Empezamos con k-medoides clasico (pam) con 3 clases. El gráfico nos da tambien el coeficiente de silueta
pam.result=pam(iris2,3)
table(pam.result$clustering,iris$Species)
plot(pam.result)
#(Ver descripcion de la funcion)
group=pam.result$clustering
cluster.stats(dist(iris2),group,alt.clustering=as.integer(iris$Species))
