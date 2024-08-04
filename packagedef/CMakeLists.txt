CMAKE_MINIMUM_REQUIRED(VERSION 3.28)

PROJECT(newhome/packagedef C)

ADD_LIBRARY(nh-package
    package.h
    package$1.h package$1.c
)

ADD_EXECUTABLE(nhp
    package.h
    package.c
)

TARGET_LINK_LIBRARIES(nhp nh-package)
