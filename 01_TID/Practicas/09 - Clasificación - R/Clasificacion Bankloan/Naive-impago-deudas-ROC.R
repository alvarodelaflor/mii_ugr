#Un ejemplo m?s complejo de ?rboles de decisi?n. utilizo los datos personales de 
# bankloan para predecir impago veo curva ROC
#genero el dataset
library("dplyr", lib.loc="/Library/Frameworks/R.framework/Versions/3.2/Resources/library")
datos=filter(bankloan,is.na(bankloan$impago)==FALSE)
impagos=as.character(datos$impago)
dato=data.frame(datos,impagos)
#Notese que cambiamos la clse a caracteres
# Genero conjunto de entrenamiento y conjunto de test.
#Hago muestreo aleatorio mediante la generaci?n de dos particiones al 70% y 30% de los datos
ind=sample(2,nrow(dato),replace=TRUE,prob=c(0.7,0.3))
entrena=dato[ind==1,]
test=dato[ind==2,]
#Seleccionar la libreria e1071, 
library("e1071", lib.loc="/Library/Frameworks/R.framework/Versions/3.2/Resources/library")
#Describo el modelo de clasificacion, clase y variables que intervienen. 
#Notese que las clases son caracters
modelo=(impagos~ingresos+deudaingr+deudacred+deudaotro)
arbol4=naiveBayes(modelo,data=entrena)
table(predict(arbol4,entrena,type="class"),entrena$impagos)
#Ver que pasa con los test 
testpred=predict(arbol4,newdata=test,type="class")
tt=table(testpred,test$impagos)
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
#Calculo de las curvas ROC
library("ROCR", lib.loc="C:/Program Files/R/R-3.1.2/library")
m=predict(arbol4,newdata=test,type="prob")
pred=prediction(m[,2],test$impagos)
perf=performance(pred,"tpr","fpr")
plot(perf, main="Naive Bayes")
#Otra forma de curva ROC
library("pROC", lib.loc="/Library/Frameworks/R.framework/Versions/3.2/Resources/library")
predi=roc(test$impagos,m[,2])
plot(predi,main="Arbol de decision deudas")

