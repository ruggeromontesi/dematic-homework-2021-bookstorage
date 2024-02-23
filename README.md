# bookstorage
Project for the interview with Dematic in March 2021

How to create a book:
curl -X 'POST'  'http://localhost:8080/books/create' -H "Content-Type: application/json"   -d '{
 "title": "titleA",
 "author": "autorA",
 "barcode" : "10001",
 "quantity" : 5,
 "price": 40.5
 }'
