mysql -u root -p -h localhost -P 3306
use mydb;

insert into  mydb.Project (latcoord, longcoord, neighborhood,  address, city, country, state, zipcode, projectName, projectNumber) values (0.0, 0.0, "Greenridge", "1369 Greenridge Trail", "Lithonia", "USA", "GA", "30058", "TestProject", "1234567890")
