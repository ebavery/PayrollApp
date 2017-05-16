create table Employee (
  EMPNO integer not null,
  LASTNAME varchar(25), 
  FIRSTNAME varchar(25), 
  MI char(1), 
  STREET varchar(30), 
  CITY varchar(25),
  STATE char(2), 
  ZIP char(5),
  PHONE char(10),
  primary key (EMPNO));

insert into Employee values (
	1, 'Johnson', 'James', 'L', '99 Kingston Street', 'Norwalk', 'CT', '06488', '2035551212');
insert into Employee values (
	2, 'Albertson', 'Mary', 'A', '45 Maple Street', 'Stamford', 'CT', '06312', '2035551111');
insert into Employee values (
	3, 'Jones', 'Harry', 'T', '129 Elm Rd.', 'Darien', 'CT', '06411', '2035551111');
insert into Employee values (
	4, 'Baker', 'Timothy', 'C', '23 Orchard Ave.', 'Bridgeport', 'CT', '06498', '2035552222');
insert into Employee values (
	5, 'Zucker', 'Matthew', 'D', '111 Park Avenue', 'Wilton', 'CT', '06485', '2035554321');
insert into Employee values (
	6, 'Nelson', 'Betty', 'M', '32 Davidson Drive', 'White Plains', 'NY', '10532', '9145557890');
insert into Employee values (
	7, 'Smith', 'James', 'S', '111 Main Street', 'Danbury', 'CT', '06810', '2035552345');
insert into Employee values (
	8, 'Klein', 'Stephen', 'R', '89 Mockingbird Lane Apt. 2', 'Ridgefield', 'CT', '06833', '2035554567');
insert into Employee values (
	9, 'Smith', 'James', 'T', '85 Oak Street', 'Redding', 'CT', '06811', '2035559876');
insert into Employee values (
	10, 'Baker', 'Thelma', 'R', '23 Orchard Ave.', 'Bridgeport', 'CT', '06498', '2035552222');

