@echo off
echo ============================================
echo   Compiling Smart City Application...
echo ============================================
cd src
javac SmartCityApplication.java module1\*.java module2\*.java module3\*.java
if %errorlevel% neq 0 (
    echo.
    echo Compilation FAILED! Please check for errors.
    pause
    exit /b 1
)
echo Compilation successful!
echo.
echo ============================================
echo   Running Smart City Application...
echo ============================================
echo.
java SmartCityApplication
cd ..
pause
