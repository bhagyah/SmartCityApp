@echo off
echo ============================================
echo   Compiling Smart City Application (GUI)...
echo ============================================
cd src
"C:\Users\BHAGYA\.vscode\extensions\redhat.java-1.53.0-win32-x64\jre\21.0.10-win32-x86_64\bin\javac.exe" gui\MainGUI.java gui\RoutePlannerGUI.java gui\DataSorterGUI.java gui\PerformanceAnalyzerGUI.java module1\*.java module2\*.java module3\*.java
if %errorlevel% neq 0 (
    echo.
    echo Compilation FAILED! Please check for errors.
    pause
    exit /b 1
)
echo Compilation successful!
echo.
echo ============================================
echo   Launching Smart City Application (GUI)...
echo ============================================
echo.
"C:\Users\BHAGYA\.vscode\extensions\redhat.java-1.53.0-win32-x64\jre\21.0.10-win32-x86_64\bin\java.exe" gui.MainGUI
cd ..
pause
