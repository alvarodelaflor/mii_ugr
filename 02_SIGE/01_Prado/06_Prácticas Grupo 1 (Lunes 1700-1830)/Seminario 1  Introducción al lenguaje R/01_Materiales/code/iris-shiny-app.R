library(shiny)
library(shinythemes)
library(tidyverse)

# Leer datos
iris_data <- read_delim("iris.csv", delim = " ")
head(iris)
names(iris_data) <- make.names(names(iris_data), unique=TRUE)

# Definir interfaz
ui <- fluidPage(theme = shinytheme("lumen"),
                titlePanel("Iris : Shiny App"),
                sidebarLayout(
                  sidebarPanel(
                    
                    selectInput("x_variable", label = "Variable x:",
                                choices = c("petal.length", "petal.width", "sepal.length", "sepal.width"), 
                                selected = "petal.length"),
                    
                    selectInput("y_variable", label = "Variable y:",
                                choices = c("petal.length", "petal.width", "sepal.length", "sepal.width"), 
                                selected = "petal.width")
                  ),
                  
                  # Gráfico con panel principal
                  mainPanel(
                    plotOutput(outputId = "dispersion", height = "300px"),
                    textOutput(outputId = "descripcion")
                  )
                )
)

# Define funciones del servidor
server <- function(input, output) {
  
  # Crear el gráfico que espera la función 
  output$dispersion <- renderPlot({
    ggplot(data = iris_data) + 
      geom_point(aes_string(x = input$x_variable, y = input$y_variable, color = "class", shape = "class"))  +
      labs(x = input$x_variable, y = input$y_variable) +  
      scale_color_discrete(name ="Clase", labels=c("Iris Setosa", "Iris Versicolor", "Iris Virginica")) +
      scale_shape_discrete(name ="Clase", labels=c("Iris Setosa", "Iris Versicolor", "Iris Virginica"))
  })
  
  # Descripcion
  output$descripcion <- renderText({
    paste("Gráfico de dispersión", input$x_variable, "vs", input$y_variable)
  })
}

# Create Shiny object
shinyApp(ui = ui, server = server)
