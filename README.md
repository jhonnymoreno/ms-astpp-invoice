# ms-astpp-invoice
- Micro-service for invoice on Astpp software.
- The micro-service allows creating invoices for periods, generating a file in PDF format or retrieving the response data from the REST API.

## Run application
- mvn spring-boot:run

## Request example:
### Generate PDF
- localhost:8080/api/v1/invoice/accountNumber/generateInvoice?startDate=01/01/2021&endDate=10/01/2021

### Get invoice data
- localhost:8080/api/v1/invoice/accountNumber?startDate=01/01/2021&endDate=10/01/2021

## Pre-requisitos:
- Have configured Astpp database connection.



