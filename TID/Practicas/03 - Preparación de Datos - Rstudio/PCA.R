# El set de datos USArrests del paquete básico de R contiene el porcentaje de asaltos (Assault), asesinatos (Murder) y secuestros (Rape) por cada 100,000 habitantes para cada uno de los 50 estados de USA (1973). Además, también incluye el porcentaje de la población de cada estado que vive en zonas rurales (UrbanPoP).
data("USArrests")
head(USArrests)
dim(USArrests)
# El promedio de los datos muestra que hay tres veces más secuestros que asesinatos y 8 veces más asaltos que secuestros.
apply(X = USArrests, MARGIN = 2, FUN = mean)

# La varianza es muy distinta entre las variables, en el caso de Assault, la varianza es varios órdenes de magnitud superior al resto.
apply(X = USArrests, MARGIN = 2, FUN = var)

# Si no se estandarizan las variables para que tengan media cero y desviación estándar 1 antes de realizar el estudio PCA, la variable Assault dominará la mayoría de las componentes principales.
# La función prcomp() es una de las múltiples funciones en R que realizan PCA. Por defecto, prcomp() centra las variables para que tengan media cero, pero si se quiere además que su desviación estándar sea de uno, hay que indicar scale = TRUE.
pca <- prcomp(USArrests, scale = TRUE)
names(pca)

# Los elementos center y scale almacenados en el objeto pca contienen la media y desviación típica de las variables previa estandarización (en la escala original).
pca$center
pca$scale

# rotation contiene el valor de los loadings ϕ para cada componente (eigenvector). El número máximo de componentes principales se corresponde con el mínimo(n-1,p), que en este caso es min(49,4)=4.
pca$rotation
head(pca$x)
dim(pca$x) 

# Mediante la función biplot() se puede obtener una representación bidimensional de las dos primeras componentes. Es recomendable indicar el argumento scale = 0 para que las flechas estén en la misma escala que las componentes.
biplot(x = pca, scale = 0, cex = 0.6, col = c("blue4", "brown3"))

# Una vez calculadas las componentes principales, se puede conocer la varianza explicada por cada una de ellas, la proporción respecto al total y la proporción de varianza acumulada.

library(ggplot2)
pca$sdev^2

prop_varianza <- pca$sdev^2 / sum(pca$sdev^2)
prop_varianza

ggplot(data = data.frame(prop_varianza, pc = 1:4),
       aes(x = pc, y = prop_varianza)) +
  geom_col(width = 0.3) +
  scale_y_continuous(limits = c(0,1)) +
  theme_bw() +
  labs(x = "Componente principal",
       y = "Prop. de varianza explicada")


prop_varianza_acum <- cumsum(prop_varianza)
prop_varianza_acum

ggplot(data = data.frame(prop_varianza_acum, pc = 1:4),
       aes(x = pc, y = prop_varianza_acum, group = 1)) +
  geom_point() +
  geom_line() +
  theme_bw() +
  labs(x = "Componente principal",
       y = "Prop. varianza explicada acumulada")

# En este caso, la primera componente explica el 62% de la varianza observada en los datos y la segunda el 24.7%. Las dos últimas componentes no superan por separado el 1% de varianza explicada. Si se empleasen únicamente las dos primeras componentes se conseguiría explicar el 86.75% de la varianza observada.

# T-SNE
#En este caso, la primera componente explica el 62% de la varianza observada en los datos y la segunda el 24.7%. Las dos últimas componentes no superan por separado el 1% de varianza explicada. Si se empleasen únicamente las dos primeras componentes se conseguiría explicar el 86.75% de la varianza observada.

# El paquete tsne contiene una implementación del algoritmo t-SNE original, no la aproximación de Barnes-Hut.

# El UC Irvine Machine Learning Repository contiene multitud de sets de datos muy adecuados para el estudio de métodos estadísticos y de machine learning. En concreto, el Optical Recognition of Handwritten Digits Data Set, contiene la imagen digitalizada de 3822 números (del 0 al 9) escritos a mano. La información digitalizada de cada número genera un espacio de 64 dimensiones, es decir, digitalmente, cada número está descrito por 64 variables. Se pretende evaluar si t-SNE es capaz de reducir todas esas variables a solo 2, sin perder el patrón/estructura de los datos.

library(readr)
library(dplyr)

# Carga de datos
datos <- read_csv(paste0("http://archive.ics.uci.edu/ml/machine-learning-",
                         "databases/optdigits/optdigits.tra"))
dim(datos)
# La última columna contiene el número real al que se corresponde la observación.
# Se renombra como "numero"
datos <- datos %>% rename(numero = `0_26`)

# La función tsne() recibe como argumento una matriz, no un data.frames
datos <- data.matrix(datos)

# Debido a los requerimientos computacionales del t-SNE, se limita este ejemplo
# únicamente a 1000 observaciones.
datos <- datos[1:1000,]
# A diferencia del proceso de PCA, en el que se suelen calcular todas o gran parte de las componentes principales y luego se estudia que porcentaje de información contiene cada una, en tSNE se especifica de antemano a cuantas dimensiones se tiene que reducir el espacio original.

install.packages('tsne')
library(tsne)

# También se limita el número de iteraciones (epoch) a 100, aunque los
# resultados podrían mejorar si se aumentara
set.seed(321)
tsne_reduction <- tsne(datos, k = 2, perplexity = 30, epoch = 100)

# Para poder representar el verdadero número al que corresponde cada imagen,
# se adjunta la variable "numero" del set de datos
resultados <- as.data.frame(tsne_reduction)
colnames(resultados) <- c("dim_1", "dim_2")
resultados$numero <- as.character(datos[ ,"numero"])
ggplot(data = resultados, aes(x = dim_1, y = dim_2)) +
  geom_point(aes(color = numero)) + 
  theme_bw()
