# **Mutation Testing on Library Management System**

### **Team Members**
- **Manasi Purkar** (Roll Number: MT2023158)  
- **Pracheti Bhale** (Roll Number: MT2023155)  

### **Course**: CSE 731 - Software Testing  
### **Instructor**: Prof. Meenakshi D Souza  

---

## **Project Overview**
This project implements **mutation testing** on a Library Management System to evaluate the quality of its test cases. Mutation testing is a **white-box testing technique** used to identify weak test cases, improve test suite robustness, and ensure high code quality.

---

## **Tools Used**
### **1. JUnit**
- A Java testing framework for writing unit and integration test cases.

### **2. PIT (Pitest)**
- A mutation testing tool for Java.
- **Features**:
  - Generates mutants based on predefined mutation operators.
  - Produces a **mutation score** to evaluate test suite effectiveness.
- **Commands**:
  - To run test cases:
    ```bash
    mvn clean test
    ```
  - To execute mutation testing:
    ```bash
    mvn org.pitest:pitest-maven:mutationCoverage
    ```

---
### **Mutation Operators**  
- **Unit-Level**: Includes `MATH`, `INCREMENTS`, `TRUE_RETURNS`, and `CONDITIONALS_BOUNDARY` to test isolated methods.  
- **Integration-Level**: Includes `REMOVE_CONDITIONALS`, `NULL_RETURNS`, and `VOID_METHOD_CALLS` to test interactions between components.  

---
### **Result**
- Line Coverage - 99%
- Mutation Coverage - 89%
  
---
### **Contributions**
1. Manasi Purkar (MT2023158) - Installation of pit, creating codebase of library
management system, written unit level test cases for library and library
management classes, written integration test cases for main class, library
management class and book class

3. Pracheti Bhale (MT2023155) - Installation of pit, creating codebase of library
management system, written unit test cases for classes Book and User, written
integration test cases for user class and library class
