f0 <- function(x,y){
  
  100*(y-x^2)^2+(1-x)^2
  
}

f1 <- function(x,y){
  
  (4.0-2.1*x^2+x^4/3.0)*x^2 + x*y + (4.0*y^2-4.0)*y^2
  
}

f2 <- function(x,y){
  
  20+x^2-10*cos(2*pi*x)+y^2-10*cos(2*pi*y)
  
}

f3 <- function(x,y){
  
  (x^2+y^2)/4000 - cos(x)*cos(y/sqrt(2)) + 1
  
}

#Rosenbrock
#x <- seq(from=-2.048,to=2.048,by=0.1)
#y <- seq(from=-2.048,to=2.048,by=0.1)
#z <- outer(x,y,f0)

#Six-hump camel back
#x <- seq(from=-3.0,to=3.0,by=0.1)
#y <- seq(from=-2.0,to=2.0,by=0.1)
#z <- outer(x,y,f1)

#Rastrigin
#x <- seq(from=-5.12,to=5.12,by=0.1)
#y <- seq(from=-5.12,to=5.12,by=0.1)
#z <- outer(x,y,f2)

#Griewang
x <- seq(from=-600,to=600,by=10)
y <- seq(from=-600,to=600,by=10)
z <- outer(x,y,f3)

nrz<-nrow(z)
ncz<-ncol(z)
jet.colors <-  colorRampPalette(c("midnightblue","blue",
                                  "cyan","green", "yellow","orange","red", "darkred"))
nbcol<-128
color<-jet.colors(nbcol)
zfacet<-z[-1,-1]+z[-1,-ncz]+z[-nrz,-1]+z[-nrz,-ncz]
facetcol<-cut(zfacet,nbcol)
res <- persp(x,y,z,col=color[facetcol],phi=85,theta=120)

data <- matrix(scan("C:\\Users\\catavlas\\Desktop\\points.txt"), ncol=3)

x1 = data[1:100]
x2 = data[101:200]
x3 = data[201:300] 
      
mypoints <- trans3d(x1, x2, x3, pmat=res)
points(mypoints, pch=1, col="white")

x1 = data[301:400]
x2 = data[401:500]
x3 = data[501:600]

mypoints <- trans3d(x1, x2, x3, res)
points(mypoints, pch=1, col="red")