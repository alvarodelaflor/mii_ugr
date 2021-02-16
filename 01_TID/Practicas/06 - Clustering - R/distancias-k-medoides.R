#Obtengo demo-cs.R por importacion
demo=demo_cs.R
#Genero datos y distancia numericos
x=data.frame(demo$edad,demo[,10:15],demo[,17:18],demo$residen)
View(x)
dist1=dist(x)
#Genero datos y distancia binaria
y=data.frame(demo$marital,demo$retirado,demo$genero,demo[,21:35])
View(y)
dist2=dist(y,method="binary")
#Calculo distancia ponderada y Cluster
dista=(dist1+dist2)/2
pam.result=pam(dista,5)
idx=sample(1:dim(x)[1],150)
#Analisis de bondad
grupo=pam.result$clustering
plotcluster(x[idx,],grupo[idx])
plotcluster(y[idx,],grupo[idx])
d1=dist(x[idx,])
d2=dist(y[idx,])
d3=(d1+d2)/2
shi= silhouette(grupo[idx],d3)
plot(shi,col=1:5)
#(Ver descripcion de la funcion)
cluster.stats(d3,grupo[idx])

