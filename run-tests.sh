#!/bin/bash

# Print header
echo "=== Running All Tests ==="
echo

# Run backend tests
echo "=== Running Backend Tests ==="
cd backend
./mvnw test
BACKEND_RESULT=$?
cd ..
echo

# Run frontend tests
echo "=== Running Frontend Tests ==="
cd frontend
npm test
FRONTEND_RESULT=$?
cd ..
echo

# Check results
if [ $BACKEND_RESULT -eq 0 ] && [ $FRONTEND_RESULT -eq 0 ]; then
    echo "=== All Tests Passed ==="
    exit 0
else
    echo "=== Tests Failed ==="
    echo "Backend tests exit code: $BACKEND_RESULT"
    echo "Frontend tests exit code: $FRONTEND_RESULT"
    exit 1
fi 