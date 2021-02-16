# Para trabajar con este script hay que tener instalados las librerias dplyr y tidyr
library("dplyr", lib.loc="C:/Program Files/R/R-3.1.2/library")
library("tidyr", lib.loc="C:/Program Files/R/R-3.1.2/library")
#Leemos el dataset normalizado que se ha creado en el ejercicio "generacion de variables normalizadas". 
#Tambien se puede importar desde Rstudio
norm = read.csv("./normalizados.csv", header=TRUE)
#Filtramos datos por el valor de variables, en el dataset de variable normalizadas
n1=filter(norm, norm$Clase=="setosa")
n1
n2=filter(norm, norm$SLTip > 0.0 , norm$PLTip > 0.0)
n2
#Se comprueba que solo versicolor y virginica tienen valores por encima de la media
#Se recomienda explorar las posibilidades de R como lenguguaje programacion, revisando:
#R Reference Card en sus apartados "Data selection and manipulation" y "Advanced data processing"
#Se recomienda explorar las posibilidades de las librerias  dplyr y tidyr
