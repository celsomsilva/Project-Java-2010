
# SISBOL – Scholarship Registration System

## 📖 Overview

This repository contains the source code for **SISBOL (Scholarship Registration System)** — a Java desktop application developed in 2010 as part of an project at **UERJ (Rio de Janeiro State University)**, within the **Postgraduate Program in Computer Engineering – Geomatics Concentration Area (PGEC)**.

The system was designed to manage the registration and control of scholarships, students, advisors, and scholarship holders, along with report generation through **JasperReports/iReport** integrated with a **MySQL database**.

---

## 🎯 Project Objective

Develop a robust, efficient Java desktop application to:
- Register, update, and delete records of students, scholarships, advisors, and scholarship holders.
- Generate and print useful departmental reports using JasperReports.
- Securely manage database access via user login integrated with **MySQL**.

---

## ⚙️ Features

- **User Authentication**
- **CRUD operations** for:
  - Students
  - Advisors
  - Scholarships
  - Scholarship Holders
- **Dynamic Report Generation** using **iReport/JasperReports**
- **Role-based Access Control**
- **Simple and lightweight Swing GUI**

---

## 📦 Tech Stack

- Java SE 6/7
- Swing (UI)
- JDBC (Database connectivity)
- MySQL 5.x
- JasperReports / iReport
- Eclipse IDE

---

## 📂 Project Structure

Only the `.java` source files are included in this repository due to project constraints and Jasper/iReport licensing.

To fully use the project:
1. Install **MySQL 5.x**
2. Install **JasperReports/iReport 3.x**
3. Set up the database schema (SQL scripts not included here)
4. Compile `.java` files using `javac`
5. Configure the report paths inside the source code as per your local environment

---

## 📝 Usage Instructions

When launching the application:
- A login window prompts for **MySQL username and password**.
- Upon successful authentication:
  - The main system window appears
  - Menus for **File**, **Registration**, **Reports**, and **Help** are enabled.
- Data operations and report generation become accessible via the GUI.

---

## 📜 Licensing

**MIT License**

```
Copyright (c) 2010-2025

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software...
```

---

## 📌 Important Notes

- The system heavily relies on **JasperReports/iReport** for reporting — which requires external configuration not present in this repository.
- Original .jrxml and compiled .jasper files were omitted.
- Database creation scripts were excluded for privacy and contractual reasons.
- Historical context: This system prototype was part of internal tooling used at **UERJ's Geomatics PGEC program** for academic and administrative scholarship management.

---

## 📜 Projeto em Português 🇧🇷

### 📖 Apresentação

Este repositório contém o código-fonte do **SISBOL (Sistema de Cadastramento de Bolsas)** — aplicação desktop Java desenvolvida em 2010 como projeto na **UERJ (Universidade do Estado do Rio de Janeiro)**, no âmbito do **Programa de Pós-Graduação em Engenharia de Computação – Geomática (PGEC)**.

---

### 🎯 Objetivo

Desenvolver um sistema em Java capaz de:
- Cadastrar, atualizar e excluir dados de alunos, professores, bolsas e bolsistas.
- Gerar e imprimir relatórios administrativos.
- Controlar acesso via login com integração ao **MySQL**.

---

### ⚙️ Tecnologias

- Java SE 6/7
- Swing
- JDBC
- MySQL 5.x
- JasperReports / iReport
- Eclipse IDE

---

### 📦 Organização

Foram incluídos apenas arquivos `.java` neste repositório. Arquivos Jasper/iReport e scripts SQL não foram disponibilizados por motivos de licença e confidencialidade.

Para uso:
1. Instale o **MySQL**
2. Instale o **iReport**
3. Compile os `.java` via `javac`
4. Configure os caminhos dos relatórios no código-fonte

---

## 📞 Contato

Para dúvidas ou informações:

- 💻 [https://linkedin.com/in/celso-m-silva]

