library(caret)
library(randomForest)
library(e1071)
library(mice)

#load data
bc_data <- read.table("/media/karel/Trabajo/karel/decsai/2019-2020/TID/clases/TID/TID/Datasets-practicas/breast-cancer-wisconsin.data.txt", 
                      header = FALSE, 
                      sep = ",")
colnames(bc_data) <- c("sample_code_number", 
                       "clump_thickness", 
                       "uniformity_of_cell_size", 
                       "uniformity_of_cell_shape", 
                       "marginal_adhesion", 
                       "single_epithelial_cell_size", 
                       "bare_nuclei", 
                       "bland_chromatin", 
                       "normal_nucleoli", 
                       "mitosis", 
                       "classes")

bc_data$classes <- ifelse(bc_data$classes == "2", "benign",
                          ifelse(bc_data$classes == "4", "malignant", NA))

bc_data[bc_data == "?"] <- NA

# how many NAs are in the data
length(which(is.na(bc_data)))

# how many samples would we loose, if we removed them?
nrow(bc_data)

# impute missing data


bc_data[,2:10] <- apply(bc_data[, 2:10], 2, function(x) as.numeric(as.character(x)))
dataset_impute <- mice(bc_data[, 2:10],  print = FALSE)
bc_data <- cbind(bc_data[, 11, drop = FALSE], mice::complete(dataset_impute, 1))

bc_data$classes <- as.factor(bc_data$classes)

# how many benign and malignant cases are there?
summary(bc_data$classes)


# Modeling the original unbalanced data
set.seed(42)
index <- createDataPartition(bc_data$classes, p = 0.7, list = FALSE)
train_data <- bc_data[index, ]
test_data  <- bc_data[-index, ]

model_rf <- caret::train(classes ~ .,
                         data = train_data,
                         method = "rf",
                         preProcess = c("scale", "center"),
                         trControl = trainControl(method = "repeatedcv", 
                                                  number = 10, 
                                                  repeats = 10, 
                                                  verboseIter = FALSE))
final <- data.frame(actual = test_data$classes,
                    predict(model_rf, newdata = test_data, type = "prob"))
final$predict <- ifelse(final$benign > 0.5, "benign", "malignant")
cm_original <- confusionMatrix(as.factor(final$predict), test_data$classes)

# Under-sampling

ctrl <- trainControl(method = "repeatedcv", 
                     number = 10, 
                     repeats = 10, 
                     verboseIter = FALSE,
                     sampling = "down")

model_rf_under <- caret::train(classes ~ .,
                               data = train_data,
                               method = "rf",
                               preProcess = c("scale", "center"),
                               trControl = ctrl)
final_under <- data.frame(actual = test_data$classes,
                          predict(model_rf_under, newdata = test_data, type = "prob"))
final_under$predict <- ifelse(final_under$benign > 0.5, "benign", "malignant")
cm_under <- confusionMatrix(as.factor(final_under$predict), test_data$classes)

# Oversampling
ctrl <- trainControl(method = "repeatedcv", 
                     number = 10, 
                     repeats = 10, 
                     verboseIter = FALSE,
                     sampling = "up")

model_rf_over <- caret::train(classes ~ .,
                              data = train_data,
                              method = "rf",
                              preProcess = c("scale", "center"),
                              trControl = ctrl)
final_over <- data.frame(actual = test_data$classes,
                         predict(model_rf_over, newdata = test_data, type = "prob"))
final_over$predict <- ifelse(final_over$benign > 0.5, "benign", "malignant")
cm_over <- confusionMatrix(as.factor(final_over$predict), test_data$classes)
