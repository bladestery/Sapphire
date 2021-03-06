# Install script for directory: /home/bladestery/Downloads/opencv-3.2.0/modules

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/install")
endif()
string(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
if(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  if(BUILD_TYPE)
    string(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  else()
    set(CMAKE_INSTALL_CONFIG_NAME "Debug")
  endif()
  message(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
endif()

# Set the component getting installed.
if(NOT CMAKE_INSTALL_COMPONENT)
  if(COMPONENT)
    message(STATUS "Install component: \"${COMPONENT}\"")
    set(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  else()
    set(CMAKE_INSTALL_COMPONENT)
  endif()
endif()

# Install shared libraries without execute permission?
if(NOT DEFINED CMAKE_INSTALL_SO_NO_EXE)
  set(CMAKE_INSTALL_SO_NO_EXE "1")
endif()

if(NOT CMAKE_INSTALL_LOCAL_ONLY)
  # Include the install script for each subdirectory.
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/calib3d/.calib3d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/core/.core/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudaarithm/.cudaarithm/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudabgsegm/.cudabgsegm/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudacodec/.cudacodec/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudafeatures2d/.cudafeatures2d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudafilters/.cudafilters/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudaimgproc/.cudaimgproc/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudalegacy/.cudalegacy/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudaobjdetect/.cudaobjdetect/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudaoptflow/.cudaoptflow/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudastereo/.cudastereo/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudawarping/.cudawarping/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/cudev/.cudev/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/features2d/.features2d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/flann/.flann/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/highgui/.highgui/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/imgcodecs/.imgcodecs/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/imgproc/.imgproc/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/java/.java/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/ml/.ml/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/objdetect/.objdetect/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/photo/.photo/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/python/.python/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/shape/.shape/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/stitching/.stitching/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/superres/.superres/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/ts/.ts/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/video/.video/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/videoio/.videoio/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/videostab/.videostab/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/viz/.viz/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/world/.world/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/core/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/flann/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/imgproc/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/ml/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/photo/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/video/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/imgcodecs/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/shape/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/videoio/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/highgui/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/objdetect/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/superres/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/ts/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/features2d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/calib3d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/stitching/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/mips/modules/videostab/cmake_install.cmake")

endif()

