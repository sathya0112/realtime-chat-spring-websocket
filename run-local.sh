#!/bin/bash

# Run the application with local profile (uses application-local.yml)
# This profile contains your local MySQL credentials

echo "🚀 Starting Real-Time Chat Application (Local Profile)..."
echo "📊 Using local MySQL database configuration"
echo ""

# Check if application-local.yml exists
if [ ! -f "src/main/resources/application-local.yml" ]; then
    echo "❌ Error: application-local.yml not found!"
    echo "📝 Please copy application-local.yml.example to application-local.yml"
    echo "   and update it with your local MySQL credentials:"
    echo ""
    echo "   cp src/main/resources/application-local.yml.example src/main/resources/application-local.yml"
    echo ""
    exit 1
fi

# Run with local profile
mvn spring-boot:run -Dspring-boot.run.profiles=local

