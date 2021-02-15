iris2=iris
iris2$Species=NULL
#normalizo iris2, Notese el uso y sintaxis del bucle
for (j in 1:4) {x=iris2[,j] ; v=(x-mean(x))/sqrt(var(x)); iris2[,j]=v}
kmeans.result=kmeans(iris2,3)
kmeans.result
table(iris$Species,kmeans.result$cluster)
#Analisis de bondad
plot(iris2[c("Sepal.Length","Sepal.Width")], col=kmeans.result$cluster)
points(kmeans.result$centers[,c("Sepal.Length","Sepal.Width")],col=1:3,pch=8,cex=2)
x=kmeans.result$cluster
plotcluster(iris2,x)
shi= silhouette(kmeans.result$cluster,dist(iris2))
plot(shi,col=1:3)
#Calculo de algunas otras medidas de bondad del agrupamiento
#(Ver descripcion de la funcion)
group=kmeans.result$cluster
cluster.stats(dist(iris2),group,alt.clustering=as.integer(iris$Species))
