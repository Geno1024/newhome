CMAKE_MINIMUM_REQUIRED(VERSION 3.25)

PROJECT(newhome/ufi C)

ADD_LIBRARY(
    nh-ufi SHARED
    ir/gree-ac.h ir/gree-ac.c
)
