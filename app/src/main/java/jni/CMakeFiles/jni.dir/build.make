# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.8

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CMake.app/Contents/bin/cmake

# The command to remove a file.
RM = /Applications/CMake.app/Contents/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/nikhithadurvasula/desktop/jni

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/nikhithadurvasula/desktop/jni

# Include any dependencies generated for this target.
include CMakeFiles/jni.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/jni.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/jni.dir/flags.make

CMakeFiles/jni.dir/Stitcher.cpp.o: CMakeFiles/jni.dir/flags.make
CMakeFiles/jni.dir/Stitcher.cpp.o: Stitcher.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/nikhithadurvasula/desktop/jni/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/jni.dir/Stitcher.cpp.o"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/jni.dir/Stitcher.cpp.o -c /Users/nikhithadurvasula/desktop/jni/Stitcher.cpp

CMakeFiles/jni.dir/Stitcher.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/jni.dir/Stitcher.cpp.i"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/nikhithadurvasula/desktop/jni/Stitcher.cpp > CMakeFiles/jni.dir/Stitcher.cpp.i

CMakeFiles/jni.dir/Stitcher.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/jni.dir/Stitcher.cpp.s"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/nikhithadurvasula/desktop/jni/Stitcher.cpp -o CMakeFiles/jni.dir/Stitcher.cpp.s

CMakeFiles/jni.dir/Stitcher.cpp.o.requires:

.PHONY : CMakeFiles/jni.dir/Stitcher.cpp.o.requires

CMakeFiles/jni.dir/Stitcher.cpp.o.provides: CMakeFiles/jni.dir/Stitcher.cpp.o.requires
	$(MAKE) -f CMakeFiles/jni.dir/build.make CMakeFiles/jni.dir/Stitcher.cpp.o.provides.build
.PHONY : CMakeFiles/jni.dir/Stitcher.cpp.o.provides

CMakeFiles/jni.dir/Stitcher.cpp.o.provides.build: CMakeFiles/jni.dir/Stitcher.cpp.o


# Object files for target jni
jni_OBJECTS = \
"CMakeFiles/jni.dir/Stitcher.cpp.o"

# External object files for target jni
jni_EXTERNAL_OBJECTS =

jni: CMakeFiles/jni.dir/Stitcher.cpp.o
jni: CMakeFiles/jni.dir/build.make
jni: /usr/local/lib/libopencv_calib3d.a
jni: /usr/local/lib/libopencv_core.a
jni: /usr/local/lib/libopencv_features2d.a
jni: /usr/local/lib/libopencv_flann.a
jni: /usr/local/lib/libopencv_highgui.a
jni: /usr/local/lib/libopencv_imgcodecs.a
jni: /usr/local/lib/libopencv_imgproc.a
jni: /usr/local/lib/libopencv_ml.a
jni: /usr/local/lib/libopencv_objdetect.a
jni: /usr/local/lib/libopencv_photo.a
jni: /usr/local/lib/libopencv_shape.a
jni: /usr/local/lib/libopencv_stitching.a
jni: /usr/local/lib/libopencv_superres.a
jni: /usr/local/lib/libopencv_video.a
jni: /usr/local/lib/libopencv_videoio.a
jni: /usr/local/lib/libopencv_videostab.a
jni: /usr/local/lib/libopencv_objdetect.a
jni: /usr/local/lib/libopencv_calib3d.a
jni: /usr/local/lib/libopencv_features2d.a
jni: /usr/local/lib/libopencv_flann.a
jni: /usr/local/lib/libopencv_highgui.a
jni: /usr/local/lib/libopencv_ml.a
jni: /usr/local/lib/libopencv_photo.a
jni: /usr/local/lib/libopencv_video.a
jni: /usr/local/lib/libopencv_videoio.a
jni: /usr/local/lib/libopencv_imgcodecs.a
jni: /usr/local/share/OpenCV/3rdparty/lib/liblibjpeg.a
jni: /usr/local/share/OpenCV/3rdparty/lib/liblibwebp.a
jni: /usr/local/share/OpenCV/3rdparty/lib/liblibpng.a
jni: /usr/local/share/OpenCV/3rdparty/lib/liblibtiff.a
jni: /usr/local/share/OpenCV/3rdparty/lib/liblibjasper.a
jni: /usr/local/share/OpenCV/3rdparty/lib/libIlmImf.a
jni: /usr/local/lib/libopencv_imgproc.a
jni: /usr/local/lib/libopencv_core.a
jni: /usr/local/share/OpenCV/3rdparty/lib/libzlib.a
jni: /usr/local/share/OpenCV/3rdparty/lib/libippicv.a
jni: CMakeFiles/jni.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/nikhithadurvasula/desktop/jni/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable jni"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/jni.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/jni.dir/build: jni

.PHONY : CMakeFiles/jni.dir/build

CMakeFiles/jni.dir/requires: CMakeFiles/jni.dir/Stitcher.cpp.o.requires

.PHONY : CMakeFiles/jni.dir/requires

CMakeFiles/jni.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/jni.dir/cmake_clean.cmake
.PHONY : CMakeFiles/jni.dir/clean

CMakeFiles/jni.dir/depend:
	cd /Users/nikhithadurvasula/desktop/jni && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/nikhithadurvasula/desktop/jni /Users/nikhithadurvasula/desktop/jni /Users/nikhithadurvasula/desktop/jni /Users/nikhithadurvasula/desktop/jni /Users/nikhithadurvasula/desktop/jni/CMakeFiles/jni.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/jni.dir/depend

