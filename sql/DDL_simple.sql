create table Publisher
	(Email_address		varchar(15),
       	Publisher_name		varchar(15),
	 Banking_account		numeric(10,0),
	 Publisher_ID		numeric(4,0),
	 primary key (Banking_account, Publisher_ID)
	);

create table Owners
	(Owners_ID			numeric(4,0),
	 Owners_name		varchar(20), 
	 Address		    varchar(15), 
	 Phone_number		numeric(12,2),
	 primary key (Owners_ID)
	);

create table Store
	(Store_ID			numeric(4,0), 
	 Store_name			varchar(20), 
	 Address			varchar(20), 
	 Owners_ID			numeric(4,0),
	 primary key (Store_ID),
	 foreign key (Owners_ID) references Owners
	 
	);

create table Book
	(ISBN				varchar(10), 
	 Book_title			varchar(50), 
	 Author_name		varchar(50), 
	 Gernre				varchar(10),
	 Price				numeric(4,0),
	 Publisher_ID		numeric(4,0),
	 Store_ID			numeric(4,0),
	 Banking_account		numeric(10),
	 Percentage	    	numeric(2),
	 Sold			numeric(1),			                                  
	 primary key (ISBN),
	 foreign key (Banking_account, Publisher_ID) references Publisher,
	 foreign key (Store_ID) references Store
	);

create table Users
	(
	Password			varchar(10), 
	Account			varchar(10), 
	Gender			varchar(10), 
	User_ID			numeric(4,0), 
	 User_name			varchar(20), 
	 primary key (User_ID)
	);

create table Orders
	(Orders_number			numeric(10), 
	 Billing_address		varchar(10),
	 Shipping_address		varchar(10),
	 Users_ID				numeric(4,0), 
	 Status				varchar(20),			
	 primary key (Orders_number),
	 foreign key (Users_ID) references Users
	);


