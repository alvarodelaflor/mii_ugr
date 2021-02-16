# Apriori

# Data Preprocessing
# install.packages('arules')
library(arules)
# Hay que ajustar "Set Working Directory" a la carpeta adecuada
dataset = read.csv('Market_Basket_Optimisation.csv', header = FALSE)
dataset
dataset = read.transactions('Market_Basket_Optimisation.csv', sep = ',', rm.duplicates = TRUE)
dataset
summary(dataset)
itemFrequencyPlot(dataset, topN = 10)

# Training Apriori on the dataset
rules = apriori(data = dataset, parameter = list(support = 0.004, confidence = 0.2))

# Visualising the results
inspect(sort(rules, by = 'lift')[1:10])
