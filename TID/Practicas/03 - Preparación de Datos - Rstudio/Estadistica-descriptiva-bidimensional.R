# Analisis bidimensional
#Comparacion variable continua seg?n una discreta. Se recuerda de Species es un factor
#Calculo de valores por grupo
tapply(iris$Sepal.Length,iris$Species, "mean")
tapply(iris$Sepal.Length,iris$Species, "summary")
boxplot(iris$Sepal.Len~iris$Species,col=3)
#Comparacion de variables continuas
cor(iris[,1:4])
cov(iris[,1:4])
#Se puede trabajar con dos variasble continuas convirtiendo una en un factor
factsl=factor(cut(iris$Sepal.Length, breaks=4.50+0.5*(0:7)))
tapply(iris$Petal.Length,factsl, "summary")
boxplot(iris$Petal.Length~factsl,col=3)
#Graficas bidimensionales
#scatter plot, con colores segun un factor
plot(iris$Sepal.Length,iris$Sepal.Width, col=iris$Species)
plot(iris$Petal.Length,iris$Petal.Width, col=factsl)
#scatter plot, con colores  y formas segun un factor
plot(iris$Sepal.Length,iris$Sepal.Width, col=iris$Species, pch=as.numeric(iris$Species))
#scatter plot en global, se toman las variables y se da el color que se quiere
pairs(iris[,1:4],col=iris$Species)




