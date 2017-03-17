cmake_minimum_required(VERSION 2.8)

project( ImageStitcher )

find_package( OpenCV )

include_directories( ${OpenCV_INCLUDE_DIRS} )

add_executable( jni Stitcher.cpp )

target_link_libraries( jni ${OpenCV_LIBS} )