create table Salary_Level (
  JOB_LEVEL char(2), 
  SALARY_MINIMUM double,
  SALARY_MAXIMUM double,
  primary key (JOB_LEVEL));

insert into Salary_Level values (
	'52', 1900.00, 2400.00);
insert into Salary_Level values (
	'54', 2400.00, 2900.00);
insert into Salary_Level values (
	'55', 2900.00, 3400.00);
insert into Salary_Level values (
	'57', 3400.00, 3900.00);
insert into Salary_Level values (
	'59', 3900.00, 4400.00);

