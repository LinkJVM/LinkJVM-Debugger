CMAKE_MINIMUM_REQUIRED(VERSION 2.8.2)
project(Java)
find_package(Java)

include(UseJava)
include(jar.cmake)

set(JAVA_SRC_DIR src)
set(JAVA_SRC ${JAVA_SRC_DIR}/linkjvm/debugger/client/*.java)

add_executable_jar(DebuggerClient linkjvm.debugger.client.Debugger ${JAVA_SRC})
