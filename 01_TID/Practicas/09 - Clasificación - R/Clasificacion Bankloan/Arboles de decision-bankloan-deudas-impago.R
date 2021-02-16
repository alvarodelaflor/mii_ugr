#Un ejemplo más complejo de árboles de decisión. utilizo los datos económicos de 
# bankloan para predecir impago
library("dplyr", lib.loc="C:/Program Files/R/R-3.1.2/library")
#genero el dataset
datos=data.frame(bankloan[,5:9])
dato=filter(datos,is.na(datos$impago)==FALSE)
# Genero conjunto de entrenamiento y conjunto de test.
#Hago muestreo aleatorio mediante la generación de dos particiones al 70% y 30% de los datos
ind=sample(2,nrow(dato),replace=TRUE,prob=c(0.7,0.3))
entrena=dato[ind==1,]
test=dato[ind==2,]
entrena
test
#Seleccionar la libreria rpart, se obtiene un arbol de decision/regresion muy general
library("rpart", lib.loc="C:/Program Files/R/R-3.1.2/library")
#Describo el modelo de clasificacion, clase y variables que intervienen. 
#Notese que las clases son caracters
modelo=(as.character(impago)~ingresos+deudaingr+deudacred+deudaotro)
arbol3=rpart(modelo,data=entrena)
arbol4=rpart(modelo,data=entrena,parms=list(split="information"))
plot(arbol3) ; text(arbol3)
plot(arbol4) ; text(arbol4)
table(predict(arbol3,type="class"),as.character(entrena$impago))
table(predict(arbol4,type="class"),as.character(entrena$impago))
#Ver que pasa con los test uso solo arbol3 porque evalua lo mismo
testpred=predict(arbol3,newdata=test,type="class")
table(testpred,as.character(test$impago))
tt=table(testpred,as.character(test$impago))
tt
#calculo de las medidas de calidad para prediccion
#Porcentaje de bien clasificados y de error
#Genero el vector diagonal y lo sumo
dd=diag(tt)
bien=sum(dd)
bien_clasificados=(bien/nrow(test))*100
#Bien y mal clasificados
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
#Analisis del problema despues de podar el arbol
arbol5=prune(arbol3, cp=0.5)
plot(arbol5); text(arbol5)
testpred=predict(arbol5,newdata=test,type="class")
table(testpred,as.character(test$bankloan.impago))
tt=table(testpred,as.character(test$bankloan.impago))
tt
#calculo de las medidas de calidad para prediccion
#Porcentaje de bien clasificados y de error
#Genero el vector diagonal y lo sumo
dd=diag(tt)
bien=sum(dd)
bien_clasificados=(bien/nrow(test))*100
#Bien y mal clasificados
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

