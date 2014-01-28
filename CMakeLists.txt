CMAKE_MINIMUM_REQUIRED(VERSION 2.8.2)
find_package(Java)
include(UseJava)

set(JAVA_SRC_DIR src)
set(JAVA_SRC ${JAVA_SRC_DIR}/linkjvm/debugger/client/*.java)
set(CMAKE_JAVA_EXECUTABLE linkjvm/debugger/client/Debugger)

add_jar(DebuggerClient.jar ${JAVA_SRC})
