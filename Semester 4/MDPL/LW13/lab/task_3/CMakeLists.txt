cmake_minimum_required(VERSION 3.10.0)
project(pr_3 VERSION 0.1.0 LANGUAGES C CXX)

# Добавляем исполняемый файл
add_executable(pr_3 WIN32 6-8.cpp 6-8.rc)

# Подключаем необходимые библиотеки
target_link_libraries(pr_3 user32 gdi32 comdlg32)

# Настройки для Windows приложения
set_target_properties(pr_3 PROPERTIES
    WIN32_EXECUTABLE TRUE
)

include(CTest)
enable_testing()

set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})
include(CPack)
