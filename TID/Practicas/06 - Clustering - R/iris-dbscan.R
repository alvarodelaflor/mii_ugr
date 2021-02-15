 #iris-dbscan, preparo datos
 library(fpc)
 iris2=iris
iris2$Species=NULL
ds=dbscan(iris2,eps=0.45,MinPts=5)
table(ds$cluster,iris$Species)
plot(ds,iris2)
 plot(ds,iris2[c(1,4)])
 plot(ds,iris2[c(3,4)])
 x=table(ds$cluster,iris$Species)
 nc=length(x[,1])
 nc
shi= silhouette(ds$cluster,dist(iris2))
 plot(shi,col=1:nc)

