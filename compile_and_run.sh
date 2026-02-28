#!/bin/bash
echo "============================================"
echo "  Compiling Smart City Application..."
echo "============================================"
cd src
javac SmartCityApplication.java module1/*.java module2/*.java module3/*.java
if [ $? -ne 0 ]; then
    echo ""
    echo "Compilation FAILED! Please check for errors."
    exit 1
fi
echo "Compilation successful!"
echo ""
echo "============================================"
echo "  Running Smart City Application..."
echo "============================================"
echo ""
java SmartCityApplication
cd ..
