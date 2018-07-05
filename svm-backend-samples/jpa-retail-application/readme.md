# Sample retail application.  
  This project represents basic usage of modules and use cases in real life retail application.
  
## Requirements
Application should be built in micro service manner.

### Account service

 1. Authentication:
     1. Stateless JWT with asymmetric key;
	 2. Short lived access token with long lived refresh token. Auto issued access token for web application.
	 3. Windows AD sso (kerberos);
	 4. Second factor;
	 5. OAuth 2 authentication with side providers(facebook, github);
 2. Registration with email and phone number;
 3. Messaging with AMQP broker (new user registered, modified, etc);
 4. API:
	 1. OAuth2 client registration by regular user;
	 2. User management by admin.

### Purchase service

1. Purchase order;
2. Purchase;
3. Procurement;
4. Vendor invoice;

### Inventory service

1. Product management;
2. Warehouse management;
3. Stock order;
4. Stock inquiry;
5. Transfer;
6. Stock counts report;

### Selling service

1. Selling;
2. Price list;
3. Customer invoice;
4. Shipping;

### Financial service

1. Payments;
2. Accounts payable / Accounts receivable report;

