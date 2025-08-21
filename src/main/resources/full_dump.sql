-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: caredesk_db
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table admins
--

LOCK TABLES admins WRITE;
/*!40000 ALTER TABLE admins DISABLE KEYS */;
INSERT INTO admins VALUES (1,'2025-08-08 17:37:15.163316',NULL,'123','admin');
/*!40000 ALTER TABLE admins ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table article_categories
--

LOCK TABLES article_categories WRITE;
/*!40000 ALTER TABLE article_categories DISABLE KEYS */;
INSERT INTO article_categories VALUES (1,'General FAQ articles','General'),(2,'Articles about troubleshooting and support','Technical Support'),(3,'Articles about billing, invoices, and payments','Billing');
/*!40000 ALTER TABLE article_categories ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table articles
--

LOCK TABLES articles WRITE;
/*!40000 ALTER TABLE articles DISABLE KEYS */;
INSERT INTO articles VALUES (22,'Question: I forgot my password. How can I reset it?\r\n\r\nAnswer:\r\nYou can reset your password by following these steps:\r\n\r\nGo to the login page of the system.\r\n\r\nClick on “Forgot Password?” below the password field.\r\n\r\nEnter your registered email address and click Submit.\r\n\r\nCheck your email inbox for a password reset link. (If you don’t see it, check your spam/junk folder.)\r\n\r\nClick the link and enter a new password. Make sure it meets the system’s security requirements:\r\n\r\nMinimum 8 characters\r\n\r\nAt least one uppercase letter\r\n\r\nAt least one number or special character\r\n\r\nSave your new password and log in.\r\n\r\nTips:\r\n\r\nAvoid using easily guessable passwords (like birthdays or “password123”).\r\n\r\nIf you do not receive the reset email, contact customer support for further assistance.','2025-08-21 07:16:07.433617','How do I reset my password?',NULL,2,1),(23,'Question: I am trying to log in, but I can’t access the system. What should I do?\r\n\r\nAnswer:\r\nThere could be several reasons why you are unable to access the system:\r\n\r\nIncorrect login credentials: Double-check your username and password. Use the password reset procedure if necessary.\r\n\r\nAccount locked: After multiple failed login attempts, accounts may be temporarily locked. Wait 15–30 minutes or contact support to unlock your account.\r\n\r\nBrowser issues: Clear your browser cache and cookies or try a different browser.\r\n\r\nSystem maintenance: Check if the system is undergoing maintenance by visiting the status page or system notifications.\r\n\r\nNetwork issues: Ensure you have a stable internet connection. Try restarting your router if needed.\r\n\r\nIf none of these steps resolve the issue, contact customer support with your username and a description of the problem.','2025-08-21 07:16:37.215695','Why can’t I access the system?',NULL,1,1),(24,'Question: I encountered an error while using the system. How can I report it?\r\n\r\nAnswer:\r\nTo report a technical problem, follow these steps:\r\n\r\nLog in to the customer care system (if possible).\r\n\r\nNavigate to the “Support” or “Report an Issue” section.\r\n\r\nFill out the form with the following details:\r\n\r\nDescription of the issue\r\n\r\nSteps to reproduce the problem\r\n\r\nScreenshots or error messages (if available)\r\n\r\nDate and time the problem occurred\r\n\r\nSubmit the report. You will receive a confirmation email with a ticket number.\r\n\r\nTips:\r\n\r\nBe as detailed as possible; this helps the support team resolve your issue faster.\r\n\r\nKeep your ticket number handy for reference during follow-ups.','2025-08-21 07:17:04.543788','How do I report a technical problem?',NULL,3,1);
/*!40000 ALTER TABLE articles ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-21 13:03:32