# Install script for directory: /home/bladestery/Downloads/opencv-3.2.0

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/install")
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

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "dev" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/sdk/native/jni/include/opencv2" TYPE FILE FILES "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/cvconfig.h")
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "dev" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/sdk/native/jni/include/opencv2" TYPE FILE FILES "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/opencv2/opencv_modules.hpp")
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "dev" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/sdk/native/jni" TYPE FILE FILES "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/unix-install/OpenCV.mk")
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "dev" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/sdk/native/jni" TYPE FILE FILES "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/unix-install/OpenCV-armeabi.mk")
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "dev" OR NOT CMAKE_INSTALL_COMPONENT)
  if(EXISTS "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/sdk/native/jni/abi-armeabi/OpenCVModules.cmake")
    file(DIFFERENT EXPORT_FILE_CHANGED FILES
         "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/sdk/native/jni/abi-armeabi/OpenCVModules.cmake"
         "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/CMakeFiles/Export/sdk/native/jni/abi-armeabi/OpenCVModules.cmake")
    if(EXPORT_FILE_CHANGED)
      file(GLOB OLD_CONFIG_FILES "$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/sdk/native/jni/abi-armeabi/OpenCVModules-*.cmake")
      if(OLD_CONFIG_FILES)
        message(STATUS "Old export file \"$ENV{DESTDIR}${CMAKE_INSTALL_PREFIX}/sdk/native/jni/abi-armeabi/OpenCVModules.cmake\" will be replaced.  Removing files [${OLD_CONFIG_FILES}].")
        file(REMOVE ${OLD_CONFIG_FILES})
      endif()
    endif()
  endif()
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/sdk/native/jni/abi-armeabi" TYPE FILE FILES "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/CMakeFiles/Export/sdk/native/jni/abi-armeabi/OpenCVModules.cmake")
  if("${CMAKE_INSTALL_CONFIG_NAME}" MATCHES "^([Dd][Ee][Bb][Uu][Gg])$")
    file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/sdk/native/jni/abi-armeabi" TYPE FILE FILES "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/CMakeFiles/Export/sdk/native/jni/abi-armeabi/OpenCVModules-debug.cmake")
  endif()
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "dev" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/sdk/native/jni/abi-armeabi" TYPE FILE FILES
    "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/unix-install/OpenCVConfig-version.cmake"
    "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/unix-install/abi-armeabi/OpenCVConfig.cmake"
    )
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "dev" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/sdk/native/jni" TYPE FILE FILES
    "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/unix-install/OpenCVConfig-version.cmake"
    "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/unix-install/OpenCVConfig.cmake"
    )
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "dev" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/sdk/native/jni" TYPE FILE FILES "/home/bladestery/Downloads/opencv-3.2.0/platforms/android/android.toolchain.cmake")
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "libs" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/." TYPE FILE PERMISSIONS OWNER_READ GROUP_READ WORLD_READ FILES "/home/bladestery/Downloads/opencv-3.2.0/LICENSE")
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "libs" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/." TYPE FILE PERMISSIONS OWNER_READ GROUP_READ WORLD_READ FILES "/home/bladestery/Downloads/opencv-3.2.0/platforms/android/README.android")
endif()

if(NOT CMAKE_INSTALL_LOCAL_ONLY)
  # Include the install script for each subdirectory.
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/3rdparty/libtiff/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/3rdparty/libjpeg/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/3rdparty/libwebp/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/3rdparty/libjasper/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/3rdparty/libpng/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/3rdparty/openexr/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/3rdparty/carotene/hal/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/include/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/modules/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/doc/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/data/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/samples/cmake_install.cmake")
  include("/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/platforms/android/service/cmake_install.cmake")

endif()

if(CMAKE_INSTALL_COMPONENT)
  set(CMAKE_INSTALL_MANIFEST "install_manifest_${CMAKE_INSTALL_COMPONENT}.txt")
else()
  set(CMAKE_INSTALL_MANIFEST "install_manifest.txt")
endif()

string(REPLACE ";" "\n" CMAKE_INSTALL_MANIFEST_CONTENT
       "${CMAKE_INSTALL_MANIFEST_FILES}")
file(WRITE "/home/bladestery/Sapphire/example_apps/AndroidStudioMinnie/sapphire/.externalNativeBuild/cmake/debug/armeabi/${CMAKE_INSTALL_MANIFEST}"
     "${CMAKE_INSTALL_MANIFEST_CONTENT}")
