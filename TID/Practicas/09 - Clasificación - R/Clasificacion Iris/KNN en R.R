# Genero conjunto de entrenamiento y conjunto de test.
#Hago muestreo aleatorio mediante la generaci?n de dos particiones al 70% y 30% de los datos
ind=sample(2,nrow(iris),replace=TRUE,prob=c(0.7,0.3))
entrena=iris[ind==1,]
test=iris[ind==2,]
#Seleccionar la libreria kknn
library("kknn")
#Describo el modelo de clasificacion, clase y variables que intervienen
modelo=Species~Sepal.Length+Sepal.Width+Petal.Length+Petal.Width
iris.knn=kknn(formula=modelo,entrena, test, na.action=na.omit(),k=3)
summary(iris.knn)
fit=fitted(iris.knn)
tt=table(fit,test$Species)
tt
#calculo de las medidas de calidad para prediccion
#Porcentaje de bien clasificados y de error
#Genero el vector diagonal y lo sumo
dd=diag(tt)
bien=sum(dd)
bien_clasificados=(bien/nrow(test))*100
#Bien y mal clasificados
bien_knn=bien_clasificados
bien_clasificados
100-bien_clasificados
#Precision, Recall y F-measure
ft=0 
m=c(1:nrow(tt))
n=m
prec=n
rec=m
fmes=m
for(i in 1:nrow(tt)){m[i]=sum(tt[i,])}
for(i in 1:nrow(tt)){n[i]=sum(tt[,i])}
for(i in 1:nrow(tt)){prec[i]=dd[i]/m[i]}
for(i in 1:nrow(tt)){rec[i]=dd[i]/n[i]} 
for(i in 1:nrow(tt)){fmes[i]=2*dd[i]/(m[i]+n[i])}
ft=0
for(i in 1:nrow(tt)){ft=ft+(fmes[i]/nrow(tt))}
#Precision
prec

#Recall
rec
#F-measure
fmes
#F-measure total
ft
ft_knn=ft



