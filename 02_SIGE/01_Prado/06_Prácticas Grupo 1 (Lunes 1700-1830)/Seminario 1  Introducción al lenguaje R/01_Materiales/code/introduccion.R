## Primeros pasos en R
# Uso de variables en R:
a <- 5
b <- 7
a + b

# Vectores:
V <- c(a, b, 9)
V[2]
W <- V + 1
mean(W)
x <- 1:5
y <- -4:0
V[1:3]

# Listas:
L <- list("a", 1, TRUE)
L[[1]]
L[1]
M <- list(letter = "a", n = 1, c = TRUE)
M
M$letter

## Data frames
q <- c("Futbol", "Baloncesto", "Tenis", "Balonmano", "Voleibol")
df <- data.frame(x, y, q)
df
names(df) <- c("Var X", "Var Y", "Sport")
df
df <- data.frame(Var_X=x, Var_Y=y, Sport=q)

df[1]
df['Var_X']
df$Var_Y
df[,1]
df[1:3, c('Var_X', 'Var_Y')]
head(df, n=2)
df[, "Sport", drop=TRUE]
df[df$Var_X > 2,]
df[df$Sport=="Tenis",]

## Funciones
hello.world <- function(firstName, secondName = "", surname) {
  print(sprintf("Hello %s %s %s!", firstName, secondName, surname))
}
hello.world("Juan", "?", "Gomez")
hello.world("Juan", surname = "Gomez")
hello.world(surname = "Gomez", firstName = "Juan")

power <- function(x) {
  r <- x * x
  return(r)
}
power(x=7)
```