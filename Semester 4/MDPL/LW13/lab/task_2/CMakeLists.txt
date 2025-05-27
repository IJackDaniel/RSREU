cmake_minimum_required(VERSION 3.10.0)
project(pr_2 VERSION 0.1.0 LANGUAGES C CXX)

# Добавляем исполняемый файл
add_executable(pr_2 WIN32 6-7.cpp 6-7.rc)

# Подключаем необходимые библиотеки
target_link_libraries(pr_2 user32 gdi32 comdlg32)

# Настройки для Windows приложения
set_target_properties(pr_2 PROPERTIES
    WIN32_EXECUTABLE TRUE
)

include(CTest)
enable_testing()

set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})
include(CPack)
