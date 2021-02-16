#Ver datos
dim(iris)
names(iris)
str(iris)
iris[1:5,]
iris[1:10,"Sepal.Length"]
iris$Sepal.Length[1:10]
#Estadistica descriptiva unidimensional
summary(iris)
quantile(iris$Sepal.Length)
quantile(iris$Sepal.Length, c(0.20,0.40,0.80))
#Gr?ficas unidimensionales
#Ejemplos de histogramas
hist(iris$Sepal.Length)
hist(iris$Sepal.Length,breaks=c(4.3,5.,5.60,6.52,7.9))
hist(iris$Sepal.Length,freq=FALSE)
hist(iris$Sepal.Length,freq=FALSE,col=2,border=1)
hist(iris$Sepal.Length,freq=FALSE,col=5,border=1)
hist(iris$Sepal.Length,freq=FALSE,density=10,col=2,border=1)
hist(iris$Sepal.Length,freq=FALSE,density=20,col=5,border=1)
#Ejemplos de calculo de frecstr(irisuencias
#Frecuencias absolutas
#variables discretas
# a Species se le puede aplicar table porque puede ser considerada de "tipo factor"
tab=table(iris$Species)
pie(tab)
barplot(tab)
#variables contiuas
# Para hacer un histograma simple
hist(iris$Sepal.Length)
# Para capturar una distribuci?n de frecuencias absolutas a gusto  
# Se genera una variable de tipo factor y se aplica table a la misma
factsl=factor(cut(iris$Sepal.Length, breaks=4.50+0.25*(0:14)))
table(factsl)
barplot(table(factsl))
#frecuencias relativas
#Simple funcion de densidad
plot(density(iris$Sepal.Length))
#calculo de distribuci?n de frecuencias
rela=table(factsl)/length(iris$Sepal.Length)
plot(rela,col=3)
#frecuencias acumulativas
freac=cumsum(table(factsl))
freac
freacr=(freac/length(iris$Sepal.Length))*100
freacr
barplot(freacr)
plot(freacr)

