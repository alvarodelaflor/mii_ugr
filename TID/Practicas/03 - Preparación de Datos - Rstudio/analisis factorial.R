#Selecciono los variables numericas de iris
x=iris[,1:4]
# llamo a analisis factorial con un factor ( no ermite hacerlo con mas con solo 4 variables)
solu=factanal(x,factors=1,scores="regression")
solu
#compruebo como se comporta este factor en relaci?n con las clases
y1=solu$scores
y2=iris$Species
h=data.frame(y1,y2)
str(h)
boxplot(h$Factor1 ~ h$y2,col=3)
#Otra funcion que hace analisis factorial (realmente componentes principales)
datos=x
sal=prcomp(datos,retX=TRUE)
sal
str(sal)
sal$x
#El numero de factores a seleccionar se ajusta mediante tol= umbral (ver descripcion de la funci?n)
str
sal
str(sal)
sal$x
#vamos a visualizar la representaci?n factorial y a guardarla
h=data.frame(sal$x[1:150,1:2],y2)
names(h)=c("factor1","factor2","Species")
h[1:10,]
#Haremos estadistica bidimensional con los facotres y las especies 
#Calculo de valores por grupo y dibujo alguna gr?fica
tapply(h$factor1,h$Species, "summary")
boxplot(h$factor1~h$Species,col=2)
tapply(h$factor2,h$Species, "summary")
boxplot(h$factor2~h$Species,col=3)
plot(h$factor1,h$factor2, col=h$Species, pch=as.numeric(h$Species))
plot(h$factor2,h$factor1, col=h$Species, pch=as.numeric(h$Species))
write.csv(h,"./repfact-iris.csv",row.names=FALSE)
#Vamos ahora a analizar las variables en funci?n de los factores
#Analizando la salida de sal vemos que los datos que nos intersan est?n en rotation.
h=data.frame(row.names(sal$rotation),sal$rotation[1:4,1:2])
names(h)=c("variable","factor1","factor2")
h
plot(h$factor1,h$factor2, col=h$variable, pch=as.numeric(h$variable))
plot(h$factor2,h$factor1, col=h$variable, pch=as.numeric(h$variable))
write.csv(h,"./repesepct-iris.csv",row.names=FALSE)
