# SISBOL – Scholarship Registration System (Java, 2010)

> **Note:** This repo contains the original Portuguese version.
> The English-translated version is here:
> [https://github.com/celsomsilva/project-java-2010_en](https://github.com/celsomsilva/project-java-2010_en)

SISBOL is a **Java desktop system** I built in 2010 during my work at **UERJ** (Postgraduate Program in Computer Engineering – Geomatics).
It manages scholarships, students, advisors, and report generation using **JasperReports** and a **MySQL database**.

This was built in the Java SE 6/7 era — Swing UI, JDBC, Eclipse, and a lot of manual wiring.

---

## What the System Does

* Login screen with database authentication
* Register/edit/remove:

  * Students
  * Advisors
  * Scholarships
  * Scholarship holders
* Generate printable reports (JasperReports/iReport)
* Basic role-based access
* Simple Swing interface

---

## Tech Used (2010 stack)

* Java SE 6/7
* Swing
* MySQL 5.x
* JDBC
* JasperReports / iReport 3.x
* Eclipse

---

## Project Structure

Below is the structure as originally written (simplified view):

```
Project-Java-2010/
  control/
    AlunoControl.java
    BolsistaControl.java
    BolsaControl.java
    ProfessorControl.java

  dao/
    Aluno.java
    AlunoDAO.java
    Bolsista.java
    BolsistaDAO.java
    Bolsa.java
    BolsaDAO.java
    Pessoa.java
    Professor.java
    ProfessorDAO.java
    DBConnection.java

  view/
    AlunoWindow.java
    BolsaWindow.java
    BolsistaWindow.java
    ProfessorWindow.java
    PessoaWindow.java
    MainWindow.java
    WindowFrame.java
    MyPanel.java
    PanelButtons.java
    geomatica_background.jpg

  LICENSE
  README.md
```

Only the `.java` files are included here.
Jasper/iReport templates and SQL scripts are not part of this repo.

---

## How to Run (if you want to recreate the environment)

1. Install **MySQL 5.x**
2. Install **JasperReports/iReport 3.x**
3. Create the DB schema (not included)
4. Compile the Java files (`javac`)
5. Adjust file paths for your Jasper report templates
6. Run the application and log in with your MySQL credentials

On successful login, the main window loads and all registration/report menus become available.

---

## Notes

* Reports depend on **iReport/JasperReports**, so you’ll need to configure this manually.
* `.jrxml` and `.jasper` files were removed due to licensing and project constraints.
* DB scripts were omitted because the system was used internally at UERJ.
* This repo is preserved as a **historical snapshot** of the system as it existed in 2010 — Swing UI, DAOs, manual SQL, early reporting tools.

---

## Author

This project was developed by an engineer and data scientist with a background in:

* Postgraduate degree in **Data Science and Analytics (USP)**
* Bachelor's degree in **Computer Engineering (UERJ)**
* Special interest in statistical models, interpretability, and applied AI

---

## Contact  

- [LinkedIn](https://linkedin.com/in/celso-m-silva)  
- Or open an [issue](https://github.com/celsomsilva/project-java-2010/issues)


