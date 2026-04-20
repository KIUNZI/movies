@echo off
cls

set Path=%NATIVE_JAVA_HOME%\bin;%PATH%
set JAVA_HOME=%NATIVE_JAVA_HOME%

REM Load Visual Studio 2022 C++ build environment
call "C:\Program Files (x86)\Microsoft Visual Studio\2022\BuildTools\VC\Auxiliary\Build\vcvars64.bat"

REM Optional sanity check
where cl || exit /b 1

REM Run Quarkus native build
gradlew build -Dquarkus.package.type=native -Dquarkus.profile=prod -Dquarkus.kubernetes.namespace=application