#generación de variables normalizadas
 x=iris$Sepal.Length
 y=iris$Petal.Length
 z=(x-mean(x))/sqrt(var(x))
 w=(y-mean(y))/sqrt(var(y))
 u=(x-min(x))/(max(x)-min(x))
 v=(y-min(y))/(max(y)-min(y))
 norm=data.frame(z,w,u,v, iris$Species)
 names(norm)=c("SLTip","PLTip","SL01","PL01","Clase")
 norm[1:10,]
with(norm, plot(SLTip,PLTip,col=Clase,pch=as.numeric(Clase))) 
with(norm,plot(SL01,PL01,col=Clase,pch=as.numeric(Clase)))
write.csv(norm,"./normalizados.csv",row.names=FALSE)