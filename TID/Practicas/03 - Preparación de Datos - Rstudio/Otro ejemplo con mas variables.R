x=1#Voy a utilizar airquality. Esta en datasets
airquality
library("dplyr", lib.loc="/Library/Frameworks/R.framework/Versions/3.1/Resources/library")
#Como tiene valres perdidos tendré que llamar la funcion de forma más indicando esto
z=filter(airquality,is.na(airquality$Ozone) == FALSE,is.na(airquality$Solar.R) == FALSE)
#Con un factor
sol1=factanal(z,factors=1,scores = "regression")
sol1
#Explica un 0.343 de varianza
#con dos factores
sol2=factanal(z,factors=2,scores = "regression")
sol2
#Solo explica un .517 de varianza
#Con tres factores
sol3=factanal(z,factors=3,scores = "regression")
sol3
#Tres factores no mejoran la varianza explicada, seleccionamos 2
#Reprentacion de los datos en funcion de los dos factores
plot(sol2$scores[,1],sol2$socres[,2])
