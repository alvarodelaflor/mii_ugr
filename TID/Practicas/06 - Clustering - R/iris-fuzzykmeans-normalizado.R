iris2=iris
iris2$Species=NULL
#normalizo iris2, Notese el uso y sintaxis del bucle
for (j in 1:4) {x=iris2[,j] ; v=(x-mean(x))/sqrt(var(x)); iris2[,j]=v}
#Trabajo con clustering difuso
fuzzy.result=fanny(iris2,3)
str(fuzzy.result)
fuzzy.result$membership
table(fuzzy.result$clustering,iris$Species)
plot(fuzzy.result)
#(Ver descripcion de la funcion)
cluster.stats(dist(iris2),fuzzy.result$clustering,alt.clustering=as.integer(iris$Species))
