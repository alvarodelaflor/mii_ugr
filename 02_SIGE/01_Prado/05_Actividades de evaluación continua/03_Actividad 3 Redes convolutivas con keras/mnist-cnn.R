## -------------------------------------------------------------------------------------
## Sistemas Inteligentes para la Gestión en la Empresa
## Curso 2020-2021
## Juan Gómez Romero
## -------------------------------------------------------------------------------------

library(tidyverse)
library(keras)

## -------------------------------------------------------------------------------------
## Cargar y pre-procesar datos

# Cargar MNIST
mnist <- dataset_mnist()

x_train <- mnist$train$x
y_train <- mnist$train$y
x_test  <- mnist$test$x
y_test  <- mnist$test$y

# Redimensionar imagenes
x_train <- array_reshape(x_train, c(nrow(x_train), 28, 28, 1))  # 60.000 matrices 28x28x1
x_test  <- array_reshape(x_test,  c(nrow(x_test),  28, 28, 1))  # 60.000 matrices 28x28x1

# Reescalar valores de imagenes a [0, 255]
x_train <- x_train / 255
x_test  <- x_test  / 255

# Crear 'one-hot' encoding
y_train <- to_categorical(y_train, 10)
y_test  <- to_categorical(y_test,  10)

## -------------------------------------------------------------------------------------
## Crear modelo

# Definir arquitectura
model <- keras_model_sequential() %>% 
  layer_conv_2d(32, kernel_size=c(3, 3), activation="relu", input_shape = c(28, 28, 1)) %>%
  layer_max_pooling_2d(pool_size=c(2, 2)) %>%
  layer_conv_2d(64, kernel_size=c(3, 3), activation="relu") %>%
  layer_max_pooling_2d(pool_size=c(2, 2)) %>%
  layer_flatten() %>%
  layer_dropout(0.5) %>%
  layer_dense(32, activation="relu", kernel_constraint=constraint_maxnorm(max_value = 6)) %>%
  layer_dropout(0.3) %>%
  layer_dense(16, activation="relu", kernel_constraint=constraint_maxnorm(max_value = 3)) %>%
  layer_dense(10, activation="softmax")

summary(model)

# Compilar modelo
model %>% compile(
  loss = 'categorical_crossentropy',
  optimizer =  optimizer_adam(),
  metrics = c('accuracy')
)

# Entrenamiento
history <- model %>% 
  fit(
    x_train, y_train, 
    epochs = 10, 
    batch_size = 128,
    validation_split = 0.1
  )

# Guardar modelo (HDF5)
model %>% save_model_hdf5("minist-cnn.h5")

# Visualizar entrenamiento
plot(history)

## -------------------------------------------------------------------------------------
## Evaluar modelo con datos de validación

# Calcular metrica sobre datos de validación
model %>% evaluate(x_test, y_test)

# Obtener predicciones de clase
predictions <- model %>% 
  predict_classes(x_test)

# Crear matriz de confusión
library(caret)
cm <- confusionMatrix(as.factor(mnist$test$y), as.factor(predictions))
cm_prop <- prop.table(cm$table)
plot(cm$table)

library(scales)
cm_tibble <- as_tibble(cm$table)
ggplot(data = cm_tibble) + 
  geom_tile(aes(x=Reference, y=Prediction, fill=n), colour = "white") +
  geom_text(aes(x=Reference, y=Prediction, label=n), colour = "white") +
  scale_fill_continuous(trans = 'reverse')

# Puntuación
scores <- model %>% evaluate(
  x_test, y_test, verbose = 0
)

# Output metrics
cat('Test loss:', scores[[1]], '\n')
cat('Test accuracy:', scores[[2]], '\n')

# Cambios realizados:

# Arquitectura de la red: combinación de la red convolucional de la actividad anterior con redes convolutivas

# Algoritmos de optimización: tras las pruebas realizadas el optimizador adam ha mostrado el mejor desempeño
# Como función de pérdida se ha mantenido "categorical_crossentropy"

# Hiperparámetros (tasa de aprendizaje):

# Otros aspectos: número de epochs, tamaño del batch, etc.
# Hemos aumentodo el número de epochs a 10. A parir de ese valor se produce sobreaprendizaje
# Se ha reducido el valor de "validation_split" a 0.1

