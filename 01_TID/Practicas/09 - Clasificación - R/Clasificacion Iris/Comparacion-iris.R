#Una vez ejecutados los scripts que estan en "clasificacion/iris
#Se puede hacer una comparación de funcionamiento
v1=c("Precision-total","F-medida_total")
v2=c(bien_ctree,ft_ctree)
v3=c(bien_tree,ft_tree)
v4=c(bien_rparty,ft_rparty)
v5=c(bien_naive,ft_naive)
v6=c(bien_knn,ft_knn)
v7=c(bien_rf,ft_rf)
td=data.frame(v1,v2,v3,v4,v5,v6,v7)
names(td)=c("Medida","Arbol-reg","Arbol-Gini","Arbol-Rpart","Naive","Knn", "Random Forest")
td