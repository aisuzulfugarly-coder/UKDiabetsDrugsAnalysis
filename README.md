# UK Diabetes Drugs Analysis

## Project Overview
A Java-based project for analyzing UK diabetes prescription data using Apache Commons CSV.  
The project reads prescription records, aggregates total items per drug, identifies the top 5 most prescribed drugs, and analyzes 12-month trends.

## Features
•⁠  ⁠Parse CSV files with headers using Apache Commons CSV
•⁠  ⁠Aggregate total prescriptions per drug
•⁠  ⁠Identify top 5 most prescribed drugs
•⁠  ⁠Analyze 12-month trends and calculate percentage changes
•⁠  ⁠Output clear console reports with trend indicators (📈📉➡️)

## Technologies
•⁠  ⁠Java 17+
•⁠  ⁠Apache Commons CSV
•⁠  ⁠Collections, Maps, and basic algorithms for data aggregation

## Usage
1.⁠ ⁠Clone the repository
2.⁠ ⁠Place your CSV file in the ⁠ data/ ⁠ folder
3.⁠ ⁠Open ⁠ src/DiabetesAnalyzer.java ⁠ and update the ⁠ csvFile ⁠ path if needed
4.⁠ ⁠Compile and run:
```bash
javac src/DiabetesAnalyzer.java
java -cp src DiabetesAnalyzer
