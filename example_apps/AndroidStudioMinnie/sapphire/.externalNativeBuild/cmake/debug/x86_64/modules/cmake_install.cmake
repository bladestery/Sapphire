# Install script for directory: /home/bladestery/Downloads/opencv-3.2.0/modules

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/install")
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
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/calib3d/.calib3d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/core/.core/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudaarithm/.cudaarithm/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudabgsegm/.cudabgsegm/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudacodec/.cudacodec/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudafeatures2d/.cudafeatures2d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudafilters/.cudafilters/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudaimgproc/.cudaimgproc/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudalegacy/.cudalegacy/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudaobjdetect/.cudaobjdetect/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudaoptflow/.cudaoptflow/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudastereo/.cudastereo/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudawarping/.cudawarping/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/cudev/.cudev/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/features2d/.features2d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/flann/.flann/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/highgui/.highgui/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/imgcodecs/.imgcodecs/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/imgproc/.imgproc/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/java/.java/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/ml/.ml/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/objdetect/.objdetect/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/photo/.photo/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/python/.python/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/shape/.shape/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/stitching/.stitching/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/superres/.superres/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/ts/.ts/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/video/.video/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/videoio/.videoio/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/videostab/.videostab/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/viz/.viz/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/world/.world/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/core/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/flann/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/imgproc/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/ml/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/photo/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/video/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/imgcodecs/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/shape/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/videoio/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/highgui/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/objdetect/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/superres/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/ts/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/features2d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/calib3d/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/stitching/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/x86_64/modules/videostab/cmake_install.cmake")

endif()

