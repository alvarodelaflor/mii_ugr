library(tidyverse)

# Leer datos
iris_data <- read_delim("iris.csv", delim = " ")
head(iris)

# Histograma
ggplot(data = iris_data) + 
  geom_histogram(aes(x = class), stat = "count")

ggplot(data = iris_data) + 
  geom_histogram(aes(x = get("sepal length")), bins = 10)

names(iris_data) <- make.names(names(iris_data), unique=TRUE)

ggplot(data = iris_data) + 
  geom_density(aes(x = sepal.length), fill = "lightgray")

# Gráfico de dispersión
ggplot(data = iris_data) + 
  geom_point(aes(x = petal.length, y = petal.width))

ggplot(data = iris_data) + 
  geom_point(aes(x = petal.length, y = petal.width, color = class, shape = class)) 

# Facets
ggplot(data = iris_data) + 
  geom_point(aes(x = petal.length, y = petal.width, color = class)) +
  facet_wrap(~class)

# Gráficos sofisticados
g <- ggplot(data = iris_data) 

g  + 
  geom_point(aes(x = petal.length, y = petal.width, color=class))
  labs(x = "Petal Length", y = "Petal Width") +  
  scale_color_discrete(name ="Clase", labels=c("Iris Setosa", "Iris Versicolor", "Iris Virginica"))

library(ggthemes)
g +
  geom_point(aes(x = petal.length, y = petal.width, color=class)) +
  theme_hc() + 
  scale_color_hc(palette = "darkunica", labels = c("Iris Setosa", "Iris Versicolor", "Iris Virginica")) +
  labs(x = "Longitud del pétalo", y = "Anchura del pétalo", 
       title = "Visualización del dataset IRIS",
       subtitle = "Análisis de variables más informativas para la clasificación de flores",
       caption = "Sistemas Inteligentes para gestión en la empresa 2021",
       color = "Variedad") 
  