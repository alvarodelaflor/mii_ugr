#Preparacion de los atributos discretos de iris, Discretiza por cuartiles y pone
iris2=iris
for (j in 1:4){ iris2[,j]=ordered(cut(iris[,j],breaks=quantile(iris[,j])),labels=c("Bajo","Medio","Alto","Muy-Alto"))}
iris2
#seleccionar el paquete arules
#Lo convertimos en una BD de transacciones, vemos informacion acerca de el
iristrans=as(iris2,"transactions")
summary(iristrans)
itemFrequencyPlot(iristrans,support=0.1)
#Calculo apriori, los parametros que se han especificado son los que se dan por defecto 
sale1=apriori(iristrans, parameter = list(support = 0.1, confidence = 0.8, minlen = 2))
#salida del conjunto de reglas
summary(sale1)
sale3=sort(sale1,by="confidence")
salida1=inspect(sale3,ruleSep = "---->", itemSep = " + ", setStart = "", setEnd ="", linebreak=TRUE)
#restriccion de reglas, solo las que predicen clases y con confianza mayor que 0.9
sale2=subset(sale3,subset=rhs %in% c("Species=versicolor","Species=virginica","Species=setosa")& confidence >= 0.9)
salida2= inspect(sale2,ruleSep = "---->", itemSep = " + ", setStart = "", setEnd ="", linebreak=TRUE)
#Visualizacion de reglas
#Selecciono arulesViz
plot(sale1,method="scatterplot",measure=c("support","confidence"))
plot(sale1, shading="order", control=list(main = "Two-key plot",col=rainbow(5)))
plot(sale2, method="matrix",measure="confidence", control=list(main = "Matriz",col=rainbow(8)))
plot(sale2, method="matrix3d",shading="order", control=list(main = "Tridimensional"))
plot(sale1, method="grouped",measure="confidence", control=list(main = "Cluster",k=10,col=rainbow(8)))
plot(sale2[1:10], method="graph",measure="confidence", control=list(main = "Grafo"))
